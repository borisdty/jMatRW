package common;

import java.util.Arrays;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxStructDataObject extends MxDataObject
{
        private MxDataObject[] data_vec;
        
        public MxStructDataObject()
        {
                super();
                classType = MxClassID.mxSTRUCT_CLASS;
                data_vec = null;
        }
        
        public MxStructDataObject( MxDataObject other )
        {
                super(other);
                
                /*
                 * If other has not defined the classType, we can do it now
                 * If other has another classType defined, we overwrite it here anyway
                 * */
                classType = MxClassID.mxSTRUCT_CLASS;
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
        
        public MxDataObject getField(String name)
        {
                if (name == null)
                        return null;
                
                for ( MxDataObject obj : data_vec)
                {
                        if (name.equals(obj.getName()))
                                return obj;
                }
                
                return null;
        }
        
        public MxDataObject[] getAllFields()
        {
                return data_vec;
        }
        
        public void setAllFields(MxDataObject[] data_vec)
        {
                this.data_vec = data_vec;
        }
}
