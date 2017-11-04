package exception;

import java.lang.Exception;

/**
 * Thrown when field names of a structure array are not unique 
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class UniqueFieldNameExpection extends Exception
{
        private static final long serialVersionUID = 1L;
        
        /**
         * Construct a UniqueFieldNameExpection.
         */
        public UniqueFieldNameExpection() {
                super();
        }
        
        /**
         * Construct a UniqueFieldNameExpection with detail message.
         *
         * @param msg  detail message
         */
        public UniqueFieldNameExpection(String msg)
        {
                super(msg);
        }
}
