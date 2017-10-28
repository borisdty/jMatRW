package subelements.numeric;

import io.MyDataInputStream;

import java.io.IOException;
import java.nio.ByteOrder;

import abstractTypes.AbstractDataElement;
import common.DataTagFieldReader;
import common.DataType;
import common.Bytes;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class CharacterPartSubelement extends AbstractDataElement
{
        private String dataObj;
        
        public CharacterPartSubelement()
        {
                super(DataType.miUINT16);
        }
        
        public CharacterPartSubelement(String s)
        {
                this();
                setData(s);
        }
        
        public CharacterPartSubelement(char[] c)
        {
                this(new String(c));
        }
        
        public String getString()
        {
                return dataObj;
        }
        
        public void setData(String s)
        {
                dataObj = s;
                super.setNumOfDataElementBytes(dataObj.length()*super.sizeOfDataType());
        }
        
        public void setData(char[] c)
        {
                setData(new String(c));
        }
        
        public byte[] dataToByteArray(ByteOrder byte_order)
        {
                byte[] b      = new byte[super.getDataLength()];
                String data   = dataObj;
                int    len    = super.getDataLength() / super.sizeOfDataType();
                int    offset = 0;
                
                for ( int i = 0; i < len; i++ )
                {
                        char value = data.charAt(i);
                        
                        if ( byte_order == ByteOrder.BIG_ENDIAN )
                        {
                                b[offset++] = (byte)(value >> 8);
                                b[offset++] = (byte)(value >> 0);
                        }
                        else
                        {
                                b[offset++] = (byte)(value >> 0);
                                b[offset++] = (byte)(value >> 8);
                        }
                }
                
                return b;
        }
        
        public CharacterPartSubelement read(MyDataInputStream in)
        {
                DataTagFieldReader tagInfo = new DataTagFieldReader(in.getByteOrder());
                
                tagInfo.read(in);
                
                if ( tagInfo.getDataType() != DataType.miUINT16 )
                        return null;
                
                int dataSize = tagInfo.getDataSize();
                int atomSize = tagInfo.getDataType().sizeOf();
                
                int nChars = dataSize / atomSize;
                
                char[] out = new char[nChars];
                
                if (tagInfo.isShortElement() == true)
                {
                        int offset = 4; // skip short tag itself
                        byte[] buf = tagInfo.getTagData();
                        
                        for ( int i = 0; i < nChars; i++ )
                        {
                                out[i] = Bytes.toChar(buf,offset,in.getByteOrder());
                                offset += atomSize;
                        }
                }
                else
                {
                        byte[] buf = new byte[atomSize];
                        
                        for ( int i = 0; i < nChars; i++ )
                        {
                                try {
                                        in.read(buf); // What about return value?
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                }
                                out[i] = Bytes.toChar(buf,0,in.getByteOrder());
                        }
                        
                        try {
                                in.skip(tagInfo.getPadding());
                        }
                        catch (IOException e) {
                                e.printStackTrace();
                                return null;
                        }
                }
                
                setData(out);
                
                return this;
        }
}
