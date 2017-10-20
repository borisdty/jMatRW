package common;

import java.nio.ByteOrder;

/**
 * Provides static conversion function from byte-array (byte[]) to
 * - byte (1 byte)
 * - unsigned byte (returned as short) (1 byte)
 * - char (2 bytes)
 * - short (2 bytes)
 * - unsigned short (returned as int) (2 bytes)
 * - int (4 bytes)
 * - unsigned int (returned as long) (4 bytes)
 * - long (8 bytes)
 * - float (4 bytes) acc. to IEEE754
 * - double (8 bytes) acc. to IEEE754
 *
 * Byte order can be chosen between BIG_ENDIAN and LITTLE_ENDIAN
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class Bytes
{
        public static byte toByte(byte[] in, int offset, ByteOrder byte_order)
        {
                byte out = in[offset];
                return out;
        }
        
        public static short toUByte(byte[] in, int offset, ByteOrder byte_order)
        {
                short out = (short)(in[offset]&0xFF);
                return out;
        }
        
        public static char toChar(byte[] in, int offset, ByteOrder byte_order)
        {
                char out = 0;
                
                if ( byte_order.equals(ByteOrder.BIG_ENDIAN) )
                {
                        out |= (in[offset+0]&0xFF) <<  8;
                        out |= (in[offset+1]&0xFF) <<  0;
                }
                else
                {
                        out |= (in[offset+1]&0xFF) <<  8;
                        out |= (in[offset+0]&0xFF) <<  0;
                }
                
                return out;
        }
        
        public static short toShort(byte[] in, int offset, ByteOrder byte_order)
        {
                short out = 0;
                
                if ( byte_order.equals(ByteOrder.BIG_ENDIAN) )
                {
                        out |= (in[offset+0]&0xFF) <<  8;
                        out |= (in[offset+1]&0xFF) <<  0;
                }
                else
                {
                        out |= (in[offset+1]&0xFF) <<  8;
                        out |= (in[offset+0]&0xFF) <<  0;
                }
                
                return out;
        }
        
        public static int toUShort(byte[] in, int offset, ByteOrder byte_order)
        {
                int out = 0;
                
                if ( byte_order.equals(ByteOrder.BIG_ENDIAN) )
                {
                        out |= (in[offset+0]&0xFF) <<  8;
                        out |= (in[offset+1]&0xFF) <<  0;
                }
                else
                {
                        out |= (in[offset+1]&0xFF) <<  8;
                        out |= (in[offset+0]&0xFF) <<  0;
                }
                
                return out;
        }
        
        public static int toInt(byte[] in, int offset, ByteOrder byte_order)
        {
                if (offset + Integer.BYTES > in.length)
                        throw new IndexOutOfBoundsException("Bytes::toInt: " +
                                        "Not enough bytes available for complete conversion.");
                
                int out = 0;
                
                if ( byte_order.equals(ByteOrder.BIG_ENDIAN) )
                {
                        for(int i = offset; i < (offset + Integer.BYTES); i++)
                        {
                                out <<= 8;
                                out ^= in[i] & 0xFF;
                        }
                }
                else
                {
                        for(int i = offset + Integer.BYTES-1; i >= offset; i--)
                        {
                                out <<= 8;
                                out ^= in[i] & 0xFF;
                        }
                }
                
                return out;
        }
        
        public static long toUInt(byte[] in, int offset, ByteOrder byte_order)
        {
                long out = 0;
                
                if ( byte_order.equals(ByteOrder.BIG_ENDIAN) )
                {
                        out |= (in[offset+0]&0xFFL) << 24;
                        out |= (in[offset+1]&0xFFL) << 16;
                        out |= (in[offset+2]&0xFFL) <<  8;
                        out |= (in[offset+3]&0xFFL) <<  0;
                }
                else
                {
                        out |= (in[offset+3]&0xFFL) << 24;
                        out |= (in[offset+2]&0xFFL) << 16;
                        out |= (in[offset+1]&0xFFL) <<  8;
                        out |= (in[offset+0]&0xFFL) <<  0;
                }
                
                return out;
        }
        
        public static long toLong(byte[] in, int offset, ByteOrder byte_order)
        {
                if (offset + Long.BYTES > in.length)
                        throw new IndexOutOfBoundsException("Bytes::toLong: " +
                                        "Not enough bytes available for complete conversion.");
                
                long out = 0;
                
                if ( byte_order.equals(ByteOrder.BIG_ENDIAN) )
                {
                        for(int i = offset; i < offset + Long.BYTES; i++)
                        {
                                out <<= 8;
                                //out ^= in[i] & 0xFFL;
                                out ^= in[i] & 0xFF;
                        }
                }
                else
                {
                        for(int i = offset + Long.BYTES-1; i >= offset; i--)
                        {
                                out <<= 8;
                              //out ^= in[i] & 0xFFL;
                                out ^= in[i] & 0xFF;
                        }
                }
                
                return out;
        }
        
        public static float toFloat(byte[] in, int offset, ByteOrder byte_order)
        {
                int bits = toInt(in, offset, byte_order);
                return Float.intBitsToFloat(bits);
        }
        
        public static double toDouble(byte[] in, int offset, ByteOrder byte_order)
        {
                long bits = toLong(in, offset, byte_order);
                return Double.longBitsToDouble(bits);
        }
}
