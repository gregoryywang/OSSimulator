package edu.uw.tcss422.components;

import edu.uw.tcss422.types.GenericProcess;
import edu.uw.tcss422.types.ProcessType;

public class CPU extends Thread {
  
  /**
   * Reference to PCBList.
   */
  private PCBList pcbList;
  
  /**
   * Reference Shared Memory.
   */
  private SharedMemory sharedMemory;
  
  /**
   * Reference to Scheduler.
   */
  private Scheduler scheduler;
  
  /**
   * Kill condition variable.
   */
  private boolean bKill = false;
  
  /**
   * Interrupted condition variable.
   */
  private boolean bInterrupted = false;
  
  public CPU(PCBList pcbList, SharedMemory sharedMemory, Scheduler scheduler) {
    this.pcbList = pcbList;
    this.sharedMemory = sharedMemory;
    this.scheduler = scheduler;
  }
  
  /**
   * Sets the kill condition variable of the CPU.
   */
  public synchronized void kill() {
	 bKill = true;
  }
  
  @Override
  public void run() {
	  int triggerPoint = 0;
	  int PC = 0;

	  while (bKill) {
		  //Get next process to run
		  int PID = scheduler.getNextProcessID(); //Temp until scheduler interface changes

		  //Retrieve PCB for current process
		  ProcessControlBlock pcb = pcbList.getPCB(PID);

		  //Get trigger point
		  triggerPoint = pcb.getProcess().getTriggerPoint();

		  //Get current PC
		  PC = pcb.getNextStep();

		  //Loop through all instructions and roll over when max instructions has been met.
		  while(triggerPoint != PC )
			  PC = (PC + 1) % (GenericProcess.MAX_INSTRUCTIONS - 1);

		  //Make system call based on process type
		  systemCall(PID);
	  }

  }
  
  /**
   * Helper method that dispatches system calls based on process type.
   * In our simulation, there is a one to one mapping of system call to process type.
   * @param type The process type of the requesting process.
   */
  private void systemCall(int PID) {
    
    ProcessType type = pcbList.getPCB(PID).getProcess().getProcessType();
    
    if( type == ProcessType.COMPUTE ) {
      
    } else if( type == ProcessType.CONSUMER ) {
    	//Example
    	updateSharedMemory();
    } else if( type == ProcessType.PRODUCER ) {
      
    } else if( type == ProcessType.IDLE ) {
      
    } else if( type == ProcessType.UI ) {
      
    }
  }
  
  //Example of a system call
  /**
   * The actual system call method.
   */
  private void updateSharedMemory() {
	  
  }
}
