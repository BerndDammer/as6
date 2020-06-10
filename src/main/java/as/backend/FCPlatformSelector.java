package as.backend;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import as.functionchain.IC_FunctionChainElement;
import as.interim.message.IL_MessageBaseReceiver;
import as.interim.message.MessagePlatformSelect;
import as.logging.LoggingInit;
import as.persistent.IC_SubTreeBase;
import as.persistent.PersistentCentral;
import as.starter.StaticStarter;

public class FCPlatformSelector extends Thread
        implements IC_FunctionChainElement, IL_MessageBaseReceiver<MessagePlatformSelect>
{
    private enum SOUND_PLATFORM
    {
        //JAVASOUND, ASIO, ALSA, JACK;
        JAVASOUND, ASIO;
    }

    private static final String TAG_PLATFORM = "Platform";
    private STATE state; 
    private final Logger logger = LoggingInit.get( this );
    private final List<String> names = new LinkedList<>();
    private final String[] sfnames;
    private SOUND_PLATFORM selected = null;

    private final MessagePlatformSelect receivePlatformSelect = new MessagePlatformSelect();
    private final MessagePlatformSelect transmittPlatformSelect = new MessagePlatformSelect();
    private final IC_SubTreeBase para;
    private FCChannelSelectorBase platformActivator = null;
    
    // TODO crashes at invalid Platform string
    
    
    public FCPlatformSelector()
    {
        super( FCPlatformSelector.class.getCanonicalName() );
        state = STATE.NO_PARA;
        for (SOUND_PLATFORM sp : SOUND_PLATFORM.values())
        {
            names.add( sp.name() );
        }
        sfnames = new String[names.size()];
        for (int i = 0; i < sfnames.length; i++)
        {
            sfnames[i] = names.get( i );
        }
        StaticStarter.getServerPort().register( receivePlatformSelect, this );
        para = PersistentCentral.subPlatformSelector();
        if (para.containsKey( "Platform" ))
        {
            try
            {
                String sPlatform = (String) para.get( TAG_PLATFORM );
                selected = SOUND_PLATFORM.valueOf( sPlatform );
                state = STATE.HAS_PARA;
            }
            catch (Exception e)
            {
                logger.warning( "Error reading platform" );
            }
        }
        else
        {
            logger.warning( "missing parameter" );
        }
        if (selected != null)
            activateChild();
        start();
    }

    @Override
    public void run()
    {
        while (true)
        {
            process();
        }
    }

    private synchronized void process()
    {
        try
        {
            wait( 3000 );
        }
        catch (InterruptedException e)
        {
        }
    }

    @Override
    public void receiveMessage( MessagePlatformSelect mps )
    {
        switch (mps.cmd)
        {
            case ANSWER_LIST:
                break;
            case REQUEST_LIST:
                transmittPlatformSelect.names = names;
                transmittPlatformSelect.selected = selected.name();
                transmittPlatformSelect.cmd = MessagePlatformSelect.CMD.ANSWER_LIST;
                StaticStarter.getServerPort().publish( transmittPlatformSelect );
                break;
            case SET:
                try
                {
                    selected = SOUND_PLATFORM.valueOf( mps.selected );
                    activateChild();
                }
                catch (Exception e)
                {
                    logger.throwing( null, null, e );
                    logger.warning( "Error reading platform" );
                    selected = SOUND_PLATFORM.JAVASOUND;
                }

                para.clear();
                para.put( TAG_PLATFORM, selected.name() );
                para.flush();
                break;
            default:
                break;
        }
    }
    private void activateChild()
    {
        if( platformActivator != null)
        {
            platformActivator.activeFromParent( false );
            platformActivator = null;
        }
        switch( selected )
        {
            case ASIO:
                platformActivator = new FCChannelSelectorASIO();
                break;
            case JAVASOUND:
                platformActivator = new FCChannelSelectorJavasound();
                break;
        }
        platformActivator.activeFromParent( true );
        state = STATE.RUNNING;
    }
    @Override
    public void activeFromParent( boolean active )
    {
        //ignored because here is no parent
    }
}
