package edu.uw.tcss422.components;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class SystemTimer {
	
	private boolean debugFlag = false;

	/**
	 * @param currentCPU A reference to the CPU to call its interrupt method.
	 * @param min The minimum delay time in milliseconds to be randomly generated.
	 * @param max The maximum delay time in milliseconds to be randomly generated.
	 * @param debugFlag Will print debug info to console if set to true.
	 */
	public SystemTimer(CPU currentCPU, int min, int max, boolean debugFlag) {
		
		this.debugFlag = debugFlag;
		
		Random random = new Random();
		long period = random.nextInt((max - min) + 1) + min;
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new GenerateInterrupts(), 0, Long.valueOf(period));
		
		if(debugFlag) {
			System.out.println("System timer delay period is set to " + period + " milliseconds.");
		}

	}
	
	/**
	 * Inner class that will be called to run when triggered by the Timer.
	 * @author yongyuwang
	 *
	 */
	class GenerateInterrupts extends TimerTask {
		public void run() {
			// calls interrupt method in CPU
			//TODO: implement interrupt method
			if(debugFlag) {
				System.out.println("Timer has triggered a CPU interrupt.");
			}
		}
	}
}
