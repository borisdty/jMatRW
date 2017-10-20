package io;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.ByteOrder;

/**
 * This data input stream lets an application read primitive Java data
 * types from an underlying input stream in a machine-independent
 * way. It is very similar to java.io.DataInputStream, but has capabilities
 * read data with flexible byte ordering (BIG-ENDIAN or LITTLE_ENDIAN).
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class MyDataInputStream extends FilterInputStream
{
        private ByteOrder    byteOrder;
        
        /**
         * Creates a MyDataInputStream that uses the specified
         * underlying InputStream and byte order.
         *
         * @param  in          the specified input stream
         * @param  byteOrder   the byte order to assume when reading short, int, ...
         */
        public MyDataInputStream(InputStream in, ByteOrder byteOrder)
        {
                super(in);
                this.byteOrder = byteOrder;
        }
        
        /**
         * Returns the byte order assumed when reading multiple bytes
         */
        public ByteOrder getByteOrder()
        {
                return byteOrder;
        }
        
        /**
         * See the general contract of the <code>readBoolean</code>
         * method of <code>DataInput</code>.
         * <p>
         * Bytes for this operation are read from the contained input stream.
         *
         * @return     the <code>boolean</code> value
         * @exception  EOFException  if this input stream has reached the end.
         * @exception  IOException   the stream has been closed and the contained
         *             input stream does not support reading after close, or
         *             another I/O error occurs.
         * @see        java.io.FilterInputStream#in
         */
        public final boolean readBoolean() throws IOException
        {
                int ch = in.read();
                if (ch < 0) // Test for EOF
                        throw new EOFException();
                return (ch != 0);
        }
        
        /**
         * See the general contract of the <code>readByte</code>
         * method of <code>DataInput</code>.
         * <p>
         * Bytes for this operation are read from the contained input stream.
         *
         * @return     the next byte of this input stream as a signed 8-bit
         *             <code>byte</code>.
         * @exception  EOFException  if this input stream has reached the end.
         * @exception  IOException   the stream has been closed and the contained
         *             input stream does not support reading after close, or
         *             another I/O error occurs.
         * @see        java.io.FilterInputStream#in
         */
        public final byte readByte() throws IOException
        {
                int ch = in.read();
                if (ch < 0) // Test for EOF
                        throw new EOFException();
                return (byte)(ch);
        }
        
        /**
         * See the general contract of the <code>readUnsignedByte</code>
         * method of <code>DataInput</code>.
         * <p>
         * Bytes for this operation are read from the contained input stream.
         *
         * @return     the next byte of this input stream; return type is integer,
         *             interpreted as an unsigned 8-bit number.
         * @exception  EOFException  if this input stream has reached the end.
         * @exception  IOException   the stream has been closed and the contained
         *             input stream does not support reading after close, or
         *             another I/O error occurs.
         * @see         java.io.FilterInputStream#in
         */
        public final int readUnsignedByte() throws IOException
        {
                int ch = in.read();
                if (ch < 0) // Test for EOF
                        throw new EOFException();
                return ch;
        }
        
        /**
         * See the general contract of the <code>readShort</code>
         * method of <code>DataInput</code>.
         * <p>
         * Bytes for this operation are read from the contained input stream.
         *
         * @return     short value, build from two bytes of the input stream,
         *             signed 16-bit number
         * @exception  EOFException  if this input stream reaches the end before
         *             reading two bytes.
         * @exception  IOException   the stream has been closed and the contained
         *             input stream does not support reading after close, or
         *             another I/O error occurs.
         * @see        java.io.FilterInputStream#in
         */
        public final short readShort() throws IOException
        {
                int ch1 = in.read();
                int ch2 = in.read();
                if ((ch1 | ch2) < 0)
                        throw new EOFException();
                if (byteOrder == ByteOrder.BIG_ENDIAN)
                        return (short)((ch1 << 8) + (ch2 << 0));
                else
                        return (short)((ch2 << 8) + (ch1 << 0));
        }
        
        /**
         * See the general contract of the <code>readUnsignedShort</code>
         * method of <code>DataInput</code>.
         * <p>
         * Bytes for this operation are read from the contained input stream.
         *
         * @return     int value, build from two bytes of the input stream,
         *             unsigned 16-bit number
         * @exception  EOFException  if this input stream reaches the end before
         *             reading two bytes.
         * @exception  IOException   the stream has been closed and the contained
         *             input stream does not support reading after close, or
         *             another I/O error occurs.
         * @see        java.io.FilterInputStream#in
         */
        public final int readUnsignedShort() throws IOException
        {
                int ch1 = in.read();
                int ch2 = in.read();
                if (ch1 < 0 || ch2 < 0)
                        throw new EOFException();
                if (byteOrder == ByteOrder.BIG_ENDIAN)
                        return (ch1 << 8) + (ch2 << 0);
                else
                        return (ch2 << 8) + (ch1 << 0);
        }
        
        /**
         * See the general contract of the <code>readChar</code>
         * method of <code>DataInput</code>.
         * <p>
         * Bytes for this operation are read from the contained input stream.
         *
         * @return     <code>char</code> value, build from two bytes of the input stream
         * @exception  EOFException  if this input stream reaches the end before
         *             reading two bytes.
         * @exception  IOException   the stream has been closed and the contained
         *             input stream does not support reading after close, or
         *             another I/O error occurs.
         * @see        java.io.FilterInputStream#in
         */
        public final char readChar() throws IOException
        {
                int ch1 = in.read();
                int ch2 = in.read();
                if (ch1 < 0 || ch2 < 0)
                        throw new EOFException();
                if (byteOrder == ByteOrder.BIG_ENDIAN)
                        return (char)((ch1 << 8) + (ch2 << 0));
                else
                        return (char)((ch2 << 8) + (ch1 << 0));
        }
        
        /**
         * See the general contract of the <code>readInt</code>
         * method of <code>DataInput</code>.
         * <p>
         * Bytes for this operation are read from the contained input stream.
         *
         * @return     <code>int</code> value, build from four bytes of the input stream
         * @exception  EOFException  if this input stream reaches the end before
         *             reading four bytes.
         * @exception  IOException   the stream has been closed and the contained
         *             input stream does not support reading after close, or
         *             another I/O error occurs.
         * @see        java.io.FilterInputStream#in
         */
        public final int readInt() throws IOException
        {
                int ch1 = in.read();
                int ch2 = in.read();
                int ch3 = in.read();
                int ch4 = in.read();
                if ((ch1 | ch2 | ch3 | ch4) < 0)
                        throw new EOFException();
                if (byteOrder == ByteOrder.BIG_ENDIAN)
                        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
                else
                        return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0));
        }
        
        /**
         * Similar to the general contract of the <code>readUnsignedShort</code>
         * method of <code>DataInput</code>.
         * <p>
         * Bytes for this operation are read from the contained input stream.
         *
         * @return     long value, build from four bytes of the input stream,
         *             unsigned 32-bit number
         * @exception  EOFException  if this input stream reaches the end before
         *             reading two bytes.
         * @exception  IOException   the stream has been closed and the contained
         *             input stream does not support reading after close, or
         *             another I/O error occurs.
         * @see        java.io.FilterInputStream#in
         */
        public final long readUnsignedInt() throws IOException
        {
                int ch1 = in.read();
                int ch2 = in.read();
                int ch3 = in.read();
                int ch4 = in.read();
                if ((ch1 | ch2 | ch3 | ch4) < 0)
                        throw new EOFException();
                if (byteOrder == ByteOrder.BIG_ENDIAN)
                        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0)) & 0xFFFFFFFFL;
                else
                        return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0)) & 0xFFFFFFFFL;
        }
        
        /**
         * See the general contract of the <code>readLong</code>
         * method of <code>DataInput</code>.
         * <p>
         * Bytes for this operation are read from the contained input stream.
         *
         * @return     <code>long</code> value, build from eight bytes of the input stream
         * @exception  EOFException  if this input stream reaches the end before
         *             reading eight bytes.
         * @exception  IOException   the stream has been closed and the contained
         *             input stream does not support reading after close, or
         *             another I/O error occurs.
         * @see        java.io.FilterInputStream#in
         */
        public final long readLong() throws IOException
        {
                long l1 = readUnsignedInt();
                long l2 = readUnsignedInt();
                if (byteOrder == ByteOrder.BIG_ENDIAN)
                        return ((l1 << 32) + (l2 << 0));
                else
                        return ((l2 << 32) + (l1 << 0));
        }
        
        /**
         * See the general contract of the <code>readFloat</code>
         * method of <code>DataInput</code>.
         * <p>
         * Bytes
         * for this operation are read from the contained
         * input stream.
         *
         * @return     the next four bytes of this input stream, interpreted as a
         *             <code>float</code>.
         * @exception  EOFException  if this input stream reaches the end before
         *             reading four bytes.
         * @exception  IOException   the stream has been closed and the contained
         *             input stream does not support reading after close, or
         *             another I/O error occurs.
         * @see        io.MyDataInputStream#readInt()
         * @see        java.lang.Float#intBitsToFloat(int)
         */
        public final float readFloat() throws IOException
        {
                return Float.intBitsToFloat(readInt());
        }
        
        /**
         * See the general contract of the <code>readDouble</code>
         * method of <code>DataInput</code>.
         * <p>
         * Bytes for this operation are read from the contained input stream.
         *
         * @return     the next eight bytes of this input stream, interpreted as a
         *             <code>double</code>.
         * @exception  EOFException  if this input stream reaches the end before
         *             reading eight bytes.
         * @exception  IOException   the stream has been closed and the contained
         *             input stream does not support reading after close, or
         *             another I/O error occurs.
         * @see        io.MyDataInputStream#readLong()
         * @see        java.lang.Double#longBitsToDouble(long)
         */
        public final double readDouble() throws IOException
        {
                return Double.longBitsToDouble(readLong());
        }
}
