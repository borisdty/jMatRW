package common;

import java.util.Arrays;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxDataObject
{
        private boolean     complex;
        private boolean     global;
        private boolean     logical;
        private boolean     sparse;
        
        protected MxClassID   classType;
        protected int[]       dimensions;
        protected String      name;
        private   int         numOfElements;
        
        public MxDataObject()
        {
                complex    = false;
                global     = false;
                logical    = false;
                sparse     = false;
                classType  = MxClassID.mxDOUBLE_CLASS;
                dimensions = new int[]{0,0};
                name       = "";
                numOfElements = 0;
        }
        
        public MxDataObject( MxDataObject other )
        {
                complex    = other.complex;
                global     = other.global;
                logical    = other.logical;
                sparse     = other.sparse;
                classType  = other.classType;
                dimensions = other.dimensions;
                name       = other.name;
                numOfElements = other.numOfElements;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((classType == null) ? 0 : classType.hashCode());
                result = prime * result + (complex ? 1231 : 1237);
                result = prime * result + Arrays.hashCode(dimensions);
                result = prime * result + (global ? 1231 : 1237);
                result = prime * result + (logical ? 1231 : 1237);
                result = prime * result + ((name == null) ? 0 : name.hashCode());
                result = prime * result + (sparse ? 1231 : 1237);
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
                
                if (obj == null)
                        return false;
                
                if (getClass() != obj.getClass())
                        return false;
                
                MxDataObject other = (MxDataObject) obj;
                
                if (complex != other.complex)
                        return false;
                
                if (global != other.global)
                        return false;
                
                if (logical != other.logical)
                        return false;
                
                if (sparse != other.sparse)
                        return false;
                
                if (classType != other.classType)
                        return false;
                
                if (!Arrays.equals(dimensions, other.dimensions))
                        return false;
                
                if (name == null)
                {
                        if (other.name != null)
                                return false;
                }
                else if (!name.equals(other.name))
                {
                        return false;
                }
                
                return true;
        }
        
        public boolean isComplex()
        {
                return complex;
        }
        
        public boolean isGlobal()
        {
                return global;
        }
        
        public boolean isLogical()
        {
                return logical;
        }
        
        public boolean isSparse()
        {
                return sparse;
        }
        
        public MxClassID getClassID()
        {
                return classType;
        }
        
        public int[] getDimensions()
        {
                return dimensions.clone();
        }
        
        public String getName()
        {
                return name;
        }
        
        public int getNumOfElements()
        {
                return numOfElements;
        }
        
        public void setComplex(boolean complex)
        {
                this.complex = complex;
        }
        
        public void setGlobal(boolean global)
        {
                this.global = global;
        }
        
        public void setLogical(boolean logical)
        {
                this.logical = logical;
        }
        
        public void setSparse(boolean sparse)
        {
                this.sparse = sparse;
        }
        
        public void setClassID(MxClassID classID)
        {
                this.classType = classID;
        }
        
        public void setDimensions(int[] dimensions)
        {
                this.dimensions = dimensions.clone();
                
                numOfElements = 1;
                for ( int k = 0; k < dimensions.length; k++ )
                        numOfElements *= dimensions[k];
        }
        
        public void setName(String name)
        {
                this.name = name;
        }
}
