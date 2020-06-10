package as.persistent;

import java.util.TreeMap;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import as.logging.LoggingInit;

// package privat
class SubTreeJson extends TreeMap<String, Object> implements IC_SubTreeBase
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggingInit.get(this);
    private final IC_SubTreeBase parent;
    //private JsonObject jsonObject;

    // package privat
    SubTreeJson(SubTreeJson parent)
    {
        this.parent = parent;
    }

    @Override
    public IC_SubTreeBase getOrCreateSubTree(String name)
    {
        IC_SubTreeBase result = null;
        if( containsKey(name) )
        {
            Object o = get(name);
            if( o instanceof SubTreeJson)
            {
                result = (SubTreeJson)o;
            }
            else
                logger.severe( "Json Structure Problem" );
        }
        if( result == null)
        {
            result = new SubTreeJson(this);
            put(name, result);
        }
        return (result);
    }

    // package privat
    JsonObject buildJsonObject()
    {
        JsonObjectBuilder job = Json.createObjectBuilder();
        for (String key : keySet())
        {
            Object o = get(key);
            if (o instanceof String)
            {
                job.add(key, (String) o);
            }
            else if (o instanceof Integer)
            {
                job.add(key, (int) o);
            }
            else if (o instanceof SubTreeJson)
            {
                job.add(key, ((SubTreeJson) o).buildJsonObject());
            }
            else
            {
                logger.severe("You should not be here!");
            }
        }
        JsonObject jo = job.build();
        return jo;
    }

    void readJsonObject(JsonObject jo)
    {
        clear();
        //jsonObject = jo;
        for (String jsonKey : jo.keySet())
        {
            JsonValue jv = jo.get(jsonKey);
            switch (jv.getValueType())
            {
            case NUMBER:
                put(jsonKey, jo.getInt(jsonKey));
            break;
            case STRING:
                put(jsonKey, jo.getString(jsonKey));
            break;
            case OBJECT:
            {
                SubTreeJson stj = new SubTreeJson(this);
                stj.readJsonObject(jo.getJsonObject(jsonKey));
                put(jsonKey,stj);
            }
            break;
            case ARRAY:
            case FALSE:
            case NULL:
            case TRUE:
            default:
                logger.severe("Non supported json type");
            break;
            }
        }
    }

    @Override
    public void flush()
    {
        parent.flush();
    }
}
