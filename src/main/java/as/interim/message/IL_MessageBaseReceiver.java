package as.interim.message;

public interface IL_MessageBaseReceiver<M extends MessageBase>
{
    public void receiveMessage(M message); 
}
