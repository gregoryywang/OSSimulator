package edu.uw.tcss422.components;

import edu.uw.tcss422.types.ProcessState;

public class ProcessControlBlock {

	private int pid;
	
	private int nextStep;
	
	private ProcessState state;
	
	private Process process;
	
	private int mutex;
	
	public ProcessControlBlock(int pid, int nextStep, ProcessState state, Process process, int mutex) {
		this.pid = pid;
		this.nextStep = nextStep;
		this.state = state;
		this.process = process;
		this.mutex = mutex;
	}
	
	public int getPid() {
		return pid;
	}
	
	public int getNextStep() {
		return nextStep;
	}
	
	public ProcessState getState() {
		return state;
	}
	
	public Process getProcess() {
		return process;
	}
	
	public int getMutex() {
		return mutex;
	}
	
	public void setNextStep(int nextStep) {
		this.nextStep = nextStep;
	}
	
	public void setState(ProcessState state) {
		this.state = state;
	}
	
	public void setMutex(int mutex) {
		this.mutex = mutex;
	}
}
