package subelements.numeric;

import io.MyDataInputStream;

import java.io.IOException;
import java.nio.ByteOrder;

import subelements.base.BooleanArray;
import subelements.base.ByteArray;
import subelements.base.CharArray;
import subelements.base.DataArray;
import subelements.base.DoubleArray;
import subelements.base.FloatArray;
import subelements.base.IntArray;
import subelements.base.ShortArray;

import abstractTypes.AbstractDataElement;
import common.DataTagFieldReader;
import common.DataType;
import common.Bytes;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class NumericPartSubelement extends AbstractDataElement
{
        private DataArray dataObj;
        
        public NumericPartSubelement()
        {
                super(DataType.miDOUBLE);
        }
        
        public NumericPartSubelement(double [][] d)
        {
                super(DataType.miDOUBLE);
                setData(d);
        }
        
        public NumericPartSubelement(double [] d)
        {
                super(DataType.miDOUBLE);
                setData(d);
        }
        
        public NumericPartSubelement(int [][] d)
        {
                super(DataType.miINT32);
                setData(d);
        }
        
        public NumericPartSubelement(int [] d)
        {
                super(DataType.miINT32);
                setData(d);
        }
        
        public NumericPartSubelement(short [][] d)
        {
                super(DataType.miINT16);
                setData(d);
        }
        
        public NumericPartSubelement(short [] d)
        {
                super(DataType.miINT16);
                setData(d);
        }
        
        public NumericPartSubelement(byte [][] d)
        {
                super(DataType.miINT8);
                setData(d);
        }
        
        public NumericPartSubelement(byte [] d)
        {
                super(DataType.miINT8);
                setData(d);
        }
        
        public NumericPartSubelement(char [][] d)
        {
                super(DataType.miUINT16);
                setData(d);
        }
        
        public NumericPartSubelement(char [] d)
        {
                super(DataType.miUINT16);
                setData(d);
        }
        
        public NumericPartSubelement(boolean [][] d)
        {
                super(DataType.miUINT8);
                setData(d);
        }
        
        public NumericPartSubelement(boolean [] d)
        {
                super(DataType.miUINT8);
                setData(d);
        }
        
        // Need to overwrite to expose access to protected method/information
        // AbstractDataElement::getDataType()
        @Override
        public DataType getDataType()
        {
                return super.getDataType();
        }
        
        public DataArray getData()
        {
                return dataObj;
        }
        
        public void setData(double [][] d)
        {
                setData(d,true);
        }
        
        public void setData(double [] d)
        {
                setData(d,true);
        }
        
        public void setData(float[][] d)
        {
                int n   = d.length;
                int m   = d[0].length;
                int len = n*m;
                
                float[] data = new float[len];
                
                for ( int j = 0; j < m; j++ )
                        for ( int i = 0; i < n; i++ )
                                data[i+j*n] = d[i][j];
                
                dataObj = new FloatArray(data);
                
                super.setDataType(DataType.miSINGLE);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        public void setData(float[] d)
        {
                int n   = d.length;
                int len = n;
                float[] data = new float[len];
                
                for ( int i = 0; i < n; i++ )
                        data[i] = d[i];
                
                dataObj = new FloatArray(data);
                
                super.setDataType(DataType.miSINGLE);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        public void setData(int[][] d)
        {
                int n   = d.length;
                int m   = d[0].length;
                int len = n*m;
                
                int[] data = new int[len];
                
                for ( int j = 0; j < m; j++ )
                        for ( int i = 0; i < n; i++ )
                                data[i+j*n] = d[i][j];
                
                dataObj = new IntArray(data);
                
                super.setDataType(DataType.miINT32);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        public void setData(int[] d)
        {
                int n   = d.length;
                int len = n;
                int[] data = new int[len];
                
                for ( int i = 0; i < n; i++ )
                        data[i] = d[i];
                
                dataObj = new IntArray(data);
                
                super.setDataType(DataType.miINT32);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        public void setData(short[][] d)
        {
                int n   = d.length;
                int m   = d[0].length;
                int len = n*m;
                
                short[] data = new short[len];
                
                for ( int j = 0; j < m; j++ )
                        for ( int i = 0; i < n; i++ )
                                data[i+j*n] = d[i][j];
                
                dataObj = new ShortArray(data);
                
                super.setDataType(DataType.miINT16);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        public void setData(short [] d)
        {
                int n   = d.length;
                int len = n;
                short[] data = new short[len];
                
                for ( int i = 0; i < n; i++ )
                        data[i] = d[i];
                
                dataObj = new ShortArray(data);
                
                super.setDataType(DataType.miINT16);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        public void setData(char[][] d)
        {
                int n   = d.length;
                int m   = d[0].length;
                int len = n*m;
                
                char[] data = new char[len];
                
                for ( int j = 0; j < m; j++ )
                        for ( int i = 0; i < n; i++ )
                                data[i+j*n] = d[i][j];
                
                dataObj = new CharArray(data);
                
                super.setDataType(DataType.miUINT16);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        public void setData(char [] d)
        {
                int n   = d.length;
                int len = n;
                char[] data = new char[len];
                
                for ( int i = 0; i < n; i++ )
                        data[i] = d[i];
                
                dataObj = new CharArray(data);
                
                super.setDataType(DataType.miUINT16);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        public void setData(byte [][] d)
        {
                int n   = d.length;
                int m   = d[0].length;
                int len = n*m;
                
                byte[] data = new byte[len];
                
                for ( int j = 0; j < m; j++ )
                        for ( int i = 0; i < n; i++ )
                                data[i+j*n] = d[i][j];
                
                dataObj = new ByteArray(data);
                
                super.setDataType(DataType.miINT8);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        public void setData(byte [] d)
        {
                int n   = d.length;
                int len = n;
                byte[] data = new byte[len];
                
                for ( int i = 0; i < n; i++ )
                        data[i] = d[i];
                
                dataObj = new ByteArray(data);
                
                super.setDataType(DataType.miINT8);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        public void setData(boolean [][] d)
        {
                int n   = d.length;
                int m   = d[0].length;
                int len = n*m;
                
                boolean[] data = new boolean[len];
                
                for ( int j = 0; j < m; j++ )
                        for ( int i = 0; i < n; i++ )
                                data[i+j*n] = d[i][j];
                
                dataObj = new BooleanArray(data);
                
                super.setDataType(DataType.miUINT8);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        public void setData(boolean [] d)
        {
                int n   = d.length;
                int len = n;
                boolean[] data = new boolean[len];
                
                for ( int i = 0; i < n; i++ )
                        data[i] = d[i];
                
                dataObj = new BooleanArray(data);
                
                super.setDataType(DataType.miUINT8);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        private void setData(double [][] d, boolean compress)
        {
                int n   = d.length;
                int m   = d[0].length;
                int len = n*m;
                
                double[] data = new double[len];
                
                for ( int j = 0; j < m; j++ )
                        for ( int i = 0; i < n; i++ )
                                data[i+j*n] = d[i][j];
                
                dataObj = new DoubleArray(data);
                
                super.setDataType(DataType.miDOUBLE);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
                
                if (compress == true)
                        compressDouble(data);
        }
        
        private void setData(double [] d, boolean compress)
        {
                int n   = d.length;
                int len = n;
                double[] data = new double[len];
                
                for ( int i = 0; i < n; i++ )
                        data[i] = d[i];
                
                dataObj = new DoubleArray(data);
                
                super.setDataType(DataType.miDOUBLE);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
                
                if (compress == true)
                        compressDouble(data);
        }
        
        /*
         * Used class internally to be able to set an unsigned type (even, the
         * actual data is signed), in case all data is non-negative
         */
        private void setData(int [] d, DataType type)
        {
                int n   = d.length;
                int len = n;
                int[] data = new int[len];
                
                for ( int i = 0; i < n; i++ )
                        data[i] = d[i];
                
                dataObj = new IntArray(data);
                
                super.setDataType(type);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        private void setData(short [] d, DataType type)
        {
                int n   = d.length;
                int len = n;
                short[] data = new short[len];
                
                for ( int i = 0; i < n; i++ )
                        data[i] = d[i];
                
                dataObj = new ShortArray(data);
                
                super.setDataType(type);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        private void setData(byte [] d, DataType type)
        {
                int n   = d.length;
                int len = n;
                byte[] data = new byte[len];
                
                for ( int i = 0; i < n; i++ )
                        data[i] = d[i];
                
                dataObj = new ByteArray(data);
                
                super.setDataType(type);
                super.setNumOfDataElementBytes(data.length*super.sizeOfDataType());
        }
        
        public byte[] dataToByteArray(ByteOrder byte_order)
        {
                return dataObj.dataToByteArray(byte_order);
        }
        
        private void compressDouble(double[] data)
        {
                int      reqBytes = requiredByteSize(data);
                boolean  signed   = reqBytes < 0;
                
                reqBytes = (signed ? -reqBytes : reqBytes);
                
                if ( reqBytes == 8 ) // No compression possible
                {
                        return;
                }
                
                // The following cases are coordinated
                // with method 'requiredByteSize(double[])'
                // TODO: Are the conversions below right?
                if ( signed )
                {
                        if ( reqBytes == 2 )
                        {
                                short[] d = new short[data.length];
                                
                                for ( int i = 0; i < data.length; i++ )
                                {
                                        d[i] = (short)data[i];
                                }
                                
                                setData(d);
                        }
                        else if ( reqBytes == 4 )
                        {
                                int[] d = new int[data.length];
                                
                                for ( int i = 0; i < data.length; i++ )
                                {
                                        d[i] = (int)data[i];
                                }
                                
                                setData(d);
                        }
                }
                else
                {
                        if ( reqBytes == 1 )
                        {
                                byte[] d = new byte[data.length];
                                
                                for ( int i = 0; i < data.length; i++ )
                                {
                                        // Convert first to long to make a
                                        // clean conversion from double to
                                        // a first integer type
                                        long tmp = (long)data[i];
                                        d[i] = (byte)tmp;
                                }
                                
                                setData(d,DataType.miUINT8);
                        }
                        else if ( reqBytes == 2 )
                        {
                                short[] d = new short[data.length];
                                
                                for ( int i = 0; i < data.length; i++ )
                                {
                                        // Convert first to long to make a
                                        // clean conversion from double to
                                        // a first integer type
                                        long tmp = (long)data[i];
                                        d[i] = (short)tmp;
                                }
                                
                                setData(d,DataType.miUINT16);
                        }
                        else if ( reqBytes == 4 )
                        {
                                int[] d = new int[data.length];
                                
                                for ( int i = 0; i < data.length; i++ )
                                {
                                        // Convert first to long to make a
                                        // clean conversion from double to
                                        // a first integer type
                                        long tmp = (long)data[i];
                                        d[i] = (int)tmp;
                                }
                                
                                setData(d,DataType.miUINT32);
                        }
                }
        }
        /**
         * Check whether the double-array could be also represented
         * by a byte- or short- or int-array (signed/unsigned) without loss of information.
         * 
         * @param d  the int-array to be checked for compression
         * @return   the number of bytes required per int encoded as the magnitude of the returned value;
         *           if the return value is negative the array contains signed values
         */
        private int requiredByteSize(double[] d)
        {
                // Check whether values are really double (in opposite to integer)
                for ( int i = 0; i < d.length; i++ )
                        if ( d[i] != Math.round(d[i]) )
                                return 8;
                
                // Find min and max value to check necessary range
                long minVal = 0;
                long maxVal = 0;
                for ( int i = 0; i < d.length; i++ )
                {
                        if ( d[i] < minVal )
                        {
                                minVal = (long)d[i];
                        }
                        else if ( d[i] > maxVal )
                        {
                                maxVal = (long)d[i];
                        }
                }
                
                int nBytes = 8;
                
                /*
                 * MATLAB chooses (test with single MATLAB variable of type double)
                 * -2^7    i16      0      u8   2^8-1
                 * -2^8    i16      0      u16  2^8
                 * -2^15   i16      0      u16  2^15-1
                 * -2^15   i16      0      u16  2^15
                 * -2^15-1 i32      0      u16  2^16-1
                 * -2^16   i32      0      i32  2^16
                 * -2^31   i32      0      i32  2^31-1
                 * -2^31-1 double   0   double  2^31
                 * -2^32   double   0   double  2^32
                 * 
                 * -2^15 < x < 2^15-1   : i16
                 */
                boolean signed = minVal < 0;
                
                if ( signed )
                {
                        // Found that MATLAB not really using miINT8 for signed;
                        // so we skip test for 8 bytes
                        
                        if ( Short.MIN_VALUE <= minVal && maxVal <= Short.MAX_VALUE )
                        {
                                nBytes = 2; // int16
                        }
                        else if ( Integer.MIN_VALUE <= minVal && maxVal <= Integer.MAX_VALUE )
                        {
                                nBytes = 4; // int32
                        }
                }
                else
                {
                        if ( maxVal <= (0x1<<Byte.SIZE)-1 )
                        {
                                nBytes = 1; // uint8
                        }
                        else if ( maxVal <= (0x1<<Short.SIZE)-1 )
                        {
                                nBytes = 2; // uint16
                        }
                        else if ( maxVal <= Math.pow(2,31)-1 )
                        {
                                nBytes = 4;
                                
                                // force (signed) int32 (MATLAB apparently does!?)
                                signed = true;
                        }
                }
                
                return (signed ? -nBytes : nBytes);
        }
        public NumericPartSubelement read(MyDataInputStream in)
        {
                DataTagFieldReader tagInfo = new DataTagFieldReader(in.getByteOrder());
                
                tagInfo.read(in);
                
                // This call to 'setDataType(...)' is superfluous, since
                // 1) the data is always converted to type double anyway;
                // see the definition of variable 'out' and call of function
                // 'bytesToDouble(...)' that always returns type double further
                // down
                // and
                // 2) function setData( double[], boolean ) is always called at
                // the end in this function, which itself will set the type
                // to miDOUBLE, too.
                super.setDataType(tagInfo.getDataType());
                
                int dataSize = tagInfo.getDataSize();
                int atomSize = tagInfo.getDataType().sizeOf();
                
                int nElements = dataSize / atomSize;
                
                DataType  type = tagInfo.getDataType();
                
                if (tagInfo.isShortElement() == true)
                {
                        int offset = 4; // skip short tag itself
                        byte[] buf = tagInfo.getTagData();
                        
                        // The following may produce trouble with the
                        // satData(...) method at the end
                        // Type recognition can fail in case of e.g.
                        // miUINT8 and char-type
                        if ( type == DataType.miINT8 )
                        {
                                byte[] out2 = new byte[nElements];
                                
                                for ( int i = 0; i < nElements; i++ )
                                {
                                        out2[i] = Bytes.toByte(buf, offset, in.getByteOrder());
                                        offset += atomSize;
                                }
                                
                                setData(out2,DataType.miINT8);
                        }
                        else if ( type == DataType.miUINT8 )
                        {
                                byte[] out2 = new byte[nElements];
                                
                                for ( int i = 0; i < nElements; i++ )
                                {
                                        out2[i] = (byte)Bytes.toUByte(buf, offset, in.getByteOrder());
                                        offset += atomSize;
                                }
                                
                                setData(out2,DataType.miUINT8);
                        }
                        else if ( type == DataType.miINT16 )
                        {
                                short[] out2 = new short[nElements];
                                
                                for ( int i = 0; i < nElements; i++ )
                                {
                                        out2[i] = Bytes.toShort(buf, offset, in.getByteOrder());
                                        offset += atomSize;
                                }
                                
                                setData(out2,DataType.miINT16);
                        }
                        else if ( type == DataType.miUINT16 )
                        {
                                short[] out2 = new short[nElements];
                                
                                for ( int i = 0; i < nElements; i++ )
                                {
                                        out2[i] = (short)Bytes.toUShort(buf, offset, in.getByteOrder());
                                        offset += atomSize;
                                }
                                
                                setData(out2);
                        }
                        else if ( type == DataType.miINT32 )
                        {
                                int[] out2 = new int[nElements];
                                
                                for ( int i = 0; i < nElements; i++ )
                                {
                                        out2[i] = Bytes.toInt(buf, offset, in.getByteOrder());
                                        offset += atomSize;
                                }
                                
                                setData(out2,DataType.miINT32);
                        }
                        else if ( type == DataType.miUINT32 )
                        {
                                int[] out2 = new int[nElements];
                                
                                for ( int i = 0; i < nElements; i++ )
                                {
                                        out2[i] = (int)Bytes.toUInt(buf, offset, in.getByteOrder());
                                        offset += atomSize;
                                }
                                
                                setData(out2,DataType.miUINT32);
                        }
                        else if ( type == DataType.miSINGLE )
                        {
                                float[] out2 = new float[nElements];
                                
                                for ( int i = 0; i < nElements; i++ )
                                {
                                        out2[i] = Bytes.toFloat(buf, offset, in.getByteOrder());
                                        offset += atomSize;
                                }
                                
                                setData(out2);
                        }
                }
                else
                {
                        if ( type == DataType.miINT8 )
                        {
                                byte[] out2 = new byte[nElements];
                                
                                try {
                                        for ( int i = 0; i < nElements; i++ )
                                        {
                                                out2[i] = (byte)in.readByte();
                                        }
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                }
                                
                                setData(out2,DataType.miINT8);
                        }
                        else if ( type == DataType.miUINT8 )
                        {
                                byte[] out2 = new byte[nElements];
                                
                                try {
                                        for ( int i = 0; i < nElements; i++ )
                                        {
                                                out2[i] = (byte)in.readUnsignedByte();
                                        }
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                }
                                
                                setData(out2,DataType.miUINT8);
                        }
                        else if ( type == DataType.miINT16 )
                        {
                                short[] out2 = new short[nElements];
                                
                                try {
                                        for ( int i = 0; i < nElements; i++ )
                                        {
                                                out2[i] = in.readShort();
                                        }
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                }
                                
                                setData(out2,DataType.miINT16);
                        }
                        else if ( type == DataType.miUINT16 )
                        {
                                char[] out2 = new char[nElements];
                                
                                try {
                                        for ( int i = 0; i < nElements; i++ )
                                        {
                                                out2[i] = (char)in.readChar();
                                        }
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                }
                                
                                setData(out2);
                        }
                        else if ( type == DataType.miINT32 )
                        {
                                int[] out2 = new int[nElements];
                                
                                try {
                                        for ( int i = 0; i < nElements; i++ )
                                        {
                                                out2[i] = in.readInt();
                                        }
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                }
                                
                                setData(out2,DataType.miINT32);
                        }
                        else if ( type == DataType.miUINT32 )
                        {
                                int[] out2 = new int[nElements];
                                
                                try {
                                        for ( int i = 0; i < nElements; i++ )
                                        {
                                                out2[i] = (int)in.readUnsignedInt();
                                        }
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                }
                                
                                setData(out2,DataType.miUINT32);
                        }
                        else if ( type == DataType.miDOUBLE )
                        {
                                double[] out2 = new double[nElements];
                                
                                try {
                                        for ( int i = 0; i < nElements; i++ )
                                        {
                                                out2[i] = in.readDouble();
                                        }
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                }
                                
                                setData(out2,false);
                        }
                        
                        try {
                                in.skip(tagInfo.getPadding());
                        }
                        catch (IOException e) {
                                e.printStackTrace();
                                return null;
                        }
                }
                
                return this;
        }
}
