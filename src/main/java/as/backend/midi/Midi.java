package as.backend.midi;

import java.util.logging.Logger;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import as.functionchain.IC_FunctionChainElement;
import as.functionchain.IC_FunctionChainElement.STATE;
import as.interim.message.DemuxReceiver;
import as.interim.message.IL_MessageBaseReceiver;
import as.interim.message.midi.MessageMidiControl;
import as.logging.LoggingInit;
import as.starter.StaticStarter;

public class Midi implements Receiver, IL_MessageBaseReceiver<MessageMidiControl> {
	private final Logger LOG = LoggingInit.get(this);

	private Transmitter tm = null;
	private MidiDevice md = null;


	private IC_FunctionChainElement.STATE state = STATE.NO_PARA;

	public Midi() {
		state = STATE.HAS_PARA;
		StaticStarter.getServerPort().register(new MessageMidiControl(), this);
	}

	@Override
	public void send(MidiMessage message, long timeStamp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@DemuxReceiver(used = true)
	public void receiveMessage(MessageMidiControl mmc) {
		switch (mmc.cmd) {
		case REQUEST_INFO:
			break;
		case SET_INPUT:
			if (state != state.HAS_PARA) {
				mmc.cmd = MessageMidiControl.CMD.REQUEST_INFO;
				mmc.state = state.ERROR;
				// send back
				break;
			}
//			MidiDevice md = deviceChoice.getValue();
//			try {
//				// md = MidiSystem.getMidiDevice(mi);
//				tm = md.getTransmitter();
//				md.open();
//				tm.setReceiver(MidiPane.this);
//				LOG.info("End init midi");
//			} catch (MidiUnavailableException e) {
//				LOG.info("Problem init midi");
//				LOG.throwing("Midi exception", "init", e);
//			}

			break;
		case SET_STATE:
			break;
		default:
			LOG.warning("Unexpected message cmd");
			break;
		}
	}
}
