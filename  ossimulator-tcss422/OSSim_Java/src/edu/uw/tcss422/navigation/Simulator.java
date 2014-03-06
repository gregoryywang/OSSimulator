package edu.uw.tcss422.navigation;

import java.util.Scanner;

import edu.uw.tcss422.components.CPU;

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
		
		//Create CPU object
		CPU cpu = new CPU();
		cpu.start();
		try {
      cpu.join();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
		
		
	}

}