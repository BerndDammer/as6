package outtake;

public interface VRoot
{
    public static boolean check(VRoot root)
    {
        return VRootUtils.checkRootElement(root);
    }
}
