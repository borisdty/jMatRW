package array;

import java.nio.ByteOrder;

import abstractTypes.AbstractArrayElement;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxEmptyArray extends AbstractArrayElement
{
        public MxEmptyArray()
        {
                super.setNumOfDataElementBytes(0);
        }
        
        /**
         * Is not used since method AbstractArrayElement.dataToByteArray() is
         * overwritten, but must be implemented as declared abstract in
         * super-class AbstractArrayElement.
         */
        public byte[] specDataToByteArray(ByteOrder byte_order)
        {
                // Maybe it should be new byte[0] ???
                // But it is not supposed to be called at all, anyway
                return null;
        }
        
        /**
         * Overwrites method 'dataToByteArray(ByteOrder)' of super class
         * AbstractArrayElement to produce always empty Data Part.
         * An empty data array consists just of the data tag bytes.
         */
        @Override
        public byte[] dataToByteArray(ByteOrder byte_order)
        {
                byte[] b = new byte[0];
                return b;
        }
}
