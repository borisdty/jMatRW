package common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/*
 * See also:
 *    http://www.ajaxonomy.com/2007/java/making-the-most-of-java-50-enum-tricks
 */
/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public enum MxClassID
{
        /* MATLAB Array Types (Classes)
         * The value of the Class field identifies the MATLAB data type.
         * The value of the Data Type field in the data element tag identifies
         * the data type used to actually store the data in the MAT-file.
         */
        mxCELL_CLASS    (1),
        mxSTRUCT_CLASS  (2),
        mxOBJECT_CLASS  (3),
        mxCHAR_CLASS    (4),
        mxSPARSE_CLASS  (5),
        mxDOUBLE_CLASS  (6),
        mxSINGLE_CLASS  (7),
        mxINT8_CLASS    (8),
        mxUINT8_CLASS   (9),
        mxINT16_CLASS   (10),
        mxUINT16_CLASS  (11),
        mxINT32_CLASS   (12),
        mxUINT32_CLASS  (13),
        mxINT64_CLASS   (14),
        mxUINT64_CLASS  (15);
        
        private static final Map<Integer,MxClassID> lookup =
                        new HashMap<Integer,MxClassID>();
        
        static
        {
                for(MxClassID s : EnumSet.allOf(MxClassID.class))
                        lookup.put(s.getValue(), s);
        }
        
        private int value;
        
        private MxClassID(int value)
        {
                this.value = value;
        }
        
        public int getValue()
        {
                return value;
        }
        
        public static MxClassID get(int value)
        {
                return lookup.get(value);
        }
}
