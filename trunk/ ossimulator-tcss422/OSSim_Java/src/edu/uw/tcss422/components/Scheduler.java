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
