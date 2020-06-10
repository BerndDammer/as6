/**
 * 
 */
package as.interim;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * creates input stream from a byte buffer
 * Allows reusing of class
 * @author manfred.hagemann
 *
 */
public class ByteBufferInputStream extends InputStream
{
    private ByteBuffer bb = null;
    public ByteBufferInputStream()
    {
    }

    /* (non-Javadoc)
     * @see java.io.InputStream#read()
     */
    @Override
    public int read() throws IOException
    {
        return bb.get();
    }
    public void setByteBuffer(ByteBuffer bb)
    {
        this.bb = bb;
    }
}
