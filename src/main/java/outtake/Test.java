package outtake;

public class Test
{
    public static void main(final String[] args)
    {
        new Test();
    }

    public Test()
    {
        VObject o = new VObject();
        o.put("a", new VObject());
        VObject l1 = (VObject) o.get("a");
        l1.put("ghjg§", new Double(33.678));
        l1.put("b12", "Aber Doch nicht hier");
        check(o);
        check(o);
        VArray agood = new VArray();
        o.put("array_of_good", agood);
        VArray abad = new VArray();
        o.put("array_of_bad", abad);
        agood.add(new Double(22));
        agood.add(222.4444);
        agood.add(null);
        agood.add(false);
        agood.add(true);
        check(o);
        o.put("d1", new Double(2.2222));
        check(o);
        o.put("i", new Integer(22));
        abad.add(22);
        check(o);
        abad.add(Void.TYPE);
        check(o);

        System.out.println(o);
    }

    private void check(VRoot r)
    {
        String s;
        if (VRoot.check(r))
        {
            s = "OK";
        } else
        {
            s = "KKKK";
        }
        System.out.println(s);
    }
}
