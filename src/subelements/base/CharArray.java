package subelements.base;

import java.nio.ByteOrder;
import java.util.Arrays;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class CharArray implements DataArray
{
        final private int FIELDLENGTH = 2;
        
        private char[] data_array;
        
        public CharArray()
        {
                this.data_array = new char[0];
        }
        
        public CharArray(char[] data_array)
        {
                this.data_array = data_array;
        }
        
        public void setData(char[] data_array)
        {
                this.data_array = data_array;
        }
        
        public char[] getData()
        {
                return data_array;
        }
        
        public int length()
        {
                return data_array.length;
        }
        
        public byte[] dataToByteArray(ByteOrder byte_order)
        {
                int len = data_array.length;
                
                byte[] b = new byte[len*FIELDLENGTH];
                
                int    offset = 0;
                
                if ( byte_order == ByteOrder.BIG_ENDIAN )
                {
                        for ( int i = 0; i < len; i++ )
                        {
                                char value = data_array[i];
                                
                                b[offset++] = (byte)(value >> 8);
                                b[offset++] = (byte)(value >> 0);
                        }
                }
                else
                {
                        for ( int i = 0; i < len; i++ )
                        {
                                char value = data_array[i];
                                
                                b[offset++] = (byte)(value >> 0);
                                b[offset++] = (byte)(value >> 8);
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
                
                CharArray other = (CharArray) obj;
                
                if (!Arrays.equals(this.data_array, other.data_array))
                        return false;
                
                return true;
        }
        
        public DoubleArray toDoubleArray(boolean isSigned)
        {
                double[] a = new double[data_array.length];
                
                for ( int i = 0; i < data_array.length; i++ )
                        a[i] = data_array[i];
                
                return new DoubleArray(a);
        }
}
