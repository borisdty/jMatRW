package subelements;

import io.compression.StreamCompressor;

import java.io.IOException;
import java.nio.ByteOrder;

import abstractTypes.AbstractDataElement;

import common.DataType;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class CompressedDataElement extends AbstractDataElement
{
        private byte[] dataObj;
        
        public CompressedDataElement()
        {
                super(DataType.miCOMPRESSED);
        }
        
        public void setData(byte[] uncompressedData)
        {
                dataObj = uncompressedData;
        }
        
        protected byte[] dataToByteArray(ByteOrder byte_order)
        {
                StreamCompressor streamCompressor = new StreamCompressor();
                
                byte[] b = null;
                try {
                        streamCompressor.write(dataObj);
                        b = streamCompressor.toByteAray();
                        streamCompressor.close();
                }
                catch (IOException e) {
                        e.printStackTrace();
                        return null;
                }
                
                // Adjust data tag, since only right now we know the final
                // size of the data.
                super.setNumOfDataElementBytes(b.length); 
                
                return b;
        }
}
