package subelements.common;

import io.MyDataInputStream;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Arrays;

import abstractTypes.AbstractDataElement;

import common.DataTagFieldReader;
import common.DataType;
import exception.DataTypeException;

/**
 * Sub-element that is common to all array types.
 * It is itself a Data Element and consists of Data Tag Field and Data Part.
 * This sub-element specified the size of each dimension of an n-dimensional
 * array in an n-sized array of 32-bit values (miINT32).
 * All numeric arrays have at least two dimensions.
 * The Dimension Array sub-element is common to all MATLAB array types.
 * Generally, to calculate the number of dimensions in an array, divide the
 * value stored in the Number of Bytes field in the Dimension Array sub-element
 * tag by 4 (the size of miINT32, the data type used in the sub-element).
 * 
 * |--------------------------------------------------
 * |  Data Type (=miINT32) |     Number of Bytes     |  Tag (2 x 32 bit)
 * |--------------------------------------------------
 * |     Variable size (#dimensions x 4 bytes)       |  Data Part
 * |-------------------------------------------------|
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class DimensionsArraySubelement extends AbstractDataElement
{
        private int[] dataObj;
        
        public DimensionsArraySubelement()
        {
                super(DataType.miINT32);
        }
        
        public DimensionsArraySubelement(int[] dims)
        {
                this();
                setData(dims);
        }
        
        public void setData(int[] dims)
        {
                //if ( dims.length < 2 )
                //{
                //        System.err.println("setData: Sparse arrays support dimensions <= 2!!");
                //        System.err.println("setData: Otherwise,number of dimensions must be >= 2!!");
                //        return;
                //}
                dataObj = new int[dims.length];
                System.arraycopy(dims, 0, dataObj, 0, dims.length);
                super.setNumOfDataElementBytes(dims.length*super.sizeOfDataType());
        }
        
        public int[] getDimensions()
        {
                return dataObj;
        }
        
        @Override
        public String toString()
        {
                String str = "";
                str += super.toString();
                str += " [dimensions = " + Arrays.toString(dataObj) + "]";
                return str;
        }
        
        /**
         * Returns an array of bytes representing the Data Part (only).
         * The array consists of number of dimension x miINT32 bytes.
         * @return array of bytes representing the Data Part (only)
         */
        public byte[] dataToByteArray(ByteOrder byte_order)
        {
                byte[] b      = new byte[super.getDataLength()];
                int[]  data   = dataObj;
                int    len    = super.getDataLength() / super.sizeOfDataType();
                int    offset = 0;
                
                for ( int i = 0; i < len; i++ )
                {
                        int n = data[i];
                        
                        if ( byte_order == ByteOrder.BIG_ENDIAN )
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
        
        public DimensionsArraySubelement read(MyDataInputStream in) throws DataTypeException
        {
                DataTagFieldReader tagInfo = new DataTagFieldReader(in.getByteOrder());
                tagInfo.read(in);
                
                if ( tagInfo.getDataType() != DataType.miINT32 )
                        throw new DataTypeException( DataType.miINT32, tagInfo.getDataType() );
                
                if ( tagInfo.getDataSize() < 8 )
                        return null;
                
                int dataSize = tagInfo.getDataSize();
                int atomSize = tagInfo.getDataType().sizeOf();
                
                int[] dims = new int[dataSize/atomSize];
                
                for ( int i = 0; i < dims.length; i++ )
                {
                        try {
                                dims[i] = in.readInt();
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
                
                setData(dims);
                
                return this;
        }
}
