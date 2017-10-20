package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Implements an InputStream based on a RandomAccessFile
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class RandomAccessFileInputStream extends InputStream
{
        private RandomAccessFile  file;
        private int               readlimit;
        private long              markpos = 0;
        
        public RandomAccessFileInputStream(RandomAccessFile file)
        {
                this.file  = file;
                
                int limit = Integer.MAX_VALUE;
                
                try {
                        limit = (int)(file.length()-file.getFilePointer());
                }
                catch (IOException e) {
                        e.printStackTrace();
                }
                
                this.readlimit = limit;
        }
        
        public RandomAccessFileInputStream(RandomAccessFile file, int limit)
        {
                this.file      = file;
                this.readlimit = limit;
        }
        
        @Override
        public synchronized int read() throws IOException
        {
                if ( readlimit <= 0 )
                        return -1;
                
                int i = file.read() & 0xFF;
                
                readlimit--;
                
                return i;
        }
        
        @Override
        public int available() throws IOException
        {
                return (int)(file.length() - file.getFilePointer());
        }
        
        /**
         * Set the current marked position in the stream.
         * RandomAccessFileInputStream objects are marked at position zero by
         * default when constructed. They may be marked at another
         * position by this method.
         * A mark has no restriction on how many read operations can be performed
         * before the mark gets invalidated.
         *
         * <p> Note: The <code>dummy</code> input argument for this method
         *  has no meaning.
         *  The method behavior is identical to the method mark().
         */
        public synchronized void mark(int dummy)
        {
                try {
                        markpos = file.getFilePointer();
                }
                catch (IOException e) {
                        //System.err.println(e.getStackTrace());
                        //System.err.println(Arrays.toString(e.getStackTrace()));
                        e.printStackTrace();
                        return;
                }
        }
        
        public synchronized void mark()
        {
                mark(0);
        }
        
        @Override
        public synchronized void reset() throws IOException
        {
                if ( markpos > file.length() )
                    throw new IOException("Resetting to invalid mark");
                file.seek(markpos);
        }
        
        @Override
        public boolean markSupported() {
                return true;
        }
}
