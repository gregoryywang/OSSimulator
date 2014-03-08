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
		int PC = 0;

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
				} else if (bInterrupted) {
					// Perform action for hardware interupt
					// Then change the state of a blocked UIProcess to READY
					// Seems like the IODevice should have something that can be paired with a UIProcess 

				} else if(scheduler.getCurrentSchedulerPolicy() == SchedulePolicy.LOTTERY 
						|| scheduler.getCurrentSchedulerPolicy() == SchedulePolicy.PRIORITY
						&& PC == GenericProcess.MAX_INSTRUCTIONS - 1){
					break;
				}

				PC = (PC + 1) % (GenericProcess.MAX_INSTRUCTIONS - 1);
			}
		}
	}

	/**
	 * This method will be called by IODevices.
	 */
	public void IOinterupt() {
		bInterrupted = true;
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
			requestResource(pcb);
		}
	}

	/**
	 * The system call to request a resource from shared memory.
	 */
	private void requestResource(ProcessControlBlock pcb) {
		if (sharedMemory.isEmpty(pcb.getMutex())) {
			// resource not available
			pcb.setState(ProcessState.BLOCKED);
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
