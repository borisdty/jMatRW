package common;

import java.nio.ByteOrder;

/**
 * The Tag Field is part of every Data Element.
 * It is composed of two 32-bit fields: Data Type and Number of Bytes
 * |----------------------------------------------
 * |      Data Type      |    Number of Bytes    |  Tag (2 x 32 bit)
 * |----------------------------------------------
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class DataTagField
{
        // Specifies how the data in the element is encoded and should be interpreted, that is,
        // its size and format. See abstractTypes.AbstractDataElement.
        private DataType dataType;
        
        // Specifies the number of bytes of data in the element.
        private int      numOfBytes;
        
        public DataTagField()
        {
                dataType   = null;
                numOfBytes = 0;
        }
        
        public DataTagField(DataType type)
        {
                dataType   = type;
                numOfBytes = 0;
        }
        
        /**
         * @param type  Specifies how data in the data part of a data element should be interpreted
         * @param size  Specifies the number of bytes of data in the data part of a data element
         */
        public DataTagField(DataType type, int size)
        {
                dataType   = type;
                numOfBytes = size;
        }
        
        public DataType getDataType()
        {
                return dataType;
        }
        
        public int getNumOfBytes()
        {
                return numOfBytes;
        }
        
        public void setDataType(DataType type)
        {
                dataType = type;
        }
        
        public void setNumOfBytes(int size)
        {
                numOfBytes = size;
        }
        
        public int getDataTagSize()
        {
                if ( numOfBytes <= 4 )
                        return 4;
                return 8;
        }
        
        @Override
        public String toString()
        {
                String s;
                
                s = "[type: " + dataType.toString() + ", size: " + numOfBytes + "]";
                
                return s;
        }
        
        /*
         * Returns the byte representation of the Data Tag Field as array
         */
        public byte[] toByteArray(ByteOrder byte_order)
        {
                byte[] dest;
                if ( numOfBytes <= 4 )
                {
                        dest = new byte[4];
                        
                        if ( byte_order.equals(ByteOrder.BIG_ENDIAN) )
                        {
                                // type and size are in exchanged order
                                dest[0] = (byte)(numOfBytes >> 8);
                                dest[1] = (byte)(numOfBytes >> 0);
                                dest[2] = (byte)(dataType.getIndex() >> 8);
                                dest[3] = (byte)(dataType.getIndex() >> 0);
                        }
                        else
                        {
                                // type and size are in exchanged order
                                dest[3] = (byte)(numOfBytes >> 8);
                                dest[2] = (byte)(numOfBytes >> 0);
                                dest[1] = (byte)(dataType.getIndex() >> 8);
                                dest[0] = (byte)(dataType.getIndex() >> 0);
                        }
                }
                else
                {
                        dest = new byte[8];
                        
                        if ( byte_order.equals(ByteOrder.BIG_ENDIAN) )
                        {
                                dest[0] = (byte)(dataType.getIndex() >> 24);
                                dest[1] = (byte)(dataType.getIndex() >> 16);
                                dest[2] = (byte)(dataType.getIndex() >> 8);
                                dest[3] = (byte)(dataType.getIndex() >> 0);
                                dest[4] = (byte)(numOfBytes >> 24);
                                dest[5] = (byte)(numOfBytes >> 16);
                                dest[6] = (byte)(numOfBytes >> 8);
                                dest[7] = (byte)(numOfBytes >> 0);
                        }
                        else
                        {
                                dest[3] = (byte)(dataType.getIndex() >> 24);
                                dest[2] = (byte)(dataType.getIndex() >> 16);
                                dest[1] = (byte)(dataType.getIndex() >> 8);
                                dest[0] = (byte)(dataType.getIndex() >> 0);
                                dest[7] = (byte)(numOfBytes >> 24);
                                dest[6] = (byte)(numOfBytes >> 16);
                                dest[5] = (byte)(numOfBytes >> 8);
                                dest[4] = (byte)(numOfBytes >> 0);
                        }
                }
                
                return dest;
        }
}
