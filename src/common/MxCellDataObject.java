package common;

import java.util.Arrays;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxCellDataObject extends MxDataObject
{
        private MxDataObject[] data_vec;
        
        public MxCellDataObject()
        {
                data_vec = null;
        }
        
        public MxCellDataObject( MxDataObject other )
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
                
                MxCellDataObject other = (MxCellDataObject) obj;
                
                if (!Arrays.equals(data_vec, other.data_vec))
                        return false;
                
                return true;
        }
        
        public MxDataObject getCell( int i, int j )
        {
                // TODO: Do range check
                int dim1 = dimensions[0];
                return data_vec[i+j*dim1];
        }
        
        /**
         * Return a cell element by linear addressing - the most
         * universal addressing, since it is across dimension.
         * It is up to the user to know what cell it is in a
         * multi-dimensional Cell Array
         * @param i   the linear index of a requested cell
         * @return    the cell element at requested linear index
         */
        public MxDataObject getCellLinear( int i )
        {
                // TODO: Do range check
                return data_vec[i];
        }
        
        public MxDataObject getCell( int... indices )
        {
                if ( indices.length != dimensions.length )
                        throw new IllegalArgumentException("MxDataObject::getCell: " +
                                        "number of arguments is " + indices.length + "," +
                                        "but Cell has " + dimensions.length + "dimensions");
                
                // TODO: Do range check
                int idx = indices[dimensions.length-1];
                for ( int k = dimensions.length-2; k >= 0; k-- )
                {
                        idx = idx*dimensions[k] + indices[k];
                }
                
                return data_vec[idx];
        }
        
        /**
         * The order in the provided linear cell-array must be compatible
         * to the linear array indexing scheme of Matlab.
         * @param data_vec
         */
        public void setCells(MxDataObject[] data_vec)
        {
                this.data_vec = data_vec;
        }
}
