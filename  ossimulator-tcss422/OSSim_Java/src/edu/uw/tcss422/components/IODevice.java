
package edu.uw.tcss422.components;

import java.util.Random;

public class IODevice implements Runnable {
	
	private CPU currentCPU;
	
	private String deviceType;
	
	private int max, min;
	
	private boolean debugFlag = false;
	
	public IODevice(CPU currentCPU, String deviceType, int max, int min) {
		this.currentCPU = currentCPU;
		this.deviceType = deviceType;
		this.max = max;
		this.min = min;
	}

	@Override
	public void run() {
		Random random = new Random();
		while(true) {
			int delayPeriod = random.nextInt((max - min) + 1) + min;
			try {
				Thread.sleep(Long.valueOf(delayPeriod));
			} catch (InterruptedException e) {
				System.out.println("IODevices thread interrupted! This is not supposed to happen.");
				e.printStackTrace();
			}
			
			//TODO: call interrupt method in CPU.
			
			if(debugFlag = true) {
				System.out.println("IODevice generated an interrupt. Delay time = " + delayPeriod +
						" milliseconds");
			}
			
		}
	}
	
}
