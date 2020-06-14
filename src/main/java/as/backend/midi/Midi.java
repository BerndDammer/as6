package as.backend.midi;

import as.functionchain.IC_FunctionChainElement;
import as.functionchain.IC_FunctionChainElement.STATE;

public class Midi {
	
	private IC_FunctionChainElement.STATE state = STATE.NO_PARA;
	
	Midi()
	{
		state = STATE.HAS_PARA;
	}
}
