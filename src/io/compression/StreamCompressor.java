package io.compression;

import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class StreamCompressor extends OutputStream
{
        ByteArrayOutputStream   baos;
        DeflaterOutputStream    dos;
        
        public StreamCompressor()
        {
                baos       = new ByteArrayOutputStream();
                dos        = new DeflaterOutputStream(baos);
        }
        
        public synchronized void write(int b) throws IOException
        {
                dos.write(b); // b -> dos -> baos
        }
        
        public byte[] toByteAray() throws IOException
        {
                dos.close();
                baos.close();
                byte[] compressedBytes = baos.toByteArray();
                return compressedBytes;
        }
}
