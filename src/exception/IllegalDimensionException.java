package exception;

import java.lang.Exception;

/**
 * Thrown when dimension used to access a cell array do not match the actual number of cell dimensions
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class IllegalDimensionException extends Exception
{
        private static final long serialVersionUID = 1L;
        
        /**
         * Construct a IllegalDimensionException.
         */
        public IllegalDimensionException() {
                super();
        }
        
        /**
         * Construct a IllegalDimensionException with detail message.
         *
         * @param msg  detail message
         */
        public IllegalDimensionException(String msg)
        {
                super(msg);
        }
}
