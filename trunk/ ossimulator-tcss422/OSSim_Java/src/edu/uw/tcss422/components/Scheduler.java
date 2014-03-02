package edu.uw.tcss422.components;

public class Scheduler {
	
	private enum schedule {ROUND_ROBIN, LOTTERY, PRIORITY};
	
	private schedule mySchedule;
	
	public Scheduler(int scheduleInt) {
		if (scheduleInt == 1)
			mySchedule = schedule.ROUND_ROBIN;
		else if (scheduleInt == 2)
			mySchedule = schedule.PRIORITY;
		else
			mySchedule = schedule.LOTTERY;
	}
	
	public long getNextProcessID(long currentPID) {
		
		switch(mySchedule) {
			case ROUND_ROBIN:
				return 1;
				
			case PRIORITY:
				return 2;
				
			case LOTTERY:
				return 3;
				
			default:
				return 0;
		}
	}

}
