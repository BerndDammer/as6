package as.interim.message;

import java.util.List;

public class MessagePlatformSelect extends MessageBase
{
    private static final long serialVersionUID = -533735861149591462L;
    // using list is not really bigger than field
    // List empty 3 smaller; List filled 1 more
    //
    public List<String> names = null;
    //public String[] names;
    public CMD cmd;
    public String selected;
    public MessagePlatformSelect()
    {
        
    }
    
    public enum CMD
    {
        REQUEST_LIST,
        ANSWER_LIST,
        SET;
    }
}
