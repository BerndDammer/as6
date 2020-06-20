package as.persistent.midi;

import as.backend.midi.MidiMapper;
import as.globals.Globals;

public class MidiMapperLDP8 extends MidiMapper {

	public MidiMapperLDP8()
	{
		potFilter = new MidiFilter[Globals.MAX_BANKS][]; 
		for( int bank = 0; bank < Globals.MAX_BANKS; bank ++)
		{
			potFilter[bank] = new MidiFilter[Globals.MAX_POTS];
			for( int channel = 0; channel < 8; channel ++)
			{
				potFilter[bank][channel] = new MidiFilter( (byte)( -80 + bank), (byte)(channel+1));
			}
		}
	}
	@Override
	public String getName() {
		return "LDP8";
	}
}
