package subelements.common;

import io.MyDataInputStream;

import java.io.IOException;
import java.nio.ByteOrder;

import abstractTypes.AbstractDataElement;
import common.DataTagFieldReader;
import common.DataType;
import exception.DataTypeException;

/**
 * This sub-element specifies the name assigned to the array, as an array of
 * signed 8-bit values (miINT8). This sublelement is common to all Matlab
 * array types.
 * 
 * |--------------------------------------------------
 * |  Data Type (=miINT8)  |     Number of Bytes     |  Tag (2 x 32 bit)
 * |--------------------------------------------------
 * |      Variable size (#characters x 1 byte)       |  Data Part
 * |-------------------------------------------------|
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class ArrayNameSubelement extends AbstractDataElement
{
        private String dataObj;
        
        public ArrayNameSubelement()
        {
                super(DataType.miINT8);
                dataObj = null;
        }
        
        public ArrayNameSubelement(String name)
        {
                this();
                setData(name);
        }
        
        public void setData(String name)
        {
                dataObj = name;
                super.setNumOfDataElementBytes(name.length()*super.sizeOfDataType());
        }
        
        public String getName()
        {
                return dataObj;
        }
        
        @Override
        public String toString()
        {
                String str = "";
                str += super.toString();
                str += " name = " + (String)dataObj;
                return str;
        }
        
        /**
         * Returns an array of bytes representing the Data Part (only).
         * The array consists of number of characters x 1 byte.
         * @return array of bytes representing the Data Part (only)
         */
        public byte[] dataToByteArray(ByteOrder byte_order)
        {
                byte[] b      = new byte[super.getDataLength()];
                String data   = (String)dataObj;
                int    len    = super.getDataLength() / super.sizeOfDataType();
                int    offset = 0;
                
                for ( int i = 0; i < len; i++ )
                {
                        b[offset++] = (byte)data.charAt(i);
                }
                
                return b;
        }
        
        public ArrayNameSubelement read(MyDataInputStream in) throws DataTypeException
        {
                DataTagFieldReader tagInfo = new DataTagFieldReader(in.getByteOrder());
                tagInfo.read(in);
                
                if ( tagInfo.getDataType() != DataType.miINT8 )
                        throw new DataTypeException( DataType.miINT8, tagInfo.getDataType() );
                
                int size = tagInfo.getDataSize();
                
                String name;
                
                if ( tagInfo.isShortElement() )
                {
                        name = new String(tagInfo.getTagData(),4,size);
                        // In case of short elements, the padding is
                        // already included in the tag and therefore
                        // already read. Therefore, we do not skip any padding.
                }
                else
                {
                        byte[] buf = new byte[size];
                        
                        try {
                                in.read(buf);
                                in.skip(tagInfo.getPadding());
                        }
                        catch (IOException e) {
                                e.printStackTrace();
                                return null;
                        }
                        name = new String(buf);
                }
                
                setData(name);
                
                return this;
        }
}
