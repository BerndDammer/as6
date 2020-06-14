package as.gui.functionpanes;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.interim.message.IF_DefaultReceiver;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public abstract class CenterPaneBase extends GridPane implements IC_FunctionPane, IF_DefaultReceiver
{
    protected final IC_RootParent rootParent;

    CenterPaneBase(IC_RootParent rootParent)
	{
        this.rootParent = rootParent;

	    setPadding( new Insets( 5, 5, 5, 5 ) );
	    setVgap( 4.0 );
	    setHgap( 4.0 );
	}

    @Override
    public void setActive( boolean active )
    {
        if (active)
        {
            rootParent.getHeaderInterface().setTitle( "Base" );
        }
        else
        {
            rootParent.getHeaderInterface().setTitle( "Off" );
        }
    }

    /////////////////////// vector implements
    @Override
    public Pane getPane()
    {
        return this;
    }

}
