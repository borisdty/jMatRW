package subelements.base;

import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * This class encapsulates an int-array and provides different
 * conversion methods to arrays of other data types.
 *
 */
public class LongArray implements DataArray
{
        final private int FIELDLENGTH = 8;
        
        private long[] data_array;
        
        public LongArray()
        {
                this.data_array = new long[0];
        }
        
        public LongArray(long[] data_array)
        {
                this.data_array = data_array;
        }
        
        public void setData(long[] data_array)
        {
                this.data_array = data_array;
        }
        
        public long[] getData()
        {
                return data_array;
        }
        
        public int length()
        {
                return data_array.length;
        }
        
        /**
         * Converts a long-array into a byte-array assuming a
         * given byte-order of the long-integer data elements.
         * 
         * @param byte_order  the byte-order assumed for the long-int data items
         * @return a byte-array with elements (i:i+7) representing the long-integer
         *         at index i in the long-integer array (acc. to given byte_order)
         */
        public byte[] dataToByteArray(ByteOrder byte_order)
        {
                int len = data_array.length;
                
                byte[] b = new byte[len*FIELDLENGTH];
                
                int    offset = 0;
                
                if ( byte_order == ByteOrder.BIG_ENDIAN )
                {
                        for ( int i = 0; i < len; i++ )
                        {
                                long value = data_array[i];
                                
                                b[offset++] = (byte)(value >> 56);
                                b[offset++] = (byte)(value >> 48);
                                b[offset++] = (byte)(value >> 40);
                                b[offset++] = (byte)(value >> 32);
                                b[offset++] = (byte)(value >> 24);
                                b[offset++] = (byte)(value >> 16);
                                b[offset++] = (byte)(value >> 8);
                                b[offset++] = (byte)(value >> 0);
                        }
                }
                else
                {
                        for ( int i = 0; i < len; i++ )
                        {
                                long value = data_array[i];
                                
                                b[offset++] = (byte)(value >> 0);
                                b[offset++] = (byte)(value >> 8);
                                b[offset++] = (byte)(value >> 16);
                                b[offset++] = (byte)(value >> 24);
                                b[offset++] = (byte)(value >> 32);
                                b[offset++] = (byte)(value >> 40);
                                b[offset++] = (byte)(value >> 48);
                                b[offset++] = (byte)(value >> 56);
                        }
                }
                
                return b;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + Arrays.hashCode(data_array);
                return result;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj)
        {
                if (this == obj) // Check for equal reference
                        return true;
                
                if (obj == null)
                        return false;
                
                // Any sub-class of DataArray would be true with instanceof() operator ... so we do not use it
                // http://onewebsql.com/blog/on-equals
                if (getClass() != obj.getClass())
                        return false;
                
                LongArray other = (LongArray) obj;
                
                if (!Arrays.equals(this.data_array, other.data_array))
                        return false;
                
                return true;
        }
        
        public DoubleArray toDoubleArray(boolean isSigned)
        {
                double[] a = new double[data_array.length];
                
                if ( isSigned )
                {
                        for ( int i = 0; i < data_array.length; i++ )
                                a[i] = data_array[i];
                }
                else
                {
                        double tmp = Math.pow(2,63);
                        // Does it make sense?
                        for ( int i = 0; i < data_array.length; i++ )
                                //a[i] = (data_array[i] & 0x7fffffffffffffffL) + (data_array[i] < 0 ? tmp : 0.0);
                                a[i] = data_array[i] >= 0 ? data_array[i] : ((-data_array[i]) + tmp);
                }
                
                return new DoubleArray(a);
        }
}
