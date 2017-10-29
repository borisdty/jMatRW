package common;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Date;

/*
 * .mat-file Header
 * 
 * Level 5 MAT-files are made up of a 128-byte header followed by one or more data
 * elements.
 * Level 5 MAT-files begin with a 128-byte header made up of a 124-byte text field and
 * two 16-bit flag fields.
 * The first 116 bytes of the header can contain text data in human-readable form.
 * Bytes 117 through 124 of the header contain an offset to subsystem-specific data in the
 * MAT-file.
 * The last 4 bytes in the header are divided into two 16-bit flag fields (int16) for
 * indication of the version and endianess.
 * 
 * |---------------------------------------------|
 * |                                             | Every line line represents 8 bytes. 
 * |         Descriptive Text (116 bytes)        |
 * |                                             |
 * |                     ------------------------|
 * |                     |  subsys data offset   |
 * |---------------------------------------------|
 * | subsys data offset  | Version   | Endian    |
 * |---------------------------------------------|
 */
/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxFileHeader
{
        private static String DEFAULT_DESCRIPTIVE_TEXT = "MATLAB 5.0 MAT-file, Platform: "
                + System.getProperty("os.name") + " (Java)"
                + ", Created on: ";
        private static int    DEFAULT_VERSION          = 0x0100;
        private static byte[] DEFAULT_ENDIAN_INDICATOR = new byte[] {(byte)'M', (byte)'I'};
        
        private String descriptiveText;
        private int    version;
        private byte[] endianIndicator;
        
        /**
         * New MAT-file header
         * 
         * @param descriptiveText  descriptive text (max 116 characters)
         * @param version          Quote MathWorks: when creating a MAT-file, set this field to 0x0100
         * @param endianIndicator  byte array, set this to {'M','I'}
         */
        private MxFileHeader(String descriptiveText, int version, byte[] endianIndicator)
        {
                this.descriptiveText = descriptiveText;
                this.version         = version;
                this.endianIndicator = endianIndicator;
        }
        
        /**
         * Get the descriptive text
         * 
         * @return  String containing the descriptive text
         */
        public String getDescriptiveText()
        {
                return descriptiveText;
        }
        
        /**
         * Get the MAT-file version number
         * 
         * @return  integer value of the version number
         */
        public int getVersion()
        {
                return version;
        }
        
        /**
         * Get the Endian Indicator.
         * {'M','I'} indicates no byte-swapping needed for correct data interpretation
         * {'I','M'} indicates byte-swapping is needed for correct data interpretation
         * 
         * @return  byte array {'M','I'} or {'I','M'}
         */
        public byte[] getEndianIndicator()
        {
                return endianIndicator;
        }
        
        /**
         * Creates new MxFileHeader object with default header values:
         *  
         * @return - new MxFileHeader object
         */
        public static MxFileHeader fileHeaderFactory()
        {
                return new MxFileHeader( DEFAULT_DESCRIPTIVE_TEXT + (new Date()).toString(), 
                                DEFAULT_VERSION, 
                                DEFAULT_ENDIAN_INDICATOR);
        }
        
        /**
         * Returns a string representation of the object.
         * The string contains the descriptive text in the header, the version number
         * and the endian indicator.
         * 
         * @return  a string
         */
        @Override
        public String toString()
        {
                StringBuffer sb = new StringBuffer();
                
                sb.append("[descriptiveText: " + descriptiveText);
                sb.append(", version: " + version);
                sb.append(", endianIndicator: " + new String(endianIndicator) + "]");
                
                return sb.toString();
        }
        
        /**
         * Creates a byte array of the header information
         * 
         * @param byte_order   BIG_ENDIAN or LITTLE_ENDIAN to convert integers into byte sequences
         * 
         * @return  byte array representing the header information
         */
        public byte[] toByteArray(ByteOrder byte_order)
        {
                byte[] desc = descriptiveText.getBytes();
                
                byte[] dest = new byte[128];
                Arrays.fill(dest, (byte)32); // ASCII(32) = space
                
                int offset = 0;
                
                // write descriptive text
                int len = Math.min(116,desc.length); // if description is too long
                System.arraycopy(desc, 0, dest, offset, len);
                offset += 116;
                
                // For now: jump over "Subsys Data Offset" field (8 bytes);
                //          (unused so far??)
                // I found that for version 7 .mat files, these bytes are set 0x00.
                // For version 5 files, it is 0x20 (ASCII 32 = space)
                offset += 8;
                
                if ( byte_order == ByteOrder.BIG_ENDIAN )
                {
                        // write Version
                        dest[offset++] = (byte)(version >> 8);
                        dest[offset++] = (byte)(version >> 0);
                        
                        // write Endian Indicator
                        dest[offset++] = endianIndicator[0];
                        dest[offset++] = endianIndicator[1];
                }
                else
                {
                        // write Version
                        dest[offset++] = (byte)(version >> 0);
                        dest[offset++] = (byte)(version >> 8);
                        
                        // write Endian Indicator
                        dest[offset++] = endianIndicator[1];
                        dest[offset++] = endianIndicator[0];
                }
                
                return dest;
        }
        
        /**
         * Performs an analysis whether header information is compliant. If set
         * <ul>
         *  <li>bit 0: Length violation (!=128 bytes)</li>
         *  <li>bit 1: First four bytes of the header are zero</li>
         *  <li>bit 2: Byte order indicator is not valid</li>
         *  <li>bit 3: Version number is not valid (not 0x0100 in case of BIG_ENDIAN)</li>
         *  <li>bit 4: Version number is not valid (not 0x0100 in case of LITTLE_ENDIAN)</li>
         *  <li>bit 5: same as bit 2</li>
         * </ul>
         * 
         * @param header header information as byte array (128 bytes)
         * @return error code as integer
         */
        public static int checkHeader(byte[] header)
        {
                int errorCode = 0;
                
                if ( header.length != 128 )
                {
                        errorCode |= 0x01;
                }
                
                //if ( header[0] == 0 || header[1] == 0 ||
                //                header[2] == 0 || header[3] == 0 )
                if ( (header[0] | header[1] | header[2]| header[3]) == 0 )
                {
                        errorCode |= 0x02;
                }
                
                if ( !(header[126] == 'M' && header[127] == 'I') &&
                     !(header[126] == 'I' && header[127] == 'M') )
                {
                        errorCode |= 0x04; // Undetermined byte order
                }
                
                if ( header[126] == 'M' && header[127] == 'I' )
                {
                        // Supposed to be Big Endian format
                        if ( header[124] != 0x01 || header[125] != 0x00 )
                                errorCode |= 0x08; // Failed version check
                }
                else if ( header[126] == 'I' && header[127] == 'M' )
                {
                        // Supposed to be Little Endian format
                        if ( header[124] != 0x00 || header[125] != 0x01 )
                                errorCode |= 0x10; // Failed version check
                }
                else
                {
                        errorCode |= 0x20;
                }
                
                return errorCode;
        }
}
