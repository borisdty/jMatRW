package common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/*
 * Reverse Lookup method from:
 *    http://www.ajaxonomy.com/2007/java/making-the-most-of-java-50-enum-tricks
 */
/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public enum DataType
{
        /*
         * MAT-File Data Types
         * The value of the Data Type field in the data element tag
         * identifies the data type used to store the data in the MAT-file.
         */
        miINT8      ( 1, 1, "int8"),
        miUINT8     ( 2, 1, "uint8"),
        miINT16     ( 3, 2, "int16"),
        miUINT16    ( 4, 2, "uint16"),
        miINT32     ( 5, 4, "int32"),
        miUINT32    ( 6, 4, "uint32"),
        miSINGLE    ( 7, 4, "single"),
        miDOUBLE    ( 9, 8, "double"),
        miINT64     (12, 8, "int64"),
        miUINT64    (13, 8, "uint64"),
        miMATRIX    (14, 1, "matrix"),
        miCOMPRESSED(15, 1, "compressed"),
        miUTF8      (16, 1, "utf8"),
        miUTF16     (17, 2, "utf16"),
        miUTF32     (18, 4, "utf32");
        
        private static final Map<Integer,DataType> lookup =
                        new HashMap<Integer,DataType>();
        
        static
        {
                for(DataType s : EnumSet.allOf(DataType.class))
                        lookup.put(s.getIndex(), s);
        }
        
        private int    index;
        private int    size;  // number of bytes that the data type allocates
        private String name;  // alternative string representation
        
        private DataType(int index, int size, String name)
        {
                this.index = index;
                this.size  = size;
                this.name  = name;
        }
        
        public int    getIndex() { return index; }
        public int    sizeOf()   { return size; }
        public String getName()  { return name; }
        public String toString() { return name; }
        
        public static DataType get(int index)
        { 
                return lookup.get(index);
        }
        
        public boolean isSignedType()
        {
                if ( this == DataType.miDOUBLE ||
                     this == DataType.miSINGLE ||
                     this == DataType.miINT64 ||
                     this == DataType.miINT32 ||
                     this == DataType.miINT16 ||
                     this == DataType.miINT8 )
                        return true;
                
                return false;
        }
}
