package as.interim.message.midi;

import java.util.List;

import as.functionchain.IC_FunctionChainElement;
import as.interim.message.MessageBase;

public class MessageMidiControl extends MessageBase {
	private static final long serialVersionUID = 982670355066155211L;
	public List<String> inputNames;
	public List<String> mappingNames;
	public String selectedInput;
	public String selectedMapping;
	public CMD cmd;
	public IC_FunctionChainElement.STATE state;

	public enum CMD {
		REQUEST_INFO, SET_STATE, SET_INPUT;
	}
}
