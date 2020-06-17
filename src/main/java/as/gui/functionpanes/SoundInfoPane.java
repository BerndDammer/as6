package as.gui.functionpanes;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.SelectionButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class SoundInfoPane extends CenterPaneBase {
	// private final Logger logger = LoggingInit.get( this );
	private L0SoundInfo soundInfo = new L0SoundInfo();

	private class L3Control extends TreeItem<String>
	{
		private final Control cc;

		private L3Control(Control cc)
		{
			super(cc.getType().toString());
			this.cc = cc;
		}
	}
	private class L3AudioFormat extends TreeItem<String>
	{
		private final AudioFormat af;

		private L3AudioFormat(AudioFormat af)
		{
			super(af.toString() + "BPS: " + af.getSampleRate());
			this.af = af;
		}
	}


	private class L2Line extends TreeItem<String> {

		private final Line.Info li;
		TreeItem<String> controls = new TreeItem<String>("Controls");

		private L2Line(Mixer mixer, Line.Info li) {
			super(li.getLineClass().getName());

			this.li = li;
			getChildren().add(new TreeItem<String>("Implementer Class : " + li.getClass().getName()));
			try {
				Line line = mixer.getLine(li);
				if (line instanceof Port) {
					Port p = (Port) line;
					p.open();
					// TODO cast
					Port.Info pi = (Port.Info) p.getLineInfo();
					String cHeader = "Controls : ... Port.Info" + pi.getName() + " : ";
					controls = new TreeItem<String>(cHeader);

					Control[] fcc = p.getControls();
					if (fcc.length > 0) {
						for (Control cc : fcc)
						{
							controls.getChildren().add(new L3Control(cc));
						}
						getChildren().add(controls);
					}
					p.close();
				}

				TreeItem<String> tn;
				if (line instanceof SourceDataLine) {
					tn = new TreeItem<String>("Source Audio Formats");
					if (buildAudioFormats((DataLine.Info) li, tn))
						getChildren().add(tn);
				}
				if (line instanceof TargetDataLine) {
					tn = new TreeItem<String>("Target Audio Formats");
					if (buildAudioFormats((DataLine.Info) li, tn))
						getChildren().add(tn);
				}

			} catch (LineUnavailableException e) {
				getChildren().add(new TreeItem<String>("Error getting Line : " + e.getMessage()));
			}
		}

		private boolean buildAudioFormats(DataLine.Info dli, TreeItem<String> tn) {
			boolean result;
			AudioFormat[] faf = dli.getFormats();
			result = faf.length > 0;
			if (result) {
				for (AudioFormat af : faf)
				{
					tn.getChildren().add(new L3AudioFormat(af));
				}
			}
			return result;
		}

		@Override
		public String toString() {
			return (li.getLineClass().getName());
		}
	}

	private class L1Mixer extends TreeItem<String> {
		private final Mixer.Info mi;

		private L1Mixer(Mixer.Info mi) {
			super(mi.getName());
			this.mi = mi;
			Mixer mixer = AudioSystem.getMixer(mi);

			for (Line.Info li : mixer.getSourceLineInfo()) {
				getChildren().add(new L2Line(mixer, li));
			}
			for (Line.Info li : mixer.getTargetLineInfo()) {
				getChildren().add(new L2Line(mixer, li));
			}
		}
	}

	private class L0SoundInfo extends TreeItem<String> {
		L0SoundInfo() {
			super("Root");
			Mixer.Info[] fmi = AudioSystem.getMixerInfo();
			for (Mixer.Info mi : fmi) {
				getChildren().add(new L1Mixer(mi));
			}
		}
	}

	public SoundInfoPane(IC_RootParent rootParent) {
		super(rootParent);
		rootParent.getSelectionInterface().add(new SelectionButton("Sound", this, rootParent));

		TreeView<String> treeView = new TreeView<>(soundInfo);
		//add(new ScrollPane(treeView), 0, 0);
		add(treeView, 0, 0);
		GridPane.setHgrow(treeView, Priority.ALWAYS);
		treeView.setMaxWidth(Double.MAX_VALUE);
		treeView.setShowRoot(false);
	}

	@Override
	public void setActive(boolean active) {
		if (active) {
			rootParent.getHeaderInterface().setTitle("Sound Information");
		}
	}
}
