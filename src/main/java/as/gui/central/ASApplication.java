package as.gui.central;

import java.util.logging.Logger;

import as.logging.LoggingInit;
import as.starter.IC_StaticConst;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ASApplication extends Application
{
    private final Logger logger = LoggingInit.get( this );

    class MyScene extends Scene
    {
        MyScene( int w, int h )
        {
            super( new ASRootNode(), w, h, Color.ALICEBLUE );
        }

        MyScene()
        {
            super( new ASRootNode() );
        }
    }

    //////////////////////////
    @Override
    public void start( Stage primaryStage ) throws Exception
    {
        if (IC_StaticConst.SET_SIZE_INTERNAL)
        {
            primaryStage.initStyle( StageStyle.UNDECORATED );
            primaryStage.setScene( new MyScene() );
        }
        else
        {
            if (isBigger())
            {
                // primaryStage.initStyle( StageStyle.UNDECORATED );
                primaryStage.setScene( new MyScene( IC_StaticConst.SCREEN_WIDTH + 40, IC_StaticConst.SCREEN_HEIGHT + 30 ) );
            }
            else
            {
                primaryStage.setFullScreen( true );
                primaryStage.initStyle( StageStyle.UNDECORATED );
                primaryStage.setScene( new MyScene( IC_StaticConst.SCREEN_WIDTH, IC_StaticConst.SCREEN_HEIGHT ) );
            }
        }
        primaryStage.setTitle( "Grid Root Test" );
        primaryStage.show();

    }

    private boolean isBigger()
    {
        boolean bigger;
        Screen root = Screen.getPrimary();
        Rectangle2D b = root.getBounds();
        bigger = b.getWidth() > IC_StaticConst.SCREEN_WIDTH;
        bigger |= b.getHeight() > IC_StaticConst.SCREEN_HEIGHT;
        logger.fine( "Screen Size >>>>" + b.toString() );
        return bigger;
    }
}
