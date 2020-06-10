package as.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class SingleLineOrThrowFormatter extends Formatter
{
    private static final boolean SHOW_LOGGER = true;
    private final Date date = new Date();
    private final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd__HH:mm");
    
    public synchronized String format(LogRecord record)
    {
        String result;
        date.setTime(record.getMillis());
        String dateString = sdf.format( date );
        
        if( record.getThrown() != null)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.printf( "<<<Thrown>>> [" + dateString + "]\n");
            record.getThrown().printStackTrace(pw);
            pw.printf( ">>>Thrown<<<\n");
            pw.close();
            result = sw.toString();
        }
        else
        {
            
            if( record.getSourceClassName() != null && record.getSourceMethodName() != null)
            {
                result = String.format("%4$s:[%1$s] %5$s   {%2$s:%6$s}", 
                        dateString, 
                        record.getSourceClassName(), 
                        record.getLoggerName(), 
                        getLevelString(record.getLevel()), 
                        record.getMessage(),
                        record.getSourceMethodName()
                        );
            }
            else
            {
                result = String.format("%s:[%s] %s", 
                        getLevelString( record.getLevel()), 
                        dateString, 
                        record.getMessage());
            }
            if( SHOW_LOGGER) result += "       <<<" + record.getLoggerName()+">>>";
            result += "\n";

        }
        return result;
    }
    private String getLevelString( Level level)
    {
        if( level == Level.ALL ) return "------";
        if( level == Level.CONFIG ) return "CONFIG";
        if( level == Level.FINE ) return "FINE  ";
        if( level == Level.FINER ) return "FINER ";
        if( level == Level.FINEST ) return "FINEST";
        if( level == Level.INFO ) return "INFO  ";
        if( level == Level.OFF ) return "------";
        if( level == Level.SEVERE ) return "SEVERE";
        if( level == Level.WARNING ) return "WARNIG";
        return "------";
            
    }
}
