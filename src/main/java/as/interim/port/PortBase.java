package as.interim.port;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import as.interim.message.IC_DemultiplexerMessage;
import as.interim.message.IL_MessageBaseReceiver;
import as.interim.message.MessageBase;
import as.interim.message.MessageIdentityDisk;
import as.logging.LoggingInit;

public class PortBase implements  IC_DemultiplexerMessage
{
    private final Logger logger = LoggingInit.get( this );

    protected final Map<MessageIdentityDisk, List<IL_MessageBaseReceiver<? extends MessageBase>>> receivers = new TreeMap<MessageIdentityDisk, List<IL_MessageBaseReceiver<? extends MessageBase>>>();

    public PortBase()
    {
    }

    
    @Override
    public final void register( MessageBase message, IL_MessageBaseReceiver<? extends MessageBase> receiver )
    {
        //DemuxCall.scan( receiver );
        MessageIdentityDisk md = message.getMessageIdentityDisk();
        if (receivers.containsKey( md ))
        {
            receivers.get( md ).add( receiver );
        }
        else
        {
            List<IL_MessageBaseReceiver<? extends MessageBase>> mrs = new LinkedList<>();
            mrs.add( receiver );
            receivers.put( md, mrs );
        }
    }

    @Override
    public final void unregister( MessageBase message, IL_MessageBaseReceiver<? extends MessageBase> receiver )
    {
        MessageIdentityDisk md = message.getMessageIdentityDisk();
        if (receivers.containsKey( md ))
        {
            receivers.get( md ).add( receiver );
        }
        else
        {
            logger.warning("Nothing to unregister");
        }
    }

}
