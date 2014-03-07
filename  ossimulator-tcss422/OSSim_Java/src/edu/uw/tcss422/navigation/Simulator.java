package edu.uw.tcss422.navigation;

import java.util.Scanner;

import edu.uw.tcss422.components.CPU;
import edu.uw.tcss422.components.PCBList;
import edu.uw.tcss422.components.Scheduler;
import edu.uw.tcss422.components.SharedMemory;

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
		SharedMemory memory = new SharedMemory(1, ProdConsumProcesses);
		
		//Create CPU object
		CPU cpu = new CPU(pcbList, memory, scheduler);
		cpu.start();
		try {
			cpu.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

}