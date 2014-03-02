package edu.uw.tcss422.types;

public abstract class GenericProcess {
  
  /**
   * The number of instructions.
   */
  public static final int MAX_INSTRUCTIONS = 10000;
  
  /**
   * The process type.
   */
  private ProcessType processType;
  
  /**
   * Trigger point. The address where the actual service call is made.
   */
  private int triggerPoint;
  
  /**
   * Returns process's trigger point.
   * @return The process's trigger point.
   */
  public int getTriggerPoint() {
    return triggerPoint;
  }
  
  /**
   * Returns process's process type.
   * @return The process's process type.
   */
  public ProcessType getProcessType() {
    return processType;
  }
  
  /**
   * Simulates system call made by current process. Actual simulation code goes here.
   */
  public abstract void service();
  
  

}
