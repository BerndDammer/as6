package as.backend.central;

import as.backend.FCPlatformSelector;
import as.backend.midi.Midi;
import as.interim.message.IServerPort;

public class CentralEffectSwitcher {
	public CentralEffectSwitcher(IServerPort serverPort, Midi midi, FCPlatformSelector ps)
	{
		midi.getPots();
	}
}
