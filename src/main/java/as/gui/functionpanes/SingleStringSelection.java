package as.gui.functionpanes;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SingleStringSelection extends VBox implements EventHandler<ActionEvent> {
	private final SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

	public SingleStringSelection() {
		setPadding(new Insets(6, 6, 6, 6));
		setSpacing(10);
		setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	void setItems(List<String> items) {
		getChildren().clear();
		for (String s : items) {
			CheckBox checkBox = new CheckBox(s);
			checkBox.setOnAction(this);
			getChildren().add(checkBox);
		}
		simpleStringProperty.setValue(null);
		getParent().requestLayout();
	}

	@Override
	public void handle(ActionEvent event) {
		CheckBox activatedBox = (CheckBox) event.getSource();
		if (activatedBox.selectedProperty().getValue()) {
			simpleStringProperty.setValue(activatedBox.getText());
		} else {
			simpleStringProperty.setValue(null);
		}
		for (Node node : SingleStringSelection.this.getChildren()) {
			CheckBox otherBox = (CheckBox) node;
			if (activatedBox != otherBox) {
				otherBox.selectedProperty().setValue(false);
			}
		}
	}

	public String getValue() {
		return simpleStringProperty.getValue();
	}

	public void setValue(String selectedInput) {
		for (Node node : SingleStringSelection.this.getChildren()) {
			CheckBox anyBox = (CheckBox) node;
			anyBox.selectedProperty().setValue(selectedInput.equals(anyBox.getText()));
		}
	}
}
