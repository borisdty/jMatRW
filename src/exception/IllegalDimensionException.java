package exception;

import java.lang.Exception;

/**
 * Thrown when data type conversion leads possibly to a Loss Of Precision 
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class IllegalDimensionException extends Exception
{
        private static final long serialVersionUID = 1L;
        
        /**
         * Construct a LossOfPrecisionException.
         */
        public IllegalDimensionException() {
                super();
        }
        
        /**
         * Construct a LossOfPrecisionException with detail message.
         *
         * @param msg  detail message
         */
        public IllegalDimensionException(String msg)
        {
                super(msg);
        }
}
