package as.persistent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import as.backend.midi.MidiMapper;
import as.logging.LoggingInit;
import as.persistent.midi.MidiMapperKorgNanoKontrolStudio;
import as.persistent.midi.MidiMapperLDP8;
import as.persistent.midi.MidiMapperMidiFighterTwister;
import as.persistent.templates.ResourceVector;

public class PersistentCentral
{

    private final Logger logger = LoggingInit.get(this);
    private final SubTreeJson rootObject;

    InputStream getParameterInputStream(String name)
    {
        InputStream result = null;
        File file;
        file = new File(name);
        try
        {
            FileInputStream fis = new FileInputStream(file);
            result = fis;
        }
        catch (FileNotFoundException e)
        {
            logger.info("No File; try resource");
        }
        if (result == null)
        {
            result = ResourceVector.class.getResourceAsStream(name);
        }
        if (result == null)
        {
            logger.severe("No file; no resource; this should not happen!");
        }
        return (result);
    }

    private PersistentCentral()
    {
        rootObject = new SubTreeJsonRoot("asroot", this);
    }

    private SubTreeJson getRootObject()
    {
        return rootObject;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    //
    // singleton results
    //
    
    public List<MidiMapper> getMidiMappers()
    {
    	LinkedList<MidiMapper> mappings = new LinkedList<>();
    	mappings.add( new MidiMapperLDP8());
    	mappings.add( new MidiMapperMidiFighterTwister());
    	mappings.add( new MidiMapperKorgNanoKontrolStudio());
    	return mappings;
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    //
    // static part
    //
    private static final PersistentCentral singletonPersistentCentral = new PersistentCentral();
    public static PersistentCentral getPersPersistentCentral()
    {
    	return(singletonPersistentCentral);
    }
    public static IC_SubTreeBase subPlatformSelector()
    {
        IC_SubTreeBase result = singletonPersistentCentral.getRootObject().getOrCreateSubTree("PlatformSelector");
        return result;
    }
    public static IC_SubTreeBase subChannelSelector()
    {
        IC_SubTreeBase result = singletonPersistentCentral.getRootObject().getOrCreateSubTree("ChannelSelector");
        return result;
    }
}
