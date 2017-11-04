package exception;

import java.lang.Exception;

/**
 * Thrown when data type conversion leads possibly to a Loss Of Precision 
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class LossOfPrecisionException extends Exception
{
        private static final long serialVersionUID = 1L;
        
        /**
         * Construct a LossOfPrecisionException.
         */
        public LossOfPrecisionException() {
                super();
        }
        
        /**
         * Construct a LossOfPrecisionException with detail message.
         *
         * @param msg  detail message
         */
        public LossOfPrecisionException(String msg)
        {
                super(msg);
        }
}
