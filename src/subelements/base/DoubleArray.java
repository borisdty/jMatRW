package subelements.base;

import java.nio.ByteOrder;
import java.util.Arrays;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class DoubleArray implements DataArray
{
        final private int FIELDLENGTH = 8;
        
        private double[] data_array;
        
        public DoubleArray()
        {
                this.data_array = new double[0];
        }
        
        public DoubleArray(double[] data_array)
        {
                this.data_array = data_array;
        }
        
        public void setData(double[] data_array)
        {
                this.data_array = data_array;
        }
        
        public double[] getData()
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
                                double value = data_array[i];
                                long   dl    = Double.doubleToRawLongBits(value);
                                
                                b[offset++] = (byte)(dl >> 56);
                                b[offset++] = (byte)(dl >> 48);
                                b[offset++] = (byte)(dl >> 40);
                                b[offset++] = (byte)(dl >> 32);
                                b[offset++] = (byte)(dl >> 24);
                                b[offset++] = (byte)(dl >> 16);
                                b[offset++] = (byte)(dl >> 8);
                                b[offset++] = (byte)(dl >> 0);
                        }
                }
                else
                {
                        for ( int i = 0; i < len; i++ )
                        {
                                double value = data_array[i];
                                long   dl    = Double.doubleToRawLongBits(value);
                                
                                b[offset++] = (byte)(dl >> 0);
                                b[offset++] = (byte)(dl >> 8);
                                b[offset++] = (byte)(dl >> 16);
                                b[offset++] = (byte)(dl >> 24);
                                b[offset++] = (byte)(dl >> 32);
                                b[offset++] = (byte)(dl >> 40);
                                b[offset++] = (byte)(dl >> 48);
                                b[offset++] = (byte)(dl >> 56);
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
                
                DoubleArray other = (DoubleArray) obj;
                
                if (!Arrays.equals(this.data_array, other.data_array))
                        return false;
                
                return true;
        }
        
        public DoubleArray toDoubleArray(boolean isSigned) // Should never happen
        {
                double[] data_copy = data_array.clone();
                return new DoubleArray(data_copy);
        }
}
