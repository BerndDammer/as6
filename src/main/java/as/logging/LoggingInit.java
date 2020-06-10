package as.logging;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggingInit implements PropertyChangeListener
{
    public static Logger get( Object o)
    {
        return( Logger.getLogger( o.getClass().getCanonicalName() ) );
    }
    private static final boolean LOG_NEW = false;
    private static final boolean FILE_FORMAT_NEW = true;
    private static final Level LEVEL = Level.INFO;
    
    public static final void forceClassLoadingAndSetLogName( String filename )
    {
        //LogManager.getLogManager().aaddPropertyChangeListener( new LoggingInit() );
        LogManager.getLogManager().reset();

        dumpLogger();
        
        Enumeration<String> es = LogManager.getLogManager().getLoggerNames();
        while (es.hasMoreElements())
        {
            boolean doFileHandler = false;
            String fhn = null;
            String loggerName = es.nextElement();
            Logger logger = Logger.getLogger( loggerName );
            if (loggerName.equals( Logger.GLOBAL_LOGGER_NAME ))
            {
                fhn = filename + "_G";
                doFileHandler = false;
            }
            else if (loggerName.equals( "" ) )
            {
                //fhn = filename + "_A";
                fhn = filename;
                doFileHandler = true;
            }
            if( doFileHandler )
            {
                //logger.setLevel( LEVEL );
                FileHandler fh = null;
                ConsoleHandler ch = new ConsoleHandler();
                //ch.setLevel( LEVEL );
                ch.setFormatter( new SingleLineOrThrowFormatter() );
                ch.setFilter( new AlwaysThrowFilter(LEVEL) );
                ch.setLevel( Level.ALL );
                logger.addHandler( ch );
                if( doFileHandler)
                {
                    try
                    {
                        fh = new FileHandler( fhn );
                    }
                    catch (SecurityException | IOException e)
                    {
                        // TODO Auto-generated catch block
                        throwOutsideLogger( e );
                        return;
                    }
                    //fh.setLevel( LEVEL );
                    fh.setFormatter( new SingleLineOrThrowFormatter() );
                    fh.setFilter( new AlwaysThrowFilter(LEVEL) );
                    fh.setLevel( Level.ALL );
                    logger.addHandler( fh );
                }
                logger.setLevel( Level.ALL );
            }
        }
        {
            // lower some internal logger
            //Logger.getLogger( "sun" ).setLevel( Level.INFO );
            //Logger.getLogger( "java.awt" ).setLevel( Level.INFO );
            //Logger.getLogger( "sun" ).setLevel( Level.INFO );
            //Logger.getLogger( "javafx" ).setLevel( Level.INFO );
            //Logger.getLogger( "com.sun" ).setLevel( Level.INFO );
        }
    }

    public static final void forceClassLoadingAndSetLogNameOld( String filename )
    {
        LogManager.getLogManager().reset();
        // ///////create logging properties
        Properties myLogPoperties = new Properties();

        /////////////// throwing is done by finer level
        if (LOG_NEW)
        {
            myLogPoperties.setProperty( ".level", "INFO" );
            myLogPoperties.setProperty( "handlers", "java.util.logging.ConsoleHandler,java.util.logging.FileHandler" );
            //////////////////////////////////////////////////////////// console
            //////////////////////////////////////////////////////////// formatter
            myLogPoperties.setProperty( "java.util.logging.ConsoleHandler.level", "INFO" );
            myLogPoperties.setProperty( "java.util.logging.ConsoleHandler.formatter",
                    "logging.SingleLineOrThrowFormatter" );
            ///////////////////////////////////////////////////////////// file
            myLogPoperties.setProperty( "java.util.logging.FileHandler.level", "INFO" );
            myLogPoperties.setProperty( "java.util.logging.FileHandler.pattern", filename );
            if (FILE_FORMAT_NEW)
            {
                myLogPoperties.setProperty( "java.util.logging.FileHandler.formatter",
                        "logging.SingleLineOrThrowFormatter" );
                myLogPoperties.setProperty( "logging.SingleLineOrThrowFormatter.format", "dummy dont used" );
            }
            else
            {
                myLogPoperties.setProperty( "java.util.logging.FileHandler.formatter",
                        "java.util.logging.SimpleFormatter" );
                myLogPoperties.setProperty( "java.util.logging.SimpleFormatter.format", "%4$s: [%1$tc]  <<<%5$s>>>%n" );
            }
        }
        else
        {
            myLogPoperties.setProperty( ".level", "ALL" );
            myLogPoperties.setProperty( "handlers", "java.util.logging.ConsoleHandler,java.util.logging.FileHandler" );
            myLogPoperties.setProperty( "java.util.logging.ConsoleHandler.level", "ALL" );
            myLogPoperties.setProperty( "java.util.logging.FileHandler.level", "ALL" );
            myLogPoperties.setProperty( "java.util.logging.FileHandler.pattern", filename );

            myLogPoperties.setProperty( "java.util.logging.ConsoleHandler.formatter",
                    "logging.SingleLineOrThrowFormatter" );
            myLogPoperties.setProperty( "java.util.logging.FileHandler.formatter",
                    "logging.SingleLineOrThrowFormatter" );
        }

        // ///////make input stream of properties
        try
        {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            myLogPoperties.store( output, null );
            ByteArrayInputStream input = new ByteArrayInputStream( output.toByteArray() );
            LogManager.getLogManager().readConfiguration( input );
        }
        catch (IOException e)
        {
            throwOutsideLogger( e );
        }
    }

    private static final void dumpLogger()
    {
        Enumeration<String> es = LogManager.getLogManager().getLoggerNames();
        while (es.hasMoreElements())
        {
            String loggerName = es.nextElement();
            LoggingInit.messageOutsideLogger( "(!§$)Logger Name : " + loggerName );
            Logger logger = Logger.getLogger( loggerName );
            {
                Logger parent = logger.getParent();
                if (parent == null)
                {
                    LoggingInit.messageOutsideLogger( "(!§$)This Logger has no parent" );
                }
                else
                {
                    LoggingInit.messageOutsideLogger( "(!§$)Parent Logger : <" + parent.getName() + ">" );
                }
            }
        }
    }
    
    //////////////////////// calls before logging init
    public static void messageOutsideLogger( String message )
    {
        System.out.println( message );
    }

    public static void throwOutsideLogger( Throwable t )
    {
        t.printStackTrace();
    }
    /////////////////////////////////////////////////////////////////////////
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        messageOutsideLogger("Logging properties changed");
    }
}
