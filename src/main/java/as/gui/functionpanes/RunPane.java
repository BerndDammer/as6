package as.gui.functionpanes;

import java.util.logging.Logger;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.logging.LoggingInit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class RunPane extends CenterPaneBase implements IC_FunctionPane
{
    private final Logger logger = LoggingInit.get( this );
    
    class MyButton extends Button implements EventHandler<ActionEvent>
    {
        MyButton ()
        {
            setText( "Go go go" );
            setOnAction( this );
        }

        @Override
        public void handle( ActionEvent event )
        {
            System.out.println( "running" );
        }
    }



    public RunPane(IC_RootParent rootParent)
    {
    	super(rootParent);
        
        setGridLinesVisible( true );

        add( new MyButton(), 0, 3 );

    }
	@Override
	public void onPaneShow() {
	}
	@Override
	public void onPaneHide() {
	}
	@Override
	public String getHeadline() {
		return "Run";
	}
	@Override
	public String getButtonName() {
		return "Run";
	}
}
