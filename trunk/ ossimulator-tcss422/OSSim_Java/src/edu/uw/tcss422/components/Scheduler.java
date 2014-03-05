package edu.uw.tcss422.components;

import java.util.Iterator;
import java.util.Map.Entry;

import edu.uw.tcss422.types.ProcessState;
import edu.uw.tcss422.types.SchedulePolicy;

public class Scheduler {
	
	private SchedulePolicy currentSchedule;
	
	public Scheduler(int scheduleInt) {
		if (scheduleInt == 1)
			currentSchedule = SchedulePolicy.ROUND_ROBIN;
		else if (scheduleInt == 2)
			currentSchedule = SchedulePolicy.PRIORITY;
		else
			currentSchedule = SchedulePolicy.LOTTERY;
	}
	
	public long getNextProcessID(PCBList currentList) {
		
		switch(currentSchedule) {
			case ROUND_ROBIN:
				return roundRobin(currentList);
				
			case PRIORITY:
				return priority(currentList);
				
			case LOTTERY:
				return lottery(currentList);
				
			default:
				return 0;
		}
	}

	private long roundRobin(PCBList currentList) {
		int currentPID = 0;	// Should maybe begin at 1?
		do {
			currentPID++;
		} while (currentList.getPCBList().get(currentPID).getState() == ProcessState.RUNNING);
		
		int nextPID = currentPID++;
		
		while (currentList.getPCBList().get(nextPID).getState() != ProcessState.BLOCKED) {
			nextPID = (nextPID++) % currentList.getPCBList().size();
		}
		
		return nextPID;
	}

	private long priority(PCBList currentList) {
		// TODO Auto-generated method stub
		return 0;
	}

	private long lottery(PCBList currentList) {
		int size = currentList.getPCBList().size();
		ProcessControlBlock pcb;
		do {
			pcb = currentList.getPCBList().get((int) (Math.random() * size));
		} while (pcb.getState() != ProcessState.BLOCKED);
		
		return pcb.getPid();
	}

}