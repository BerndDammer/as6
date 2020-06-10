package as.gui.headerbar;

import as.gui.interfaces.IC_HeaderElement;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class HeadlineOther extends Text implements IC_HeaderElement
{
    HeadlineOther(String text)
    {
        super( text );
        //setAlignment( Pos.TOP_LEFT );
        setFont( Font.font( "Tahoma", FontWeight.NORMAL, 10 ) );
    }

    @Override
    public Node getNode()
    {
        return this;
    }
}

