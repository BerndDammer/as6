package as.gui.functionpanes;

import java.util.logging.Logger;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.SelectionButton;
import as.logging.LoggingInit;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebPane extends GridPane implements IC_FunctionPane
{

    private final Logger logger = LoggingInit.get( this );
    private final IC_RootParent rootParent;
    private WebView webView = null;

    public WebPane(IC_RootParent rootParent)
    {
        this.rootParent = rootParent;
        rootParent.getSelectionInterface().add( new SelectionButton("Web", this, rootParent) );
    }
    @Override
    public void setActive( boolean active )
    {
        if(active)
        {
            rootParent.getHeaderInterface().setTitle( "Web" );
            webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            webEngine.load( "http://docs.oracle.com/javase/8/javafx/api/index.html" );
            //webEngine.load( "https://login.live.com" );
            add(webView, 0,0 );
            logger.info("added web");
        }
        else
        {
        	getChildren().clear();
        	webView = null;
            logger.info("web killed");
        }
    }

    /////////////////////// vector implements
    @Override
    public Pane getPane()
    {
        return this;
    }

}
