package abstractTypes;

import java.nio.ByteOrder;

import common.DataType;
import common.MxClassID;

import subelements.common.ArrayFlagsSubelement;
import subelements.common.ArrayNameSubelement;
import subelements.common.DimensionsArraySubelement;

/**
 * This abstract class is the base class of all MATLAB array types
 * (type miMATRIX(14)). The miMATRIX has the structure of a Data Elements
 * (Data Tag Field + Data Part). It is a compound data type
 * composed of multiple sub-elements. All sub-elements are considered as data
 * and therefore belong to the Data Part of every Array Element.
 * Three sub-elements are common to all Array Data Elements:
 * Array Flags, Dimensions Array and Array Name. Such arrays are already made
 * elements of this abstract class. The other sub-elements are depending on
 * the specific array type and must be defined in derived classes.
 * 
 * |-------------------------------------------------|
 * | Data Type (=miMATRIX) |     Number of Bytes     |  Tag (2 x 32 bit)
 * |-------------------------------------------------|
 * |      Array Flags sub-element (2 x 4 bytes)      |      ||
 * |-------------------------------------------------|      ||
 * |   Dimensions Array sub-element (variable size)  |      ||
 * |-------------------------------------------------|      ||
 * |      Array Name sub-element (variable size)     |      || Data Part
 * |-------------------------------------------------|      ||
 * |                                                 |      ||
 * |       (specific for each array type) Data       |      ||
 * |-------------------------------------------------|      ||
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public abstract class AbstractArrayElement extends AbstractDataElement
{
        private ArrayFlagsSubelement       arrayFlagsSubelement;
        private DimensionsArraySubelement  dimensionsArraySubelement;
        private ArrayNameSubelement        arrayNameSubelement;
        
        /*
         * Number of bytes of the array-specific Data Element
         */
        private int size_ArraySpecificDataPart;
        
        protected final boolean matlabNameConvention = true;
        
        protected AbstractArrayElement()
        {
                this(false);
        }
        
        protected AbstractArrayElement(boolean compFlag)
        {
                super(DataType.miMATRIX,compFlag);
                arrayFlagsSubelement       = new ArrayFlagsSubelement();
                dimensionsArraySubelement  = new DimensionsArraySubelement();
                arrayNameSubelement        = new ArrayNameSubelement();
                size_ArraySpecificDataPart = 0;
        }
        
        protected void setDimensions(int[] dims)
        {
                dimensionsArraySubelement.setData(dims);
                
                // Since the DimensionsArraySubelement has been changed,
                // force an update of the calculation of number of bytes
                // needed to represent the array-specific data and
                // the array common fields.
                setNumOfSpecDataBytes(size_ArraySpecificDataPart);
        }
        
        public void setName(String name)
        {
                arrayNameSubelement.setData(name);
                
                // Since the ArrayNameSubelement has been changed,
                // force an update of the calculation of number of bytes
                // needed to represent the array-specific data and
                // the array common fields.
                setNumOfSpecDataBytes(size_ArraySpecificDataPart);
        }
        
        protected void setClassType(MxClassID type)
        {
                arrayFlagsSubelement.setClassType(type);
        }
        
        protected void setSparseFlag(boolean flag)
        {
                arrayFlagsSubelement.setSparseFlag(flag);
        }
        
        protected void setComplexFlag(boolean flag)
        {
                arrayFlagsSubelement.setComplexFlag(flag);
        }
        
        public void setGlobalFlag(boolean flag)
        {
                arrayFlagsSubelement.setGlobalFlag(flag);
        }
        
        protected void setLogicalFlag(boolean flag)
        {
                arrayFlagsSubelement.setLogicalFlag(flag);
        }
        
        protected void setNzMax(int n)
        {
                arrayFlagsSubelement.setNzMax(n);
        }
        
        /**
         * Set the number of bytes needed to represent the array-specific
         * data and the array common fields ( Array Flags, Dimensions Array
         * and Array Name) in binary form.
         * @param size
         */
        protected void setNumOfSpecDataBytes(int size)
        {
                size_ArraySpecificDataPart = size;
                
                /*
                 * Update the overall size of the Data Part (including
                 * Array Flags, Dimensions Array and Array Name)
                 */
                int numBytes = size_ArraySpecificDataPart;
                numBytes += arrayFlagsSubelement.getTotalElementLength();
                numBytes += dimensionsArraySubelement.getTotalElementLength();
                numBytes += arrayNameSubelement.getTotalElementLength();
                
                super.setNumOfDataElementBytes(numBytes);
        }
        
        protected int getNumOfSpecDataBytes()
        {
                return size_ArraySpecificDataPart;
        }
        
        public String toString()
        {
                String str = "";
                str += "arrayFlagsSubelement = "      + arrayFlagsSubelement.toString();
                str += "dimensionsArraySubelement = " + dimensionsArraySubelement.toString();
                str += "arrayNameSubelement = "       + arrayNameSubelement.toString();
                str += " data length = " + super.getDataLength();
                return str;
        }
        
        /**
         * Returns an array of bytes representing the array-specific
         * Data Part (only) of derived Array classes.
         */
        protected abstract byte[] specDataToByteArray(ByteOrder byte_order);
        
        /**
         * Returns an array of bytes representing the Data Part (only) of
         * derived Array classes (consisting of Array Flags, Dimensions array,
         * Array Name and array-specific Data Element).
         */
        protected byte[] dataToByteArray(ByteOrder byte_order)
        {
                byte[] data = specDataToByteArray(byte_order);
                
                byte[] dest = new byte[super.getDataLength()];
                
                int offset = 0;
                
                byte[] tmp;
                
                tmp = arrayFlagsSubelement.toByteArray(byte_order);
                System.arraycopy(tmp, 0, dest, offset, tmp.length);
                offset += tmp.length;
                
                tmp = dimensionsArraySubelement.toByteArray(byte_order);
                System.arraycopy(tmp, 0, dest, offset, tmp.length);
                offset += tmp.length;
                
                tmp = arrayNameSubelement.toByteArray(byte_order);
                System.arraycopy(tmp, 0, dest, offset, tmp.length);
                offset += tmp.length;
                
                System.arraycopy(data, 0, dest, offset, data.length);
                
                return dest;
        }
}
