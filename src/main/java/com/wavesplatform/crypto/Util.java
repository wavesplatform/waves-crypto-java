package com.wavesplatform.crypto;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class Util {

    public static Bytes concat(final Bytes bytes1, Bytes bytes2) {
        return Bytes.of(concat(bytes1.value(), bytes2.value()));
    }

    public static byte[] concat(final byte[] array1, byte[] array2) {
        byte[] joinedArray = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    //TODO split()

}
