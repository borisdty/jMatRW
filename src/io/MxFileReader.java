package io;

import io.compression.StreamDecompressor;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.util.Hashtable;
import java.util.Set;

import common.DataTagFieldReader;
import common.DataType;
import common.MxFileHeader;
import common.MxDataObject;
import common.MxCellDataObject;
import common.MxCharacterDataObject;
import common.MxEmptyDataObject;
import common.MxNumericDataObject;
import common.MxSparseDataObject;
import common.MxStructDataObject;
import exception.ClassIDException;
import exception.DataTypeException;
import subelements.common.ArrayFlagsSubelement;
import subelements.common.ArrayNameSubelement;
import subelements.common.DimensionsArraySubelement;
import subelements.numeric.CharacterPartSubelement;
import subelements.numeric.NumericPartSubelement;
import subelements.sparse.ColumnIndexSubelement;
import subelements.sparse.RowIndexSubelement;
import subelements.sparse.SparseRaw;
import subelements.struct.FieldNameLengthSubelement;
import subelements.struct.FieldNamesSubelement;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxFileReader implements Closeable
{
        private RandomAccessFile  rfile;
        
        private ByteOrder byte_order = ByteOrder.BIG_ENDIAN; // Java default
        
        private Hashtable<String,MatElementInfo>  indexTable;
        
        public MxFileReader(String filename)
        {
                File file = new File(filename);
                
                try {
                        rfile = new RandomAccessFile(file,"r");
                }
                catch (FileNotFoundException e) {
                        e.printStackTrace();
                }
                
                try {
                        byte[] header = getHeader();
                        
                        int err_code = MxFileHeader.checkHeader(header);
                        
                        if ( (err_code & 0x02) != 0 )
                        {
                                System.err.println("MxFileReader:: Not a MATLAB >= Version 5 file.");
                                return;
                        }
                        
                        if ( (err_code & 0x04) != 0 )
                        {
                                System.err.println("MxFileReader:: Undetermined byte order.");
                                return;
                        }
                        
                        if ( (err_code & (0x08+0x10)) != 0 )
                        {
                                System.err.println("MxFileReader:: Check version ID failed.");
                                return;
                        }
                        
                        if ( err_code != 0 )
                        {
                                System.err.println("MxFileReader:: Unknown error in file header.");
                                return;
                        }
                        
                        if ( header[126] == 'M' && header[127] == 'I' )
                        {
                                byte_order = ByteOrder.BIG_ENDIAN;
                        }
                        else
                        {
                                byte_order = ByteOrder.LITTLE_ENDIAN;
                        }
                        
                        indexTable = createIndexTable();
                        
                        if ( indexTable == null )
                        {
                                System.err.println("MxFileReader::MxFileReader(): Cannot create index.");
                                return;
                        }
                }
                catch (IOException e) {
                        e.printStackTrace();
                }
        }
        
        public void close() throws IOException
        {
                rfile.close();
        }
        
        public byte[] readEntireFile() throws IOException
        {
                int len = (int)rfile.length();
                
                byte[] b = new byte[len];
                
                long cur_position = rfile.getFilePointer();
                rfile.seek(0L);
                rfile.read(b);
                rfile.seek(cur_position);
                
                return b;
        }
        
        public String[] getVarNames()
        {
                Set<String> e = indexTable.keySet();
                int len = e.size();
                String[] a = new String[len];
                e.toArray(a);
                return a;
        }
        
        public MxDataObject getVariable(String varName)
        {
                MatElementInfo i = indexTable.get(varName);
                
                if ( i == null )
                {
                        System.err.println("MxFileReader::getVariable: Cannot find variable.");
                        return null;
                }
                
                int pos = i.position;
                try {
                        rfile.seek(pos);
                }
                catch (IOException e) {
                        e.printStackTrace();
                        return null;
                }
                
                InputStream is = new RandomAccessFileInputStream(rfile);
                
                MxDataObject obj = evalArray(is);
                
                return obj;
        }
        
        private byte[] getHeader() throws IOException
        {
                byte[] header = new byte[128];
                long cur_position = rfile.getFilePointer();
                rfile.seek(0L);
                rfile.read(header);
                rfile.seek(cur_position);
                return header;
        }
        
        private Hashtable<String, MatElementInfo> createIndexTable() throws IOException
        {
                Hashtable<String,MatElementInfo> table = new Hashtable<String,MatElementInfo>();
                
                rfile.seek(128); // position file pointer behind header
                
                while (true)
                {
                        long curPos = rfile.getFilePointer();
                        
                        byte[] tag = new byte[8];
                        rfile.read(tag);
                        
                        DataTagFieldReader tagInfo = new DataTagFieldReader(byte_order);
                        
                        if(tagInfo.read(tag) == false)
                                return null;
                        
                        if(isArrayElement(tagInfo) == false)
                                return null;
                        
                        int elementSize = tagInfo.getElementSize();
                        
                        InputStream is = new RandomAccessFileInputStream(rfile,tagInfo.getElementSize());
                        
                        if (isCompressedElement(tagInfo))
                        {
                                is = new StreamDecompressor(is);
                                
                                // re-read compressed matrix tag
                                is.read(tag);
                                
                                if(tagInfo.read(tag) == false)
                                {
                                        is.close();
                                        return null;
                                }
                                
                                if(isMatrixElement(tagInfo) == false)
                                {
                                        is.close();
                                        return null;
                                }
                        }
                        
                        MyDataInputStream mi = new MyDataInputStream(is,byte_order);
                        
                        ArrayFlagsSubelement      flagField;
                        DimensionsArraySubelement dimField;
                        ArrayNameSubelement       nameField;
                        
                        try {
                                flagField = new ArrayFlagsSubelement().read(mi);
                                dimField  = new DimensionsArraySubelement().read(mi);
                                nameField = new ArrayNameSubelement().read(mi);
                        }
                        catch (DataTypeException e) {
                                e.printStackTrace();
                                return null;
                        }
                        
                        MatElementInfo obj = new MatElementInfo();
                        
                        obj.complex     = flagField.getComplexFlag();
                        obj.global      = flagField.getGlobalFlag();
                        obj.logical     = flagField.getLogicalFlag();
                        obj.classType   = flagField.getClassType();
                        obj.dimensions  = dimField.getDimensions();
                        obj.name        = nameField.getName();
                        obj.position    = (int)curPos;
                        // obj could also include tag information
                        
                        table.put(nameField.getName(), obj);
                        
                        if ( curPos + elementSize < rfile.length() )
                                rfile.seek( curPos + elementSize );
                        else
                                break;
                }
                
                return table;
        }
        
        private MxDataObject evalArray(InputStream in)
        {
                /*
                 * Array consists of
                 * 1) Data element tag
                 * 2) Flag      sub-element
                 * 3) Dimension sub-element
                 * 4) Name      sub-element
                 * 5) Further array type specific data
                 */
                
                //
                // Evaluate array tag field
                //
                DataTagFieldReader tagInfo = new DataTagFieldReader(byte_order);
                
                if ( tagInfo.read(in) == false )
                        return null;
                
                if ( isArrayElement(tagInfo) == false )
                        return null;
                
                if ( tagInfo.getDataSize() == 0 )
                        return new MxEmptyDataObject();
                
                if (isCompressedElement(tagInfo))
                {
                        in = new StreamDecompressor(in);
                        
                        // re-read compressed matrix tag
                        tagInfo.read(in);
                        
                        if(isMatrixElement(tagInfo) == false)
                                return null;
                }
                
                MyDataInputStream mi = new MyDataInputStream(in,byte_order);
                
                ArrayFlagsSubelement      flagField;
                DimensionsArraySubelement dimField;
                ArrayNameSubelement       nameField;
                
                try {
                        flagField = new ArrayFlagsSubelement().read(mi);
                        dimField  = new DimensionsArraySubelement().read(mi);
                        nameField = new ArrayNameSubelement().read(mi);
                }
                catch (DataTypeException e) {
                        e.printStackTrace();
                        return null;
                }
                
                MxDataObject dataObject = new MxDataObject();
                
                dataObject.complex     = flagField.getComplexFlag();
                dataObject.global      = flagField.getGlobalFlag();
                dataObject.logical     = flagField.getLogicalFlag();
                dataObject.classType   = flagField.getClassType();
                dataObject.dimensions  = dimField.getDimensions();
                dataObject.name        = nameField.getName();
                
                switch (dataObject.classType)
                {
                        case mxDOUBLE_CLASS:
                        {
                                MxNumericDataObject numDataObject = new MxNumericDataObject(dataObject);
                                
                                NumericPartSubelement real_part = new NumericPartSubelement().read(mi);
                                
                                numDataObject.real_data_type = real_part.getDataType();
                                numDataObject.real_part      = real_part.getData();
                                
                                if ( numDataObject.complex == true )
                                {
                                        NumericPartSubelement imag_part = new NumericPartSubelement().read(mi);
                                        
                                        numDataObject.imag_data_type = imag_part.getDataType();
                                        numDataObject.imag_part      = imag_part.getData();
                                        
                                        if ( numDataObject.real_part.length() != numDataObject.imag_part.length() )
                                                return null; // TODO: Better to throw an Exception ???
                                }
                                
                                // Generating MATLAB equivalent (i.e. as finally
                                // visible in MATLAB workspace)
                                
                                double[][][] data = new double[2][][];
                                data[1] = null;
                                
                                // Transform all data to double type
                                try {
                                        data[0] = numDataObject.getRealDoubleMtx();
                                }
                                catch (ClassIDException e) {
                                        e.printStackTrace();
                                        return null;
                                }
                                
                                if ( numDataObject.complex == true )
                                {
                                        try {
                                                data[1] = numDataObject.getImagDoubleMtx();
                                        }
                                        catch (ClassIDException e) {
                                                e.printStackTrace();
                                                return null;
                                        }
                                }
                                
//                              int dim1 = numDataObject.dimensions[0];
//                              int dim2 = numDataObject.dimensions[1];
//
//                                try {
//                                        double[] d = numDataObject.real_part.toDoubleArray(numDataObject.real_data_type.isSignedType()).getData();
//                                        data[0] = reshape(d,dim1,dim2);
//                                }
//                                catch (ClassIDException e) {
//                                        e.printStackTrace();
//                                        return null;
//                                }
//                                
//                                if ( numDataObject.complex == true )
//                                {
//                                        try {
//                                                double[] d = numDataObject.imag_part.toDoubleArray(numDataObject.imag_data_type.isSignedType()).getData();
//                                                data[1] = reshape(d,dim1,dim2);
//                                        }
//                                        catch (Exception e) {
//                                                e.printStackTrace();
//                                                return null;
//                                        }
//                                }
                                
                                numDataObject.data = data;
                                
                                return numDataObject;
                        }
                        case mxINT64_CLASS:
                        case mxINT32_CLASS:
                        case mxINT16_CLASS:
                        case mxINT8_CLASS:
                        case mxUINT64_CLASS:
                        case mxUINT32_CLASS:
                        case mxUINT16_CLASS:
                        case mxUINT8_CLASS:
                        {
                                MxNumericDataObject numDataObject = new MxNumericDataObject(dataObject);
                                
                                NumericPartSubelement real_part = new NumericPartSubelement().read(mi);
                                
                                numDataObject.real_data_type = real_part.getDataType();
                                numDataObject.real_part      = real_part.getData();
                                
                                return numDataObject;
                        }
                        case mxCHAR_CLASS:
                        {
                                MxCharacterDataObject charDataObject = new MxCharacterDataObject(dataObject);
                                
                                charDataObject.data_string = new CharacterPartSubelement().read(mi).getString();
                                
                                return charDataObject;
                        }
                        case mxCELL_CLASS:
                        {
                                MxCellDataObject cellDataObject = new MxCellDataObject(dataObject);
                                
                                int nElements = 1;
                                
                                for ( int i = 0; i < cellDataObject.dimensions.length; i++ )
                                {
                                        nElements *= cellDataObject.dimensions[i];
                                }
                                
                                cellDataObject.data_vec = evalCellArrayData(in,nElements);
                                
                                return cellDataObject;
                        }
                        case mxSTRUCT_CLASS:
                        {
                                MxStructDataObject structDataObject = new MxStructDataObject(dataObject);
                                
                                // Evaluate field names length
                                FieldNameLengthSubelement lenField;
                                int                       len = 0;
                                
                                try {
                                        lenField = new FieldNameLengthSubelement().read(mi);
                                        len = lenField.getLength();
                                } catch (DataTypeException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                }
                                
                                // Evaluate field names
                                FieldNamesSubelement namesField;
                                String[]             names = null;
                                try {
                                        namesField = new FieldNamesSubelement().read(mi,len);
                                        
                                        // TODO: Is this list ordered in any way??
                                        names      = namesField.listNames();
                                } catch (DataTypeException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                }
                                
                                // Evaluate struct data fields
                                structDataObject.data_vec = evalStructArrayData(in,names);
                                
                                return structDataObject;
                        }
                        case mxSPARSE_CLASS:
                        {
                                MxSparseDataObject sparseDataObject = new MxSparseDataObject(dataObject);
                                
                                sparseDataObject.nzmax = flagField.getNzMax();
                                
                                SparseRaw sr = evalSparseArrayData(mi,sparseDataObject.complex);
                                
                                sparseDataObject.real_data_type = sr.real_data_type;
                                sparseDataObject.imag_data_type = sr.imag_data_type;
                                sparseDataObject.real_part      = sr.real_part;
                                sparseDataObject.imag_part      = sr.imag_part;
                                sparseDataObject.ir             = sr.ir;
                                sparseDataObject.jc             = sr.jc;
                                
                                return sparseDataObject;
                        }
                        default:
                                return null;
                }
        }
        
        private SparseRaw evalSparseArrayData(MyDataInputStream in, boolean isComplex)
        {
                RowIndexSubelement rowIndices = null;
                try {
                        rowIndices = new RowIndexSubelement().read(in);
                } catch (DataTypeException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                
                ColumnIndexSubelement colIndices = null;
                try {
                        colIndices = new ColumnIndexSubelement().read(in);
                } catch (DataTypeException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                
                int[][] coord = transform(rowIndices.getRowIndices(),colIndices.getRowIndices());
                
                SparseRaw sr = new SparseRaw();
                
                sr.ir = coord[0];
                sr.jc = coord[1];
                
                // Evaluate data field
                NumericPartSubelement realField = new NumericPartSubelement().read(in);
                
                sr.real_part      = realField.getData();
                sr.real_data_type = realField.getDataType();
                
                if ( isComplex == true )
                {
                        NumericPartSubelement imagField = new NumericPartSubelement().read(in);
                        
                        sr.imag_part      = imagField.getData();
                        sr.imag_data_type = imagField.getDataType();
                        
                        if ( sr.real_part.length() != sr.imag_part.length() )
                                return null;
                }
                
                return sr;
        }
        
        /*
         * cols(j) points to (is the index of) the first elements of column j
         * in rows[]. Since rows[cols(j+1)] is the row-index of the first
         * element in column (j+1), it follows that rows[cols(j+1)-1] is the
         * row-index of the last element in column j.
         * Also, cols(j) = #(elements in columns 0:j-1). Therefore,
         * cols(j+1)- cols(j) = #(elements in column j)
         */
        private int[][] transform(int[] rows, int[] cols)
        {
                int n_cols     = cols.length-1;
                int n_elements = cols[n_cols];
                
                // 'rows' may contain more elements than non-zero elements
                // in the sparse matrix (it may contain excess storage),
                // we use 'n_elements' to determine the exact number of
                // non-zero elements.
                int[][] coord = new int[2][n_elements];
                
                for ( int j = 0 ; j < n_cols; j ++)
                {
                        for ( int i = cols[j]; i <= cols[j+1]-1; i++)
                        {
                                coord[0][i] = rows[i]; // Could be done outside the for-loops
                                coord[1][i] = j;
                        }
                }
                
                return coord;
        }
        
        private MxDataObject[] evalCellArrayData(InputStream in, int nElements)
        {
                MxDataObject[] elements = new MxDataObject[nElements];
                
                for ( int i = 0; i < nElements; i++ )
                        elements[i] = evalArray(in);
                
                return elements;
        }
        
        private MxDataObject[] evalStructArrayData(InputStream in, String[] names)
        {
                int nElements = names.length;
                
                MxDataObject[] elements = new MxDataObject[nElements];
                
                for ( int i = 0; i < nElements; i++ )
                {
                        elements[i] = evalArray(in);
                        elements[i].name = names[i]; // Substitute field name
                }
                
                return elements;
        }
        
        private class MatElementInfo extends MxDataObject
        {
                int position;
                
                public String toString()
                {
                        return "" + position;
                }
        }
        
        private boolean isArrayElement(DataTagFieldReader tagInfo)
        {
                return (isMatrixElement(tagInfo) || isCompressedElement(tagInfo));
        }
        
        private boolean isMatrixElement(DataTagFieldReader tagInfo)
        {
                if ( tagInfo.isShortElement() )
                        return false;
                
                boolean isMatrix = tagInfo.getDataType() == DataType.miMATRIX;
                
                return isMatrix;
        }
        
        private boolean isCompressedElement(DataTagFieldReader tagInfo)
        {
                if ( tagInfo.isShortElement() )
                        return false;
                
                boolean isCompressed = tagInfo.getDataType() == DataType.miCOMPRESSED;
                
                return isCompressed;
        }
        
//        private double[][] reshape (double[] in, int dim1, int dim2)
//        {
//                double[][] out = new double[dim1][dim2];
//                for ( int j = 0; j < dim2; j++ )
//                        for ( int i = 0; i < dim1; i++ )
//                                out[i][j] = in[i+j*dim1];
//                return out;
//        }
}
