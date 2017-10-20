package common;

import java.util.Arrays;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxStructDataObject extends MxDataObject
{
        public MxDataObject[] data_vec;
        
        public MxStructDataObject()
        {
                data_vec = null;
        }
        
        public MxStructDataObject( MxDataObject other )
        {
                super(other);
                
                data_vec = null;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
                final int prime = 31;
                int result = super.hashCode();
                result = prime * result + Arrays.hashCode(data_vec);
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
                
                MxStructDataObject other = (MxStructDataObject) obj;
                
                if (!Arrays.equals(data_vec, other.data_vec))
                        return false;
                
                return true;
        }
}
