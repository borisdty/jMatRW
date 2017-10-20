package abstractTypes;

import java.nio.ByteOrder;

import subelements.CompressedDataElement;
import common.DataTagField;
import common.DataType;

/**
 * A Data Element is the principal prototype of MATLAB data structures.
 * It consists of a tag and a data part
 * 
 * |----------------------------------------------
 * |      Data Type      |    Number of Bytes    |  Tag (2 x 32 bit)
 * |----------------------------------------------
 * |                                             |
 * |                Variable size                | Data Part
 * |---------------------------------------------|
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public abstract class AbstractDataElement
{
        private DataTagField dataTagField;
        
        /**
         * If set true, the method toByteArray(ByteOrder) will produce a byte array of
         * a compressed (zipped) Data Element
         */
        private boolean      compressed;
        
        /**
         * Creates a Data Element
         * @param type  The type in which data is encoded in
         */
        protected AbstractDataElement(DataType type)
        {
                dataTagField = new DataTagField(type);
                compressed   = false;
        }
        
        /**
         * Creates a Data Element
         * @param type      The type (e.g. uint32) in which data is represented
         * @param compFlag  If set true, the method toByteArray(ByteOrder) will produce a compressed (zipped)
         */
        protected AbstractDataElement(DataType type, boolean compFlag)
        {
                dataTagField = new DataTagField(type);
                compressed   = compFlag;
        }
        
        /**
         * Sets the type (e.g. uint32) used to represent data 
         * @param type  The type (e.g. uint32) which data is represented in
         */
        protected void setDataType(DataType type)
        {
                dataTagField.setDataType(type);
        }
        
        /**
         * Gets the type (e.g. uint32) used to represent data 
         * @return The type (e.g. uint32) which data is represented in
         */
        protected DataType getDataType()
        {
                return dataTagField.getDataType();
        }
        
        /**
         * Sets the number of bytes used to represent a Data Element's data part (only)
         * @param size  The number of bytes used to represent a Data Element's data part (only)
         */
        protected void setNumOfDataElementBytes(int size)
        {
                dataTagField.setNumOfBytes(size);
        }
        
        /**
         * Return the number of bytes necessary to represent the
         * element's data part (only).
         * @return The length in bytes of the data part
         */
        protected int getDataLength()
        {
                return dataTagField.getNumOfBytes();
        }
        
        /**
         * Return the number of bytes necessary to represent the
         * element's data part and the element tag together.
         * @return The length in bytes of the data and element tag
         */
        private int getElementLength()
        {
                return getDataLength() + dataTagField.getDataTagSize();
        }
        
        /**
         * Return the number of bytes necessary to represent the
         * element's data part, the element tag and the final padding sequence.
         * @return The length in bytes of the data part, element tag and padding
         */
        public int getTotalElementLength()
        {
                return getElementLength() + getPadding(getDataLength());
        }
        
        @Override
        public String toString()
        {
                String str = dataTagField.toString();
                str += " data length = "    + getDataLength();
                str += " total length = "   + getElementLength();
                str += " element length = " + getTotalElementLength();
                return str;
        }
        
        /**
         * Calculate the number of necessary padding bytes.
         * For standard Data Elements an 8-byte alignment is required.
         * For Small Data Elements a 4-byte alignment is required.
         * This is to make sure the tag of the next data element falls on a 8-byte boundary
         * BTW, compressed (zipped) data is not padded.
         * 
         * @param size  Number of bytes required to represent the data
         */
        protected int getPadding(int size)
        {
                if (size == 0)
                        return 4;
                
                boolean smallData = (size <= 4); // Data part will be packed in the data tag itself
                
                // default alignment width: 8 bytes; data will not be packed in the tag
                int width = 8;
                
                if ( smallData ) // data will be packed in the tag
                {
                        // alignment width for data elements of less than 4 bytes
                        // is 4 bytes
                        width = 4;
                }
                
                /*
                 * a = size/sizeOfDataType()  = number of data items fitting in given number of bytes
                 * b = width/sizeOfDataType() = possible data items within alignment
                 * c = a%b                    = number of data items in excess
                 * d = c*sizeOfDataType()     = number of bytes items in excess
                 */
                //int b = ((size/sizeOfDataType())%(width/sizeOfDataType())) * sizeOfDataType();
                //int padding = (b != 0) ? width-b : 0;
                
                int b = size - width * (size/width);
                int padding = (width-b) % width;
                
                return padding;
        }
        
        /**
         * Returns the number of bytes needed to represent one element
         * in the data part (e.g. for double it is 8 bytes, uint32 needs 4).
         * 
         * @return - Number of bytes for 1 datum in the data part 
         */
        protected int sizeOfDataType()
        {
                return dataTagField.getDataType().sizeOf();
        }
        
        /**
         * Returns an array of bytes representing the Data Part (only) of
         * derived Data Elements.
         */
        protected abstract byte[] dataToByteArray(ByteOrder byte_order);
        
        /**
         * Returns an array of bytes representing the overall Data Element.
         * The array consists of bytes for the Data Tag Field, the Data Part
         * and Padding.
         * @return array of bytes representing the overall Data Element
         */
        public byte[] toByteArray(ByteOrder byte_order)
        {
                // Important to have this call first, since it allows to update
                // the data tag size field before method 'getDataLength()' is
                // called. Necessary for compressed files.
                byte[] data = dataToByteArray(byte_order);
                
                int padding = getPadding(getDataLength());
                
                if ( getDataType() == DataType.miCOMPRESSED )
                        padding = 0;
                
                byte[] tagBytes = dataTagField.toByteArray(byte_order);
                
                int len = getElementLength() + padding;
                
                byte[] dest = new byte[len];
                
                int offset = 0;
                
                System.arraycopy(tagBytes, 0, dest, offset, tagBytes.length);
                offset += tagBytes.length;
                
                System.arraycopy(data, 0, dest, offset, data.length);
                offset += data.length;
                
                System.arraycopy(new byte[padding], 0, dest, offset, padding);
                
                if (compressed == true)
                {
                        CompressedDataElement element = new CompressedDataElement();
                        element.setData(dest);
                        byte[] compressedData = element.toByteArray(byte_order);
                        return compressedData;
                }
                
                return dest;
        }
}
