package edu.uw.tcss422.components;

import java.util.HashMap;

import process.ComputeProcess;
import process.ConsumerProcess;
import process.ProducerProcess;
import process.UIProcess;
import edu.uw.tcss422.types.GenericProcess;

public class PCBList {
	
	private HashMap<Integer, ProcessControlBlock> pcbList;
	
	// Only ConsumerProcess and UIProcess will be in here.
	private HashMap<Integer, ProcessControlBlock> mutexMap;
	
	public PCBList(int UIprocesses, int calculatingProcesses, int ProdConsumProcesses) {
		pcbList = new HashMap<Integer, ProcessControlBlock>();
		mutexMap = new HashMap<Integer, ProcessControlBlock>();
		int pid = 1; // Usually PID 0 is for scheduler.
		int mutex = 0; // The memory location that the producer-consumer pair looking at.
					   // A pair share the same mutex number.
		
		for (int i = 0; i < UIprocesses; i++) {
			GenericProcess ui = new UIProcess();
			ProcessControlBlock uiPCB = new ProcessControlBlock(pid, ui);
			pcbList.put(pid, uiPCB);
			mutexMap.put(mutex, uiPCB);
			pid++;
			mutex++;
		}
		
		for (int i = 0; i < calculatingProcesses; i++) {
			GenericProcess compute = new ComputeProcess();
			pcbList.put(pid, new ProcessControlBlock(pid, compute));
			pid++;
		}
		
		for (int i = 0; i < ProdConsumProcesses; i++) {
			GenericProcess producer = new ProducerProcess();
			GenericProcess consumer = new ConsumerProcess();
			ProcessControlBlock producerPCB = new ProcessControlBlock(pid++, producer);
			ProcessControlBlock consumerPCB = new ProcessControlBlock(pid++, consumer);
			producerPCB.setMutex(mutex);
			consumerPCB.setMutex(mutex);
			pcbList.put(producerPCB.getPid(), producerPCB);
			pcbList.put(consumerPCB.getPid(), consumerPCB);
			mutexMap.put(consumerPCB.getPid(), consumerPCB);
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
	
	public ProcessControlBlock getPCBbyMutex(int mutex) {
		return mutexMap.get(mutex);
	}
}
