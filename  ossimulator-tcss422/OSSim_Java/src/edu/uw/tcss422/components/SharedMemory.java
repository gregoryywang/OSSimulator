package edu.uw.tcss422.components;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * The shared memory class which contains all of the memory used by the
 * intercommunicating process pairs.
 */
public class SharedMemory {
	
	private ArrayBlockingQueue<Integer>[] myMemory;
	
	@SuppressWarnings("unchecked")
	public SharedMemory(int capacity, int numberOfPairs) {
		myMemory = (ArrayBlockingQueue<Integer>[]) new ArrayBlockingQueue<?>[numberOfPairs];
		
		for (int i = 0; i < numberOfPairs; i++) {
			myMemory[i] = new ArrayBlockingQueue<Integer>(capacity);
		}
	}
	
	/*
	 * Returns a boolean whether or not the queue is empty.
	 */
	public boolean isEmpty(int index) {
		boolean isEmpty;
		
		isEmpty = myMemory[index].isEmpty();
		
		return isEmpty;
	}
	
	/*
	 * Adds the value to the tail of the queue and returns a boolean.
	 */
	public boolean push(int index, int value) {
		boolean isSuccessful;
		
		isSuccessful = myMemory[index].offer(value);
		
		return isSuccessful;
	}
	
	/*
	 * Returns the head of the queue (or null if empty).
	 */
	public int pop(int index) {
		int output;
		
		output = myMemory[index].poll();
		
		return output;
	}

}
