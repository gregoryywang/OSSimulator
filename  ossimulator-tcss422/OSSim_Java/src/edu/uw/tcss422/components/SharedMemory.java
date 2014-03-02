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
	 * Adds the value to the stack and returns a boolean.
	 */
	public boolean push(int index, int value) {
		boolean isSuccessful;
		
		isSuccessful = myMemory[index].offer(value);
		
		return isSuccessful;
	}
	
	/*
	 * Returns the head of the stack (or null of stack is empty).
	 */
	public int pop(int index) {
		int output;
		
		output = myMemory[index].poll();
		
		return output;
	}

}
