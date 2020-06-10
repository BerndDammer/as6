package as.gui.selectionbar;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ExitButton extends SelectionButton implements EventHandler<ActionEvent>
{
    public ExitButton()
    {
        super( "Exit", null, null );
        setOnAction( this );
    }

    @Override
    public void handle( ActionEvent event )
    {
        Platform.exit();
        System.exit( 0 );
    }
}
