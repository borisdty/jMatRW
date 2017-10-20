package io.compression;

import io.RandomAccessFileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.zip.InflaterInputStream;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class StreamDecompressor extends InputStream
{
        private InflaterInputStream  iis;
        
        public StreamDecompressor(InputStream is)
        {
                this.iis  = new InflaterInputStream(is);
        }
        
        public StreamDecompressor(RandomAccessFile raFile, int limit)
        {
                this(new RandomAccessFileInputStream(raFile,limit));
        }
        
        public int read() throws IOException
        {
                return iis.read();
        }
        
        @Override
        public void close() throws IOException
        {
                iis.close();
        }
}
