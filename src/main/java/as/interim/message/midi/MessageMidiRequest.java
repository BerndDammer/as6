package as.interim.message.midi;

public class MessageMidiRequest extends MessageMidiBase {
	private static final long serialVersionUID = -7060886345423984387L;

	public String selectedInput;
	public String selectedMapping;

	public CMD cmd;

	public enum CMD {
		SCAN, SET_INPUT, SET_MAPPING, ACTIVATE, DEACTIVATE, SET_ECHO_ON, SET_ECHO_OFF, SET_MESSAGE_ECHO_OFF, SET__MESSAGE_ECHO_ON;
	}
}
