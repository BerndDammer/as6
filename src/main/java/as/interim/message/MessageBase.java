package as.interim.message;

import java.io.Serializable;

public class MessageBase implements Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;
    private final MessageIdentityDisk messageIdentityDisk = new MessageIdentityDisk(this); 
    
    public MessageBase()
    {
        //this.receiver = receiver;
    }
    long getID()
    {
        return serialVersionUID;
    }
    public MessageIdentityDisk getMessageIdentityDisk()
    {
        //return messageId;
        return messageIdentityDisk;
    }
    public MessageBase clone()
    {
        try
        {
            return (MessageBase)super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            // TODO handle
            e.printStackTrace();
            return null;
        }
    }
}
