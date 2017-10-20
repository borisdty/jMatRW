package array;

import java.nio.ByteOrder;
import java.util.ArrayList;

import abstractTypes.AbstractArrayElement;
import common.MxClassID;

import subelements.numeric.NumericPartSubelement;
import subelements.sparse.ColumnIndexSubelement;
import subelements.sparse.RowIndexSubelement;

/**
 * 
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxSparseArray extends AbstractArrayElement
{
        private RowIndexSubelement     rowIndexSubelement;
        private ColumnIndexSubelement  colIndexSubelement;
        private NumericPartSubelement  realPartSubelement = null;
        private NumericPartSubelement  imagPartSubelement = null;
        
        public MxSparseArray(String name)
        {
                this(name,false);
        }
        
        public MxSparseArray(String name, boolean compFlag)
        {
                super(compFlag);
                super.setClassType(MxClassID.mxSPARSE_CLASS);
                super.setName(name);
        }
        
        /*
         * Input data format for sparse matrix S with N non-zero elements is:
         *     i=1:N:  S( ir[i], ic[i] ) = data_real[i]
         * This is not the MATLAB internal method to index non-zero elements;
         * the representation is internally transformed to the MATLAB way.
         */
        public void setData(int[] ir, int[] jc, double[] data_real)
        {
                FlexMatrix entries = new FlexMatrix();
                
                entries.add(ir, jc, data_real);
                
                int[] dims = entries.getDimensions();
                
                super.setDimensions(dims);
                super.setNzMax(entries.getNumElements());
                
                SparseElement[] a = entries.toArray(); // Entries are in column order
                
                int[] mxir           = new int[a.length];
                int[] mxjc           = new int[dims[1]+1];
                double[] mxdata_real = new double[a.length];
                
                int i = 0;
                for ( SparseElement e : a )
                {
                        mxir[i]          = e.pos_row;
                        mxdata_real[i] = e.data_real;
                        i++;
                        
                        mxjc[e.pos_col+1]++;
                        
//                        // jc[j] is the sum of nonzero elements in all columns [0:j-1]
//                        // jc[j+1] - jc[j] = #(elements column j)
//                        for (int col = e.pos_col + 1; col < jc.length; col++)
//                        {
//                                mxjc[col]++;
//                        }
                }
                
                mxjc[0] = 0;
                for ( int k = 1; k < dims[1]+1; k++ )
                {
                        mxjc[k] += mxjc[k-1];
                }
                
                rowIndexSubelement = new RowIndexSubelement(mxir);
                colIndexSubelement = new ColumnIndexSubelement(mxjc);
                realPartSubelement = new NumericPartSubelement(mxdata_real);
                
                int numBytes = 0;
                numBytes += rowIndexSubelement.getTotalElementLength();
                numBytes += colIndexSubelement.getTotalElementLength();
                numBytes += realPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public void setData(int[] ir, int[] jc, double[] data_real, double[] data_imag)
        {
                FlexMatrix entries = new FlexMatrix();
                
                entries.add(ir, jc, data_real, data_imag);
                
                int[] dims = entries.getDimensions();
                
                super.setComplexFlag(true);
                super.setDimensions(dims);
                super.setNzMax(entries.getNumElements());
                
                SparseElement[] a = entries.toArray(); // Entries are in column order
                
                int[] mxir           = new int[a.length];
                int[] mxjc           = new int[dims[1]+1];
                double[] mxdata_real = new double[a.length];
                double[] mxdata_imag = new double[a.length];
                
                int i = 0;
                for ( SparseElement e : a )
                {
                        mxir[i]          = e.pos_row;
                        // mxdata_real[i++] = e.data_real; // BUG???
                        mxdata_real[i] = e.data_real;
                        mxdata_imag[i] = e.data_imag;
                        i++;
                        
                        mxjc[e.pos_col+1]++;
                        
//                        // jc[j] is the sum of nonzero elements in all columns [0:j-1]
//                        // jc[j+1] - jc[j] = #(elements column j)
//                        for (int col = e.pos_col + 1; col < jc.length; col++)
//                        {
//                                mxjc[col]++;
//                        }
                }
                
                mxjc[0] = 0;
                for ( int k = 1; k < dims[1]+1; k++ )
                {
                        mxjc[k] += mxjc[k-1];
                }
                
                rowIndexSubelement = new RowIndexSubelement(mxir);
                colIndexSubelement = new ColumnIndexSubelement(mxjc);
                realPartSubelement = new NumericPartSubelement(mxdata_real);
                imagPartSubelement = new NumericPartSubelement(mxdata_imag);
                
                int numBytes = 0;
                numBytes += rowIndexSubelement.getTotalElementLength();
                numBytes += colIndexSubelement.getTotalElementLength();
                numBytes += realPartSubelement.getTotalElementLength();
                numBytes += imagPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public byte[] specDataToByteArray(ByteOrder byte_order)
        {
                byte[] b      = new byte[super.getNumOfSpecDataBytes()];
                int    offset = 0;
                
                byte[] tmp;
                
                tmp = rowIndexSubelement.toByteArray(byte_order);
                System.arraycopy(tmp, 0, b, offset, tmp.length);
                offset += tmp.length;
                
                tmp = colIndexSubelement.toByteArray(byte_order);
                System.arraycopy(tmp, 0, b, offset, tmp.length);
                offset += tmp.length;
                
                tmp = realPartSubelement.toByteArray(byte_order);
                System.arraycopy(tmp, 0, b, offset, tmp.length);
                offset += tmp.length;
                
                if ( imagPartSubelement != null )
                {
                        tmp = imagPartSubelement.toByteArray(byte_order);
                        System.arraycopy(tmp, 0, b, offset, tmp.length);
                        offset += tmp.length;
                }
                
                return b;
        }
        
        /**
         * An object of SparseElement class contains all information for a real/complex
         * data entry in a sparse matrix. The object data is its location (columns and row index)
         * and the data value.
         */
        private class SparseElement implements Comparable<SparseElement>
        {
                private int    pos_row;
                private int    pos_col;
                private double data_real;
                private double data_imag;
                
                SparseElement(int i, int j, double data)
                {
                        this.data_real = data;
                        this.data_imag = 0;
                        this.pos_row   = i;
                        this.pos_col   = j;
                }
                
                SparseElement(int i, int j, double data_real, double data_imag)
                {
                        this.data_real = data_real;
                        this.data_imag = data_imag;
                        this.pos_row   = i;
                        this.pos_col   = j;
                }
                
                /**
                 * Indicates whether some other SparseElement is "equal to" this one.
                 * Equality is defined as having the exact same location in the sparse matrix
                 * @param other  another SparseElement
                 * @return true, if this object is the same as the other argument; false otherwise
                 */
                boolean equals(SparseElement other)
                {
                        if (other == null)
                                return false;
                        
                        return (this.pos_row == other.pos_row &&
                                        this.pos_col == other.pos_col);
                }
                
                /* (non-Javadoc)
                 * @see java.lang.Comparable#compareTo(java.lang.Comparable)
                 */
                /**
                 * Compares this object with the specified object for order within the sparse matrix.
                 * First, the column index is compared. If columns indices are identical,
                 * row indices are compared in a second step.
                 * @param other  another SparseElement
                 * @return  <li>negative int, if the other object has higher columns/row index</li>
                 *          <li>zero, if the other object has the same columns and row index</li>
                 *          <li>positive int, if the other object has lower columns/row index</li>
                 */
                public int compareTo(SparseElement other)
                {
                        if ( this.pos_col == other.pos_col )
                                return this.pos_row - other.pos_row;
                        
                        return this.pos_col - other.pos_col;
                }
        }
        
        /**
         * FlexMatrix implements a flexible/resizable list for sparse matrix
         * element data (in form of SparseElement objects).
         * You can add or overwrite existing elements.
         */
        private class FlexMatrix
        {
                private ArrayList<SparseElement> list;
                
                /*
                 * Current matrix dimensions; dimensions can change, if additional
                 * matrix element of even higher dimensions are added.
                 */
                private int dim1;
                private int dim2;
                
                FlexMatrix()
                {
                        list = new ArrayList<SparseElement>();
                        dim1 = 0;
                        dim2 = 0;
                }
                
                void add(int i, int j, double data)
                {
                        if ( data != 0.0 )
                        {
                                SparseElement e = new SparseElement(i,j,data);
                                
                                int index = indexOf(e);
                                
                                if ( index == -1 )
                                        list.add(e);
                                else
                                        list.set(index, e);
                        }
                        
                        dim1 = Math.max(dim1, i+1);
                        dim2 = Math.max(dim2, j+1);
                }
                
                void add(int i, int j, double data_real, double data_imag)
                {
                        if ( data_real != 0.0 || data_imag != 0.0 )
                        {
                                SparseElement e = new SparseElement(i,j,data_real,data_imag);
                                
                                int index = indexOf(e);
                                
                                if ( index == -1 )
                                        list.add(e);
                                else
                                        list.set(index, e);
                        }
                        
                        dim1 = Math.max(dim1, i+1);
                        dim2 = Math.max(dim2, j+1);
                }
                
                void add(int[] ir, int[] jc, double[] data)
                {
                        // TODO: Check that all arrays have the same length
                        for ( int i = 0; i < data.length; i++ )
                                add(ir[i],jc[i],data[i]);
                }
                
                void add(int[] ir, int[] jc, double[] data_real, double[] data_imag)
                {
                        // TODO: Check that all arrays have the same length
                        for ( int i = 0; i < data_real.length; i++ )
                                add(ir[i],jc[i],data_real[i],data_imag[i]);
                }
                
                /**
                 * Returns the number of non-zero elements in this FlexMatrix:
                 * 
                 * @return the number of non-zero elements in this FlexMatrix
                 */
                int getNumElements()
                {
                        return list.size();
                }
                
                /**
                 * Returns the index of the first occurrence of the specified element
                 * in this list, or -1 if this list does not contain the element.
                 * More formally, returns the lowest index <tt>i</tt> such that
                 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
                 * or -1 if there is no such index.
                 * @param  e   the SparseElement to be found
                 * @return the index of the occurrence of the specified element, or -1 if the element is not in the list.
                 */
                private int indexOf(SparseElement e)
                {
                        if (e == null) {
                                for (int i = 0; i < list.size(); i++)
                                        if (list.get(i) == null)
                                                return i;
                        } else {
                                for (int i = 0; i < list.size(); i++)
                                        if (e.equals(list.get(i)))
                                                return i;
                        }
                        
                        return -1;
                }
                
                /**
                 * Returns the minimum required dimensions of the sparse matrix, based on current element row/column indices
                 * @return integer array of size 2, containing the minimum required row and column
                 *         dimension (in this order) of the sparse matrix
                 */
                int[] getDimensions()
                {
                        return new int[]{dim1,dim2};
                }
                
                SparseElement[] toArray()
                {
                        // Need array type in the next line to specify the return type of
                        // the list.toArray() call; see specification of ArrayList.toArray(...)
                        SparseElement[] array = new SparseElement[0];
                        
                        array = list.toArray(array);
                        
                        bubblesort(array);
                        
                        return array;
                }
                
                /**
                 * Sorts the provided array of SparseElement objects in ascending order of .
                 * @param a  array of SparseElement objects to be sorted in-place
                 */
                private void bubblesort (SparseElement[] a)
                {
                        int n = a.length;
                        boolean swapped;
                        do {
                                swapped = false;
                                n--;
                                for ( int i = 0; i < n; i++ )
                                {
                                        SparseElement e1 = a[i];
                                        SparseElement e2 = a[i+1];
                                        if ( e1.compareTo(e2) > 0 )
                                        {
                                                // i.e. e2 would appear before e1 if a matrix is in column oriented representation
                                                a[i]   = e2;
                                                a[i+1] = e1;
                                                swapped = true;
                                        }
                                }
                        }
                        while (swapped == true);
                }
        }
}
