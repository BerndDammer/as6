package as.backend.midi;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import as.functionchain.IC_FunctionChainElement;
import as.functionchain.IC_FunctionChainElement.STATE;
import as.interim.message.DemuxReceiver;
import as.interim.message.IL_MessageBaseReceiver;
import as.interim.message.IServerPort;
import as.interim.message.midi.MessageMidiControl;
import as.interim.message.midi.MessageMidiData;
import as.logging.LoggingInit;
import as.starter.StaticStarter;

public class Midi implements Receiver, IL_MessageBaseReceiver<MessageMidiControl> {
	Map<String, MidiDevice.Info> devices = new TreeMap<>();
	private final Logger LOG = LoggingInit.get(this);

	private Transmitter tm = null;
	private MidiDevice md = null;
	private boolean echoData = true;
	private final IServerPort serverPort;
	private IC_FunctionChainElement.STATE state = STATE.HAS_PARA;

	public Midi(final IServerPort serverPort) {
		this.serverPort = serverPort;
		scan();
		state = STATE.HAS_PARA;
		serverPort.register(new MessageMidiControl(), this);
	}

	void scan() {
		for (MidiDevice.Info mi : MidiSystem.getMidiDeviceInfo()) {
			try {
				MidiDevice md = MidiSystem.getMidiDevice(mi);
				int recCount = md.getMaxTransmitters();
				if (recCount > 0 || recCount == -1) {
					devices.put(mi.getName(), mi);
				} else {
					LOG.warning("md:" + md);
					LOG.warning("md.name:" + md.getDeviceInfo().getName());
				}
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void send(MidiMessage message, long timeStamp) {
		if (echoData) {
			MessageMidiData mmd = new MessageMidiData();
			mmd.data = message.getMessage();
			serverPort.publish(mmd);
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@DemuxReceiver(used = true)
	public void receiveMessage(MessageMidiControl mmc) {
		switch (mmc.cmd) {
		case REQUEST_INFO:
			scan();
			mmc.inputNames.clear();
			for (String s : devices.keySet()) {
				mmc.inputNames.add(s);
			}
			StaticStarter.getServerPort().publish(mmc);
			break;
		case SET_INPUT:
			//if (state != state.HAS_PARA) // 
			{
				mmc.cmd = MessageMidiControl.CMD.REQUEST_INFO;
				mmc.state = state.ERROR;
				MidiDevice.Info mdi = devices.get(mmc.selectedInput);
				try {
					md = MidiSystem.getMidiDevice(mdi);
					state = STATE.HAS_PARA;
				} catch (MidiUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					md = null;
					state = STATE.ERROR;
				}
				// send back
				//break;
			}

			break;
		case SET_STATE:
			// starting
			if (state == STATE.HAS_PARA && mmc.state == STATE.RUNNING) {
				try {
					tm = md.getTransmitter();
					md.open();
					tm.setReceiver(this);
					LOG.info("End init midi");
				} catch (MidiUnavailableException e) {
					LOG.info("Problem init midi");
					LOG.throwing("Midi exception", "init", e);
				}

			}
			if (state == STATE.RUNNING && mmc.state == STATE.NO_PARA) {
				tm.close();
				md.close();
				tm = null;
				md = null;
			}
			break;
		}
	}
}
