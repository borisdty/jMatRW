package common;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxCharacterDataObject extends MxDataObject
{
        private String data_string;
        
        public MxCharacterDataObject()
        {
                super();
                classType = MxClassID.mxCHAR_CLASS;
                data_string = null;
        }
        
        public MxCharacterDataObject( MxDataObject other )
        {
                super(other);
                
                /*
                 * If other has not defined the classType, we can do it now
                 * If other has another classType defined, we overwrite it here anyway
                 * */
                classType = MxClassID.mxCHAR_CLASS;
                data_string = null;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
                final int prime = 31;
                int result = super.hashCode();
                result = prime * result + ((data_string == null) ? 0 : data_string.hashCode());
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
                
                MxCharacterDataObject other = (MxCharacterDataObject) obj;
                
                if (data_string == null)
                {
                        if (other.data_string != null)
                                return false;
                }
                else if (!data_string.equals(other.data_string)) {
                        return false;
                }
                
                return true;
        }
        
        public String getString()
        {
                return data_string;
        }
        
        public void setString(String str)
        {
                this.data_string = str;
        }
}
