package as.interim.message;

import java.util.logging.Logger;

public interface IF_DefaultReceiver extends IL_MessageBaseReceiver<MessageBase>
{
    static final Logger logger = Logger.getAnonymousLogger();
    
  @DemuxReceiver(used=false)
  default public void receiveMessage( MessageBase mb )
  {
      logger.severe( "Unexpected message cmd" );
  }
   
}
