package process;

import edu.uw.tcss422.types.GenericProcess;
import edu.uw.tcss422.types.ProcessType;

/**
 * The process class of compute class.
 * This type of process doesn't actually do anything in the simulator.
 * @author Oscar Hong
 *
 */
public class ComputeProcess extends GenericProcess {
	/**
	 * Construct a new compute process.
	 */
	public ComputeProcess() {
		// Because it doesn't do anything, nothing else is required.
		super(ProcessType.COMPUTE);
	}
}
