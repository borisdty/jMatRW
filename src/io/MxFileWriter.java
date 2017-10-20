package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;

import common.MxFileHeader;

import abstractTypes.AbstractArrayElement;
import array.MxCellArray;
import array.MxNumericArray;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxFileWriter
{
        RandomAccessFile raFile;
        
        //ByteOrder byte_order = ByteOrder.nativeOrder();
        ByteOrder byte_order = MxByteOrder.byte_order;
        
        public MxFileWriter(String filename)
        {
                File file = new File(filename);
                
                if (file.exists())
                        file.delete();
                
                MxFileHeader fileHeader = MxFileHeader.fileHeaderFactory();
                byte[] head = fileHeader.toByteArray(byte_order);
                
                try {
                        raFile = new RandomAccessFile(file,"rw");
                        raFile.write(head);
                }
                catch (FileNotFoundException e) {
                        e.printStackTrace();
                }
                catch (IOException e) {
                        e.printStackTrace();
                }
        }
        
        private synchronized void write(byte[] data)
        {
                try {
                        raFile.write(data);
                }
                catch (IOException e) {
                        e.printStackTrace();
                }
        }
        
        public synchronized void write(AbstractArrayElement array)
        {
                byte[] data = array.toByteArray(byte_order);
                write(data);
        }
        
        public synchronized void write(String name, double[] vec)
        {
                MxNumericArray numArray = new MxNumericArray(name);
                numArray.setData(vec);
                write(numArray);
        }
        
        public synchronized void write(String name, double[] rvec, double[] ivec)
        {
                MxNumericArray numArray = new MxNumericArray(name);
                numArray.setData(rvec,ivec);
                write(numArray);
        }
        
        public synchronized void write(String name, double[][] mat)
        {
                MxNumericArray numArray = new MxNumericArray(name);
                numArray.setData(mat);
                write(numArray);
        }
        
        public synchronized void write(String name, double[][] rmat, double[][] imat)
        {
                MxNumericArray numArray = new MxNumericArray(name);
                numArray.setData(rmat,imat);
                write(numArray);
        }
        
        /**
         * Write a vector of ArrayElements into the file as a Matlab Cell structure
         * @param name
         * @param cellData
         */
        public synchronized void write(String name, AbstractArrayElement[] cellData)
        {
                MxCellArray cellArray = new MxCellArray(name);
                cellArray.setData(cellData);
                write(cellArray);
        }
        
        /**
         * Write a vector of ArrayElements into the file as a Matlab Cell structure
         * @param name
         * @param cellData
         */
        public synchronized void write(String name, AbstractArrayElement[][] cellData)
        {
                MxCellArray cellArray = new MxCellArray(name);
                cellArray.setData(cellData);
                write(cellArray);
        }
        
        public void close()
        {
                try {
                        raFile.close();
                }
                catch (IOException e) {
                        e.printStackTrace();
                }
        }
        
        public void flush()
        {
                // RandomAccessFile does not support flush() directly.
        }
        
        @Override
        protected void finalize() throws Throwable
        {
                super.finalize();
                raFile.close();
        }
}
