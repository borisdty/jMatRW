package test;

import static org.junit.Assert.*;

import io.MxFileReader;
import io.MxFileWriter;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import subelements.base.ByteArray;
import subelements.base.DoubleArray;
import subelements.base.IntArray;

import common.DataType;
import common.MxDataObject;
import common.MxCellDataObject;
import common.MxCharacterDataObject;
import common.MxClassID;
import common.MxEmptyDataObject;
import common.MxNumericDataObject;
import common.MxSparseDataObject;
import common.MxStructDataObject;

import array.MxCellArray;
import array.MxCharacterArray;
import array.MxNumericArray;
import array.MxSparseArray;
import array.MxStructArray;

/**
 * Testing of reading and writing Matlab .mat data files
 * The test usually consist of
 * 1) Writing a data element (matrix/vector, structures...) in Java to a .mat files A
 * 2) Defining a reference object B as expected to be returned by reading this file again
 * 3) Reading the written file A again as well as a Matlab generated data file C with identical data content
 * 4) Comparing element-wise the reading result from file A and C and reference object B
 * 5) Byte-level comparison of file A and C
 * 
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 */

// TODO: 1) Include more tests with zipped (MATLAB v7 file format) data
//       2) Test MxFileReader::getVarNames()
//       3) Test MxFileReader::getVariable()
//       4) Test overwriting of individual cell elements
public class MxArrayIOTest
{
        String fileSep = System.getProperty( "file.separator");
        
        final String mDir = "lib" + fileSep + "matlab" + fileSep;
        final String jDir = "lib" + fileSep + "java"   + fileSep;
        
        public static junit.framework.Test suite()
        {
                return new JUnit4TestAdapter( MxArrayIOTest.class );
        }
        
