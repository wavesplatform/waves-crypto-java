package com.wavesplatform.crypto;

import java.util.Arrays;

public class Util {

    public static byte[] concat(final byte[] array1, byte[] array2) {
        byte[] joinedArray = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    //TODO long to byte[]

}
