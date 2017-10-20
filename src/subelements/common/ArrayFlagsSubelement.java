package subelements.common;

import io.MyDataInputStream;

import java.io.IOException;
import java.nio.ByteOrder;

import abstractTypes.AbstractDataElement;

import common.DataTagFieldReader;
import common.DataType;
import common.MxClassID;
import exception.DataTypeException;

/**
 * Sub-element that is common to all array types.
 * It is itself a Data Element and consists of Data Tag Field and Data Part.
 * This sub-element identifies the MATLAB array type (class) represented
 * by the Data Element of the Array Data Element and provides other
 * information about the array. The value of the Class field in the Array Flags
 * sub-element identifies the original MATLAB data type, independent of
 * the actual type to represent the data (e.g. double(1) can be represented by a uint8(1)).
 * The ArrayFlagsSubelement's data type is fixed to miUINT32. The Data Part
 * consists of two fields of type miUINT32 (a total of 2x4=8 bytes).
 * 
 * |--------------------------------------------------
 * | Data Type (=miUINT32) |     Number of Bytes     |  Tag (2 x 32 bit)
 * |--------------------------------------------------
 * | undefined |Flags|Class|       undefined         |  Data Part
 * |-------------------------------------------------|
 * 
 * For sparse matrices, bytes 5 through 8 are used to store the maximum
 * number of nonzero element in the matrix (nzmax).
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class ArrayFlagsSubelement extends AbstractDataElement
{
        private boolean        flag_COMPLEX;
        private boolean        flag_GLOBAL;
        private boolean        flag_LOGICAL;
        private MxClassID      classType;
        private int            nzmax;
        
        public ArrayFlagsSubelement()
        {
                super(DataType.miUINT32);
                
                flag_COMPLEX = false;
                flag_GLOBAL  = false;
                flag_LOGICAL = false;
                
                int nbytes = 2 * super.sizeOfDataType();
                super.setNumOfDataElementBytes(nbytes);
        }
        
        public ArrayFlagsSubelement(MxClassID classType)
        {
                this();
                this.classType = classType;
        }
        
        public boolean   getComplexFlag() { return flag_COMPLEX; }
        public boolean   getGlobalFlag()  { return flag_GLOBAL;  }
        public boolean   getLogicalFlag() { return flag_LOGICAL; }
        public MxClassID getClassType()   { return classType; }
        public int       getNzMax()       { return nzmax; }
        
        public void setComplexFlag(boolean flag)   { flag_COMPLEX = flag; }
        public void setGlobalFlag (boolean flag)   { flag_GLOBAL  = flag; }
        public void setLogicalFlag(boolean flag)   { flag_LOGICAL = flag; }
        public void setClassType  (MxClassID type) { classType    = type; }
        public void setNzMax      (int     n)      { nzmax        = n;    }
        
        @Override
        public String toString()
        {
                String str = "";
                str += super.toString();
                str += " flag_COMPLEX = " + flag_COMPLEX;
                str += " flag_Global = "  + flag_GLOBAL;
                str += " flag_Logical = " + flag_LOGICAL;
                str += " classType = "    + classType;
                str += " nzmax = "        + nzmax;
                return str;
        }
        
        /**
         * Returns an array of bytes representing the Data Part (only).
         * The array consists of 2 x miUINT32 = 8 bytes.
         * @return array of bytes representing the Data Part (only)
         */
        public byte[] dataToByteArray(ByteOrder byte_order)
        {
                byte[] b = new byte[super.getDataLength()];
                
                byte flags = 0;
                flags |= (flag_COMPLEX == true) ? 0x08 : 0x00;
                flags |= (flag_GLOBAL  == true) ? 0x04 : 0x00;
                flags |= (flag_LOGICAL == true) ? 0x02 : 0x00;
                
                if ( byte_order.equals(ByteOrder.BIG_ENDIAN) )
                {
                        b[0] = 0;
                        b[1] = 0;
                        b[2] = flags;
                        b[3] = (byte)classType.getValue();
                        
                        b[4] = (byte)(nzmax >> 24);
                        b[5] = (byte)(nzmax >> 16);
                        b[6] = (byte)(nzmax >> 8);
                        b[7] = (byte)(nzmax >> 0);
                }
                else
                {
                        b[3] = 0;
                        b[2] = 0;
                        b[1] = flags;
                        b[0] = (byte)classType.getValue();
                        
                        b[7] = (byte)(nzmax >> 24);
                        b[6] = (byte)(nzmax >> 16);
                        b[5] = (byte)(nzmax >> 8);
                        b[4] = (byte)(nzmax >> 0);
                }
                
                return b;
        }
        
        public ArrayFlagsSubelement read(MyDataInputStream in) throws DataTypeException
        {
                DataTagFieldReader tagInfo = new DataTagFieldReader(in.getByteOrder());
                tagInfo.read(in);
                
                if ( tagInfo.getDataType() != DataType.miUINT32 )
                        throw new DataTypeException( DataType.miUINT32, tagInfo.getDataType() );
                
                if ( tagInfo.getTagSize() != 8 )
                        return null;
                
                byte[] buf = new byte[4];
                try {
                        in.read(buf);
                }
                catch (IOException e) {
                        e.printStackTrace();
                        return null;
                }
                
                int       flags   = (buf[2] & 0xFF) & 0x0E;
                MxClassID classId = MxClassID.get(buf[3] & 0xFF);
                if (in.getByteOrder().equals(ByteOrder.LITTLE_ENDIAN))
                {
                        flags   = (buf[1] & 0xFF) & 0x0E;
                        classId = MxClassID.get(buf[0] & 0xFF);
                }
                
                int nz;
                try {
                        nz = (int)in.readUnsignedInt();
                }
                catch (IOException e) {
                        e.printStackTrace();
                        return null;
                }
                
                setComplexFlag((flags & 0x08) > 0);
                setGlobalFlag ((flags & 0x04) > 0);
                setLogicalFlag((flags & 0x02) > 0);
                setClassType(classId);
                setNzMax(nz);
                
                return this;
        }
}
