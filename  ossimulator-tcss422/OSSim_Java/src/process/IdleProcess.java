package process;

import edu.uw.tcss422.types.GenericProcess;
import edu.uw.tcss422.types.ProcessType;

public class IdleProcess extends GenericProcess {

  public IdleProcess() {
		super(ProcessType.IDLE);
		// It doesn't trigger any interupt 
		triggerPoint = 0;
	}

}
