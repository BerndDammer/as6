package as.interim.message;

public interface IC_DemultiplexerMessage
{
    void register( MessageBase message, IL_MessageBaseReceiver<? extends MessageBase> receiver );

    void unregister( MessageBase message, IL_MessageBaseReceiver<? extends MessageBase> receiver );
}
