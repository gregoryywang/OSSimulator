/**
 * SystemTimer.java
 * Uses Timer to generate a TimerTask to call the interrupt method in the CPU at fixed intervals.
 * Intervals can either be random generated on creation within specified min and max limits or
 * be a fixed value passed through the constructor.
 * Note: since TimerTask is already threaded upon scheduling, no further action is necessary.
 * 
 * @author yongyuwang
 * @version NonSandbox 1-2
 */

package edu.uw.tcss422.components;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class SystemTimer {
	
	/**
	 * Displays debugging information when set.
	 */
	private boolean debugFlag = false;
	
	/**
	 * The delay period between interrupts. Can either be set manually or randomly generated.
	 */
	private long delayPeriod;
	
	/**
	 * Reference to the CPU object to call interrupts from.
	 */
	private CPU currentCPU;

	/**
	 * "Default" constructor that triggers CPU interrupt at a randomly generated interval.
	 * @param currentCPU A reference to the CPU to call its interrupt method.
	 * @param min The minimum delay time in milliseconds to be randomly generated.
	 * @param max The maximum delay time in milliseconds to be randomly generated.
	 * @param debugFlag Will print debug info to console if set to true.
	 */
	public SystemTimer(CPU currentCPU, int min, int max) {
		
		this.currentCPU = currentCPU;
		
		Random random = new Random();
		delayPeriod = random.nextInt((max - min) + 1) + min;
		
		Timer timer = new Timer();
		
		// The TimerTask GenerateInterrupts runs in its own thread once scheduled.
		timer.scheduleAtFixedRate(new TimerInterrupt(), 0, Long.valueOf(delayPeriod));
		
		if(debugFlag) {
			System.out.println("System timer delay period is set to " + delayPeriod + " milliseconds.");
		}

	}
	
	/**
	 * Alternative constructor that triggers CPU interrupt at specified interval.
	 * @param currentCPU A reference to the CPU to call its interrupt method.
	 * @param delayPeriod A fixed interval to generate interrupts.
	 * @param debugFlag Will print debug info to console if set to true.
	 */
	public SystemTimer(CPU currentCPU, int delayPeriod) {
		
		this.currentCPU = currentCPU;
		this.delayPeriod = delayPeriod;
		
		Timer timer = new Timer();
		
		// The TimerTask GenerateInterrupts runs in its own thread once scheduled.
		timer.scheduleAtFixedRate(new TimerInterrupt(), 0, Long.valueOf(this.delayPeriod));
		
		if(debugFlag) {
			System.out.println("System timer delay period is set to " + this.delayPeriod + " milliseconds.");
		}

	}
	
	/**
	 * Created to support "For purposes of this simulation, you can set all process quanta to the same value"
	 * as stated in project description.
	 * @return long delayPeriod The time delay interval in milliseconds.
	 */
	public long getTimeDelayInterval() {
		return delayPeriod;
	}
	
	/**
	 * Sets debug flag to enable diagnostic messages.
	 * @param value
	 */
	public void setDebugFlag(boolean value) {
		debugFlag = value;
	}
	
	
	/**
	 * Inner class that will be called to run when triggered. Extends TimerTask.
	 * @author yongyuwang
	 */
	class TimerInterrupt extends TimerTask {
		/**
		 * Calls on the interrupt() method in CPU to generate a interrupt.
		 */
		public void run() {
			// calls interrupt method in CPU
			//TODO: implement interrupt method
			if(debugFlag) {
				System.out.println("Timer has triggered a CPU interrupt.");
			}
		}
		
		/**
		 * Cancels the timer.
		 * @return True if this task is scheduled for one-time execution and has not yet run, or this task is scheduled 
		 * for repeated execution. Returns false if the task was scheduled for one-time execution and has already run, 
		 * or if the task was never scheduled, or if the task was already cancelled.
		 */
		public boolean stopTimer() {
			return this.cancel();
		}
	}
}
