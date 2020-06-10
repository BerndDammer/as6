package as.interim.message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import as.starter.IC_StaticConst;

public class DemuxCall
{
    private static final Logger logger = Logger.getAnonymousLogger();

    public DemuxCall()
    {
    }

    public static void doTheDemuxCall( IL_MessageBaseReceiver<? extends MessageBase> receiver, MessageBase message )
    {
        try
        {
            Method receiveMethod;
            // receiveMethod = IL_MessageBaseReceiver.class.getMethod( "receiveMessage", MessageBase.class );
            // logger.info( receiveMethod.toString() );
            // receiveMethod = receiver.getClass().getMethod( "receiveMessage", MessageBase.class );
            // logger.info( receiveMethod.toString() );
            receiveMethod = receiver.getClass().getMethod( "receiveMessage", message.getClass() );
            // receiveMethod = receiver.getClass().getMethod( "receiveMessage", MessageBase.class );
            // logger.info( receiveMethod.toString() );
            receiveMethod.invoke( receiver, message );
        }
        catch (NoSuchMethodException e)
        {
            logger.throwing( "DemuxCall", "doTheDemuxCall", e );
        }
        catch (SecurityException e)
        {
            logger.throwing( "DemuxCall", "doTheDemuxCall", e );
        }
        catch (IllegalAccessException e)
        {
            logger.throwing( "DemuxCall", "doTheDemuxCall", e );
        }
        catch (IllegalArgumentException e)
        {
            logger.throwing( "DemuxCall", "doTheDemuxCall", e );
        }
        catch (InvocationTargetException e)
        {
            logger.throwing( "DemuxCall", "doTheDemuxCall", e );
        }
    }

    public static void scan( IL_MessageBaseReceiver<? extends MessageBase> receiver )
    {
        if (IC_StaticConst.LOG_INTERIM)
            logger.info( "Demux Scan class : " + receiver.getClass().getCanonicalName() );
        Method[] methods = receiver.getClass().getMethods();
        for (Method m : methods)
        {
            if (m.getName().equals( "receiveMessage" ))
            {
                Class<?>[] parameter = m.getParameterTypes();
                for (Class<?> p : parameter)
                {
                    if (IC_StaticConst.LOG_INTERIM)
                        logger.info( "Scan for Parameter : " + p.getCanonicalName() );
                }
            }
        }
    }
}
