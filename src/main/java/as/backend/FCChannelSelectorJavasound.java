package as.backend;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import as.interim.message.IL_MessageBaseReceiver;
import as.interim.message.sound.MessageChannelSelect;
import as.logging.LoggingInit;
import as.persistent.IC_SubTreeBase;
import as.persistent.PersistentCentral;
import as.starter.IC_StaticConst;
import as.starter.StaticStarter;

public class FCChannelSelectorJavasound extends FCChannelSelectorBase
        implements IL_MessageBaseReceiver<MessageChannelSelect>
{
    private static final String TAG_INPUT = "Input";
    private static final String TAG_OUTPUT = "Output";

    private final Logger logger = LoggingInit.get( this );

    private final List<String> inputNames = new LinkedList<String>();
    private final List<String> outputNames = new LinkedList<String>();
    private String selectedInput = null;
    private String selectedOutput = null;
    private final MessageChannelSelect receiveChannelSelect = new MessageChannelSelect();
    private final MessageChannelSelect transmittChannelSelect = new MessageChannelSelect();
    private final IC_SubTreeBase para;
    private STATE state;

    // JSON Value not in list ????
    public FCChannelSelectorJavasound()
    {
        state = STATE.NO_PARA;
        selectedInput = null;
        selectedOutput = null;
        para = PersistentCentral.subChannelSelector();
        if (para.containsKey( TAG_INPUT ))
        {
            try
            {
                selectedInput = (String) para.get( TAG_INPUT );
            }
            catch (Exception e)
            {
                logger.warning( "Error reading Input Channel Selection" );
            }
        }
        if (para.containsKey( TAG_OUTPUT ))
        {
            try
            {
                selectedOutput = (String) para.get( TAG_OUTPUT );
            }
            catch (Exception e)
            {
                logger.warning( "Error reading Output Channel Selection" );
            }
        }
        if (checkPara())
        {
            state = STATE.HAS_PARA;
        }
    }

    @Override
    public void activeFromParent( boolean active )
    {
        inputNames.clear();
        outputNames.clear();
        if (active)
        {
            StaticStarter.getServerPort().register( receiveChannelSelect, this );
            buildAvaiableLists();
            pushInfo();
            if (checkPara())
                activateChild();
        }
        else // active false
        {
            // fiewjfoew
            int a = 1;
            a--;
            a = (a +1)/ a;

        }
    }

    private boolean checkPara()
    {
        if (inputNames == null)
            return false;
        if (outputNames == null)
            return false;
        if (selectedInput == null)
            return false;
        if (selectedOutput == null)
            return false;
        if (!inputNames.contains( selectedInput ))
        {
            selectedInput = null;
            return false;
        }
        if (!outputNames.contains( selectedOutput ))
        {
            selectedOutput = null;
            return false;
        }
        return true;
    }

    private void buildAvaiableLists()
    {
        Mixer.Info[] fmi = AudioSystem.getMixerInfo();
        for (Mixer.Info mi : fmi)
        {
            Line.Info[] inputLineInfos;
            Line.Info[] outputLineInfos;
            Mixer mixer = AudioSystem.getMixer( mi );
            String name = mi.getName();
            inputLineInfos = AudioSystem.getMixer( mi ).getTargetLineInfo();
            outputLineInfos = AudioSystem.getMixer( mi ).getSourceLineInfo();
            switch (getInputLineCount( mixer, inputLineInfos ))
            {
                case 0:
                    if (IC_StaticConst.LOG_AUDIO)
                        logger.info( name + " has no input lines" );
                    break;
                case 1:
                    inputNames.add( name );
                    break;
                default:
                    logger.info( name + " has " + inputLineInfos.length + " input lines" );
                    break;

            }
            switch (getOutputLineCount( mixer, outputLineInfos ))
            {
                case 0:
                    if (IC_StaticConst.LOG_AUDIO)
                    logger.info( name + " has no outpu lines" );
                    break;
                case 1:
                    outputNames.add( name );
                    break;
                default:
                    logger.info( name + " has " + outputLineInfos.length + " output lines" );
                    break;

            }
        }
    }

    private int getInputLineCount( Mixer mixer, Line.Info[] lis )
    {
        int result = 0;
        for (Line.Info li : lis)
        {
            try
            {
                Line line = mixer.getLine( li );
                if (line instanceof TargetDataLine)
                {
                    result++;
                }
                line.close();
            }
            catch (LineUnavailableException e)
            {
                logger.warning( "Ploblem getting line info" );
            }

        }
        return result;
    }

    private int getOutputLineCount( Mixer mixer, Line.Info[] lis )
    {
        int result = 0;
        for (Line.Info li : lis)
        {
            try
            {
                Line line = mixer.getLine( li );
                if (line instanceof SourceDataLine)
                {
                    result++;
                }
                line.close();
            }
            catch (LineUnavailableException e)
            {
                logger.warning( "Ploblem getting line info" );
            }
        }
        return result;
    }

    @Override
    public void receiveMessage( MessageChannelSelect mcs )
    {
        switch (mcs.cmd)
        {
            case REQUEST_LIST:
                pushInfo();
                break;
            case SET_INPUT:
                try
                {
                    selectedInput = mcs.selectedInput;
                }
                catch (Exception e)
                {
                    logger.warning( "Error reading input" );
                    selectedInput = null;
                }
                break;
            case SET_OUTPUT:
                try
                {
                    selectedOutput = mcs.selectedOutput;
                }
                catch (Exception e)
                {
                    logger.warning( "Error reading output" );
                    selectedOutput = null;
                }
                break;
            case ANSWER_LIST:
            default:
                logger.warning( "What do You want ?" );
                break;
        }
        if (checkPara())
        {
            para.clear();
            para.put( TAG_INPUT, selectedInput );
            para.put( TAG_OUTPUT, selectedOutput );
            para.flush();
            activateChild();
        }

    }

    private void pushInfo()
    {
        transmittChannelSelect.inputNames = inputNames;
        transmittChannelSelect.outputNames = outputNames;
        transmittChannelSelect.selectedInput = selectedInput;
        transmittChannelSelect.selectedOutput = selectedOutput;
        transmittChannelSelect.cmd = MessageChannelSelect.CMD.ANSWER_LIST;
        StaticStarter.getServerPort().publish( transmittChannelSelect );
    }

    private void activateChild()
    {

    }
}
