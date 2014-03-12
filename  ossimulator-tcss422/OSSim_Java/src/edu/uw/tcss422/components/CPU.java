package edu.uw.tcss422.components;

import edu.uw.tcss422.types.GenericProcess;
import edu.uw.tcss422.types.ProcessState;
import edu.uw.tcss422.types.ProcessType;
import edu.uw.tcss422.types.SchedulePolicy;

public class CPU extends Thread {

	/**
	 * Reference to PCBList.
	 */
	private PCBList pcbList;

	/**
	 * Reference Shared Memory.
	 */
	private SharedMemory sharedMemory;

	/**
	 * Reference to Scheduler.
	 */
	private Scheduler scheduler;

	/**
	 * Kill condition variable.
	 */
	private boolean bKill = true;

	/**
	 * Interrupted condition variable.
	 */
	private boolean bInterrupted = false;
	
	/**
	 * PC counter.
	 */
	private int PC = 0;
	
	/**
	 * Collection of IO Devices available to CPU. 
	 * 0: keyboard; 1: disk;
	 */
	private IODevice[] devices;

	public CPU(PCBList pcbList, SharedMemory sharedMemory, Scheduler scheduler) {
		this.pcbList = pcbList;
		this.sharedMemory = sharedMemory;
		this.scheduler = scheduler;
	}

	/**
	 * Sets the kill condition variable of the CPU.
	 */
	public synchronized void kill() {
		bKill = true;
	}

	@Override
	public void run() {
		int triggerPoint = 0;
		
		while (bKill) {

			//Get next process to run
			int PID = scheduler.getNextProcessID(); //Temp until scheduler interface changes

			//Retrieve PCB for current process
			ProcessControlBlock pcb = pcbList.getPCB(PID);

			//Get trigger point
			triggerPoint = pcb.getProcess().getTriggerPoint();

			//Get current PC
			PC = pcb.getNextStep();
			
			System.out.println("CPU now running Process ID " + pcb.getPid());
			
			//Loop through all instructions
			while(PC != GenericProcess.MAX_INSTRUCTIONS - 1) {

				if (PC == triggerPoint) {
					//Make system call based on process type
					systemCall(PID); 
					
					if (pcb.getState() == ProcessState.BLOCKED) {
						System.out.println("Process " + pcb.getPid() + " is now BLOCKED. Switching processes");
						break;
					}
					
				} else if (bInterrupted && scheduler.getCurrentSchedulerPolicy() == SchedulePolicy.ROUND_ROBIN) {
					System.out.println("Process " + pcb.getPid() + " was interrupted");
				    break;		
				} else if (scheduler.getCurrentSchedulerPolicy() == SchedulePolicy.LOTTERY 
				    || scheduler.getCurrentSchedulerPolicy() == SchedulePolicy.PRIORITY 
				    && PC == GenericProcess.MAX_INSTRUCTIONS - 1 ) { 
				    PC = 0; //Reset PC
				    System.out.println("Process " + pcb.getPid() + " completed");
				    break;
				}

				PC = (PC + 1) % (GenericProcess.MAX_INSTRUCTIONS - 1);
			}
			
			pcb.setNextStep(PC);
		}
	}

	/**
	 * This method will be called by IODevices.
	 * Pass in the last item from your list.
	 */
	public void IOinterupt(ProcessControlBlock pcb) {
    // Perform action for hardware interrupt
    // Then change the state of a blocked UIProcess to READY
 
		pcb.setState(ProcessState.READY);
		System.out.println("IO interrupt occurred. Process " + pcb.getPid() + " UNBLOCKED");
	}

	/**
	 * This method will be called by System Timer.
	 */
	public void timerInterupt() {
		  bInterrupted = true;
	}

	public void setIODevices(IODevice[] devices) {
	  this.devices = devices; 
	}
	
	/**
	 * Helper method that dispatches system calls based on process type.
	 * In our simulation, there is a one to one mapping of system call to process type.
	 * @param type The process type of the requesting process.
	 */
	private void systemCall(int PID) {
		ProcessControlBlock pcb = pcbList.getPCB(PID);

		ProcessType type = pcb.getProcess().getProcessType();
		
		System.out.println("System call occured. Process " + pcb.getPid() + " is a " + type.name());

		if( type == ProcessType.COMPUTE ) {
			// I thought it does nothing, but in the sample run it made system call to auxiliary.
			devices[1].addPCB(pcb);
			blockPCB(pcb);
		} else if( type == ProcessType.CONSUMER ) {
			// request resource
			requestResource(pcb);
		} else if( type == ProcessType.PRODUCER ) {
			// I think it always try to put stuff in the memory.
			putResource(pcb);
		} else if( type == ProcessType.IDLE ) {
			// It does nothing
		} else if( type == ProcessType.UI ) {
			// Request resource from IODevice by adding itself to the list in the device.
			devices[0].addPCB(pcb);
			blockPCB(pcb);
		}
	}

	/**
	 * The system call to request a resource from shared memory.
	 */
	private void requestResource(ProcessControlBlock pcb) {
		int mutex = pcb.getMutex();
		if (sharedMemory.isEmpty(mutex)) {
			// resource not available
			blockPCB(pcb);
		} else {
			// resource available
			pcb.setState(ProcessState.RUNNING);
			sharedMemory.pop(pcb.getMutex());
			System.out.println("Value taken from memory location " + mutex);
		}
	}

	private void putResource(ProcessControlBlock pcb) {
		int mutex = pcb.getMutex();
		if (!sharedMemory.isEmpty(mutex)) {
			sharedMemory.push(mutex, 1); // 1 represents dummy data
			System.out.println("Value added to memory location " + mutex);
			// Seems like it has to update the consumer from BLOCKED to READY
			pcbList.getPCBbyMutex(mutex).setState(ProcessState.READY);
		}
	}
	
	private void blockPCB(ProcessControlBlock pcb) {
		pcb.setState(ProcessState.BLOCKED);
		pcb.setNextStep(PC);
		System.out.println("Process " + pcb.getPid() + " BLOCKED");
	}
}
