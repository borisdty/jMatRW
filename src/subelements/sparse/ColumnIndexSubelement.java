package subelements.sparse;

import io.MyDataInputStream;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Arrays;

import abstractTypes.AbstractDataElement;

import common.DataTagFieldReader;
import common.DataType;
import exception.DataTypeException;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class ColumnIndexSubelement extends AbstractDataElement
{
        private int[] dataObj;
        
        public ColumnIndexSubelement()
        {
                super(DataType.miINT32);
        }
        
        public ColumnIndexSubelement(int[] dims)
        {
                this();
                setData(dims);
        }
        
        public void setData(int[] idx)
        {
                dataObj = new int[idx.length];
                System.arraycopy(idx, 0, dataObj, 0, idx.length);
                setNumOfDataElementBytes(idx.length*super.sizeOfDataType());
        }
        
        public int[] getRowIndices()
        {
                return dataObj;
        }
        
        public String toString()
        {
                String str = "";
                str += super.toString();
                str += " Indices = " + Arrays.toString(dataObj);
                return str;
        }
        
        public byte[] dataToByteArray(ByteOrder byte_order)
        {
                byte[] b      = new byte[getDataLength()];
                int[]  data   = dataObj;
                int    len    = getDataLength() / super.sizeOfDataType();
                int    offset = 0;
                
                for ( int i = 0; i < len; i++ )
                {
                        int n = data[i];
                        
                        if ( byte_order.equals(ByteOrder.BIG_ENDIAN) )
                        {
                                b[offset++] = (byte)(n >> 24);
                                b[offset++] = (byte)(n >> 16);
                                b[offset++] = (byte)(n >> 8);
                                b[offset++] = (byte)(n >> 0);
                        }
                        else
                        {
                                b[offset++] = (byte)(n >> 0);
                                b[offset++] = (byte)(n >> 8);
                                b[offset++] = (byte)(n >> 16);
                                b[offset++] = (byte)(n >> 24);
                        }
                }
                
                return b;
        }
        
        public ColumnIndexSubelement read(MyDataInputStream in) throws DataTypeException
        {
                DataTagFieldReader tagInfo = new DataTagFieldReader(in.getByteOrder());
                tagInfo.read(in);
                
                if ( tagInfo.getDataType() != DataType.miINT32 )
                        throw new DataTypeException(DataType.miINT32, tagInfo.getDataType());
                
                int size = tagInfo.getDataSize();
                
                int[] idx = new int[size/tagInfo.getDataType().sizeOf()];
                
                for ( int i = 0; i < idx.length; i++ )
                {
                        try {
                                idx[i] = in.readInt();
                        }
                        catch (IOException e) {
                                e.printStackTrace();
                                return null;
                        }
                }
                
                try {
                        in.skip(tagInfo.getPadding());
                }
                catch (IOException e) {
                        e.printStackTrace();
                        return null;
                }
                
                setData(idx);
                
                return this;
        }
}
