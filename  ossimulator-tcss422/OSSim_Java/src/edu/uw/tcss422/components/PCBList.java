package edu.uw.tcss422.components;

import java.util.HashMap;
import java.util.Random;

import process.ComputeProcess;
import process.ConsumerProcess;
import process.ProducerProcess;
import process.UIProcess;
import edu.uw.tcss422.types.GenericProcess;

public class PCBList {
	
	private HashMap<Integer, ProcessControlBlock> pcbList;
	
	private Random random = new Random();
	
	private static int HIGHEST_PRIORITY = 0;
	
	private static int LOWEST_PRIORITY = 9;
	
	
	// Only ConsumerProcess and UIProcess will be in here.
	private HashMap<Integer, ProcessControlBlock> prodMutexMap;
	private HashMap<Integer, ProcessControlBlock> conMutexMap;
	
	public PCBList(int UIprocesses, int calculatingProcesses, int ProdConsumProcesses) {
		pcbList = new HashMap<Integer, ProcessControlBlock>();
		prodMutexMap = new HashMap<Integer, ProcessControlBlock>();
		conMutexMap = new HashMap<Integer, ProcessControlBlock>();
		int pid = 1; // Usually PID 0 is for scheduler.
		int mutex = 0; // The memory location that the producer-consumer pair looking at.
					   // A pair share the same mutex number.
		
		for (int i = 0; i < UIprocesses; i++) {
			GenericProcess ui = new UIProcess();
			ProcessControlBlock uiPCB = new ProcessControlBlock(pid, ui);
			setRandomPriority(uiPCB);
			pcbList.put(pid, uiPCB);
			//mutexMap.put(mutex, uiPCB);
			pid++;
			//mutex++;
		}
		
		for (int i = 0; i < calculatingProcesses; i++) {
			GenericProcess compute = new ComputeProcess();
			ProcessControlBlock cPCB = new ProcessControlBlock(pid, compute);
			setRandomPriority(cPCB);
			pcbList.put(pid, cPCB);
			pid++;
		}
		
		for (int i = 0; i < ProdConsumProcesses; i++) {
			GenericProcess producer = new ProducerProcess();
			GenericProcess consumer = new ConsumerProcess();
			ProcessControlBlock producerPCB = new ProcessControlBlock(pid++, producer);
			ProcessControlBlock consumerPCB = new ProcessControlBlock(pid++, consumer);
			producerPCB.setMutex(mutex);
			consumerPCB.setMutex(mutex);
			setRandomPriority(producerPCB);
			setRandomPriority(consumerPCB);
			pcbList.put(producerPCB.getPid(), producerPCB);
			pcbList.put(consumerPCB.getPid(), consumerPCB);
			prodMutexMap.put(producerPCB.getPid(), producerPCB);
			conMutexMap.put(consumerPCB.getPid(), consumerPCB);
			mutex++;
		}
		
		/*
		for (int i = 0; i < IOprocesses; i++) {
			GenericProcess io = new IOProcess();
			pcbList.put(pid, new ProcessControlBlock(pid, io));
			pid++;
		}*/
		
	}
	
	public HashMap<Integer, ProcessControlBlock> getPCBList() {
		return pcbList;
	}
	
	public ProcessControlBlock getPCB(int PID) {
		return pcbList.get(PID);
	}
	
	public ProcessControlBlock getprodPCBbyMutex(int mutex) {
		return prodMutexMap.get(mutex);
	}
	
	public ProcessControlBlock getconPCBbyMutex(int mutex) {
		return conMutexMap.get(mutex);
	}
	
	private void setRandomPriority(ProcessControlBlock pcb) {
		pcb.setPriority(random.nextInt(LOWEST_PRIORITY));
	}
}
