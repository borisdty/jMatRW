package common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class DataTagFieldReader
{
        private DataType  dataType;
        private int       numOfBytes;
        private int       tagSize;
        private int       elementSize;
        private int       padding;
        private boolean   isShortTag;
        private byte[]    tagData;
        
        private ByteOrder byteOrder;
        
        /**
         * @param type
         * @param size
         */
        public DataTagFieldReader(ByteOrder b)
        {
                this.byteOrder = b;
        }
        
        public DataType getDataType()
        {
                return dataType;
        }
        
        public int getDataSize()
        {
                return numOfBytes;
        }
        
        public int getTagSize()
        {
                return tagSize;
        }
        
        public int getElementSize()
        {
                return elementSize;
        }
        
        public int getPadding()
        {
                return padding;
        }
        
        public boolean isShortElement()
        {
                return isShortTag;
        }
        
        public byte[] getTagData()
        {
                return tagData;
        }
        
        @Override
        public String toString()
        {
                String str = "";
                str += "dataType = "      + dataType;
                str += ", numOfBytes = "  + numOfBytes;
                str += ", tagSize = "     + tagSize;
                str += ", elementSize = " + elementSize;
                str += ", padding = "     + padding;
                str += ", tagData = "     + Arrays.toString(tagData);
                return str;
        }
        
        /**
         * Evaluates all 8 bytes of a data element tag field 
         * @param tagBytes the first 8 bytes of a data element
         * @return
         */
        public boolean read(byte[] tagBytes)
        {
                if ( checkTagConsistency(tagBytes) == false )
                        return false;
                
                if ( isSmallElement(tagBytes) )
                {
                        dataType = DataType.get(tagBytes[3] & 0xFF);
                        numOfBytes = tagBytes[1] & 0xFF;
                        if (byteOrder != ByteOrder.BIG_ENDIAN)
                        {
                                dataType = DataType.get(tagBytes[0] & 0xFF);
                                numOfBytes = tagBytes[2] & 0xFF;
                        }
                        
                        tagSize     = 4;
                        padding     = 4 - numOfBytes;
                        elementSize = 8;
                        isShortTag  = true;
                }
                else
                {
                        dataType = DataType.get(tagBytes[3] & 0xFF);
                        if (byteOrder != ByteOrder.BIG_ENDIAN)
                        {
                                dataType = DataType.get(tagBytes[0] & 0xFF);
                        }
                        
                        numOfBytes = Bytes.toInt(tagBytes,4,byteOrder);
                        tagSize  = 8;
                        
                        int b = numOfBytes % 8;
                        padding = (b == 0) ? 0 : 8 - b; // possibly: (8-b)%8 ??
                        if ( dataType == DataType.miCOMPRESSED )
                                padding = 0;
                        
                        elementSize = tagSize + numOfBytes + padding;
                        isShortTag  = false;
                }
                
                tagData = tagBytes;
                
                return true;
        }
        
        public boolean read(InputStream in)
        {
                byte[] tagBytes = new byte[8];
                
                try {
                        in.read(tagBytes);
                }
                catch (IOException e) {
                        e.printStackTrace();
                        return false;
                }
                
                if (read(tagBytes) == false)
                        return false;
                
                return true;
        }
        
        private boolean checkTagConsistency(byte[] tagBytes)
        {
                if (byteOrder == ByteOrder.BIG_ENDIAN)
                        return checkTagConsistencyBigEndian(tagBytes);
                else
                {
                        byte[] rev = {tagBytes[3],tagBytes[2],tagBytes[1],tagBytes[0]};
                        return checkTagConsistencyBigEndian(rev);
                }
        }
        
        private boolean checkTagConsistencyBigEndian(byte[] tagBytes)
        {
                if ( tagBytes[1] != 0 ) // Short data element; data in tag field
                {
                        int type = tagBytes[3];
                        
                        if ( DataType.get(type) == null )
                                return false;
                        
                        int size = tagBytes[1];
                        
                        if ( size > 4 )
                                return false;
                        
                        if ( tagBytes[0] != 0 || tagBytes[2] != 0 )
                                return false;
                }
                else
                {
                        int type = tagBytes[3];
                        
                        if ( DataType.get(type) == null )
                                return false;
                        
                        if ( tagBytes[0] != 0 || tagBytes[1] != 0  || tagBytes[2] != 0 )
                                return false;
                }
                
                return true;
        }
        
        @Deprecated
        /* private */ boolean isShortElement_OLD(byte[] in)
        {
                if (byteOrder == ByteOrder.BIG_ENDIAN && in[1] != 0 )
                        return true;
                else if ( in[2] != 0 )
                        return true;
                
                return false;
        }
        
        /**
         * Quote MathWorks: you can tell if you are processing a small data element by
         * comparing the value of the first 2 bytes of the tag with the value zero (0).
         * If these 2 bytes are not zero, the tag uses the small data element format.
         * @param in  the tag bytes of a data element
         * @return    treu, if the tag is in small data element format, false otherwise
         */
        private boolean isSmallElement(byte[] in)
        {
                if (byteOrder == ByteOrder.BIG_ENDIAN)
                {
                        if ( (in[0] != 0 || in[1] != 0) )
                                return true;
                }
                else if ( (in[2] != 0 || in[3] != 0) )
                        return true;
                
                return false;
        }
}
