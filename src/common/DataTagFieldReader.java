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
        
        private final boolean isBigEndian;
        
        /**
         * @param type
         * @param size
         */
        public DataTagFieldReader(ByteOrder b)
        {
                this.isBigEndian = b.equals(ByteOrder.BIG_ENDIAN);
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
        
        public boolean read(byte[] tagBytes)
        {
                if ( checkTagConsistency(tagBytes) == false )
                        return false;
                
                if ( isShortElement(tagBytes) )
                {
                        dataType = DataType.get(tagBytes[3] & 0xFF);
                        numOfBytes = tagBytes[1] & 0xFF;
                        if (!isBigEndian)
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
                        if (!isBigEndian)
                        {
                                dataType = DataType.get(tagBytes[0] & 0xFF);
                        }
                        
                        ByteOrder byte_order = isBigEndian == true ?
                                        ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
                        
                        numOfBytes = Bytes.toInt(tagBytes,4,byte_order);
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
                if (isBigEndian)
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
        
        private boolean isShortElement(byte[] in)
        {
                if (isBigEndian && in[1] != 0 )
                        return true;
                else if ( in[2] != 0 )
                        return true;
                
                return false;
        }
}
