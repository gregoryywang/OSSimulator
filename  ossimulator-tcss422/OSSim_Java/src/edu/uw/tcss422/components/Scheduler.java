package edu.uw.tcss422.components;

import edu.uw.tcss422.types.ProcessState;
import edu.uw.tcss422.types.SchedulePolicy;

public class Scheduler {
	
	private SchedulePolicy currentSchedule;
	
	private PCBList pcbList;
	
	public Scheduler(int scheduleInt, PCBList pcbList) {
	  this.pcbList = pcbList;
	  
		if (scheduleInt == 1)
			currentSchedule = SchedulePolicy.ROUND_ROBIN;
		else if (scheduleInt == 2)
			currentSchedule = SchedulePolicy.PRIORITY;
		else
			currentSchedule = SchedulePolicy.LOTTERY;
	}
	
	/**
	 * Determines the next process to run according to the current scheduler policy.
	 * @return The processID of the next process for the CPU to run.
	 */
	public int getNextProcessID() {
		switch(currentSchedule) {
			case ROUND_ROBIN:
				return roundRobin();
				
			case PRIORITY:
				return priority();
				
			case LOTTERY:
				return lottery();
				
			default:
				return 0;
		}
	}

	/**
	 * Finds the next processID following the round-robin scheduling policy.
	 * @return next processID to run
	 */
	private int roundRobin() {
		int currentPID = 0;	// Should maybe begin at 1?
		do {
			currentPID = currentPID++ % pcbList.getPCBList().size();
		} while (pcbList.getPCBList().get(currentPID).getState() == ProcessState.RUNNING && currentPID != 0);
		// currentPID will get back to zero (because of mod operator) if no process is running.
		// Added second condition to prevent infinite loop.
		
		int nextPID = currentPID++;
		
		while (pcbList.getPCBList().get(nextPID).getState() == ProcessState.BLOCKED) {
			nextPID = nextPID++ % pcbList.getPCBList().size();
		}
		
		return nextPID;
	}

	/**
	 * Finds the next processID following the priority scheduling policy.
	 * @return next processID to run
	 */
	private int priority() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Finds the next processID following the lottery scheduling policy.
	 * @return next processID to run
	 */
	private int lottery() {
		int size = pcbList.getPCBList().size();
		ProcessControlBlock pcb;
		do {
			pcb = pcbList.getPCBList().get((int) (Math.random() * size));
		} while (pcb.getState() != ProcessState.BLOCKED && pcb.getState() != ProcessState.INTERRUPTED);
		
		return pcb.getPid();
	}

}
