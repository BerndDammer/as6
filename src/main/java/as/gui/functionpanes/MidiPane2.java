package as.gui.functionpanes;

import java.util.logging.Logger;

import as.functionchain.IC_FunctionChainElement;
import as.gui.helper.SingleStringSelection;
import as.gui.interfaces.IC_RootParent;
import as.interim.message.DemuxReceiver;
import as.interim.message.IF_DefaultReceiver;
import as.interim.message.midi.MessageMidiControl;
import as.interim.message.midi.MessageMidiControl.CMD;
import as.interim.message.midi.MessageMidiData;
import as.logging.LoggingInit;
import as.starter.StaticStarter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Stop;

public class MidiPane2 extends CenterPaneBase implements IF_DefaultReceiver {
	private final Logger LOG = LoggingInit.get(this);

	private SingleStringSelection singleStringSelection = new SingleStringSelection();

	MessageMidiControl mmc = new MessageMidiControl();

	private EventList eventList = new EventList();

	class EventList extends TextArea {
//		public void nextMidi(MidiMessage message) {
//			appendText("M: " + message.getMessage()[0] + "." + message.getMessage()[1] + "." + message.getMessage()[2]
//					+ "\n");
		public void nextMidi(byte[] data) {
			appendText("M: " + data[0] + "." + data[1] + "." + data[2] + "\n");
		}
	}

	private class Scan extends Button implements EventHandler<ActionEvent> {
		Scan() {
			super("Scan Midi Devices");
			setOnAction(this);
		}

		@Override
		public void handle(ActionEvent event) {
			// deviceChoice.reScan();
			mmc.cmd = MessageMidiControl.CMD.REQUEST_INFO;
			StaticStarter.getClientPort().publish(mmc);
		}
	}

	private class Select extends Button implements EventHandler<ActionEvent> {
		Select() {
			super("Select Midi Device");
			setOnAction(this);
		}

		@Override
		public void handle(ActionEvent event) {
			mmc.cmd = CMD.SET_INPUT;
			mmc.selectedInput = singleStringSelection.getValue();
			StaticStarter.getClientPort().publish(mmc);
		}
	}

	private class EchoSwitch extends ToggleButton implements EventHandler<ActionEvent> {
		EchoSwitch() {
			super("Message Echo");
			setOnAction(this);
		}

		@Override
		public void handle(ActionEvent event) {

			mmc.cmd = CMD.SET_STATE;
			mmc.state = IC_FunctionChainElement.STATE.RUNNING;
			StaticStarter.getClientPort().publish(mmc);
		}
	}

//	private class Stop extends Button implements EventHandler<ActionEvent> {
//		Stop() {
//			super("Stop");
//			setOnAction(this);
//		}
//
//		@Override
//		public void handle(ActionEvent event) {
//
//			mmc.cmd = CMD.SET_STATE;
//			mmc.state = IC_FunctionChainElement.STATE.HAS_PARA;
//			StaticStarter.getClientPort().publish(mmc);
//		}
//	}



	public MidiPane2(IC_RootParent rootParent) {
		super(rootParent);

		add(singleStringSelection, 0, 0);
		add(new Scan(), 0, 1);
		add(new Select(), 0, 2);
		add(new Start(), 0, 3);
		//add(new Stop(), 0, 4);
		add(eventList, 1, 0, 1, GridPane.REMAINING);
		eventList.setEditable(false);

		StaticStarter.getClientPort().register(new MessageMidiControl(), this);
		StaticStarter.getClientPort().register(new MessageMidiData(), this);

	}

	@DemuxReceiver(used = true)
	public void receiveMessage(MessageMidiControl message) {
		switch (message.cmd) {
		case REQUEST_INFO:
			singleStringSelection.setItems(message.inputNames);
			if (message.selectedInput != null) {
				singleStringSelection.setValue(message.selectedInput);
			}
			break;
		case SET_INPUT:
			break;
		case SET_STATE:
			break;
		default:
			break;
		}
	}

	@DemuxReceiver(used = true)
	public void receiveMessage(MessageMidiData message) {
		if (message.data.length == 3) {
			eventList.nextMidi(message.data);
		} else if (message.data.length == 2) {
			LOG.warning("2 byte Message : " + message.data[0] + " . " + message.data[1]);
		} else {
			LOG.warning("Unexpected Message : " + message.data);
		}
	}

	@Override
	public void onPaneShow() {
	}

	@Override
	public void onPaneHide() {
	}

	@Override
	public String getHeadline() {
		return "Midi2";
	}

	@Override
	public String getButtonName() {
		return "Midi2";
	}
}
