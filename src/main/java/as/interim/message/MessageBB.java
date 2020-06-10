package as.interim.message;

import java.util.List;

public class MessageBB extends MessageBase
{
    private static final long serialVersionUID = 4245033144303246502L;
    public List<String> inputNames;
    public List<String> outputNames;
    public String selectedInput;
    public String selectedOutput;
    public CMD cmd;
    public String selected;
    public byte[]b;

    public MessageBB()
    {
        b = new byte[1024];
    }

    public enum CMD
    {
        REQUEST_LIST, ANSWER_LIST, SET;
    }
}
