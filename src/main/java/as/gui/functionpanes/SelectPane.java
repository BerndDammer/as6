package as.gui.functionpanes;

import java.util.List;
import java.util.logging.Logger;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.SelectionButton;
import as.interim.message.DemuxReceiver;
import as.interim.message.IF_DefaultReceiver;
import as.interim.message.MessageChannelSelect;
import as.interim.message.MessagePlatformSelect;
import as.logging.LoggingInit;
import as.starter.StaticStarter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

//public class SelectPane extends GridPane implements IC_FunctionPane, IL_MessageBaseReceiver<MessageBase>
public class SelectPane extends GridPane implements IC_FunctionPane, IF_DefaultReceiver
{
    private final Logger logger = LoggingInit.get( this );

    private abstract class StringSelector extends VBox
    {
        private class StringToggle extends ToggleButton implements EventHandler<ActionEvent>
        {
            private String s;

            private StringToggle( String s )
            {
                this.s = s;
                setText( s );
                setOnAction( this );
            }

            @Override
            public void handle( ActionEvent event )
            {
                select( s );
                setSelected( true );
            }
        }

        private StringSelector( List<String> selections, String active )
        {
            ToggleGroup group = new ToggleGroup();
            for (String pn : selections)
            {
                StringToggle tb = new StringToggle( pn );
                group.getToggles().add( tb ) ;
                getChildren().add( tb );
                if (active != null)
                {
                    if (pn.equals( active ))
                        tb.setSelected( true );
                }
            }
        }

        protected abstract void select( String name );
    }

    private class PlatformPanel extends StringSelector
    {
        private PlatformPanel( List<String> selections, String active )
        {
            super( selections, active );
        }

        @Override
        protected void select( String name )
        {
            transmittPlatformSelect.cmd = MessagePlatformSelect.CMD.SET;
            transmittPlatformSelect.selected = name;
            if (inputPanel != null)
                getChildren().remove( inputPanel );
            if (outputPanel != null)
                getChildren().remove( outputPanel );
            inputPanel = null;
            outputPanel = null;

            StaticStarter.getClientPort().publish( transmittPlatformSelect );
        }
    }

    private class InputPanel extends StringSelector
    {
        private InputPanel( List<String> selections, String active )
        {
            super( selections, active );
        }

        @Override
        protected void select( String name )
        {
            transmittChannelSelect.cmd = MessageChannelSelect.CMD.SET_INPUT;
            transmittChannelSelect.selectedInput = name;
            StaticStarter.getClientPort().publish( transmittChannelSelect );
        }
    }

    private class OutputPanel extends StringSelector
    {
        private OutputPanel( List<String> selections, String active )
        {
            super( selections, active );
        }

        @Override
        protected void select( String name )
        {
            transmittChannelSelect.cmd = MessageChannelSelect.CMD.SET_OUTPUT;
            transmittChannelSelect.selectedOutput = name;
            StaticStarter.getClientPort().publish( transmittChannelSelect );
        }
    }


    private final IC_RootParent rootParent;
    private final MessagePlatformSelect receivePlatformSelect = new MessagePlatformSelect();
    private final MessagePlatformSelect transmittPlatformSelect = new MessagePlatformSelect();
    private final MessageChannelSelect receiveChannelSelect = new MessageChannelSelect();
    private final MessageChannelSelect transmittChannelSelect = new MessageChannelSelect();
    private PlatformPanel platformPanel = null;
    private InputPanel inputPanel = null;
    private OutputPanel outputPanel = null;

    // TODO invalid channel selections if platform is changed
    
    
    public SelectPane( IC_RootParent rootParent )
    {
        this.rootParent = rootParent;
        rootParent.getSelectionInterface().add( new SelectionButton( "Select", this, rootParent ) );

        setPadding( new Insets( 5, 5, 5, 5 ) );
        setVgap( 2.0 );
        setHgap( 1.0 );

        StaticStarter.getClientPort().register( receivePlatformSelect, this );
        StaticStarter.getClientPort().register( receiveChannelSelect, this );
    }

    @Override
    public void setActive( boolean active )
    {
        if (active)
        {
            rootParent.getHeaderInterface().setTitle( "Select" );
            transmittPlatformSelect.cmd = MessagePlatformSelect.CMD.REQUEST_LIST;
            StaticStarter.getClientPort().publish( transmittPlatformSelect );
        }
    }

    /////////////////////// vector implements
    @Override
    public Pane getPane()
    {
        // TODO Auto-generated method stub
        return this;
    }

//    @Override
//    @DemuxReceiver(used=false)
//    public void receiveMessage( MessageBase mb )
//    {
//        logger.severe( "Unexpected message cmd" );
//    }

    @DemuxReceiver(used=true)
    public void receiveMessage( MessagePlatformSelect mps )
    {
        switch (mps.cmd)
        {
            case ANSWER_LIST:
                if (platformPanel != null)
                    getChildren().remove( platformPanel );
                platformPanel = new PlatformPanel( mps.names, mps.selected);
                add( platformPanel, 0, 0 );
                break;
            default:
                logger.warning( "Unexpected message cmd" );
                break;
        }
    }

    @DemuxReceiver(used=true)
    public void receiveMessage( MessageChannelSelect mcs )
    {
        switch (mcs.cmd)
        {
            case ANSWER_LIST:
                if (inputPanel != null)
                    getChildren().remove( inputPanel );
                if (outputPanel != null)
                    getChildren().remove( outputPanel );
                inputPanel = new InputPanel( mcs.inputNames, mcs.selectedInput );
                outputPanel = new OutputPanel( mcs.outputNames, mcs.selectedOutput );
                add( inputPanel, 1, 0 );
                add( outputPanel, 2, 0 );
                break;
            default:
                logger.warning( "Unexpected message cmd" );
                break;
        }
    }
}
