package as.backend.midi;

import javax.sound.midi.MidiMessage;

/**
 * maps 1 the max 16 midi events into the expected 16 pot channels
 * 
 * @author manni1user
 *
 */
public abstract class MidiMapper {

	protected MidiFilter potFilter[][]; // bank pot

	public enum RESULT {
		NONE, POT, SWITCH, BANKSWITCH;
	}

	public class MidiFilter {
		private final byte[] triggerData;

		public MidiFilter(final byte... triggerData) {
			this.triggerData = triggerData;
		}

		boolean isMatch(MidiMessage mm) {
			for (int i = 0; i < triggerData.length; i++) {
				if (triggerData[i] != mm.getMessage()[i]) {
					return false;
				}
			}
			return true;
		}
	}

	public class MidiResult {
		RESULT type = RESULT.NONE;
		int channel = 0;
		int bank = 0;
	}

	public abstract String getName();

	public MidiResult map(MidiMessage mm) {
		MidiResult midiResult = new MidiResult();
		return midiResult;
	}
}
