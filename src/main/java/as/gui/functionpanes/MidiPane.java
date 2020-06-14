package as.gui.functionpanes;

import java.util.logging.Logger;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.SelectionButton;
import as.logging.LoggingInit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class MidiPane extends CenterPaneBase implements Receiver {
	private final Logger LOG = LoggingInit.get(this);
	private DeviceChoice deviceChoice = new DeviceChoice();

	private EventList eventList = new EventList();
	private Transmitter tm = null;
	private MidiDevice md = null;

	class EventList extends TextArea {
		public void nextMidi(MidiMessage message) {
			appendText("M: " + message.getMessage()[0] + "." + message.getMessage()[1] + "." + message.getMessage()[2]
					+ "\n");
		}
	}

	private class Scan extends Button implements EventHandler<ActionEvent> {
		Scan() {
			super("Scan Midi Devices");
			setOnAction(this);
		}

		@Override
		public void handle(ActionEvent event) {
			deviceChoice.reScan();
		}
	}

	private class Open extends Button implements EventHandler<ActionEvent> {
		Open() {
			super("Open Midi Device");
			setOnAction(this);
		}

		@Override
		public void handle(ActionEvent event) {
			MidiDevice md = deviceChoice.getValue();
			try {
				// md = MidiSystem.getMidiDevice(mi);
				tm = md.getTransmitter();
				md.open();
				tm.setReceiver(MidiPane.this);
				logger.info("End init midi");
			} catch (MidiUnavailableException e) {
				logger.info("Problem init midi");
				logger.throwing("Midi exception", "init", e);
			}

		}
	}

	private class DeviceChoice extends ChoiceBox<MidiDevice> {
		public DeviceChoice() {
			reScan();
		}

		void reScan() {
			getItems().clear();
			for (MidiDevice.Info mi : MidiSystem.getMidiDeviceInfo()) {
				try {
					MidiDevice md = MidiSystem.getMidiDevice(mi);
					int recCount = md.getMaxTransmitters();
					if (recCount > 0 || recCount == -1) {
						getItems().add(md);
					} else {
						LOG.warning("md:" + md);
						LOG.warning("md.name:" + md.getDeviceInfo().getName());
					}
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public MidiPane(IC_RootParent rootParent) {
		super(rootParent);
		rootParent.getSelectionInterface().add(new SelectionButton("Midi", this, rootParent));

		add(deviceChoice, 0, 0);
		add(new Scan(), 0, 1);
		add(new Open(), 0, 2);
		add(eventList, 1, 0, 1, GridPane.REMAINING);
		eventList.setEditable(false);
	}

	@Override
	public void send(MidiMessage message, long timeStamp) {
		LOG.info(message.toString());
		eventList.nextMidi(message);
	}

	@Override
	public void close() {
		
	}
}
