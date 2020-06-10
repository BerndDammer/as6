/**
 * 
 */
package as.interim;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * creates output steam for byte buffer
 * @author manfred.hagemann
 *
 */
public class ByteBufferOutputStream extends OutputStream
{
    private ByteBuffer bb = null;
    public ByteBufferOutputStream()
    {
    }

    /* (non-Javadoc)
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public void write( int b ) throws IOException
    {
        bb.put( (byte)b );
    }
    public void setByteBuffer(ByteBuffer bb)
    {
        this.bb = bb;
    }
}