        @Test
        /*
         * MATLAB: mvec1 = [1]; save -v6 mvec1 mvec1
         */
        public void testVec1() throws Exception
        {
                final String readName  = mDir + "mvec1.mat";
                final String writeName = jDir + "jvec1.mat";
                final String varname   = "mvec1";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[] data = {1};
                MxNumericArray numArray = new MxNumericArray(varname);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{1,1};
                ref.name       = varname;
                ref.real_data_type = DataType.miUINT8;
                ref.imag_data_type = null;
                ref.real_part = new ByteArray(new byte[]{1});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mvec2 = [1,2]; save -v6 mvec2 mvec2
         */
        public void testVec2() throws Exception
        {
                final String readName  = mDir + "mvec2.mat";
                final String writeName = jDir + "jvec2.mat";
                final String varname   = "mvec2";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[] data = {1,2};
                MxNumericArray numArray = new MxNumericArray(varname);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{1,2};
                ref.name       = varname;
                ref.real_data_type = DataType.miUINT8;
                ref.imag_data_type = null;
                ref.real_part = new ByteArray(new byte[]{1,2});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mvec3 = [1,2,3]; save -v6 mvec3 mvec3
         */
        public void testVec3() throws Exception
        {
                final String readName  = mDir + "mvec3.mat";
                final String writeName = jDir + "jvec3.mat";
                final String varname   = "mvec3";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[] data = {1,2,3};
                MxNumericArray numArray = new MxNumericArray(varname);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{1,3};
                ref.name       = varname;
                ref.real_data_type = DataType.miUINT8;
                ref.imag_data_type = null;
                ref.real_part = new ByteArray(new byte[]{1,2,3});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mvec4 = [1,2,3,4]; save -v6 mvec4 mvec4
         */
        public void testVec4() throws Exception
        {
                final String readName  = mDir + "mvec4.mat";
                final String writeName = jDir + "jvec4.mat";
                final String varname   = "mvec4";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[] data = {1,2,3,4};
                MxNumericArray numArray = new MxNumericArray(varname);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{1,4};
                ref.name       = varname;
                ref.real_data_type = DataType.miUINT8;
                ref.imag_data_type = null;
                ref.real_part = new ByteArray(new byte[]{1,2,3,4});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mvec5 = [1.1]; save -v6 mvec5 mvec5
         */
        public void testVec5() throws Exception
        {
                final String readName  = mDir + "mvec5.mat";
                final String writeName = jDir + "jvec5.mat";
                final String varname   = "mvec5";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[] data = {1.1};
                MxNumericArray numArray = new MxNumericArray(varname);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{1,1};
                ref.name       = varname;
                ref.real_data_type = DataType.miDOUBLE;
                ref.imag_data_type = null;
                ref.real_part = new DoubleArray(new double[]{1.1});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mvec6 = [1.1,2.2]; save -v6 mvec6 mvec6
         */
        public void testVec6() throws Exception
        {
                final String readName  = mDir + "mvec6.mat";
                final String writeName = jDir + "jvec6.mat";
                final String varname   = "mvec6";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[] data = {1.1,2.2};
                MxNumericArray numArray = new MxNumericArray(varname);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{1,2};
                ref.name       = varname;
                ref.real_data_type = DataType.miDOUBLE;
                ref.imag_data_type = null;
                ref.real_part = new DoubleArray(new double[]{1.1,2.2});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mvec7 = int32(257); save -v6 mvec7 mvec7
         */
        public void testVec7() throws Exception
        {
                final String readName  = mDir + "mvec7.mat";
                final String writeName = jDir + "jvec7.mat";
                final String varname   = "mvec7";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                int[] data = {257};
                MxNumericArray numArray = new MxNumericArray(varname);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxINT32_CLASS;
                ref.dimensions = new int[]{1,1};
                ref.name       = varname;
                ref.real_data_type = DataType.miINT32;
                ref.imag_data_type = null;
                ref.real_part = new IntArray(new int[]{257});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mmat1 = [1;2]; save -v6 mmat1 mmat1
         */
        public void testMat1() throws Exception
        {
                final String readName  = mDir + "mmat1.mat";
                final String writeName = jDir + "jmat1.mat";
                final String varname   = "mmat1";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[][] data = {{1},{2}};
                MxNumericArray numArray = new MxNumericArray(varname);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{2,1};
                ref.name       = varname;
                ref.real_data_type = DataType.miUINT8;
                ref.imag_data_type = null;
                ref.real_part = new ByteArray(new byte[]{1,2});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mmat2 = [1,2;3,4]; save -v6 mmat2 mmat2
         */
        public void testMat2() throws Exception
        {
                final String readName  = mDir + "mmat2.mat";
                final String writeName = jDir + "jmat2.mat";
                final String varname   = "mmat2";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[][] data = {{1,2},{3,4}};
                MxNumericArray numArray = new MxNumericArray(varname);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{2,2};
                ref.name       = varname;
                ref.real_data_type = DataType.miUINT8;
                ref.imag_data_type = null;
                ref.real_part = new ByteArray(new byte[]{1,3,2,4});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mmat3 = [1,2,3;4,5,6]; save -v6 mmat3 mmat3
         */
        public void testMat3() throws Exception
        {
                final String readName  = mDir + "mmat3.mat";
                final String writeName = jDir + "jmat3.mat";
                final String varname   = "mmat3";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[][] data = {{1,2,3},{4,5,6}};
                MxNumericArray numArray = new MxNumericArray(varname);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{2,3};
                ref.name       = varname;
                ref.real_data_type = DataType.miUINT8;
                ref.imag_data_type = null;
                ref.real_part = new ByteArray(new byte[]{1,4,2,5,3,6});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mmat4 = [1,2,3,4;5,6,7,8]; save -v6 mmat4 mmat4
         */
        public void testMat4() throws Exception
        {
                final String readName  = mDir + "mmat4.mat";
                final String writeName = jDir + "jmat4.mat";
                final String varname   = "mmat4";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[][] data = {{1,2,3,4},{5,6,7,8}};
                MxNumericArray numArray = new MxNumericArray(varname);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{2,4};
                ref.name       = varname;
                ref.real_data_type = DataType.miUINT8;
                ref.imag_data_type = null;
                ref.real_part = new ByteArray(new byte[]{1,5,2,6,3,7,4,8});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mmat5 = [1.1,2;3,4]; save -v6 mmat5 mmat5
         */
        public void testMat5() throws Exception
        {
                final String readName  = mDir + "mmat5.mat";
                final String writeName = jDir + "jmat5.mat";
                final String varname   = "mmat5";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[][] data = {{1.1,2},{3,4}};
                MxNumericArray numArray = new MxNumericArray(varname);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{2,2};
                ref.name       = varname;
                ref.real_data_type = DataType.miDOUBLE;
                ref.imag_data_type = null;
                ref.real_part = new DoubleArray(new double[]{1.1,3,2,4});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mmat6 = [1.1,2;3,4] + i*[0,2.2;0,0]; save -v6 mmat6 mmat6
         */
        public void testMat6() throws Exception
        {
                final String readName  = mDir + "mmat6.mat";
                final String writeName = jDir + "jmat6.mat";
                final String varname   = "mmat6";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[][] dataRe = {{1.1,2},{3,4}};
                double[][] dataIm = {{0,2.2},{0,0}};
                MxNumericArray numArray = new MxNumericArray(varname);
                numArray.setData(dataRe,dataIm);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = true;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{2,2};
                ref.name       = varname;
                ref.real_data_type = DataType.miDOUBLE;
                ref.imag_data_type = DataType.miDOUBLE;
                ref.real_part = new DoubleArray(new double[]{1.1,3,2,4});
                ref.imag_part = new DoubleArray(new double[]{0,0,2.2,0});
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mstr1 = 'a'; save -v6 mstr1 mstr1
         */
        public void testString1() throws Exception
        {
                final String readName  = mDir + "mstr1.mat";
                final String writeName = jDir + "jstr1.mat";
                final String varname   = "mstr1";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                String data = "a";
                MxCharacterArray charArray = new MxCharacterArray(varname);
                charArray.setData(data);
                
                fwriter.write(charArray);
                fwriter.close();
                
                MxCharacterDataObject ref = new MxCharacterDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxCHAR_CLASS;
                ref.dimensions = new int[]{1,1};
                ref.name       = varname;
                ref.data_string = "a";
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mstr2 = 'ab'; save -v6 mstr2 mstr2
         */
        public void testString2() throws Exception
        {
                final String readName  = mDir + "mstr2.mat";
                final String writeName = jDir + "jstr2.mat";
                final String varname   = "mstr2";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                String data = "ab";
                MxCharacterArray charArray = new MxCharacterArray(varname);
                charArray.setData(data);
                
                fwriter.write(charArray);
                fwriter.close();
                
                MxCharacterDataObject ref = new MxCharacterDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxCHAR_CLASS;
                ref.dimensions = new int[]{1,2};
                ref.name       = varname;
                ref.data_string = "ab";
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mstr3 = 'abcd'; save -v6 mstr3 mstr3
         */
        public void testString3() throws Exception
        {
                final String readName  = mDir + "mstr3.mat";
                final String writeName = jDir + "jstr3.mat";
                final String varname   = "mstr3";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                String data = "abcd";
                MxCharacterArray charArray = new MxCharacterArray(varname);
                charArray.setData(data);
                
                fwriter.write(charArray);
                fwriter.close();
                
                MxCharacterDataObject ref = new MxCharacterDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxCHAR_CLASS;
                ref.dimensions = new int[]{1,4};
                ref.name       = varname;
                ref.data_string = "abcd";
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mstr4 = 'abcde'; save -v6 mstr4 mstr4
         */
        public void testString4() throws Exception
        {
                final String readName  = mDir + "mstr4.mat";
                final String writeName = jDir + "jstr4.mat";
                final String varname   = "mstr4";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                String data = "abcde";
                MxCharacterArray charArray = new MxCharacterArray(varname);
                charArray.setData(data);
                
                fwriter.write(charArray);
                fwriter.close();
                
                MxCharacterDataObject ref = new MxCharacterDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxCHAR_CLASS;
                ref.dimensions = new int[]{1,5};
                ref.name       = varname;
                ref.data_string = "abcde";
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mcell1      = cell(2,2);
         *         mcell1{1,1} = [1];
         *         mcell1{1,2} = [1,2;3,4];
         *         mcell1{2,2} = 'abcde';
         *         save -v6 mcell1 mcell1
         */
        public void testCell1() throws Exception
        {
                final String readName  = mDir + "mcell1.mat";
                final String writeName = jDir + "jcell1.mat";
                final String varname   = "mcell1";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[] dataVec1 = {1};
                MxNumericArray numArrayVec1 = new MxNumericArray("vec1");
                numArrayVec1.setData(dataVec1);
                
                MxNumericDataObject refVec1 = new MxNumericDataObject();
                refVec1.complex    = false;
                refVec1.global     = false;
                refVec1.logical    = false;
                refVec1.classType  = MxClassID.mxDOUBLE_CLASS;
                refVec1.dimensions = new int[]{1,1};
                refVec1.name       = ""; // MATLAB does not store it... "vec1";
                refVec1.real_data_type = DataType.miUINT8;
                refVec1.imag_data_type = null;
                refVec1.real_part = new ByteArray(new byte[]{1});
                refVec1.imag_part = null;
                
                MxEmptyDataObject refEmpty = new MxEmptyDataObject();
                refEmpty.complex    = false;
                refEmpty.global     = false;
                refEmpty.logical    = false;
                refEmpty.classType  = MxClassID.mxDOUBLE_CLASS;
                refEmpty.dimensions = new int[]{0,0};
                refEmpty.name       = "";
                
                double[][] dataMat2 = {{1,2},{3,4}};
                MxNumericArray numArrayMat2 = new MxNumericArray("mat2");
                numArrayMat2.setData(dataMat2);
                
                MxNumericDataObject refMat2 = new MxNumericDataObject();
                refMat2.complex    = false;
                refMat2.global     = false;
                refMat2.logical    = false;
                refMat2.classType  = MxClassID.mxDOUBLE_CLASS;
                refMat2.dimensions = new int[]{2,2};
                refMat2.name       = ""; // MATLAB does not store it... "mat2";
                refMat2.real_data_type = DataType.miUINT8;
                refMat2.imag_data_type = null;
                refMat2.real_part = new ByteArray(new byte[]{1,3,2,4});
                refMat2.imag_part = null;
                
                String dataStr4 = "abcde";
                MxCharacterArray charArrayStr4 = new MxCharacterArray("str4");
                charArrayStr4.setData(dataStr4);
                
                MxCharacterDataObject refStr4 = new MxCharacterDataObject();
                refStr4.complex    = false;
                refStr4.global     = false;
                refStr4.logical    = false;
                refStr4.classType  = MxClassID.mxCHAR_CLASS;
                refStr4.dimensions = new int[]{1,5};
                refStr4.name       = ""; // MATLAB does not store it... "str4";
                refStr4.data_string = "abcde";
                
                MxCellArray cellArray = new MxCellArray(varname);
                cellArray.addData(numArrayVec1, 0,0);
                cellArray.addData(numArrayMat2, 0,1);
                // Element (1,0) is left empty in this test
                cellArray.addData(charArrayStr4,1,1);
                
                fwriter.write(cellArray);
                fwriter.close();
                
                MxCellDataObject refCell1 = new MxCellDataObject();
                refCell1.complex    = false;
                refCell1.global     = false;
                refCell1.logical    = false;
                refCell1.classType  = MxClassID.mxCELL_CLASS;
                refCell1.dimensions = new int[]{2,2};
                refCell1.name       = varname;
                refCell1.data_vec   = new MxDataObject[] {refVec1,refEmpty,refMat2,refStr4};
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, refCell1, m1);
                assertEquals(s2, refCell1, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mcell2      = cell(2,2);
         *         mcell2{2,1} = [1,2,3,4;5,6,7,8];
         *         mcell2{1,2} = [1,2,3,4];
         *         mcell2{2,2} = [1,2,3,4];
         *         save -v6 mcell2 mcell2
         */
        public void testCell2() throws Exception
        {
                final String readName  = mDir + "mcell2.mat";
                final String writeName = jDir + "jcell2.mat";
                final String varname   = "mcell2";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[]   vec = {1,2,3,4};
                MxNumericArray numArray1 = new MxNumericArray("Vec_a");
                numArray1.setData(vec);
                
                MxNumericDataObject refVec1 = new MxNumericDataObject();
                refVec1.complex    = false;
                refVec1.global     = false;
                refVec1.logical    = false;
                refVec1.classType  = MxClassID.mxDOUBLE_CLASS;
                refVec1.dimensions = new int[]{1,4};
                refVec1.name       = ""; // MATLAB does not store it... "vec1";
                refVec1.real_data_type = DataType.miUINT8;
                refVec1.imag_data_type = null;
                refVec1.real_part = new ByteArray(new byte[]{1,2,3,4});
                refVec1.imag_part = null;
                
                double[][] mat = {{1,2,3,4},{5,6,7,8}};
                MxNumericArray numArray2 = new MxNumericArray("Mat_a");
                numArray2.setData(mat);
                
                MxNumericDataObject refMat2 = new MxNumericDataObject();
                refMat2.complex    = false;
                refMat2.global     = false;
                refMat2.logical    = false;
                refMat2.classType  = MxClassID.mxDOUBLE_CLASS;
                refMat2.dimensions = new int[]{2,4};
                refMat2.name       = ""; // MATLAB does not store it... "mat2";
                refMat2.real_data_type = DataType.miUINT8;
                refMat2.imag_data_type = null;
                refMat2.real_part = new ByteArray(new byte[]{1,5,2,6,3,7,4,8});
                refMat2.imag_part = null;
                
                //EmptyNumericArray emptyNumArray = new EmptyNumericArray();
                MxEmptyDataObject refEmpty = new MxEmptyDataObject();
                refEmpty.complex    = false;
                refEmpty.global     = false;
                refEmpty.logical    = false;
                refEmpty.classType  = MxClassID.mxDOUBLE_CLASS;
                refEmpty.dimensions = new int[]{0,0};
                refEmpty.name       = "";
                
                MxNumericArray[][] cellData = {{null,numArray1},{numArray2,numArray1}};
                
                MxCellArray cellArray = new MxCellArray(varname);
                cellArray.setData(cellData);
                
                fwriter.write(cellArray);
                fwriter.close();
                
                MxCellDataObject refCell2 = new MxCellDataObject();
                refCell2.complex    = false;
                refCell2.global     = false;
                refCell2.logical    = false;
                refCell2.classType  = MxClassID.mxCELL_CLASS;
                refCell2.dimensions = new int[]{2,2};
                refCell2.name       = varname;
                refCell2.data_vec   = new MxDataObject[] {refEmpty,refMat2,refVec1,refVec1};
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, refCell2, m1);
                assertEquals(s2, refCell2, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mstruct1.vec = [1,2,3,4];
         *         mstruct1.mat = [1,2,3,4;5,6,7,8];
         *         save -v6 mstruct1 mstruct1
         */
        public void testStruct1() throws Exception
        {
                final String readName  = mDir + "mstruct1.mat";
                final String writeName = jDir + "jstruct1.mat";
                final String varname   = "mstruct1";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[][] dataMat4 = {{1,2,3,4},{5,6,7,8}};
                MxNumericArray numArrayMat4 = new MxNumericArray("mat4");
                numArrayMat4.setData(dataMat4);
                
                MxNumericDataObject refMat4 = new MxNumericDataObject();
                refMat4.complex    = false;
                refMat4.global     = false;
                refMat4.logical    = false;
                refMat4.classType  = MxClassID.mxDOUBLE_CLASS;
                refMat4.dimensions = new int[]{2,4};
                refMat4.name       = "mat";
                refMat4.real_data_type = DataType.miUINT8;
                refMat4.imag_data_type = null;
                refMat4.real_part = new ByteArray(new byte[]{1,5,2,6,3,7,4,8});
                refMat4.imag_part = null;
                
                double[] dataVec4 = {1,2,3,4};
                MxNumericArray numArrayVec4 = new MxNumericArray("vec4");
                numArrayVec4.setData(dataVec4);
                
                MxNumericDataObject refVec4 = new MxNumericDataObject();
                refVec4.complex    = false;
                refVec4.global     = false;
                refVec4.logical    = false;
                refVec4.classType  = MxClassID.mxDOUBLE_CLASS;
                refVec4.dimensions = new int[]{1,4};
                refVec4.name       = "vec";
                refVec4.real_data_type = DataType.miUINT8;
                refVec4.imag_data_type = null;
                refVec4.real_part = new ByteArray(new byte[]{1,2,3,4});
                refVec4.imag_part = null;
                
                MxStructArray structArray = new MxStructArray(varname);
                structArray.addData(refVec4.name,numArrayVec4);
                structArray.addData(refMat4.name,numArrayMat4);
                
                fwriter.write(structArray);
                fwriter.close();
                
                MxStructDataObject refStruct1 = new MxStructDataObject();
                refStruct1.complex    = false;
                refStruct1.global     = false;
                refStruct1.logical    = false;
                refStruct1.classType  = MxClassID.mxSTRUCT_CLASS;
                refStruct1.dimensions = new int[]{1,1};
                refStruct1.name       = varname;
                refStruct1.data_vec   = new MxDataObject[] {refVec4,refMat4};
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, refStruct1, m1);
                assertEquals(s2, refStruct1, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mnan = NaN;
         *         save -v6 mnan mnan
         */
        public void testNaN() throws Exception
        {
                final String readName  = mDir + "mnan.mat";
                final String writeName = jDir + "jnan.mat";
                final String varname   = "mnan";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[] dataNan1 = {Double.NaN,1};
                MxNumericArray numArrayNan1 = new MxNumericArray(varname);
                numArrayNan1.setData(dataNan1);
                
                MxNumericDataObject refNan1 = new MxNumericDataObject();
                refNan1.complex    = false;
                refNan1.global     = false;
                refNan1.logical    = false;
                refNan1.classType  = MxClassID.mxDOUBLE_CLASS;
                refNan1.dimensions = new int[]{1,2};
                refNan1.name       = varname;
                refNan1.real_data_type = DataType.miDOUBLE;
                refNan1.imag_data_type = null;
                refNan1.real_part = new DoubleArray(new double[]{Double.NaN,1});
                refNan1.imag_part = null;
                
                fwriter.write(numArrayNan1);
                fwriter.close();
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                // The following test fails, because Matlab encodes NaN as 0xFFF8...
                // and Java is using 0x7FF8 (both version are valid according to IEEE 754)
                //assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, refNan1, m1);
                assertEquals(s2, refNan1, m2);
                assertEquals(s3, m1, m2);
                
                DoubleArray da = (DoubleArray)((MxNumericDataObject)m1).real_part;
                double[] da2 = da.getData();
                assertEquals("Test if value read from file equals NaN",
                                Double.compare(Double.NaN, da2[0] ), 0 );
        }
        
        @Test
        /*
         * MATLAB: msp4 = sparse([5,4,3,2,4],[1,2,3,4,4], ...
         *                       [0.1, 1.1, 2.2, 3.3, 4.4], 5, 4);
         *         save -v6 msparse4 msp4
         */
        public void testSparse1() throws Exception
        {
                final String readName  = mDir + "msparse4.mat";
                final String writeName = jDir + "jsparse4.mat";
                final String varname   = "msp4";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                /*
                 *       0         0         0         0
                 *       0         0         0    3.3000
                 *       0         0    2.2000         0
                 *       0    1.1000         0    4.4000
                 *  0.1000         0         0         0
                 */
                int[]    ir   = {4, 3, 2, 1, 3};
                int[]    jc   = {0, 1, 2, 3, 3};
                double[] data = {0.1, 1.1, 2.2, 3.3, 4.4};
                MxSparseArray sparseArray = new MxSparseArray(varname);
                sparseArray.setData(ir,jc,data);
                
                fwriter.write(sparseArray);
                fwriter.close();
                
                MxSparseDataObject ref = new MxSparseDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxSPARSE_CLASS;
                ref.dimensions = new int[]{5,4};
                ref.name       = varname;
                ref.real_data_type = DataType.miDOUBLE;
                ref.imag_data_type = null;
                ref.real_part      = new DoubleArray(data);
                ref.imag_part      = null;
                ref.ir             = ir;
                ref.jc             = jc;
                ref.nzmax          = 5;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                // The following test would usually fail, because for sparse arrays, Matlab sets a bit
                // in the flag field (bit 4, 0x10). This part of the flag field is declared
                // undefined by MathWorks and not documented further.
                // By extending the flags in class ArrayFlagsSubelement, we fixed it.
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                // nzmax may be different;
                // in MATLAB is may be that nzmax >= #(non-zero elements);
                // in Java version it is strictly nzmax == #(non-zero elements)
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: msp5 = sparse([6,5,4,3,2,4],[5,1,2,3,4,4], ...
         *                       [0.0, 0.1, 1.1, 2.2, 3.3, 4.4], 6, 5);
         *         save -v6 msparse5 msp5
         *
         * Test what happens, if 0.0 element is included as data;
         * it should not be explicitly saved as data, since 0.0 entries
         * are redundant in the sparse matrix definition
         */
        public void testSparse2() throws Exception
        {
                final String readName  = mDir + "msparse5.mat";
                final String writeName = jDir + "jsparse5.mat";
                final String varname   = "msp5";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                /*
                 *       0         0         0         0         0
                 *       0         0         0    3.3000         0
                 *       0         0    2.2000         0         0
                 *       0    1.1000         0    4.4000         0
                 *  0.1000         0         0         0         0
                 *       0         0         0         0         0.0
                 */
                int[]    ir   = {5, 4, 3, 2, 1, 3};
                int[]    jc   = {4, 0, 1, 2, 3, 3};
                double[] data = {0.0, 0.1, 1.1, 2.2, 3.3, 4.4};
                MxSparseArray sparseArray = new MxSparseArray(varname);
                sparseArray.setData(ir,jc,data);
                
                fwriter.write(sparseArray);
                fwriter.close();
                
                MxSparseDataObject ref = new MxSparseDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxSPARSE_CLASS;
                ref.dimensions = new int[]{6,5};
                ref.name       = varname;
                ref.real_data_type = DataType.miDOUBLE;
                ref.imag_data_type = null;
                ref.real_part      = new DoubleArray(new double[]{0.1, 1.1, 2.2, 3.3, 4.4});
                ref.imag_part      = null;
                ref.ir             = new int[] {4, 3, 2, 1, 3};
                ref.jc             = new int[] {0, 1, 2, 3, 3};
                ref.nzmax          = 5;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                // The following test would usually fail, because for sparse arrays, Matlab sets a bit
                // in the flag field (bit 4, 0x10) that is not documented.
                // By extending the flags in class ArrayFlagsSubelement, we fixed it.
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                // nzmax may be different;
                // in MATLAB is may be that nzmax >= #(non-zero elements);
                // in Java version it is strictly nzmax == #(non-zero elements)
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: aaVecX_a = [1.1,2.2];
         *         aaMatX_a = [1;2];
         *         CellX_a = cell(1,2);
         *         CellX_a{1} = aaVecX_a;
         *         CellX_a{2} = aaMatX_a;
         *         StructX_a.VecX_a = aaVecX_a;
         *         StructX_a.MatX_a = aaMatX_a;
         *         save -v6 mstruct_cat_cell CellX_a StructX_a
         */
        public void struct_cat_cell_test() throws Exception
        {
                final String readName  = mDir + "mstruct_cat_cell.mat";
                final String writeName = jDir + "jstruct_cat_cell.mat";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[] vec = {1.1,2.2};
                MxNumericArray numArrayVec = new MxNumericArray("aaVecX_a");
                numArrayVec.setData(vec);
                
                MxNumericDataObject refvec = new MxNumericDataObject();
                refvec.complex    = false;
                refvec.global     = false;
                refvec.logical    = false;
                refvec.classType  = MxClassID.mxDOUBLE_CLASS;
                refvec.dimensions = new int[]{1,2};
                refvec.name       = ""; // MATLAB does not store it... "VecX_a";
                refvec.real_data_type = DataType.miDOUBLE;
                refvec.imag_data_type = null;
                refvec.real_part = new DoubleArray(new double[]{1.1,2.2});
                refvec.imag_part = null;
                
                double[][] mat = {{1},{2}};
                MxNumericArray numArrayMat = new MxNumericArray("aaMatX_a");
                numArrayMat.setData(mat);
                
                MxNumericDataObject refmat = new MxNumericDataObject();
                refmat.complex    = false;
                refmat.global     = false;
                refmat.logical    = false;
                refmat.classType  = MxClassID.mxDOUBLE_CLASS;
                refmat.dimensions = new int[]{2,1};
                refmat.name       = ""; // MATLAB does not store it... "MatX_a";
                refmat.real_data_type = DataType.miUINT8;
                refmat.imag_data_type = null;
                refmat.real_part = new ByteArray(new byte[]{1,2});
                refmat.imag_part = null;
                
                MxCellArray cellArray = new MxCellArray("CellX_a");
                cellArray.addData(numArrayVec,0,0);
                cellArray.addData(numArrayMat,0,1);
                
                MxStructArray structArray = new MxStructArray("StructX_a");
                structArray.addData("VecX_a", numArrayVec);
                structArray.addData("MatX_a", numArrayMat);
                
                fwriter.write(cellArray);
                fwriter.write(structArray);
                fwriter.close();
                
                MxCellDataObject refCell1 = new MxCellDataObject();
                refCell1.complex    = false;
                refCell1.global     = false;
                refCell1.logical    = false;
                refCell1.classType  = MxClassID.mxCELL_CLASS;
                refCell1.dimensions = new int[]{1,2};
                refCell1.name       = "CellX_a";
                refCell1.data_vec   = new MxDataObject[] {refvec,refmat};
                
                MxStructDataObject refStruct1 = new MxStructDataObject();
                refStruct1.complex    = false;
                refStruct1.global     = false;
                refStruct1.logical    = false;
                refStruct1.classType  = MxClassID.mxSTRUCT_CLASS;
                refStruct1.dimensions = new int[]{1,1};
                refStruct1.name       = "StructX_a";
                refStruct1.data_vec   = new MxDataObject[] {refvec,refmat};
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                String varname   = "CellX_a";
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, refCell1, m1);
                assertEquals(s2, refCell1, m2);
                assertEquals(s3, m1, m2);
                
                varname   = "StructX_a";
                
                m1 = jfreader.getVariable(varname);
                m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                // For structures MATLAB re-inserts field names
                refvec.name = "VecX_a";
                refmat.name = "MatX_a";
                
                s1 = "Test if value read from j-file equals reference";
                s2 = "Test if value read from m-file equals reference";
                s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, refStruct1, m1);
                assertEquals(s2, refStruct1, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mvec1 = [1]; save -v7 mvec1_v7 mvec1
         */
        public void testVec1_v7() throws Exception
        {
                final String readName  = mDir + "mvec1_v7.mat";
                final String writeName = jDir + "jvec1_v7.mat";
                final String varname   = "mvec1";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[] data = {1};
                MxNumericArray numArray = new MxNumericArray(varname,true);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{1,1};
                ref.name       = varname;
                ref.real_data_type = DataType.miUINT8;
                ref.imag_data_type = null;
                ref.real_part = new ByteArray(new byte[]{1});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mvec2 = [1,2]; save -v7 mvec2_v7 mvec2
         */
        public void testVec2_v7() throws Exception
        {
                final String readName  = mDir + "mvec2_v7.mat";
                final String writeName = jDir + "jvec2_v7.mat";
                final String varname   = "mvec2";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[] data = {1,2};
                MxNumericArray numArray = new MxNumericArray(varname,true);
                numArray.setData(data);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = false;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{1,2};
                ref.name       = varname;
                ref.real_data_type = DataType.miUINT8;
                ref.imag_data_type = null;
                ref.real_part = new ByteArray(new byte[]{1,2});
                ref.imag_part = null;
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
        
        @Test
        /*
         * MATLAB: mmat6 = [1.1,2;3,4] + i*[0,2.2;0,0]; save -v7 mmat6_v7 mmat6
         */
        public void testMat6_v7() throws Exception
        {
                final String readName  = mDir + "mmat6_v7.mat";
                final String writeName = jDir + "jmat6_v7.mat";
                final String varname   = "mmat6";
                
                MxFileWriter fwriter = new MxFileWriter(writeName);
                
                double[][] dataRe = {{1.1,2},{3,4}};
                double[][] dataIm = {{0,2.2},{0,0}};
                MxNumericArray numArray = new MxNumericArray(varname,true);
                numArray.setData(dataRe,dataIm);
                
                fwriter.write(numArray);
                fwriter.close();
                
                MxNumericDataObject ref = new MxNumericDataObject();
                ref.complex    = true;
                ref.global     = false;
                ref.logical    = false;
                ref.classType  = MxClassID.mxDOUBLE_CLASS;
                ref.dimensions = new int[]{2,2};
                ref.name       = varname;
                ref.real_data_type = DataType.miDOUBLE;
                ref.imag_data_type = DataType.miDOUBLE;
                ref.real_part = new DoubleArray(new double[]{1.1,3,2,4});
                ref.imag_part = new DoubleArray(new double[]{0,0,2.2,0});
                
                MxFileReader jfreader = new MxFileReader(writeName);
                MxFileReader mfreader = new MxFileReader(readName);
                
                MxDataObject m1 = jfreader.getVariable(varname);
                MxDataObject m2 = mfreader.getVariable(varname);
                
                jfreader.close();
                mfreader.close();
                
                assertTrue( CompareBinary.compare(readName, writeName, 128) );
                
                String s1 = "Test if value read from j-file equals reference";
                String s2 = "Test if value read from m-file equals reference";
                String s3 = "Test if value read from j-file equals m-file";
                assertEquals(s1, ref, m1);
                assertEquals(s2, ref, m2);
                assertEquals(s3, m1, m2);
        }
}
