package as.interim.message.sound;

import java.util.List;

import as.interim.message.MessageBase;

public class MessageChannelSelect extends MessageBase
{
    private static final long serialVersionUID = 4245033144303246502L;
    public List<String> inputNames;
    public List<String> outputNames;
    public String selectedInput;
    public String selectedOutput;
    public CMD cmd;

    public MessageChannelSelect()
    {

    }

    public enum CMD
    {
        REQUEST_LIST, ANSWER_LIST, SET_INPUT, SET_OUTPUT;
    }
}
