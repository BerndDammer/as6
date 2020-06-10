package as.gui.functionpanes;

import java.util.logging.Logger;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.HomeButton;
import as.logging.LoggingInit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class HomePane extends GridPane implements IC_FunctionPane
{
    class MyButton extends Button implements EventHandler<ActionEvent>
    {
        MyButton ()
        {
            setText( "Say Hello World" );
            setOnAction( this );
        }

        @Override
        public void handle( ActionEvent event )
        {
            System.out.println( "Hello World!" );
        }
    }

    private final Logger logger = LoggingInit.get( this );
    private final IC_RootParent rootParent;

    public HomePane(IC_RootParent rootParent)
    {
        this.rootParent = rootParent;
        rootParent.getSelectionInterface().add( new HomeButton(this, rootParent) );
        
        setGridLinesVisible( true );
        setPadding( new Insets( 25, 25, 25, 25 ) );
        setVgap( 2.0 );
        setHgap( 1.0 );

        Label pw = new Label( "Password:" );
        add( pw, 0, 1 );

        PasswordField pwBox = new PasswordField();
        add( pwBox, 0, 2 );
        add( new MyButton(), 0, 3 );
    }

    /////////////////////////////////////////////
    @Override
    public void setActive( boolean active )
    {
        if(active)
        {
            rootParent.getHeaderInterface().setTitle( "Home" );
        }
    }

    /////////////////////// vector implements
    @Override
    public Pane getPane()
    {
        // TODO Auto-generated method stub
        return this;
    }
}
