package as.interim.message;

/*
 * this sub class should identify the type of a message
 * 
 * 
 */
import java.io.Serializable;

public class MessageIdentityDisk implements Serializable, Comparable<MessageIdentityDisk>
{
    private static final long serialVersionUID = 1L;

    private final String idstring;
    public MessageIdentityDisk(MessageBase mb)
    {
        idstring = mb.getClass().getCanonicalName();
    }
    @Override
    public boolean equals(Object o)
    {
        boolean result;
        result = ((MessageIdentityDisk)o).idstring.equals( idstring );
        return result;
    }
    public boolean equals(MessageIdentityDisk id)
    {
        boolean result;
        result = id.idstring.equals( idstring );
        return result;
    }
    @Override
    public int compareTo( MessageIdentityDisk mid )
    {
        return idstring.compareTo( mid.idstring );
    }
}
