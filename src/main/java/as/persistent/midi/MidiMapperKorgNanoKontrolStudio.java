package as.persistent.midi;

import as.backend.midi.MidiMapper;
import as.globals.Globals;

public class MidiMapperKorgNanoKontrolStudio extends MidiMapper {
	private static final byte[] potChannels = new byte[] {
			(byte)2,
			(byte)3,
			(byte)4,
			(byte)5,
			(byte)6,
			(byte)8,
			(byte)12,
			(byte)13,
			(byte)14,
			(byte)15,
			(byte)16,
			(byte)17,
			(byte)18,
			(byte)19
	};
	public MidiMapperKorgNanoKontrolStudio()
	{
		for( int bank = 0; bank < Globals.MAX_BANKS; bank ++)
		{
			for( int channel = 0; channel < Globals.MAX_POTS; channel ++)
			{
				potFilter[bank][channel] = new MidiFilter( (byte)( -80 + bank ), potChannels[channel]);
				switchFilter[bank][channel] = new MidiFilter( (byte)( -80 + bank), (byte)(21 + channel ),(byte)127);
			}
		}
	}
	@Override
	public String getName() {
		return "nanoKONTROL Studio IN";
	}
//	@Override
//	public String getName() {
//		return "nanoKONTROL Studio 1 CTRL";
//	}
}
