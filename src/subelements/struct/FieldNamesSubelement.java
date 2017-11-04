package subelements.struct;

import io.MyDataInputStream;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;

import abstractTypes.AbstractDataElement;

import common.DataTagFieldReader;
import common.DataType;
import exception.DataTypeException;
import exception.UniqueFieldNameExpection;

/**
 * This sub-element specifies the name of each field in the structure
 * as a series of 8-bit (miINT8) character arrays.
 * The value of the FieldNameLengthSubelement determines the length
 * of each field name array (max 32 bytes). Field names must be NULL-terminated,
 * which allows names having at most 31 characters. 
 * 
 * |----------------------------------------------
 * |      Data Type      |    Number of Bytes    |  Tag (2 x 32 bit)
 * |----------------------------------------------
 * |                                             |
 * |                Field Names                  | Data Part
 * |---------------------------------------------|
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */
public class FieldNamesSubelement extends AbstractDataElement
{
        // ArrayList is a sequential list. So, insertion and retrieval order is the same.
        private ArrayList<String> dataObj;
        private int               maxNameLength;
        
        public FieldNamesSubelement()
        {
                super(DataType.miINT8);
                dataObj = new ArrayList<String>();
                maxNameLength = 0;
        }
        
        /**
         * Adds a new name to the list of field names in a Matlab Struct Array
         * @param name  a String with the name to add
         * @return      the length of the longest name
         */
        public int addData(String name)
        {
                char[] cs = name.toCharArray();
                
                int fs = containsSpaceCharacter(cs);
                if ( fs != -1 )
                {
                        System.err.println("FieldNamesSubelement::addData");
                        System.err.println("\t Field name contains space characters.");
                        System.err.println("\t Name will be stripped after first occurence.");
                        name = new String(cs,0,fs);
                }
                
                // Structure field names must be unique
                if ( exist(name) == true )
                {
                        try
                        {
                                throw new UniqueFieldNameExpection("FieldnamesSubelement::addData(String): " +
                                                "Duplicate Structure field names: " + name);
                        } catch (Exception e)
                        {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }
                
                dataObj.add(name);
                maxNameLength = Math.max(maxNameLength, name.length()+1);
                setNumOfDataElementBytes(dataObj.size()*maxNameLength);
                
                return maxNameLength;
        }
        
        private void addData(byte[] name, int offset)
        {
                int length = 0;
                while (name[offset + length] != 0) // Find '0'-termination
                        length++;
                addData(new String(name,offset,length));
        }
        
        private int containsSpaceCharacter(char[] cs)
        {
                final char s = new String(" ").toCharArray()[0];
                
                for ( int i = 0; i < cs.length; i++ )
                {
                        if ( cs[i] == s )
                                return i;
                }
                
                return -1;
        }
        
        private boolean exist(String name)
        {
                int index = dataObj.indexOf(name);
                return (index != -1);
        }
        
        public String toString()
        {
                String str = super.toString();
                str += " names = " + dataObj.toString();
                return str;
        }
        
        public String[] listNames()
        {
                String[] array = new String[dataObj.size()];
                int offset = 0;
                
                for ( String data : dataObj )
                {
                        array[offset++] = data;
                }
                
                return array;
        }
        
        private int getStringPadding(int size)
        {
                if (size == 0) // Should not be possible
                        return maxNameLength;
                
                int width = maxNameLength;
                
                // Alternative: padding = ((s+(w-1))/w)*w - s
                //                      = w-1-rem(s+(w-1),w)
                //                      = w-1-(s+(w-1))%w
                int b = size % width;
                int padding = (width-b) % width;
                
                return padding;
        }
        
        protected byte[] dataToByteArray(ByteOrder byte_order)
        {
                byte[] b      = new byte[getDataLength()];
                int    offset = 0;
                
                for ( String data : dataObj )
                {
                        int len = data.length();
                        int padding = getStringPadding(len+1); // Account for '0'-termination
                        
                        for ( int i = 0; i < len; i++ )
                        {
                                b[offset++] = (byte)data.charAt(i);
                        }
                        
                        b[offset++] = (byte)0; // '0'-termination
                        
                        System.arraycopy(new byte[padding], 0, b, offset, padding);
                        offset += padding;
                }
                
                return b;
        }
        
        public FieldNamesSubelement read(MyDataInputStream in, int fieldLen) throws DataTypeException
        {
                DataTagFieldReader tagInfo = new DataTagFieldReader(in.getByteOrder());
                tagInfo.read(in);
                
                if ( tagInfo.getDataType() != DataType.miINT8 )
                        throw new DataTypeException(DataType.miINT8, tagInfo.getDataType());
                
                int nNames = tagInfo.getDataSize() / fieldLen;
                
                if ( tagInfo.isShortElement() )
                {
                        int offset = 0;
                        for ( int i = 0; i < nNames; i++ )
                        {
                                addData(tagInfo.getTagData(),offset);
                                offset += fieldLen;
                        }
                }
                else
                {
                        byte[] buf = new byte[fieldLen];
                        
                        for ( int i = 0; i < nNames; i++ )
                        {
                                try {
                                        in.read(buf);
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                }
                                addData(buf,0);
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
