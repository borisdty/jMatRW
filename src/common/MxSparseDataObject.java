package common;

import java.util.Arrays;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxSparseDataObject extends MxNumericDataObject
{
        private int[] ir;
        private int[] jc;
        
        private int nzmax;
        
        public MxSparseDataObject()
        {
                super();
                classType = MxClassID.mxSPARSE_CLASS;
                
                ir = null;
                jc = null;
                
                nzmax = 0;
        }
        
        public MxSparseDataObject( MxDataObject other )
        {
                super(other);
                
                /*
                 * If other has not defined the classType, we can do it now
                 * If other has another classType defined, we overwrite it here anyway
                 * */
                classType = MxClassID.mxSPARSE_CLASS;
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
        
        public int getNzMax()
        {
                return nzmax;
        }
        
        public int[] getRowIndeices()
        {
                return ir.clone();
        }
        
        public int[] getColumnIndices()
        {
                return jc.clone();
        }
        
        public void setNzMax(int nzmax)
        {
                this.nzmax = nzmax;
        }
        
        public void setRowIndeices(int[] ir)
        {
                this.ir = ir;
        }
        
        public void setColumnIndices(int[] jc)
        {
                this.jc = jc;
        }
}
