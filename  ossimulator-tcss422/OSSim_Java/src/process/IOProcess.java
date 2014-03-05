package process;

import edu.uw.tcss422.types.GenericProcess;
import edu.uw.tcss422.types.ProcessType;

public class IOProcess extends GenericProcess {
	public IOProcess() {
		super(ProcessType.IO);
		// It has to make some system calls at some point, how are they being called?
		// I don't see it in the sample output in the instruction, is it required?
		triggerPoints = new int[10];
	}
}
