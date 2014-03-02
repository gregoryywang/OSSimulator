package edu.uw.tcss422.components;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class SharedMemory {
	
	private ArrayList<ArrayBlockingQueue<Integer>> myMemory;
	
	public SharedMemory(int capacity, int numberOfPairs) {
		myMemory = new ArrayList<ArrayBlockingQueue<Integer>>(numberOfPairs);
	}
	
	public boolean increment(int index) {
		boolean isSuccessful = false;
		
		
		
		return isSuccessful;
	}
	
	public boolean decrement(int index) {
		boolean isSuccessful = false;
		
		return isSuccessful;
	}

}
