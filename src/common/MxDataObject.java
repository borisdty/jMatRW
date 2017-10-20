package common;

import java.util.Arrays;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxDataObject
{
        public  boolean     complex;
        public  boolean     global;
        public  boolean     logical;
        public  MxClassID   classType;
        public  int[]       dimensions;
        public  String      name;
        
        // 'data' is used to return the final result in MxFileReader, in case
        // of matrixes; that is if the returned data is a MxNumericDataObject (not in
        // case of cell, struct, character array or sparse objects)
        // Since it can be any kind of matrix (different types and dimensions), it is of type Object.
        // In addition, the matrix data is also returned in the vectors/arrays 'real_part'
        // and 'imag_part' in MxNumericDataObject.
        // Since it is of type Object, it should not be considered in method equals(...) further down.
        /**
         * the general data object is currently not used in the code (zero references)
         */
        public  Object      data;
        
        public MxDataObject()
        {
                complex    = false;
                global     = false;
                logical    = false;
                classType  = MxClassID.mxDOUBLE_CLASS;
                dimensions = new int[]{0,0};
                name       = "";
                data       = null;
        }
        
        public MxDataObject( MxDataObject other )
        {
                complex    = other.complex;
                global     = other.global;
                logical    = other.logical;
                classType  = other.classType;
                dimensions = other.dimensions;
                name       = other.name;
                data       = other.data;
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
                result = prime * result + ((data == null) ? 0 : data.hashCode());
                result = prime * result + Arrays.hashCode(dimensions);
                result = prime * result + (global ? 1231 : 1237);
                result = prime * result + (logical ? 1231 : 1237);
                result = prime * result + ((name == null) ? 0 : name.hashCode());
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
                
//                if (data == null)
//                {
//                        if (other.data != null)
//                                return false;
//                }
//                else if (!data.equals(other.data))
//                {
//                        return false;
//                }
                
                return true;
        }
}
