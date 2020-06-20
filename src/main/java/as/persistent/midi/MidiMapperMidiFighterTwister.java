package as.persistent.midi;

import as.backend.midi.MidiMapper;
import as.globals.Globals;

public class MidiMapperMidiFighterTwister extends MidiMapper {
	public MidiMapperMidiFighterTwister()
	{
		for( int bank = 0; bank < Globals.MAX_BANKS; bank ++)
		{
			bankFilter[bank] = new MidiFilter( (byte)( -77 ), (byte)(bank), (byte)127);
			for( int channel = 0; channel < Globals.MAX_POTS; channel ++)
			{
				potFilter[bank][channel] = new MidiFilter( (byte)( -80 ), (byte)(channel + bank * Globals.MAX_POTS));
				switchFilter[bank][channel] = new MidiFilter( (byte)( -79 ), (byte)(channel + bank * Globals.MAX_POTS),(byte)127);
			}
		}
	}
	@Override
	public String getName() {
		return "Midi Fighter Twister";
	}
}
