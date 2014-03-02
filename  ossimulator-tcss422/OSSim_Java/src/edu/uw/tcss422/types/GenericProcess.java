package edu.uw.tcss422.types;

import java.util.Random;

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
   * Default constructor for all processes.
   */
  public GenericProcess() {
    //Initialize triggerPoint to random number up to MAX_INSTRUCTIONS
    Random random = new Random();
    triggerPoint = random.nextInt(MAX_INSTRUCTIONS);
  }
  
  /**
   * Single arg constructor.
   */
  public GenericProcess(ProcessType processType) {
    //Set the process type
    this.processType = processType;
  }
  
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
