package edu.uw.tcss422.components;

import process.IdleProcess;
import edu.uw.tcss422.types.GenericProcess;

public class CPU extends Thread {
  
  @Override
  public void run() {
    //Initialized with Idle Process
    GenericProcess currentProcess = new IdleProcess();
    
  }
}
