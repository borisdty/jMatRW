package subelements.struct;

import io.MyDataInputStream;

import java.nio.ByteOrder;

import abstractTypes.AbstractDataElement;
import common.DataTagFieldReader;
import common.DataType;
import exception.DataTypeException;
import common.Bytes;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class FieldNameLengthSubelement extends AbstractDataElement
{
        private int dataObj;
        
        public FieldNameLengthSubelement()
        {
                super(DataType.miINT32);
                dataObj = 0;
                setNumOfDataElementBytes(1*super.sizeOfDataType());
        }
        
        public void setData(int len)
        {
                dataObj = len;
        }
        
        public int getLength()
        {
                return dataObj;
        }
        
        public String toString()
        {
                String str = super.toString();
                str += " field name length = " + dataObj;
                return str;
        }
        
        public byte[] dataToByteArray(ByteOrder byte_order)
        {
                byte[] b      = new byte[4];
                int    data   = dataObj;
                int    offset = 0;
                
                if ( byte_order == ByteOrder.BIG_ENDIAN )
                {
                        b[offset++] = (byte)(data >> 24);
                        b[offset++] = (byte)(data >> 16);
                        b[offset++] = (byte)(data >> 8);
                        b[offset++] = (byte)(data >> 0);
                }
                else
                {
                        b[offset++] = (byte)(data >> 0);
                        b[offset++] = (byte)(data >> 8);
                        b[offset++] = (byte)(data >> 16);
                        b[offset++] = (byte)(data >> 24);
                }
                
                return b;
        }
        
        public FieldNameLengthSubelement read(MyDataInputStream in) throws DataTypeException
        {
                DataTagFieldReader tagInfo = new DataTagFieldReader(in.getByteOrder());
                tagInfo.read(in);
                
                if ( tagInfo.getDataType() != DataType.miINT32 )
                        throw new DataTypeException(DataType.miINT32, tagInfo.getDataType());
                
                if ( tagInfo.isShortElement() == false )
                        return null;
                
                int len = Bytes.toInt(tagInfo.getTagData(),4,in.getByteOrder());
                
                setData(len);
                
                return this;
        }
}
