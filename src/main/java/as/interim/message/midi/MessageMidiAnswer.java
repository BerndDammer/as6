package as.interim.message.midi;

import java.util.LinkedList;
import java.util.List;

import as.functionchain.IC_FunctionChainElement;
import as.interim.message.MessageBase;

public class MessageMidiAnswer extends MessageBase {
	private static final long serialVersionUID = -5306495854021561680L;
	public enum CMD {
		ECHO_STATUS,
		ECHO_MESSAGE;
	}

	public List<String> inputNames = new LinkedList<>();
	public List<String> mappingNames;
	public String selectedInput;
	public String selectedMapping;
	public IC_FunctionChainElement.STATE state;
	public CMD cmd;
	public byte[] midiMessage;
}
