package exception;

import common.DataType;

/**
 * Thrown when actual DataType is not the expected type 
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class DataTypeException extends Exception
{
        private static final long serialVersionUID = 1L;
        
        /**
         * Construct a DataTypeException.
         */
        public DataTypeException() {
                super();
        }
        
        /**
         * Construct a DataTypeException with detail message.
         *
         * @param msg  detail message
         */
        public DataTypeException(DataType expected, DataType actual)
        {
                super( "Expected DataType: " + expected.toString() +
                                "Actual DataType: " + actual.toString() );
        }
}
