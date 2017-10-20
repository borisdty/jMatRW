package array;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;

import abstractTypes.AbstractArrayElement;

import common.MxClassID;

import subelements.struct.FieldNameLengthSubelement;
import subelements.struct.FieldNamesSubelement;

/**
 * A MAT-file data element representing a MATLAB structure is composed of six
 * sub-elements.
 * 
 * |----------------------------------------------------|
 * | Data Type (=miMATRIX) |     Number of Bytes        |  Tag (2 x 32 bit)
 * |----------------------------------------------------|
 * |      Array Flags sub-element (2 x 4 bytes)         |      ||
 * |----------------------------------------------------|      ||
 * |   Dimensions Array sub-element (variable size)     |      ||
 * |----------------------------------------------------|      ||
 * |      Array Name sub-element (variable size)        |      ||
 * |----------------------------------------------------|      ||
 * |   Field Name Length sub-element (variable size)    |      || Data Part
 * |----------------------------------------------------|      ||
 * |      Field Names sub-element (variable size)       |      ||
 * |----------------------------------------------------|      ||
 * |                                                    |      ||
 * |                     Fields                         |      ||
 * | Each field is written in place as miMATRIX element |      ||
 * |----------------------------------------------------|      ||
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class MxStructArray extends AbstractArrayElement
{
        private FieldNameLengthSubelement  fieldNameLengthSubelement;
        private FieldNamesSubelement       fieldNamesSubelement;
        private FlexMatrix                 fields;
        
        public MxStructArray(String name)
        {
                this(name,false);
        }
        
        public MxStructArray(String name, boolean compFlag)
        {
                super(compFlag);
                super.setClassType(MxClassID.mxSTRUCT_CLASS);
                super.setName(name);
                fieldNameLengthSubelement = new FieldNameLengthSubelement();
                fieldNamesSubelement      = new FieldNamesSubelement();
                fields                    = new FlexMatrix();
        }
        
        /**
         * A field name in struct array is limited to 31 characters 
         * @param name_vec
         * @param data_vec
         */
        public void setData(String[] name_vec, AbstractArrayElement[] data_vec)
        {
                int dim1 = data_vec.length;
                
                for ( int i = 0; i < dim1; i++ )
                {
                        String                name = name_vec[i];
                        AbstractArrayElement  data = data_vec[i];
                        
                        if ( name.length() > 31 )
                        {
                                String str = "MxStructArray::setData: fieldname \"" +
                                                name + "\" exceeds maximum length of 31 characters"; 
                                System.err.println(str);
                                System.err.println("The field will be ignored.");
                                continue;
                        }
                        
                        if ( data == null )
                        {
                                // Question is whether this is conform to MATLAB, since
                                // in MATLAB any sub-field in a struct array is defined
                                MxEmptyArray a = new MxEmptyArray();
                                fields.add(a, name);
                        }
                        else
                        {
                                if ( matlabNameConvention )
                                        // Not necessary, but MATLAB behavior
                                        data.setName("");
                                fields.add(data, name);
                        }
                        
                        int len = fieldNamesSubelement.addData(name);
                        
                        // Update maximum field names length, as it could have changed
                        fieldNameLengthSubelement.setData(len);
                }
                
                // By observation: MATLAB structures always have dimension 1x1
                super.setDimensions(new int[]{1,1});
                
                int numBytes = 0;
                numBytes += fieldNameLengthSubelement.getTotalElementLength();
                numBytes += fieldNamesSubelement.getTotalElementLength();
                for ( Entry e : fields )
                {
                        AbstractArrayElement tmp = e.getArray();
                        numBytes += tmp.getTotalElementLength();
                }
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        /**
         * A field name in struct array is limited to 31 characters 
         * @param name_vec
         * @param data_vec
         */
        public void addData(String name, AbstractArrayElement data)
        {
                if ( name.length() > 31 )
                {
                        String str = "MxStructArray::setData: fieldname \"" +
                                        name + "\" exceeds maximum length of 31 characters"; 
                        System.err.println(str);
                        System.err.println("The field will be ignored.");
                        return;
                }
                
                if ( data == null )
                {
                        // Question is whether this is conform to MATLAB, since
                        // in MATLAB any sub-field in a struct array is defined
                        MxEmptyArray a = new MxEmptyArray();
                        fields.add(a, name);
                }
                else
                {
                        if ( matlabNameConvention )
                                // Not necessary, but MATLAB behavior
                                data.setName("");
                        fields.add(data, name);
                }
                
                int len = fieldNamesSubelement.addData(name);
                
                // Update maximum field names length, as it could have changed
                fieldNameLengthSubelement.setData(len);
                
                // By observation: MATLAB structures always have dimension 1x1
                super.setDimensions(new int[]{1,1});
                
                int numBytes = 0;
                numBytes += fieldNameLengthSubelement.getTotalElementLength();
                numBytes += fieldNamesSubelement.getTotalElementLength();
                for ( Entry e : fields )
                {
                        AbstractArrayElement tmp = e.getArray();
                        numBytes += tmp.getTotalElementLength();
                }
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        public byte[] specDataToByteArray(ByteOrder byte_order)
        {
                byte[] b      = new byte[super.getNumOfSpecDataBytes()];
                int    offset = 0;
                
                byte[] tmp;
                
                tmp = fieldNameLengthSubelement.toByteArray(byte_order);
                System.arraycopy(tmp, 0, b, offset, tmp.length);
                offset += tmp.length;
                
                tmp = fieldNamesSubelement.toByteArray(byte_order);
                System.arraycopy(tmp, 0, b, offset, tmp.length);
                offset += tmp.length;
                
                // Returned list should have the same order as created in
                // above call of fieldNamesSubelement.toByteArray()
                String[] names = fieldNamesSubelement.listNames();
                
                for ( String name : names )
                {
                        AbstractArrayElement obj = fields.get(name);
                        
                        tmp = ((AbstractArrayElement)obj).toByteArray(byte_order);
                        
                        System.arraycopy(tmp, 0, b, offset, tmp.length);
                        offset += tmp.length;
                }
                
                return b;
        }
        
        private class Entry
        {
                private String               name;
                private AbstractArrayElement data;
                
                Entry(AbstractArrayElement data, String name)
                {
                        this.data = data;
                        this.name = name;
                }
                
                AbstractArrayElement getArray()
                {
                        return data;
                }
                
                boolean equals(Entry other)
                {
                        if (other == null)
                                return false;
                        
                        return (this.name.equals(other.name));
                }
        }
        
        private class FlexMatrix implements Iterable<Entry>
        {
                ArrayList<Entry> list;
                
                FlexMatrix()
                {
                        list = new ArrayList<Entry>();
                }
                
                public Iterator<Entry> iterator()
                {
                        return list.iterator();
                }
                
                void add(AbstractArrayElement obj, String name)
                {
                        Entry e = new Entry(obj,name);
                        
                        int index = find(e);
                        
                        if ( index == -1 )
                                list.add(e);
                        else
                                list.set(index, e);
                }
                
                AbstractArrayElement get(String name)
                {
                        int index = find(name);
                        return list.get(index).getArray();
                }
                
                private int find(String name)
                {
                        Entry e = new Entry(null,name);
                        return find(e);
                }
                
                private int find(Entry e)
                {
                        return indexOf(e);
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
                        if (e == null)
                        {
                                for (int i = 0; i < list.size(); i++)
                                        if (list.get(i) == null)
                                                return i;
                        }
                        else
                        {
                                for (int i = 0; i < list.size(); i++)
                                        if (e.equals(list.get(i)))
                                                return i;
                        }
                        
                        return -1;
                }
        }
}
