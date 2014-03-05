package edu.uw.tcss422.components;

import java.util.ArrayList;

import process.ComputeProcess;
import process.ConsumerProcess;
import process.IOProcess;
import process.ProducerProcess;
import process.UIProcess;
import edu.uw.tcss422.types.GenericProcess;

public class PCBList {
	
	private ArrayList<ProcessControlBlock> pcbList;
	
	PCBList(int UIprocesses, int calculatingProcesses, int ProdConsumProcesses, int IOprocesses) {
		pcbList = new ArrayList<ProcessControlBlock>();
		int pid = 1; // Usually PID 0 is for scheduler.
		int mutex = 0; // The memory location that the producer-consumer pair looking at.
					   // A pair share the same mutex number.
		for (int i = 0; i < UIprocesses; i++) {
			GenericProcess ui = new UIProcess();
			pcbList.add(new ProcessControlBlock(pid++, ui));
		}
		for (int i = 0; i < calculatingProcesses; i++) {
			GenericProcess compute = new ComputeProcess();
			pcbList.add(new ProcessControlBlock(pid++, compute));
		}
		for (int i = 0; i < ProdConsumProcesses; i++) {
			GenericProcess producer = new ProducerProcess();
			GenericProcess consumer = new ConsumerProcess();
			ProcessControlBlock producerPCB = new ProcessControlBlock(pid++, producer);
			ProcessControlBlock consumerPCB = new ProcessControlBlock(pid++, consumer);
			producerPCB.setMutex(mutex);
			consumerPCB.setMutex(mutex);
			pcbList.add(producerPCB);
			pcbList.add(consumerPCB);
			mutex++;
		}
		for (int i = 0; i < IOprocesses; i++) {
			GenericProcess io = new IOProcess();
			pcbList.add(new ProcessControlBlock(pid++, io));
		}
		
	}
	
	public ArrayList<ProcessControlBlock> getPCBList() {
		return pcbList;
	}
}
