package as.functionchain;

public interface IC_FunctionChainElement
{
    enum STATE
    {
        NO_PARA,
        HAS_PARA, 
        RUNNING,
        ERROR;
    }
    public void activeFromParent(boolean active);
}
