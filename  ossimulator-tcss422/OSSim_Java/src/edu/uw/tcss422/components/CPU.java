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
			
			//Loop through all instructions
			while(PC != GenericProcess.MAX_INSTRUCTIONS - 1) {

				if (PC == triggerPoint) {
					//Make system call based on process type
					systemCall(PID); 
					
					if (pcb.getState() == ProcessState.BLOCKED)
					  break;
					
				} else if (bInterrupted && scheduler.getCurrentSchedulerPolicy() == SchedulePolicy.ROUND_ROBIN  ) {
				    break;		
				} else if (scheduler.getCurrentSchedulerPolicy() == SchedulePolicy.LOTTERY 
				    || scheduler.getCurrentSchedulerPolicy() == SchedulePolicy.PRIORITY 
				    && PC == GenericProcess.MAX_INSTRUCTIONS - 1 ) { 
				    PC = 0; //Reset PC
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
    // Perform action for hardware interupt
    // Then change the state of a blocked UIProcess to READY
 
		pcb.setState(ProcessState.READY);
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

		if( type == ProcessType.COMPUTE ) {
			// It does nothing
		} else if( type == ProcessType.CONSUMER ) {
			// request resource
			requestResource(pcb);
		} else if( type == ProcessType.PRODUCER ) {
			// I think it always try to put stuff in the memory.
			putResource(pcb);
		} else if( type == ProcessType.IDLE ) {
			// It does nothing
		} else if( type == ProcessType.UI ) {
			// Request resource from IODevice. Do we use the sharedMemory or other mechanism?
			// I'm assuming that we are using the sharedMemory here
//			requestResource(pcb); 
			// TODO Where can I get the IO device? 
		}
	}

	/**
	 * The system call to request a resource from shared memory.
	 */
	private void requestResource(ProcessControlBlock pcb) {
		if (sharedMemory.isEmpty(pcb.getMutex())) {
			// resource not available
			pcb.setState(ProcessState.BLOCKED);
			pcb.setNextStep(PC);
		} else {
			// resource available
			pcb.setState(ProcessState.RUNNING);
			sharedMemory.pop(pcb.getMutex());
		}
	}

	private void putResource(ProcessControlBlock pcb) {
		int mutex = pcb.getMutex();
		if (!sharedMemory.isEmpty(pcb.getMutex())) {
			sharedMemory.push(mutex, 1); // 1 represents dummy data
			// Seems like it has to update the consumer from BLOCKED to READY
			pcbList.getPCBbyMutex(mutex).setState(ProcessState.READY);
		}
	}
}
