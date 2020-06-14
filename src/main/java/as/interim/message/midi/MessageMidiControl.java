package as.interim.message.midi;

public class MessageMidiControl extends MessageMidiBase {
	private static final long serialVersionUID = -7060886345423984387L;
	public CMD cmd;

	public enum CMD {
		REQUEST_INFO, SET_STATE, SET_INPUT;
	}
}
