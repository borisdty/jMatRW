package io;

import java.nio.ByteOrder;

/**
 *
 * @author Boris Dortschy (<a href="mailto:bodo.pub@gmail.com">bodo.pub@gmail.com</a>)
 *
 */
public class MxByteOrder
{
        /*
         * Byte order used in the project
         * A common place of definition allows easier and common control
         * within the whole project.
         */
        public static final ByteOrder byte_order = ByteOrder.nativeOrder();
        //public static final ByteOrder byte_order = ByteOrder.LITTLE_ENDIAN;
        //public static final ByteOrder byte_order = ByteOrder.BIG_ENDIAN;
}
