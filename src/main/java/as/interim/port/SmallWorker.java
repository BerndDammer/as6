package as.interim.port;

import java.util.logging.Logger;

import as.logging.LoggingInit;

public class SmallWorker extends Thread
{
    private static final long MAX_TIME = 3000L;
    private static final long WARN_TIME = 3000L;
    private final Logger logger = LoggingInit.get( this );

    public SmallWorker( String name )
    {
        super( name );
    }

    protected void shortWait()
    {
        long startTime = System.currentTimeMillis();
        try
        {
            wait( MAX_TIME );
        }
        catch (InterruptedException e)
        {
            logger.severe( "Unexpected Interrupt" );
        }
        if ((System.currentTimeMillis() - startTime) >= WARN_TIME)
        {
            logger.severe( "ShortWait at " + getClass().getCanonicalName() );
        }
    }

    protected void controlWait()
    {
        long startTime = System.currentTimeMillis();
        try
        {
            wait( MAX_TIME );
        }
        catch (InterruptedException e)
        {
            logger.severe( "Unexpected Interrupt" );
        }
        if ((System.currentTimeMillis() - startTime) >= WARN_TIME)
        {
            logger.severe( "ControlWait at " + getClass().getCanonicalName() );
        }
    }

    protected void defaultWait()
    {
        try
        {
            wait();
        }
        catch (InterruptedException e)
        {
            logger.severe( "Unexpected Interrupt" );
        }
    }

}
