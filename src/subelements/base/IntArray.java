package subelements.base;

import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * This class encapsulates an int-array and provides different
 * conversion methods to arrays of other data types.
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class IntArray implements DataArray
{
        final private int FIELDLENGTH = 4;
        
        private int[] data_array;
        
        public IntArray()
        {
                this.data_array = new int[0];
        }
        
        public IntArray(int[] data_array)
        {
                this.data_array = data_array;
        }
        
        public void setData(int[] data_array)
        {
                this.data_array = data_array;
        }
        
        public int[] getData()
        {
                return data_array;
        }
        
        public int length()
        {
                return data_array.length;
        }
        
        /**
         * Converts an int-array into a byte-array assuming a
         * given byte-order of the integer data elements.
         * 
         * @param byte_order  the byte-order assumed for the int data items
         * @return a byte-array with elements (i:i+3) representing the integer
         *         at index i in the integer array (acc. to given byte_order)
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
                                int value = data_array[i];
                                
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
                                int value = data_array[i];
                                
                                b[offset++] = (byte)(value >> 0);
                                b[offset++] = (byte)(value >> 8);
                                b[offset++] = (byte)(value >> 16);
                                b[offset++] = (byte)(value >> 24);
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
                
                IntArray other = (IntArray) obj;
                
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
                        for ( int i = 0; i < data_array.length; i++ )
                                a[i] = data_array[i] & 0xFFFFFFFFL;
                }
                
                return new DoubleArray(a);
        }
}
