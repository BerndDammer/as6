/**
 * 
 */
package as.logging;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author as
 *
 */
class AlwaysThrowFilter implements Filter
{
    private final Level level;

    /**
     * 
     * @param level
     *            min level to activate logging
     */
    AlwaysThrowFilter( Level level )
    {
        this.level = level;
    }

    /**
     * 
     */
    AlwaysThrowFilter()
    {
        this( Level.INFO );
    }

    @Override
    public boolean isLoggable( LogRecord record )
    {
        boolean result;
        if (record.getThrown() != null)
            return true;
        result = record.getLevel().intValue() >= level.intValue();
        return result;
    }
}
