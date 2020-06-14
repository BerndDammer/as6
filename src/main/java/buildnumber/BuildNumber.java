package buildnumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.logging.Logger;

public class BuildNumber
{
    static private Logger logger = Logger.getAnonymousLogger();
    
    public static int get()
    {
        int result = -1;
        String line;
//        try
//        {
//            InputStream is = BuildNumber.class.getResourceAsStream( "as.buildnumber");
//            InputStreamReader isr = new InputStreamReader( is );
//            LineNumberReader lnr = new LineNumberReader( isr );
//            while( (line = lnr.readLine()) != null )
//            {
//                if( line.contains( "build.number" ) )
//                {
//                    int gi = line.indexOf("=");
//                    result = Integer.decode( line.substring( gi+1 ) );
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            logger.throwing(null, null, e);
//        }
        return( result );
    }
}
