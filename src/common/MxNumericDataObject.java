package common;

import exception.ClassIDException;
import exception.LossOfPrecisionException;
import subelements.base.ByteArray;
import subelements.base.DataArray;
import subelements.base.IntArray;
import subelements.base.LongArray;
import subelements.base.ShortArray;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxNumericDataObject extends MxDataObject
{
        protected DataArray real_part;
        protected DataArray imag_part;
        
        protected DataType  real_data_type;
        protected DataType  imag_data_type;
        
        public MxNumericDataObject()
        {
                real_part = null;
                imag_part = null;
                
                real_data_type = null;
                imag_data_type = null;
        }
        
        /**
         * Initialize a MxNumericDataObject with an object of
         * type MxDataObject. Only the internal data that is
         * shared between all object types derived from MxDataObject
         * are considered.
         * @param other  the MxNumericDataObject used for initialization
         */
        public MxNumericDataObject( MxDataObject other )
        {
                super(other);
                
                real_part = null;
                imag_part = null;
                
                real_data_type = null;
                imag_data_type = null;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
                final int prime = 31;
                int result = super.hashCode();
                result = prime * result + ((imag_data_type == null) ? 0 : imag_data_type.hashCode());
                result = prime * result + ((imag_part == null) ? 0 : imag_part.hashCode());
                result = prime * result + ((real_data_type == null) ? 0 : real_data_type.hashCode());
                result = prime * result + ((real_part == null) ? 0 : real_part.hashCode());
                return result;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj)
        {
                if (this == obj)
                        return true;
                
                if (!super.equals(obj))
                        return false;
                
                if (getClass() != obj.getClass())
                        return false;
                
                MxNumericDataObject other = (MxNumericDataObject) obj;
                
                if (real_data_type != other.real_data_type)
                        return false;
                
                if (imag_data_type != other.imag_data_type)
                        return false;
                
                if (real_part == null)
                {
                        if (other.real_part != null) {
                                return false;
                        }
                }
                else if (!real_part.equals(other.real_part)) {
                        return false;
                }
                
                if (imag_part == null)
                {
                        if (other.imag_part != null) {
                                return false;
                        }
                } else if (!imag_part.equals(other.imag_part))
                {
                        return false;
                }
                
                return true;
        }
        
        public double[][] getRealDoubleMtx() throws ClassIDException
        {
                if ( classType != MxClassID.mxDOUBLE_CLASS )
                {
                        throw new ClassIDException(MxClassID.mxDOUBLE_CLASS,classType);
                }
                
                int dim1 = dimensions[0];
                int dim2 = dimensions[1];
                
                double[] d = real_part.toDoubleArray(real_data_type.isSignedType()).getData();
                
                double[][] data = reshape(d,dim1,dim2);
                
                return data;
        }
        
        public double[][] getImagDoubleMtx() throws ClassIDException
        {
                if ( classType != MxClassID.mxDOUBLE_CLASS )
                {
                        throw new ClassIDException(MxClassID.mxDOUBLE_CLASS,classType);
                }
                
                if ( isComplex() == false )
                {
                        System.err.println( "\tVariable " + getName() + " is not of complex type" +
                                        " and has therefore no imaginary part stored." );
                        return null;
                }
                
                int dim1 = dimensions[0];
                int dim2 = dimensions[1];
                
                double[] d = imag_part.toDoubleArray(imag_data_type.isSignedType()).getData();
                
                double[][] data = reshape(d,dim1,dim2);
                
                return data;
        }
        
        public long[] getInt64LinearArray() throws ClassIDException, LossOfPrecisionException
        {
                if ( classType != MxClassID.mxINT64_CLASS )
                {
                        throw new ClassIDException(MxClassID.mxINT64_CLASS,classType);
                }
                
                if ( !(real_part instanceof LongArray) )
                        throw new LossOfPrecisionException();
                
                LongArray longArray = (LongArray)real_part;
                
                long[] data = longArray.getData();
                
                return data;
        }
        
        public int[] getInt32LinearArray() throws ClassIDException, LossOfPrecisionException
        {
                if ( classType != MxClassID.mxINT32_CLASS )
                {
                        throw new ClassIDException(MxClassID.mxINT32_CLASS,classType);
                }
                
                if ( !(real_part instanceof IntArray) )
                        throw new LossOfPrecisionException();
                
                IntArray intArray = (IntArray)real_part;
                
                int[] data = intArray.getData();
                
                return data;
        }
        
        public long[] getUInt32LinearArray() throws ClassIDException, LossOfPrecisionException
        {
                if ( classType != MxClassID.mxUINT32_CLASS )
                {
                        throw new ClassIDException(MxClassID.mxUINT32_CLASS,classType);
                }
                
                if ( !(real_part instanceof IntArray) )
                        throw new LossOfPrecisionException();
                
                IntArray intArray = (IntArray)real_part;
                
                int[] d = intArray.getData();
                
                long[] data = new long[d.length];
                
                for ( int k = 0; k < d.length; k++ )
                        data[k] = d[k] & 0xFFFFFFFFL;
                
                return data;
        }
        
        public short[] getInt16LinearArray() throws ClassIDException, LossOfPrecisionException
        {
                if ( classType != MxClassID.mxINT16_CLASS )
                {
                        throw new ClassIDException(MxClassID.mxINT16_CLASS,classType);
                }
                
                if ( !(real_part instanceof ShortArray) )
                        throw new LossOfPrecisionException();
                
                ShortArray shortArray = (ShortArray)real_part;
                
                short[] data = shortArray.getData();
                
                return data;
        }
        
        public int[] getUInt16LinearArray() throws ClassIDException, LossOfPrecisionException
        {
                if ( classType != MxClassID.mxUINT16_CLASS )
                {
                        throw new ClassIDException(MxClassID.mxUINT16_CLASS,classType);
                }
                
                if ( !(real_part instanceof ShortArray) )
                        throw new LossOfPrecisionException();
                
                ShortArray shortArray = (ShortArray)real_part;
                
                short[] d = shortArray.getData();
                
                int[] data = new int[d.length];
                
                for ( int k = 0; k < d.length; k++ )
                        data[k] = d[k] & 0xFFFF;
                
                return data;
        }
        
        public byte[] getInt8LinearArray() throws ClassIDException, LossOfPrecisionException
        {
                if ( classType != MxClassID.mxINT8_CLASS )
                {
                        throw new ClassIDException(MxClassID.mxINT8_CLASS,classType);
                }
                
                if ( !(real_part instanceof ByteArray) )
                        throw new LossOfPrecisionException();
                
                ByteArray byteArray = (ByteArray)real_part;
                
                byte[] data = byteArray.getData();
                
                return data;
        }
        
        public short[] getUInt8LinearArray() throws ClassIDException, LossOfPrecisionException
        {
                if ( classType != MxClassID.mxUINT8_CLASS )
                {
                        throw new ClassIDException(MxClassID.mxUINT8_CLASS,classType);
                }
                
                if ( !(real_part instanceof ByteArray) )
                        throw new LossOfPrecisionException();
                
                ByteArray byteArray = (ByteArray)real_part;
                
                byte[] d = byteArray.getData();
                
                short[] data = new short[d.length];
                
                for ( int k = 0; k < d.length; k++ )
                        data[k] = (short)(d[k] & 0xFF);
                
                return data;
        }
        
        public boolean[] getBooleanLinearArray() throws ClassIDException, LossOfPrecisionException
        {
                if ( classType != MxClassID.mxUINT8_CLASS )
                {
                        throw new ClassIDException(MxClassID.mxUINT8_CLASS,classType);
                }
                
                if ( isLogical() == false )
                {
                        System.err.println( "\tThe logical-flag was not set," +
                                        " but boolean will" +
                                        " be generated anyway according to" +
                        " value != 0 is boolean true." );
                }
                
                if ( !(real_part instanceof ByteArray) )
                        throw new LossOfPrecisionException();
                
                ByteArray byteArray = (ByteArray)real_part;
                
                byte[] d = byteArray.getData();
                
                boolean[] data = new boolean[d.length];
                
                for ( int k = 0; k < d.length; k++ )
                        data[k] = d[k] != 0;
                
                return data;
        }
        
        private double[][] reshape (double[] in, int dim1, int dim2)
        {
                double[][] out = new double[dim1][dim2];
                for ( int j = 0; j < dim2; j++ )
                        for ( int i = 0; i < dim1; i++ )
                                out[i][j] = in[i+j*dim1];
                return out;
        }
        
        public DataArray getRealDataStorage()
        {
                return real_part;
        }
        
        public DataArray getImagDataStorage()
        {
                return imag_part;
        }
        
        public DataType getRealDataStorageType()
        {
                return real_data_type;
        }
        
        public DataType getImagDataStorageType()
        {
                return imag_data_type;
        }
        
        public void setRealDataStorageType(DataType type)
        {
                this.real_data_type = type;
        }
        
        public void setImagDataStorageType(DataType type)
        {
                this.imag_data_type = type;
        }
        
        public void setRealDataStorage(DataArray real_part)
        {
                this.real_part = real_part;
        }
        
        public void setImagDataStorage(DataArray imag_part)
        {
                this.imag_part = imag_part;
        }
}
