package as.persistent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Logger;

import as.logging.LoggingInit;
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
    // static part
    //
    private static final PersistentCentral singletonPersistentCentral = new PersistentCentral();

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
