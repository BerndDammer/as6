package as.persistent;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

import as.logging.LoggingInit;

class SubTreeJsonRoot extends SubTreeJson
{
    private static final long serialVersionUID = 1L;
    private final Map<String, Object> prettyPrintingProperties;
    private final Logger logger = LoggingInit.get(this);
    private final String name;

    SubTreeJsonRoot(String name, PersistentCentral pc)
    {
        super(null);
        prettyPrintingProperties = new HashMap<>(1);
        prettyPrintingProperties.put(JsonGenerator.PRETTY_PRINTING, true);

        this.name = name + ".json";
        InputStream is = pc.getParameterInputStream(this.name);
        JsonObject jo = null;
        if( is != null)
        {
            JsonReader rdr = null;
            rdr = Json.createReader(is);
            jo = rdr.readObject();
            readJsonObject(jo);
            try
            {
                is.close();
            }
            catch (Exception e)
            {
                logger.throwing(null, null, e);
            }
        }
        if( ! containsKey("General") )
        {
            IC_SubTreeBase genInfo = getOrCreateSubTree("General" );
            genInfo.put("SpecVersionIndex", 1 );
            genInfo.put("Date", "now");
            genInfo.flush();
        }
        // --------------
    }

    // must override because root has no parent
    @Override
    public void flush()
    {
        JsonObject jo = buildJsonObject();
        try
        {
            FileWriter fw = new FileWriter(name);
            JsonWriterFactory jwf = Json.createWriterFactory(prettyPrintingProperties);
            JsonWriter jw = jwf.createWriter(fw);
            jw.writeObject(jo);
            jw.close();
            fw.close();
        }
        catch (IOException e)
        {
            logger.throwing(null, null, e);
        }
    }
}