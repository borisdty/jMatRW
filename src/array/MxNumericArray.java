package array;

import java.nio.ByteOrder;

import abstractTypes.AbstractArrayElement;
import common.MxClassID;
import subelements.numeric.NumericPartSubelement;

/**
 * 
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxNumericArray extends AbstractArrayElement
{
        private NumericPartSubelement  realPartSubelement = null;
        private NumericPartSubelement  imagPartSubelement = null;
        
        public MxNumericArray(String name)
        {
                this(name,false);
        }
        
        public MxNumericArray(String name, boolean compFlag)
        {
                super(compFlag);
                super.setName(name);
        }
        
        public void setData(double[] data)
        {
                int   dim1 = 1;
                int   dim2 = data.length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxDOUBLE_CLASS);
                super.setDimensions(dims);
                
                realPartSubelement = new NumericPartSubelement(data);
                imagPartSubelement = null;
                
                int numBytes = realPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(double[][] data)
        {
                int   dim1 = data.length;
                int   dim2 = data[0].length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxDOUBLE_CLASS);
                super.setDimensions(dims);
                
                realPartSubelement = new NumericPartSubelement(data);
                imagPartSubelement = null;
                
                int numBytes = realPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(double[] real, double[] imag)
        {
                if ( real == null && imag == null)
                {
                        System.err.println("real == null && imag == null.");
                        return;
                }
                
                /*
                 * if the imaginary part is numerically zero, use a setData(...)
                 * method for pure real input.
                 */
                if ( imag == null )
                {
                        System.err.println("imag == null. " +
                        "Use method setData(double[]) to set the real part only.");
                        imag = new double [real.length]; // Assuming default init in Java is 0.0
                }
                
                /*
                 * There is no pure complex data in MATLAB. If the data
                 * consists of an imaginary part only the real part must be
                 * provided nevertheless (all-zero vector or matrix)
                 */
                if ( real == null )
                {
                        System.err.println("real == null. " +
                        "Provide at least a real part vector of zero (real=zeros(size(imag))).");
                        real = new double [imag.length]; // Assuming default init in Java is 0.0
                }
                
                if ( real.length != imag.length )
                        System.err.println("Dimensions do not fit.");
                
                int   dim1 = 1;
                int   dim2 = real.length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxDOUBLE_CLASS);
                super.setDimensions(dims);
                super.setComplexFlag(true);
                
                realPartSubelement = new NumericPartSubelement(real);
                imagPartSubelement = new NumericPartSubelement(imag);
                
                int numBytes = 0;
                numBytes += realPartSubelement.getTotalElementLength();
                numBytes += imagPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(double[][] real, double[][] imag)
        {
                if ( real == null && imag == null)
                {
                        System.err.println("real == null && imag == null.");
                        return;
                }
                
                /*
                 * if the imaginary part is numerically zero, use a setData(...)
                 * method for pure real input.
                 */
                if ( imag == null )
                {
                        System.err.println("imag == null. " +
                        "Use method setData(double[]) to set the real part only.");
                        imag = new double [real.length][real[0].length]; // Assuming default init in Java is 0.0
                        
                        // Matlab would change flag of the matrix to non-complex (i.e. standard double), if no
                        // matrix element has any non-zero imaginary part. But we keep the, probably intended,
                        // complex interpretation and therefore flagging anyway.
                }
                
                /*
                 * There is no pure complex data in MATLAB. If the data
                 * consists of an imaginary part only the real part must be
                 * provided nevertheless (all-zero vector or matrix)
                 */
                if ( real == null )
                {
                        System.err.println("real == null. " +
                        "Use method setData(double[]) to set the real part only.");
                        real = new double [imag.length][imag[0].length]; // Assuming default init in Java is 0.0
                }
                
                /*
                 * There is no pure complex data in MATLAB. If the data consists of
                 * an imaginary part only the real part must be provided nevertheless
                 * (all-zero vector or matrix)
                 */
                if ( real.length != imag.length || real[0].length != imag[0].length )
                        System.err.println("Dimensions do not fit.");
                
                int   dim1 = real.length;
                int   dim2 = real[0].length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxDOUBLE_CLASS);
                super.setDimensions(dims);
                super.setComplexFlag(true);
                
                realPartSubelement = new NumericPartSubelement(real);
                imagPartSubelement = new NumericPartSubelement(imag);
                
                int numBytes = 0;
                numBytes += realPartSubelement.getTotalElementLength();
                numBytes += imagPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(int[] data)
        {
                int dim1 = 1;
                int dim2 = data.length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxINT32_CLASS);
                super.setDimensions(dims);
                
                realPartSubelement = new NumericPartSubelement(data);
                imagPartSubelement = null;
                
                int numBytes = realPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(int[][] data)
        {
                int dim1 = data.length;
                int dim2 = data[0].length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxINT32_CLASS);
                super.setDimensions(dims);
                
                realPartSubelement = new NumericPartSubelement(data);
                imagPartSubelement = null;
                
                int numBytes = realPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(short[] data)
        {
                int dim1 = 1;
                int dim2 = data.length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxINT16_CLASS);
                super.setDimensions(dims);
                
                realPartSubelement = new NumericPartSubelement(data);
                imagPartSubelement = null;
                
                int numBytes = realPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(short[][] data)
        {
                int dim1 = data.length;
                int dim2 = data[0].length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxINT16_CLASS);
                super.setDimensions(dims);
                
                realPartSubelement = new NumericPartSubelement(data);
                imagPartSubelement = null;
                
                int numBytes = realPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(char[] data)
        {
                int dim1 = 1;
                int dim2 = data.length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxUINT16_CLASS);
                super.setDimensions(dims);
                
                realPartSubelement = new NumericPartSubelement(data);
                imagPartSubelement = null;
                
                int numBytes = realPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(char[][] data)
        {
                int dim1 = data.length;
                int dim2 = data[0].length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxUINT16_CLASS);
                super.setDimensions(dims);
                
                realPartSubelement = new NumericPartSubelement(data);
                imagPartSubelement = null;
                
                int numBytes = realPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(byte[] data)
        {
                int dim1 = 1;
                int dim2 = data.length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxINT8_CLASS);
                super.setDimensions(dims);
                
                realPartSubelement = new NumericPartSubelement(data);
                imagPartSubelement = null;
                
                int numBytes = realPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(byte[][] data)
        {
                int dim1 = data.length;
                int dim2 = data[0].length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxINT8_CLASS);
                super.setDimensions(dims);
                
                realPartSubelement = new NumericPartSubelement(data);
                imagPartSubelement = null;
                
                int numBytes = realPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(boolean[] data)
        {
                int dim1 = 1;
                int dim2 = data.length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxUINT8_CLASS);
                super.setDimensions(dims);
                super.setLogicalFlag(true);
                
                realPartSubelement = new NumericPartSubelement(data);
                imagPartSubelement = null;
                
                int numBytes = realPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(boolean[][] data)
        {
                int dim1 = data.length;
                int dim2 = data[0].length;
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxUINT8_CLASS);
                super.setDimensions(dims);
                super.setLogicalFlag(true);
                
                realPartSubelement = new NumericPartSubelement(data);
                imagPartSubelement = null;
                
                int numBytes = realPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public byte[] specDataToByteArray(ByteOrder byte_order)
        {
                byte[] b      = new byte[super.getNumOfSpecDataBytes()];
                int    offset = 0;
                byte[] tmp;
                
                if ( realPartSubelement != null )
                {
                        tmp = realPartSubelement.toByteArray(byte_order);
                        System.arraycopy(tmp, 0, b, offset, tmp.length);
                        offset += tmp.length;
                }
                
                if ( imagPartSubelement != null )
                {
                        tmp = imagPartSubelement.toByteArray(byte_order);
                        System.arraycopy(tmp, 0, b, offset, tmp.length);
                        offset += tmp.length;
                }
                
                return b;
        }
}
