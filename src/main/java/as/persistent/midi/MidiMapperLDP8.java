package as.persistent.midi;

import as.backend.midi.MidiMapper;
import as.globals.Globals;

public class MidiMapperLDP8 extends MidiMapper {

	public MidiMapperLDP8()
	{
		for( int bank = 0; bank < Globals.MAX_BANKS; bank ++)
		{
			bankFilter[bank] = new MidiFilter((byte)-64, (byte)bank );
			for( int channel = 0; channel < 8; channel ++)
			{
				potFilter[bank][channel] = new MidiFilter( (byte)( -80 + bank), (byte)(channel+1));
			}
		}
		switchFilter[0][0] = new MidiFilter( (byte)-112, (byte)36, (byte)127);
		switchFilter[0][1] = new MidiFilter( (byte)-112, (byte)37, (byte)127);
		switchFilter[0][2] = new MidiFilter( (byte)-112, (byte)38, (byte)127);
		switchFilter[0][3] = new MidiFilter( (byte)-112, (byte)39, (byte)127);
		switchFilter[0][4] = new MidiFilter( (byte)-112, (byte)40, (byte)127);
		switchFilter[0][5] = new MidiFilter( (byte)-112, (byte)41, (byte)127);
		switchFilter[0][6] = new MidiFilter( (byte)-112, (byte)42, (byte)127);
		switchFilter[0][7] = new MidiFilter( (byte)-112, (byte)43, (byte)127);
		switchFilter[1][0] = new MidiFilter( (byte)-111, (byte)35, (byte)127);
		switchFilter[1][1] = new MidiFilter( (byte)-111, (byte)36, (byte)127);
		switchFilter[1][2] = new MidiFilter( (byte)-111, (byte)42, (byte)127);
		switchFilter[1][3] = new MidiFilter( (byte)-111, (byte)39, (byte)127);
		switchFilter[1][4] = new MidiFilter( (byte)-111, (byte)37, (byte)127);
		switchFilter[1][5] = new MidiFilter( (byte)-111, (byte)38, (byte)127);
		switchFilter[1][6] = new MidiFilter( (byte)-111, (byte)46, (byte)127);
		switchFilter[1][7] = new MidiFilter( (byte)-111, (byte)44, (byte)127);
		switchFilter[2][0] = new MidiFilter( (byte)-110, (byte)60, (byte)127);
		switchFilter[2][1] = new MidiFilter( (byte)-110, (byte)62, (byte)127);
		switchFilter[2][2] = new MidiFilter( (byte)-110, (byte)64, (byte)127);
		switchFilter[2][3] = new MidiFilter( (byte)-110, (byte)65, (byte)127);
		switchFilter[2][4] = new MidiFilter( (byte)-110, (byte)67, (byte)127);
		switchFilter[2][5] = new MidiFilter( (byte)-110, (byte)69, (byte)127);
		switchFilter[2][6] = new MidiFilter( (byte)-110, (byte)71, (byte)127);
		switchFilter[2][7] = new MidiFilter( (byte)-110, (byte)72, (byte)127);
		switchFilter[3][0] = new MidiFilter( (byte)-109, (byte)36, (byte)127);
		switchFilter[3][1] = new MidiFilter( (byte)-109, (byte)38, (byte)127);
		switchFilter[3][2] = new MidiFilter( (byte)-109, (byte)40, (byte)127);
		switchFilter[3][3] = new MidiFilter( (byte)-109, (byte)41, (byte)127);
		switchFilter[3][4] = new MidiFilter( (byte)-109, (byte)43, (byte)127);
		switchFilter[3][5] = new MidiFilter( (byte)-109, (byte)45, (byte)127);
		switchFilter[3][6] = new MidiFilter( (byte)-109, (byte)47, (byte)127);
		switchFilter[3][7] = new MidiFilter( (byte)-109, (byte)48, (byte)127);
	}
	@Override
	public String getName() {
		return "LDP8";
	}
}
