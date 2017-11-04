package array;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;

import abstractTypes.AbstractArrayElement;

import common.MxClassID;

/**
 * A MAT-file data element representing a MATLAB cell array is composed of
 * four sub-elements. The array-common sub-elements are Array Flags,
 * Dimensions Array and Array Name (i.e. cell array name). In addition,
 * the Cell Array has one or several miMATRIX elements representing the cells.
 * 
 * |---------------------------------------------------|
 * | Data Type (=miMATRIX) |     Number of Bytes       |  Tag (2 x 32 bit)
 * |---------------------------------------------------|
 * |      Array Flags sub-element (2 x 4 bytes)        |      ||
 * |---------------------------------------------------|      ||
 * |   Dimensions Array sub-element (variable size)    |      ||
 * |---------------------------------------------------|      ||
 * |      Array Name sub-element (variable size)       |      || Data Part
 * |---------------------------------------------------|      ||
 * |                                                   |      ||
 * |                     Cells                         |      ||
 * | Each cell is written in place as miMATRIX element |      ||
 * |---------------------------------------------------|      ||
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class MxCellArray extends AbstractArrayElement
{
        private FlexMatrix  cells;
        
        public MxCellArray(String name)
        {
                this(name,false);
        }
        
        public MxCellArray(String name, boolean compFlag)
        {
                super(compFlag);
                super.setClassType(MxClassID.mxCELL_CLASS);
                super.setName(name);
                cells = new FlexMatrix();
        }
        
        public void setData(AbstractArrayElement[] data)
        {
                // This will result in an (1xn) cell array
                AbstractArrayElement[][] a = {data};
                setData(a);
        }
        
        /**
         * If data[i][j] == null, an MxEmptyArray is used
         * @param data
         */
        public void setData(AbstractArrayElement[][] data)
        {
                int dim1 = data.length;
                int dim2 = data[0].length;
                
                for ( int i = 0; i < dim1; i++ )
                {
                        for ( int j = 0; j < dim2; j++ )
                        {
                                if ( data[i][j] == null )
                                {
                                        MxEmptyArray a = new MxEmptyArray();
                                        cells.add(a, i, j);
                                }
                                else
                                {
                                        if ( matlabNameConvention )
                                                // Not necessary, but MATLAB behavior
                                                data[i][j].setName("");
                                        cells.add(data[i][j], i, j);
                                }
                        }
                }
                
                super.setDimensions(cells.getDimensions());
                
                int numBytes = 0;
                for ( Entry e : cells )
                {
                        AbstractArrayElement obj = e.getArray();
                        numBytes += obj.getTotalElementLength();
                }
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        /**
         * If data == null, an MxEmptyArray is used
         * @param data
         */
        public void addData(AbstractArrayElement data, int n, int m)
        {
                if ( data == null )
                {
                        MxEmptyArray a = new MxEmptyArray();
                        cells.add(a, n, m);
                }
                else
                {
                        if ( matlabNameConvention )
                                // Not necessary, but MATLAB behavior
                                data.setName("");
                        cells.add(data, n, m);
                }
                
                super.setDimensions(cells.getDimensions());
                
                int numBytes = 0;
                for ( Entry e : cells )
                {
                        AbstractArrayElement obj = e.getArray();
                        numBytes += obj.getTotalElementLength();
                }
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        protected byte[] specDataToByteArray(ByteOrder byte_order)
        {
                byte[] b      = new byte[super.getNumOfSpecDataBytes()];
                int    offset = 0;
                
                byte[] tmp;
                
                AbstractArrayElement[][] dataArray = cells.toArray();
                
                for ( int j = 0; j < dataArray[0].length; j++ )
                {
                        for ( int i = 0; i < dataArray.length; i++ )
                        {
                                AbstractArrayElement obj = dataArray[i][j];
                                
                                tmp = obj.toByteArray(byte_order);
                                
                                System.arraycopy(tmp, 0, b, offset, tmp.length);
                                offset += tmp.length;
                        }
                }
                
                return b;
        }
        
        private class Entry
        {
                private int pos_row;
                private int pos_col;
                private AbstractArrayElement data;
                
                Entry(AbstractArrayElement data, int i, int j)
                {
                        this.data = data;
                        this.pos_row = i;
                        this.pos_col = j;
                }
                
                AbstractArrayElement getArray()
                {
                        return data;
                }
                
                /**
                 * True, iff the coordinates are identical.
                 * Data content is not considered.
                 * @param other The other Entry-element
                 * @return true or false
                 */
                boolean equals(Entry other)
                {
                        if (other == null)
                                return false;
                        
                        return (this.pos_row == other.pos_row &&
                                        this.pos_col == other.pos_col);
                }
        }
        
        private class FlexMatrix implements Iterable<Entry>
        {
                private ArrayList<Entry> list;
                private int dim1;
                private int dim2;
                
                FlexMatrix()
                {
                        list = new ArrayList<Entry>();
                        dim1 = 0;
                        dim2 = 0;
                }
                
                public Iterator<Entry> iterator()
                {
                        return list.iterator();
                }
                
                void add(AbstractArrayElement obj, int i, int j)
                {
                        Entry e = new Entry(obj,i,j);
                        
                        int index = find(e);
                        
                        if ( index == -1 )
                                list.add(e);
                        else
                                list.set(index, e); // Overwrite old Entry
                        
                        dim1 = Math.max(dim1, i+1);
                        dim2 = Math.max(dim2, j+1);
                        
                        // EVEN IT IS SLOW, BUT
                        // It is important to immediately fill up the cell
                        // array with empty matrices as place holders, since
                        // otherwise reporting of the size (call to
                        // super.setNumOfSpecDataBytes(numBytes) in method
                        // addData(...) is incorrect with respect to what is
                        // returned by the call super.getNumOfSpecDataBytes()
                        // in method specDataToByteArray(...)
                        fill();
                }
                
                private int find(Entry e)
                {
                        return indexOf(e);
                }
                
                private int find(int i, int j)
                {
                        Entry e = new Entry(null,i,j);
                        return find(e);
                }
                
                /**
                 * Returns the index of the first occurrence of the specified element
                 * in this list, or -1 if this list does not contain the element.
                 * More formally, returns the lowest index <tt>i</tt> such that
                 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
                 * or -1 if there is no such index.
                 */
                private int indexOf(Entry e)
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
                
                private void fill()
                {
                        MxEmptyArray a = new MxEmptyArray();
                        
                        for ( int i = 0; i < dim1; i++ )
                        {
                                for ( int j = 0; j < dim2; j++ )
                                {
                                        int index = find(i,j);
                                        if ( index == -1 )
                                        {
                                                Entry e = new Entry(a,i,j);
                                                list.add(e);
                                        }
                                }
                        }
                }
                
                int[] getDimensions()
                {
                        return new int[]{dim1,dim2};
                }
                
                AbstractArrayElement[][] toArray()
                {
                        AbstractArrayElement[][] array = new AbstractArrayElement[dim1][dim2];
                        
                        for ( Entry e : list )
                        {
                                int i = e.pos_row;
                                int j = e.pos_col;
                                array[i][j] = e.data;
                        }
                        
                        return array;
                }
        }
}
