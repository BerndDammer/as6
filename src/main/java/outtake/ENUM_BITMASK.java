package outtake;

public class ENUM_BITMASK
{
    static enum FLAGS
    {
        READ,
        WRITE,
        ACCEPT,
        CONNECT;
    }

    private boolean[] has = new boolean[FLAGS.values().length];

    public ENUM_BITMASK()
    {
        // has = new boolean[MASK.values().length];
        for (int i = 0; i < FLAGS.values().length; i++)
        {
            has[i] = false;
        }
    }

    public void clr()
    {
        // has = new boolean[MASK.values().length];
        for (int i = 0; i < FLAGS.values().length; i++)
        {
            has[i] = false;
        }
    }

    public void set(FLAGS bit, boolean set)
    {
        has[bit.ordinal()] = set;
    }

    public boolean ask(FLAGS bit)
    {
        return has[bit.ordinal()];
    }

    public boolean or(FLAGS... bits)
    {
        boolean result = false;
        for (FLAGS bit : bits)
        {
            result |= has[bit.ordinal()];
        }

        return result;
    }

    public boolean and(FLAGS... bits)
    {
        boolean result = true;
        for (FLAGS bit : bits)
        {
            result &= has[bit.ordinal()];
        }

        return result;
    }

    public static ENUM_BITMASK create(FLAGS... bits)
    {
        ENUM_BITMASK result = new ENUM_BITMASK();
        result.clr();
        for (FLAGS bit : bits)
        {
            result.set(bit, true);
        }

        return result;
    }
}
