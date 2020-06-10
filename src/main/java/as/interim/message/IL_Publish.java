package as.interim.message;

public interface IL_Publish
{

    /******************
     * sending publishing a message. To avoid garbage collection the instance stays valid for the transmitter for
     * re-using. The message is received by all receivers on the other side of the galaxy who has registered for this
     * message type
     * 
     * @param message
     *            message to publish
     * 
     * 
     */
    void publish( MessageBase message );
}
