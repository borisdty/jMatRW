package exception;

import common.MxClassID;

/**
 * Thrown when actual MxClassID is not the expected class 
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class ClassIDException extends Exception
{
        private static final long serialVersionUID = 1L;
        
        /**
         * Construct a ClassIDException.
         */
        public ClassIDException() {
                super();
        }
        
        /**
         * Construct a ClassIDException with detail message.
         *
         * @param msg  detail message
         */
        public ClassIDException(MxClassID expected, MxClassID actual)
        {
                super( "Expected MxClassID: " + expected.toString() +
                                "Actual MxClassID: " + actual.toString() );
        }
}
