package as.persistent;

import java.util.Map;

public interface IC_SubTreeBase extends Map<String, Object>
{
    IC_SubTreeBase getOrCreateSubTree(String name);
    public void flush();
}
