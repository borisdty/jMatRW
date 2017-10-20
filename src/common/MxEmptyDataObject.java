package common;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxEmptyDataObject extends MxDataObject
{
        public MxEmptyDataObject()
        {
                ;
        }
        
        public MxEmptyDataObject( MxDataObject other )
        {
                super(other);
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
                int result = super.hashCode();
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
                
                return true;
        }
}
