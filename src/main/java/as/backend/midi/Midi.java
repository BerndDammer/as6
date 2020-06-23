package as.backend.midi;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
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
import as.interim.message.midi.MessageMidiAnswer;
import as.interim.message.midi.MessageMidiControl;
import as.interim.message.midi.MessageMidiRequest;
import as.logging.LoggingInit;
import as.starter.StaticStarter;

public class Midi implements Receiver, IL_MessageBaseReceiver<MessageMidiRequest> {
	private final Logger log = LoggingInit.get(this);

	// TODO Trigger sequencer
	private Map<String, MidiDevice.Info> devices = new TreeMap<>();
	private Transmitter tm = null;
	private MidiDevice md = null;
	private boolean echoData = false;
	private final IServerPort serverPort;
	private IC_FunctionChainElement.STATE state = STATE.HAS_PARA;
	private final MessageMidiAnswer messageMidiAnswer = new MessageMidiAnswer(); // reused
	private Thread statusScannerThread;

	public Midi(final IServerPort serverPort) {
		this.serverPort = serverPort;
		scan();
		state = STATE.HAS_PARA;
		serverPort.register(new MessageMidiRequest(), this);
	}

	void scan() {
		for (MidiDevice.Info mi : MidiSystem.getMidiDeviceInfo()) {
			try {
				MidiDevice md = MidiSystem.getMidiDevice(mi);
				int recCount = md.getMaxTransmitters();
				if (recCount > 0 || recCount == -1) {
					devices.put(mi.getName(), mi);
				} else {
					log.info("md:" + md);
					log.info("md.name:" + md.getDeviceInfo().getName());
				}
			} catch (MidiUnavailableException e) {
				log.log(Level.SEVERE, "Error scanning midi devices: ", e);
			}
		}
	}

	@Override
	public void send(MidiMessage message, long timeStamp) {
		if (echoData) {
			messageMidiAnswer.cmd = MessageMidiAnswer.CMD.ECHO_MESSAGE;
			messageMidiAnswer.midiMessage = message.getMessage().clone();
			serverPort.publish(messageMidiAnswer);
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@DemuxReceiver(used = true)
	public void receiveMessage(MessageMidiControl mmc) {
//		switch (mmc.cmd) {
//		case REQUEST_INFO:
//			scan();
//			mmc.inputNames.clear();
//			for (String s : devices.keySet()) {
//				mmc.inputNames.add(s);
//			}
//			StaticStarter.getServerPort().publish(mmc);
//			break;
//		case SET_INPUT:
//			//if (state != state.HAS_PARA) // 
//			{
//				mmc.cmd = MessageMidiControl.CMD.REQUEST_INFO;
//				mmc.state = state.ERROR;
//				MidiDevice.Info mdi = devices.get(mmc.selectedInput);
//				try {
//					md = MidiSystem.getMidiDevice(mdi);
//					state = STATE.HAS_PARA;
//				} catch (MidiUnavailableException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					md = null;
//					state = STATE.ERROR;
//				}
//				// send back
//				//break;
//			}
//
//			break;
//		case SET_STATE:
//			// starting
//			if (state == STATE.HAS_PARA && mmc.state == STATE.RUNNING) {
//				try {
//					tm = md.getTransmitter();
//					md.open();
//					tm.setReceiver(this);
//					log.info("End init midi");
//				} catch (MidiUnavailableException e) {
//					log.severe("Problem init midi");
//					log.throwing("Midi exception", "init", e);
//				}
//
//			}
//			if (state == STATE.RUNNING && mmc.state == STATE.NO_PARA) {
//				tm.close();
//				md.close();
//				tm = null;
//				md = null;
//			}
//			break;
//		}
	}

	public void setPotLimits(float[][] limits) {

	}

	/*
	 * gets a mirror of the last pot values the resulting field must only be changed
	 * at the next call the last return value is automatically invalid at the next
	 * get call
	 * 
	 * @return pot values
	 */
	public float[] getPots() {
		return new float[16];
	}

	@Override
	@DemuxReceiver(used = true)
	public void receiveMessage(MessageMidiRequest message) {
		switch (message.cmd) {
		case ACTIVATE:
			break;
		case DEACTIVATE:
			break;
		case SCAN:
			scan();
			messageMidiAnswer.inputNames.clear();
			messageMidiAnswer.inputNames.addAll(devices.keySet());
			messageMidiAnswer.selectedInput = md != null ? md.getDeviceInfo().getName() : null;
			StaticStarter.getServerPort().publish(messageMidiAnswer);
			break;
		case SET_ECHO_OFF:
			if( statusScannerThread != null)
			{
				statusScannerThread.interrupt();
				try {
					statusScannerThread.join();
				} catch (InterruptedException e) {
					log.log(Level.SEVERE,"Interrupt while joining", e);
				}
			}
			else
			{
				log.warning("Disable non running echo scanner");
			}
			break;
		case SET_ECHO_ON:
			if( statusScannerThread == null)
			{
				statusScannerThread = new Thread(this::statusScanner, "MidiStatusScanner");
				statusScannerThread.start();
			}
			else
			{
				log.warning("Enable already running echo scanner");
			}
			echoData = true;
			break;
		case SET_MESSAGE_ECHO_OFF:
			echoData = false;
			break;
		case SET__MESSAGE_ECHO_ON:
			echoData = true;
			break;
		case SET_INPUT:
			break;
		case SET_MAPPING:
			break;
		}
	}

	// TODO status scanner immer ????
	private void statusScanner() {
		final MessageMidiAnswer messageMidiAnswer = new MessageMidiAnswer(); // reused

		try {
			while (true) {
				messageMidiAnswer.inputNames.clear();
				messageMidiAnswer.inputNames.addAll(devices.keySet());
				messageMidiAnswer.selectedInput = md != null ? md.getDeviceInfo().getName() : null;
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			// intentionally nothing
			// used for terminating
		}
	}
}
