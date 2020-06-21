package as.gui.functionpanes;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.SelectionButton;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public abstract class CenterPaneBase extends GridPane implements IC_FunctionPane {
	protected final IC_RootParent rootParent;

	CenterPaneBase(IC_RootParent rootParent) {
		this.rootParent = rootParent;
		rootParent.getSelectionInterface().add(new SelectionButton( getButtonName(), this, rootParent));

		setPadding(new Insets(5, 5, 5, 5));
		setVgap(4.0);
		setHgap(4.0);
	}

	@Override
    public Pane getPane()
    {
        return this;
    }
}
