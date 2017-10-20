package subelements.base;

//import java.lang.Cloneable;
import java.nio.ByteOrder;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public interface DataArray // extends Cloneable
{
        int length();
        
        byte[] dataToByteArray(ByteOrder byte_order);
        
        /**
         * @param isSigned  if true, the DataArray internal data is assumed
         *                  to represent signed values; if false,
         *                  the array data is assumed to represent unsigned values
         * @return  a DoubleArray object with double data based on the DataArray's data
         *          (e.g. the array data could be int32 or uint16 data that is converted to double)
         */
        DoubleArray   toDoubleArray(boolean isSigned);
}
