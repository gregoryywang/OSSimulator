package edu.uw.tcss422.components;

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
	
	public long getNextProcessID(long currentPID) {
		
		switch(currentSchedule) {
			case ROUND_ROBIN:
				return roundRobin(currentPID);
				
			case PRIORITY:
				return priority(currentPID);
				
			case LOTTERY:
				return lottery(currentPID);
				
			default:
				return 0;
		}
	}

	private long roundRobin(long currentPID) {
		// TODO Auto-generated method stub
		return 0;
	}

	private long priority(long currentPID) {
		// TODO Auto-generated method stub
		return 0;
	}

	private long lottery(long currentPID) {
		// TODO Auto-generated method stub
		return 0;
	}

}
