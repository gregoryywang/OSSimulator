package edu.uw.tcss422.components;

import process.IdleProcess;
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
  
  public CPU(PCBList pcbList, SharedMemory sharedMemory, Scheduler scheduler) {
    this.pcbList = pcbList;
    this.sharedMemory = sharedMemory;
    this.scheduler = scheduler;
  }
  
  @Override
  public void run() {
    int triggerPoint;
    
    while(true) {
      
      
    }

  }
  
  private void systemCall(ProcessType type) {
    
    if( type == ProcessType.COMPUTE ) {
      
    } else if( type == ProcessType.CONSUMER ) {
      
    } else if( type == ProcessType.PRODUCER ) {
      
    } else if( type == ProcessType.IDLE ) {
      
    } else if( type == ProcessType.UI ) {
      
    }
  }
}
