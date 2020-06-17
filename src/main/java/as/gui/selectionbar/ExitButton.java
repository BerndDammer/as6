package as.gui.selectionbar;

import javafx.event.ActionEvent;

public class ExitButton extends SelectionButton 
{
    public ExitButton()
    {
        super( "Exit", null, null );
        setOnAction( this );
    }

    @Override
    public void handle( ActionEvent event )
    {
        //Platform.exit();
        System.exit( 0 );
    }
}
