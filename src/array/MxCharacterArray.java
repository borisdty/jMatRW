package array;

import java.nio.ByteOrder;

import abstractTypes.AbstractArrayElement;

import common.MxClassID;

import subelements.numeric.CharacterPartSubelement;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxCharacterArray extends AbstractArrayElement
{
        private CharacterPartSubelement  charPartSubelement;
        
        public MxCharacterArray(String name)
        {
                this(name,false);
        }
        
        public MxCharacterArray(String name, boolean compFlag)
        {
                super(compFlag);
                super.setName(name);
        }
        
        public void setData(String data)
        {
                int   dim1 = 1;
                int   dim2 = data.length();
                int[] dims = {dim1,dim2};
                
                super.setClassType(MxClassID.mxCHAR_CLASS);
                super.setDimensions(dims);
                
                charPartSubelement = new CharacterPartSubelement(data);
                
                int numBytes = charPartSubelement.getTotalElementLength();
                
                super.setNumOfSpecDataBytes(numBytes);
        }
        
        protected byte[] specDataToByteArray(ByteOrder byte_order)
        {
                byte[] b      = new byte[super.getNumOfSpecDataBytes()];
                int    offset = 0;
                
                byte[] tmp = charPartSubelement.toByteArray(byte_order);
                System.arraycopy(tmp, 0, b, offset, tmp.length);
                offset += tmp.length;
                
                return b;
        }
}
