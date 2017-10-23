package subelements.base;

import java.nio.ByteOrder;
import java.util.Arrays;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class FloatArray implements DataArray
{
        final private int FIELDLENGTH = 8;
        
        private float[] data_array;
        
        public FloatArray()
        {
                this.data_array = new float[0];
        }
        
        public FloatArray(float[] data_array)
        {
                this.data_array = data_array;
        }
        
        public void setData(float[] data_array)
        {
                this.data_array = data_array;
        }
        
        public float[] getData()
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
                                float value = data_array[i];
                                long   dl    = Float.floatToRawIntBits(value);
                                
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
                                float value = data_array[i];
                                long   dl    = Float.floatToRawIntBits(value);
                                
                                b[offset++] = (byte)(dl >> 0);
                                b[offset++] = (byte)(dl >> 8);
                                b[offset++] = (byte)(dl >> 16);
                                b[offset++] = (byte)(dl >> 24);
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
                
                FloatArray other = (FloatArray) obj;
                
                if (!Arrays.equals(this.data_array, other.data_array))
                        return false;
                
                return true;
        }
        
        public DoubleArray toDoubleArray(boolean isSigned) // Should never happen
        {
                double[] data_copy = new double[data_array.length];
                for ( int k = 0; k < data_array.length; k++ )
                        data_copy[k] = data_array[k];
                return new DoubleArray(data_copy);
        }
}
