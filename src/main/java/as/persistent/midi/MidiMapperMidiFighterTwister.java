package as.persistent.midi;

import as.backend.midi.MidiMapper;
import as.globals.Globals;

public class MidiMapperMidiFighterTwister extends MidiMapper {

	public MidiMapperMidiFighterTwister()
	{
		potFilter = new MidiFilter[Globals.MAX_BANKS][]; 
		for( int bank = 0; bank < Globals.MAX_BANKS; bank ++)
		{
			potFilter[bank] = new MidiFilter[Globals.MAX_POTS];
			for( int channel = 0; channel < Globals.MAX_POTS; channel ++)
			{
				potFilter[bank][channel] = new MidiFilter( (byte)( -80 ), (byte)(channel + bank * Globals.MAX_POTS));
			}
		}
	}
	@Override
	public String getName() {
		return "Midi Fighter Twister";
	}
}
