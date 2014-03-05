package edu.uw.tcss422.types;

import java.util.Random;

public abstract class GenericProcess {
  
  /**
   * The maximum number of instructions.
   */
  public static final int MAX_INSTRUCTIONS = 10000;
  
  /**
   * The minimum number of instructions.
   */
  public static final int MIN_INSTRUCTIONS = 1000;
  
  /**
   * The process type.
   */
  private ProcessType processType;
  
  /**
   * The number of instructions of this process.
   */
  private int numOfInstructions;
  
  /**
   * The address where the actual service call is made. <br>
   * <br>
   * Each process should have different instruction addresses that perform system calls. <br> 
   * This should be initialized by child Process class according to the ProcessType. <br>
   * For example, I/O system calls, inter-process communication request.
   */
  protected int triggerPoint;
  
  /**
   * Single arg constructor.
   */
  public GenericProcess(ProcessType processType) {
    //Set the process type
    this.processType = processType;
    Random r = new Random();
    // Generate a number between 1000 to 10000 inclusively.
    numOfInstructions = r.nextInt(MAX_INSTRUCTIONS - MIN_INSTRUCTIONS + 1) + MIN_INSTRUCTIONS; 
    triggerPoint = r.nextInt(numOfInstructions);
  }
  
  /**
   * Returns process's trigger point.
   * @return The process's trigger point.
   */
  public int getTriggerPoints() {
    return triggerPoint;
  }
  
  /**
   * Returns process's process type.
   * @return The process's process type.
   */
  public ProcessType getProcessType() {
    return processType;
  }
  
  public int getNumOfInstructions() {
	  return numOfInstructions;
  }
}
