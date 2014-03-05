package edu.uw.tcss422.components;

import edu.uw.tcss422.types.GenericProcess;
import edu.uw.tcss422.types.ProcessState;

public class ProcessControlBlock {

	/**
	 * The process ID.
	 */
	private int pid;
	
	/**
	 * This is the step count that the CPU had gotten to when this process was preempted. (Like a PC register value).
	 */
	private int nextStep;
	
	/**
	 * The state of the process.
	 */
	private ProcessState state;
	
	/**
	 * The actual process.
	 */
	private GenericProcess process;
	
	/**
	 * Which mutex lock does it own. Can be used for producer-consumer pair.
	 */
	private int mutex;
	
	/**
	 * Construct a Process Control Block. Initially the step count is 0 and the ProcessState is Ready.
	 * @param pid the process ID
	 * @param process the process type
	 * @param mutex the mutex
	 */
	public ProcessControlBlock(int pid, GenericProcess process, int mutex) {
		this.pid = pid;
		this.nextStep = 0;
		this.state = ProcessState.READY;
		this.process = process;
		this.mutex = mutex;
	}
	
	/**
	 * Get the Process ID (pid).
	 * @return the process ID (pid)
	 */
	public int getPid() {
		return pid;
	}
	
	/**
	 * Get the next step. <br>
	 * This is the step count that the CPU had gotten to when this process was preempted. (Like a PC register value).
	 * @return the next step
	 */
	public int getNextStep() {
		return nextStep;
	}
	
	/**
	 * Get the state of the process.
	 * @return the state of the process
	 */
	public ProcessState getState() {
		return state;
	}
	
	/**
	 * Get the actual process
	 * @return the actual process
	 */
	public GenericProcess getProcess() {
		return process;
	}
	
	/**
	 * Get the mutex lock that this process own.
	 * @return the mutex lock that this process own
	 */
	public int getMutex() {
		return mutex;
	}
	
	/**
	 * Set the next step 
	 * @param nextStep the next step
	 */
	public void setNextStep(int nextStep) {
		this.nextStep = nextStep;
	}
	
	/**
	 * Set the process state
	 * @param state the process state
	 */
	public void setState(ProcessState state) {
		this.state = state;
	}
	
	/**
	 * Set a mutex.
	 * @param mutex a mutex
	 */
	public void setMutex(int mutex) {
		this.mutex = mutex;
	}
}
