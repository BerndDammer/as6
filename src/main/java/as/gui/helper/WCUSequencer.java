package as.gui.helper;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class WCUSequencer {
	private static final Duration PRE = Duration.seconds(0.04);
	private static final Duration POST = Duration.seconds(0.52);
	private final Timeline timeline;

	public WCUSequencer(final Duration cycle) {

		timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.setOnFinished(this::onFinish);

		timeline.getKeyFrames().add(new KeyFrame(PRE, "LoopStart", this::onLoopStart));
		timeline.getKeyFrames().add(new KeyFrame(PRE.add(cycle), "LoopEnd", this::onLoopEnd));
		timeline.getKeyFrames().add(new KeyFrame(PRE.add(cycle).add(POST), "Exit", this::onExit));

		timeline.playFromStart();
	}


	public void breakIt() {
		timeline.jumpTo("Exit");
	}

	private void onLoopStart(final ActionEvent event) {

	}

	private void onLoopEnd(final ActionEvent event) {
		timeline.jumpTo("LoopStart");
	}

	private void onExit(final ActionEvent event) {

	}

	private void onFinish(final ActionEvent event) {

	}
}