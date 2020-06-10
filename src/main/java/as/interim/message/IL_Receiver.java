package as.interim.message;

public interface IL_Receiver
{
    /*
     * receive is called from a non awt thread
     * informs a gui that the message contains new data
     * 
     * the message can be the class given by register or 
     * a new created class by the interface
     * 
     */
    void receive(MessageBase message);
}
