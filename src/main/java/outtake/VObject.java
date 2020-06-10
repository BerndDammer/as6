package outtake;

import java.util.Comparator;
import java.util.TreeMap;

public class VObject extends TreeMap<String, Object> implements VRoot
{
    private static final class StringComp implements Comparator<String>
    {
        @Override
        public int compare(String o1, String o2)
        {
            return o1.compareTo(o2);
        }
    }

    private static final Comparator<String> c1 = Comparator.comparing((String x) -> x);
//private static final Comparator<String> c2 = Comparator.comparing(String::toString());

    VObject()
    {
        // super(new StringComp());
        // super( (Comparator.comparing(( String x ) -> x) );
        super(c1);

    }
}
