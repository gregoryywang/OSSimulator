package edu.uw.tcss422.navigation;

import java.util.Scanner;

import edu.uw.tcss422.components.CPU;
import edu.uw.tcss422.components.IODevice;
import edu.uw.tcss422.components.PCBList;
import edu.uw.tcss422.components.Scheduler;
import edu.uw.tcss422.components.SharedMemory;
import edu.uw.tcss422.components.SystemTimer;

public class Simulator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the number of UI processes you would like: ");
		int UIprocesses = scan.nextInt();
		System.out.print("Enter the number of calculating processes you would like: ");
		int calculatingProcesses = scan.nextInt();
		System.out.print("Enter the number of producer/consumer pairs you would like: ");
		int ProdConsumProcesses = scan.nextInt();
		int schedulerPolicy = 0;
		do {
			System.out.print("Please indicate which scheduling policy (1 for round-robin, 2 for priority, 3 for lottery): ");
			schedulerPolicy = scan.nextInt();
		} while (schedulerPolicy < 1 || schedulerPolicy > 3);
		scan.close();
		
		//Create PCB object
		PCBList pcbList = new PCBList(UIprocesses, calculatingProcesses, ProdConsumProcesses);
		
		//Create Scheduler object
		Scheduler scheduler = new Scheduler(schedulerPolicy, pcbList);
		
		//Shared Memory
		SharedMemory memory = new SharedMemory(3, ProdConsumProcesses);
		
		//Create CPU object
		CPU cpu = new CPU(pcbList, memory, scheduler);
		
		//Create IO Devices
		IODevice[] ioDevices = new IODevice[]{ new IODevice(cpu, "Keyboard", 10, 20),
		                             new IODevice(cpu, "Disk", 5, 10) };
		
		//Create System Timer
		@SuppressWarnings("unused")
		SystemTimer timer = new SystemTimer(cpu, 10);
		
		//Set IO Devices References in CPU
		cpu.setIODevices(ioDevices);
		
		//Start IO Devices
		ioDevices[0].start();
		ioDevices[1].start();
		
		//Start CPU
		cpu.start();
		
		try {
			cpu.join();
			ioDevices[0].join();
			ioDevices[1].join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
}