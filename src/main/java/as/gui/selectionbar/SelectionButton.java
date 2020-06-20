package as.gui.selectionbar;

import as.globals.Globals;
import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class SelectionButton extends Button implements EventHandler<ActionEvent>
{
    private final IC_FunctionPane functionPane;
    private final IC_RootParent rootParent;

    public SelectionButton( String label, IC_FunctionPane functionPane, IC_RootParent rootParent )
    {
        this.functionPane = functionPane;
        this.rootParent = rootParent;
        setText( label );
        setPrefSize( Globals.GU, Globals.GU );
        setMinSize( Globals.GU, Globals.GU );
        // TODO Auto-generated constructor stub
        if (functionPane != null && rootParent != null)
            setOnAction( this );
    }

    @Override
    public void handle( ActionEvent event )
    {
        rootParent.activateFunctionPane( functionPane );
    }
}
