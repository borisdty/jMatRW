package common;

import java.util.Arrays;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxSparseDataObject extends MxNumericDataObject
{
        public int[] ir;
        public int[] jc;
        
        public int nzmax;
        
        public MxSparseDataObject()
        {
                ir = null;
                jc = null;
                
                nzmax = 0;
        }
        
        public MxSparseDataObject( MxDataObject other )
        {
                super(other);
                
                ir = null;
                jc = null;
                
                nzmax = 0;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
                final int prime = 31;
                int result = super.hashCode();
                result = prime * result + Arrays.hashCode(ir);
                result = prime * result + Arrays.hashCode(jc);
                result = prime * result + nzmax;
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
                
                if (!super.equals(obj))
                        return false;
                
                if (getClass() != obj.getClass())
                        return false;
                
                MxSparseDataObject other = (MxSparseDataObject) obj;
                
                if (!Arrays.equals(ir, other.ir))
                        return false;
                
                if (!Arrays.equals(jc, other.jc))
                        return false;
                
                if (nzmax != other.nzmax)
                        return false;
                
                return true;
        }
}
