package process;

import java.util.Arrays;
import java.util.Random;

import edu.uw.tcss422.types.GenericProcess;
import edu.uw.tcss422.types.ProcessType;

public class UIProcess extends GenericProcess {
	public UIProcess() {
		super(ProcessType.UI);
		// Randomly generate some UI system calls at random timing. Might be changed. 
		Random r = new Random();
		// Let say we generate at least 1 and at most 10 calls
		triggerPoints = new int[r.nextInt(10) + 1];
		
		for (int i = 0; i < triggerPoints.length; i++) {
			int triggerPoint;
			
			// Generate a trigger point that isn't is the list yet
			do {
				triggerPoint = r.nextInt(getNumOfInstructions());
			} while (!Arrays.asList(triggerPoints).contains(triggerPoint));
			
			// and put it in the array
			triggerPoints[i] = triggerPoint;
		}
		
		// and finally sort the array.
		Arrays.sort(triggerPoints);
	}
}
