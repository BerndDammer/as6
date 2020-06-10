package as.starter;

import as.backend.FCPlatformSelector;
import as.gui.central.ASApplication;
import as.interim.port.ClientPort;
import as.interim.port.ServerPort;
import as.logging.LoggingInit;
import javafx.application.Application;

// test: define main in interface ???
public class StaticStarter
{
    // TODO split into transferMetod and DataEncapsulation
    /**
     * Transfer
     * 
     * UDP-Multicast
     * RMI
     * Intern
     * TCP-Serializion
     * 
     * DataEncapsulation
     * 
     * Serialize(JavaStandard)
     * SerializeXML
     * SerializeJSON
     * SerializeBSON
     * 
     * the serialized result is always placed in a byte buffer
     * 
     * ByteBuffer[self placed]
     * 
     * All Interfacing is Based on Transferring
     * <? extends MessageBase>
     * 
     * 
     * 
     */
    private static final String[] start_parameters =
    { "rmiserver", "bbobjectclient", "CompoundSerial", "CompoundByteBuffer", "ServerRMI", "ClientRMI", "ServerByteBuffer", "ClientByteBuffer" };
    // private static final Application application = null;
    private static ClientPort clientPort;
    private static ServerPort serverPort;
    private static FCPlatformSelector fcPlatformSelector;

    public static void main( String[] args )
    {
        LoggingInit.forceClassLoadingAndSetLogName( "as.log" );
        serverPort = new ServerPort();
        clientPort = new ClientPort();
        fcPlatformSelector = new FCPlatformSelector();
        // must be last or separate thread
        Application.launch( ASApplication.class, args ); // this never returns !!!
    }

    public static ClientPort getClientPort()
    {
        return clientPort;
    }

    public static ServerPort getServerPort()
    {
        return serverPort;
    }
}
