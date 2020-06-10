package as.gui.interfaces;

public interface IC_RootParent
{
    IC_HeaderInterface getHeaderInterface();
    IC_SelectionInterface getSelectionInterface();
    void activateFunctionPane( IC_FunctionPane functionPane);
}
