package exception;

import java.lang.Exception;

/**
 * Thrown when the storage array type is not suitable for the requested integer array return type
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class StorageArrayTypeException extends Exception
{
        private static final long serialVersionUID = 1L;
        
        /**
         * Construct a StorageArrayTypeException.
         */
        public StorageArrayTypeException() {
                super();
        }
        
        /**
         * Construct a StorageArrayTypeException with detail message.
         *
         * @param msg  detail message
         */
        public StorageArrayTypeException(String msg)
        {
                super(msg);
        }
}
