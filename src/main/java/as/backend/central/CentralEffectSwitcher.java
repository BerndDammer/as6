package as.backend.central;

import as.backend.midi.Midi;
import as.backend.sound.FCPlatformSelector;
import as.interim.message.IServerPort;

public class CentralEffectSwitcher {
	public CentralEffectSwitcher(IServerPort serverPort, Midi midi, FCPlatformSelector ps)
	{
		midi.getPots();
	}
}
