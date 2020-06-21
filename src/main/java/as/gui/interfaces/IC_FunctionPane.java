package as.gui.interfaces;

import javafx.scene.layout.Pane;

public interface IC_FunctionPane
{
    Pane getPane();
	String getHeadline();
	String getButtonName();
    void onPaneShow();
	void onPaneHide();
}
