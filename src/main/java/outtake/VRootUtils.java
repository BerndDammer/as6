package outtake;

import java.util.Map;

//package private
class VRootUtils
{
    static boolean checkRootElement(Object o)
    {
        boolean result = true;

        if (o instanceof VArray)
        {
            result = checkArray((VArray) o);
        } else if (o instanceof VObject)
        {
            result = checkObject((VObject) o);
        } else
        {
            result = false;
        }
        return result;
    }

    static boolean checkElement(Object o)
    {
        boolean result = true;

        if (o instanceof VArray)
        {
            result = checkArray((VArray) o);
        } else if (o instanceof VObject)
        {
            result = checkObject((VObject) o);
        } else if (o instanceof String)
        {
            result = true;
        } else if (o instanceof Double)
        {
            result = true;
        } else if (o instanceof Boolean)
        {
            result = true;
//        } else if (o == Void.TYPE) // allowed for null
//        {
//            result = true;
        } else if (o == null)
        {
            result = true;
        } else
        {
            result = false;
        }
        return result;
    }

    static boolean checkArray(VArray array)
    {
        boolean result = true;

        for (Object o : array)
        {
            result &= checkElement(o);
        }
        return result;
    }

    static boolean checkObject(VObject vo)
    {
        boolean result = true;

//        for (Object o : vo.values())
//        {
//            result &= checkElement(o);
//        }
        for (Map.Entry<String, Object> pair : vo.entrySet())
        {
            result &= checkElement(pair.getValue());
        }
        return result;
    }

}
