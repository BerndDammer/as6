package as.interim.port;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.logging.Logger;

import as.interim.ByteBufferInputStream;
import as.interim.ByteBufferOutputStream;
import as.interim.message.DemuxCall;
import as.interim.message.IL_MessageBaseReceiver;
import as.interim.message.IL_Publish;
import as.interim.message.MessageBase;
import as.interim.message.MessageIdentityDisk;
import as.logging.LoggingInit;
import as.starter.IC_StaticConst;
import as.starter.StaticStarter;

public class ServerPort extends PortBase implements IL_Publish
{
    private final Logger logger = LoggingInit.get( this );


    private class ServerPortTransmitter extends SmallWorker
    {
        private final ByteBuffer bbOutgoing = ByteBuffer.allocate( IC_StaticConst.BB_SIZE );
        private final ByteBufferOutputStream bbosOutgoing = new ByteBufferOutputStream();

        private boolean bufferFull = false;

        private ServerPortTransmitter()
        {
            super( "ServerPortTransmitter" );
            start();
        }

        @Override
        public void run()
        {
            while (true)
            {
                process();
            }
        }

        /*
         * awt thead must convert because message can be changed outside
         * 
         */
        synchronized void publish( MessageBase message )
        {
            while (bufferFull)
                controlWait();
            bbOutgoing.clear();
            bbosOutgoing.setByteBuffer( bbOutgoing );
            ObjectOutputStream oos;
            try
            {
                oos = new ObjectOutputStream( bbosOutgoing );
                oos.writeObject( message );
                oos.close();
                if (IC_StaticConst.LOG_INTERIM)
                    logger.info( "Message Size : " + bbOutgoing.position() );
                bufferFull = true;
                notify();
            }
            catch (IOException e)
            {
                logger.throwing( null, null, e );
            }
        }

        synchronized private void process()
        {
            while (!bufferFull)
                defaultWait();
            bbOutgoing.flip();
            StaticStarter.getClientPort().incoming( bbOutgoing );
            if (IC_StaticConst.LOG_INTERIM)
                logger.info( "Message downsend" );
            bufferFull = false;
            notify();
        }
    }

    private class ServerPortReceiver extends SmallWorker
    {
        private final ByteBuffer bbIncoming = ByteBuffer.allocate( IC_StaticConst.BB_SIZE );
        private final ByteBufferInputStream bbisIncoming = new ByteBufferInputStream();

        private boolean bufferFull = false;

        private ServerPortReceiver()
        {
            super( "ServerPortReceiver" );
            start();
        }

        @Override
        public void run()
        {
            while (true)
            {
                process();
            }
        }

        synchronized void incoming( ByteBuffer bb )
        {
            while (bufferFull)
                controlWait();
            bbIncoming.clear();
            bbIncoming.put( bb );
            bbIncoming.flip();
            bufferFull = true;
            notify();
        }

        synchronized private void process()
        {
            while (!bufferFull)
                defaultWait();

            bbisIncoming.setByteBuffer( bbIncoming );

            ObjectInputStream ois;
            try
            {
                ois = new ObjectInputStream( bbisIncoming );
                Object o = ois.readObject();
                ois.close();
                if (IC_StaticConst.LOG_INTERIM)
                    logger.info( "read object : " + o.getClass().getCanonicalName() );
                bufferFull = false;
                notify();
                if (o instanceof MessageBase)
                {
                    MessageBase mb = (MessageBase) o;
                    MessageIdentityDisk md = mb.getMessageIdentityDisk();
                    if (receivers.containsKey( md ))
                    {
                        List<IL_MessageBaseReceiver<? extends MessageBase>> mrs = receivers.get( md );
                        for (IL_MessageBaseReceiver<? extends MessageBase> r : mrs)
                        {
                            DemuxCall.doTheDemuxCall( r, mb );
                        }
                    }
                    else
                    {
                        if (IC_StaticConst.LOG_INTERIM)
                            logger.info( "message without receiver" );
                    }

                }
                else
                {
                    if (IC_StaticConst.LOG_INTERIM)
                        logger.info( "read suspicious object : " + o.getClass().getCanonicalName() );
                }
            }
            catch (IOException e)
            {
                logger.throwing( null, null, e );
            }
            catch (ClassNotFoundException e)
            {
                logger.throwing( null, null, e );
            }
        }
    }

    private final ServerPortReceiver serverPortReceiver;
    private final ServerPortTransmitter serverPortTransmitter;

    public ServerPort()
    {
        serverPortReceiver = new ServerPortReceiver();
        serverPortTransmitter = new ServerPortTransmitter();
    }

    @Override
    public void publish( MessageBase message )
    {
        serverPortTransmitter.publish( message );
    }

    // intentionally package private
    void incoming( ByteBuffer bb )
    {
        serverPortReceiver.incoming( bb );
    }

}
