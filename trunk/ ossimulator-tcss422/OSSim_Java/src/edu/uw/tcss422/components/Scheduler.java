package edu.uw.tcss422.components;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

import edu.uw.tcss422.types.ProcessState;
import edu.uw.tcss422.types.SchedulePolicy;

public class Scheduler {
	
	private SchedulePolicy currentSchedule;
	
	private PCBList pcbList;
	
	private int currentPID = 0;
	
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
	 * Returns current scheduling policy.
	 * @returns Current scheduling policy.
	 */
	public SchedulePolicy getCurrentSchedulerPolicy() {
		return currentSchedule;
	}

	/**
	 * Finds the next processID following the round-robin scheduling policy.
	 * @return next processID to run
	 */
	private int roundRobin() {
		int size = pcbList.getPCBList().size();
		do {
			currentPID = ++currentPID % size;
		} while (pcbList.getPCBList().get(currentPID).getState() == ProcessState.RUNNING && currentPID != 0);
		// currentPID will get back to zero (because of mod operator) if no process is running.
		// Added second condition to prevent infinite loop.
		
		int nextPID = currentPID++ % size;
		
		while (pcbList.getPCBList().get(nextPID).getState() == ProcessState.BLOCKED) {
			nextPID = ++nextPID % size;
		}
		
		return nextPID;
	}

	/**
	 * Finds the next processID following the priority scheduling policy.
	 * @return next processID to run
	 */
	private int priority() {
		sortPriority sp = new sortPriority();
		Queue<ProcessControlBlock> priorityQueue = new PriorityQueue<ProcessControlBlock>(pcbList.getPCBList().size(), sp);
		Iterator<ProcessControlBlock> itr = pcbList.getPCBList().values().iterator();
		
		// Add all PCBs to queue
		while (itr.hasNext())
			priorityQueue.offer(itr.next());
		
		// Remove blocked PCBs until non-blocked PCB found
		while (priorityQueue.peek().getState() == ProcessState.BLOCKED)
			priorityQueue.remove();
		
		return priorityQueue.poll().getPid();
	}

	/**
	 * Finds the next processID following the lottery scheduling policy.
	 * @return next processID to run
	 */
	private int lottery() {
		int size = pcbList.getPCBList().size();
		ProcessControlBlock pcb;
		
		// Pick random until PCB that is not blocked is found
		do {
			pcb = pcbList.getPCBList().get((int) (Math.random() * size));
		} while (pcb.getState() != ProcessState.BLOCKED);
		
		return pcb.getPid();
	}
	
	/**
	 * Compares priorities for the priority scheduling policy.
	 */
	static class sortPriority implements Comparator<ProcessControlBlock> {

		@Override
		public int compare(ProcessControlBlock one, ProcessControlBlock two) {
			return one.getPriority() - two.getPriority();
		}
	}

}
