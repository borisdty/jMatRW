package subelements.base;

import java.nio.ByteOrder;
import java.util.Arrays;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class BooleanArray implements DataArray
{
        final private int FIELDLENGTH = 1;
        
        private boolean[] data_array;
        
        public BooleanArray()
        {
                this.data_array = new boolean[0];
        }
        
        public BooleanArray(boolean[] data_array)
        {
                this.data_array = data_array;
        }
        
        public void setData(boolean[] data_array)
        {
                this.data_array = data_array;
        }
        
        public boolean[] getData()
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
                
                for ( int i = 0; i < len; i++ )
                {
                        boolean value = data_array[i];
                        
                        b[offset++] = (byte)(value==true ? 1:0);
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
                
                BooleanArray other = (BooleanArray) obj;
                
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
                                a[i] = (data_array[i] ? 1.0 : 0.0);
                }
                else
                {
                        for ( int i = 0; i < data_array.length; i++ )
                                a[i] = (data_array[i] ? 1.0 : 0.0);
                }
                
                return new DoubleArray(a);
        }
}
