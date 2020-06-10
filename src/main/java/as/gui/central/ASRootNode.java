package as.gui.central;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import as.gui.functionpanes.HomePane;
import as.gui.functionpanes.InfoPane;
import as.gui.functionpanes.RunPane;
import as.gui.functionpanes.SelectPane;
import as.gui.functionpanes.VideoPane;
import as.gui.functionpanes.WebPane;
import as.gui.headerbar.HeaderBar;
import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_HeaderInterface;
import as.gui.interfaces.IC_RootParent;
import as.gui.interfaces.IC_SelectionInterface;
import as.gui.selectionbar.ExitButton;
import as.gui.selectionbar.SelectionBar;
import as.logging.LoggingInit;
import as.starter.IC_StaticConst;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class ASRootNode extends GridPane implements IC_RootParent
{
    private final Logger logger = LoggingInit.get( this );

    private final HeaderBar headerBar = new HeaderBar( this );
    private final SelectionBar functionBar = new SelectionBar( this );
    private List<IC_FunctionPane> functionPanes = new LinkedList<>();
    private IC_FunctionPane activePane;

    public ASRootNode()
    {
        if (IC_StaticConst.SET_SIZE_INTERNAL)
        {
            setPrefSize( IC_StaticConst.SCREEN_WIDTH, IC_StaticConst.SCREEN_HEIGHT );
        }
        setBackground( new Background( new BackgroundFill( Color.OLIVEDRAB, CornerRadii.EMPTY, Insets.EMPTY ) ) );
        add( headerBar, 0, 0, GridPane.REMAINING, 1 );

        activePane = new HomePane( this );
        functionPanes.add( activePane );
        functionPanes.add( new InfoPane( this ) );
        functionPanes.add( new SelectPane( this ) );
        functionPanes.add( new RunPane( this ) );
        functionPanes.add( new WebPane( this ) );
        functionPanes.add( new VideoPane( this ) );

        add( activePane.getPane(), 0, 1, GridPane.REMAINING, 1 );

        add( functionBar, 0, 2, GridPane.REMAINING, 1 );
        functionBar.add( new ExitButton() );

        defineStretching();
        activePane.setActive( true );
    }

    private final void defineStretching()
    {
        //////////// give resizing hints
        ObservableList<RowConstraints> rows = getRowConstraints();
        if (IC_StaticConst.LOG_GUI)
            logger.info( "original row constraints size" + rows.size() );

        RowConstraints rc;

        rc = new RowConstraints();
        // rc.setPercentHeight( 0 );
        rc.setVgrow( Priority.NEVER );
        rc.setMinHeight( IC_StaticConst.GU );
        rows.add( rc );
        rc = new RowConstraints();
        // rc.setPercentHeight( 100 );
        rc.setVgrow( Priority.ALWAYS );
        rows.add( rc );
        rc = new RowConstraints();
        // rc.setPercentHeight( 0 );
        rc.setVgrow( Priority.NEVER );
        rows.add( rc );

        ObservableList<ColumnConstraints> ccs = getColumnConstraints();
        if (IC_StaticConst.LOG_GUI)
            logger.info( "original column constraints size" + ccs.size() );
        ColumnConstraints cc;

        cc = new ColumnConstraints();
        cc.setHgrow( Priority.ALWAYS );
        ccs.add( cc );
    }

    ///////////////////////////// implements RootParent
    @Override
    public IC_SelectionInterface getSelectionInterface()
    {
        return functionBar;
    }

    @Override
    public IC_HeaderInterface getHeaderInterface()
    {
        return headerBar;
    }

    @Override
    public void activateFunctionPane( IC_FunctionPane functionPane )
    {
        activePane.setActive( false );
        getChildren().remove( activePane.getPane() );
        activePane = functionPane;
        add( activePane.getPane(), 0, 1, GridPane.REMAINING, 1 );
        requestLayout();
        activePane.setActive( true );
    }

}
