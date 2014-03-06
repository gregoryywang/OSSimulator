package edu.uw.tcss422.components;

import java.util.HashMap;

import process.ComputeProcess;
import process.ConsumerProcess;
import process.IOProcess;
import process.ProducerProcess;
import process.UIProcess;
import edu.uw.tcss422.types.GenericProcess;

public class PCBList {
	
	private HashMap<Integer, ProcessControlBlock> pcbList;
	
	PCBList(int UIprocesses, int calculatingProcesses, int ProdConsumProcesses, int IOprocesses) {
		pcbList = new HashMap<Integer, ProcessControlBlock>();
		int pid = 1; // Usually PID 0 is for scheduler.
		int mutex = 0; // The memory location that the producer-consumer pair looking at.
					   // A pair share the same mutex number.
		for (int i = 0; i < UIprocesses; i++) {
			GenericProcess ui = new UIProcess();
			pcbList.put(pid, new ProcessControlBlock(pid, ui));
			pid++;
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
			mutex++;
		}
		for (int i = 0; i < IOprocesses; i++) {
			GenericProcess io = new IOProcess();
			pcbList.put(pid, new ProcessControlBlock(pid, io));
			pid++;
		}
		
	}
	
	public HashMap<Integer, ProcessControlBlock> getPCBList() {
		return pcbList;
	}
	
	public ProcessControlBlock getPCB(int PID) {
		return pcbList.get(PID);
	}
	
	
}
