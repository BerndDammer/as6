package as.gui.functionpanes;

import java.util.logging.Logger;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.SelectionButton;
import as.logging.LoggingInit;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class VideoPane extends GridPane implements IC_FunctionPane
{
	private static final String MEDIA_URL = "http://download.oracle.com/otndocs/products/javafx/JavaRap/prog_index.m3u8";
    private final Logger logger = LoggingInit.get( this );
    private final IC_RootParent rootParent;
    private MediaPlayer mediaPlayer = null;
    private MediaView mediaView = null;
    
    public VideoPane(IC_RootParent rootParent)
    {
        this.rootParent = rootParent;
        rootParent.getSelectionInterface().add( new SelectionButton("Video", this, rootParent) );
    }
    @Override
    public void setActive( boolean active )
    {
        if(active)
        {
            rootParent.getHeaderInterface().setTitle( "Video" );
            Media media = new Media( MEDIA_URL);
            mediaPlayer = new MediaPlayer( media );
            mediaView = new MediaView(mediaPlayer);
            add( mediaView, 0,0 );
            mediaPlayer.play();
            logger.info("act Video");
        }
        else
        {
        	mediaPlayer.stop();
        	getChildren().clear();
        	mediaView = null;
        	mediaPlayer = null;
            logger.info("Video killed the radio star");
        }
    }

    /////////////////////// vector implements
    @Override
    public Pane getPane()
    {
        return this;
    }

}
