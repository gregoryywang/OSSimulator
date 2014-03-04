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
	
	public long getNextProcessID() {
		
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

	private long roundRobin() {
		// TODO Auto-generated method stub
		return 0;
	}

	private long priority() {
		// TODO Auto-generated method stub
		return 0;
	}

	private long lottery() {
		// TODO Auto-generated method stub
		return 0;
	}

}
