package as.gui.headerbar;

import java.util.logging.Logger;

import as.gui.central.ASRootNode;
import as.gui.interfaces.IC_HeaderElement;
import as.gui.interfaces.IC_HeaderInterface;
import as.logging.LoggingInit;
import as.starter.IC_StaticConst;
import buildnumber.BuildNumber;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class HeaderBar extends GridPane implements IC_HeaderInterface
{
    private final Logger logger = LoggingInit.get( this );

    // used to stretch between left headline and right statusses
    class DummyFiller extends Canvas
    {

    }

    private final HeadlineTitle headlineTitle = new HeadlineTitle();
    private final DummyFiller dummyFiller = new DummyFiller();
    private final HeadlineOther o1 = new HeadlineOther( "o1" );
    private final HeadlineOther o2 = new HeadlineOther( "o2" );
    private final HeadlineOther o3 = new HeadlineOther( "BuildNo : " + BuildNumber.get() );

    public HeaderBar( ASRootNode parent )
    {
        setPadding( new Insets( 11, 11, 11, 11 ) );
        setVgap( 1.5 );
        setHgap( 3.0 );

        setHalignment( headlineTitle, HPos.LEFT );
        setValignment( headlineTitle, VPos.BOTTOM );
        add( headlineTitle, 0, 0 );
        add( dummyFiller, 1, 0 );
        align( dummyFiller );

        defineStretching();

        add( o1, 2, 0 );
        add( o2, 3, 0 );
        add( o3, 4, 0 );
        align( o1 );
        align( o2 );
        align( o3 );
        setBackground( new Background( new BackgroundFill( Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY ) ) );
    }

    private void align( Node n )
    {
        setHalignment( n, HPos.RIGHT );
        setValignment( n, VPos.BOTTOM );
    }

    private final void defineStretching()
    {
        //////////// give resizing hints
        ObservableList<RowConstraints> rows = getRowConstraints();
        if (IC_StaticConst.LOG_GUI)
            logger.info( "header bar original row constraints size " + rows.size() );

        RowConstraints rc;

        rc = new RowConstraints();
        rc.setVgrow( Priority.ALWAYS );
        rows.add( rc );

        ObservableList<ColumnConstraints> ccs = getColumnConstraints();
        if (IC_StaticConst.LOG_GUI)
            logger.info( "headline column constraints size " + ccs.size() );
        ColumnConstraints cc;

        cc = new ColumnConstraints();
        cc.setHgrow( Priority.NEVER );
        ccs.add( cc );
        cc = new ColumnConstraints();
        cc.setHgrow( Priority.ALWAYS );
        ccs.add( cc );
    }

    @Override
    public void add( IC_HeaderElement he )
    {
        getChildren().add( he.getNode() );
    }

    @Override
    public void setTitle( String title )
    {
        headlineTitle.setText( title );
    }
}
