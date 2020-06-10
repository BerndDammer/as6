package as.gui.functionpanes;

import java.util.logging.Logger;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.SelectionButton;
import as.logging.LoggingInit;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class InfoPane extends GridPane implements IC_FunctionPane
{

    private final Logger logger = LoggingInit.get( this );

    private final IC_RootParent rootParent;

    public InfoPane( IC_RootParent rootParent )
    {
        this.rootParent = rootParent;
        rootParent.getSelectionInterface().add( new SelectionButton( "Info", this, rootParent ) );

        setPadding( new Insets( 10, 10, 10, 10 ) );
        setVgap( 3.0 );
        setHgap( 5.0 );
        int x = 0;
        int y = 0;
        addProp( "os.name", x, y );
        y++;
        addProp( "os.arch", x, y );
        y++;
        addProp( "javafx.platform", x, y );
        y++;
        addProp( "javafx.runtime.path", x, y );
        y++;
        addProp( "javafx.vm.name", x, y );
        y++;
        showFX( 2, 0 );
    }

    @Override
    public void setActive( boolean active )
    {
        if (active)
        {
            rootParent.getHeaderInterface().setTitle( "System Information" );
        }
    }
    /////////////////////// vector implements
    @Override
    public Pane getPane()
    {
        return this;
    }

    ////////////////////////////////////////////////
    //
    // privates
    //
    private void addProp( String name, int x, int y )
    {
        add( new Label( name + " : " ), x, y );
        add( new Label( System.getProperty( name ) ), x + 1, y );
    }

    private void showFX( int x, int y )
    {
        int counter = 0;
        add( new Label( "Conditional Features" ), x, y );
        y++;
        for (ConditionalFeature cf : ConditionalFeature.values())
        {
            add( new Label( cf.name() ), x, y );
            add( new Label( Platform.isSupported( cf ) ? "has" : "noo" ), x + 1, y );
            y++;
            if(++counter > 12)
            {
                x+=2;
                y = 0;
                counter = 0;
            }
        }
    }
}
