package subelements.sparse;

import java.util.Arrays;

import common.DataType;

import subelements.base.DataArray;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class SparseRaw
{
        public int[] ir;
        public int[] jc;
        
        public DataArray real_part;
        public DataArray imag_part;
        
        public DataType  real_data_type;
        public DataType  imag_data_type;
        
        public SparseRaw()
        {
                ir = null;
                jc = null;
                
                real_part = null;
                imag_part = null;
                
                real_data_type = null;
                imag_data_type = null;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((imag_data_type == null) ? 0 : imag_data_type.hashCode());
                result = prime * result + Arrays.hashCode(ir);
                result = prime * result + Arrays.hashCode(jc);
                result = prime * result + ((real_data_type == null) ? 0 : real_data_type.hashCode());
                return result;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj)
        {
                if (this == obj)
                        return true;
                
                if ( obj == null )
                        return false;
                
                if (getClass() != obj.getClass())
                        return false;
                
                SparseRaw other = (SparseRaw) obj;
                
                if ( real_data_type == null && other.real_data_type != null )
                        return false;
                
                if ( imag_data_type == null && other.imag_data_type != null )
                        return false;
                
                if ( real_part == null && other.real_part != null )
                        return false;
                
                if ( imag_part == null && other.imag_part != null )
                        return false;
                
                if ( real_data_type != other.real_data_type )
                        return false;
                
                if ( imag_data_type != other.imag_data_type )
                        return false;
                
                if ( real_part != null )
                {
                        if ( !(real_part.equals(other.real_part)) )
                                return false;
                }
                
                if ( imag_part != null )
                {
                        if ( !(imag_part.equals(other.imag_part)) )
                                return false;
                }
                
                if ( !Arrays.equals(ir,other.ir) )
                        return false;
                
                if ( !Arrays.equals(jc,other.jc) )
                        return false;
                
                return true;
        }
}
