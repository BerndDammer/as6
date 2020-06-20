package as.backend.midi;

import javax.sound.midi.MidiMessage;

import as.globals.Globals;

/**
 * maps 1 the max 16 midi events into the expected 16 pot channels
 * 
 * @author manni1user
 *
 */
public abstract class MidiMapper {

	protected MidiFilter potFilter[][]; // bank pot
	protected MidiFilter switchFilter[][]; // bank pot
	protected MidiFilter bankFilter[]; // bank 

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

	public MidiMapper()
	{
		potFilter = new MidiFilter[Globals.MAX_BANKS][]; 
		switchFilter = new MidiFilter[Globals.MAX_BANKS][]; 
		bankFilter = new MidiFilter[Globals.MAX_BANKS]; 

		for( int bank = 0; bank < Globals.MAX_BANKS; bank ++)
		{
			bankFilter[bank] = null;
			potFilter[bank] = new MidiFilter[Globals.MAX_POTS];
			switchFilter[bank] = new MidiFilter[Globals.MAX_POTS];
			for( int channel = 0; channel < Globals.MAX_POTS; channel ++)
			{
				potFilter[bank][channel] = null; 
				switchFilter[bank][channel] = null; 
			}
		}
	}

	public abstract String getName();

	public MidiResult map(MidiMessage mm) {
		MidiResult midiResult = new MidiResult();
		return midiResult;
	}
}
