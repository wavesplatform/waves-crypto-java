package com.wavesplatform.crypto;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class Util {

    public static Bytes concat(final Bytes bytes1, Bytes bytes2) {
        return Bytes.of(concat(bytes1.array(), bytes2.array()));
    }

    public static byte[] concat(final byte[] array1, byte[] array2) {
        byte[] joinedArray = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /*TODO what use cases? What if chunksSizes less/more than bytes length?
    public static byte[][] split(final byte[] bytes, int... chunkSizes) {
        int chunk = 2;
        for(int i=0;i<bytes.length;i+=chunk){
            System.out.println(Arrays.toString(Arrays.copyOfRange(bytes, i, Math.min(bytes.length,i+chunk))));
        }
        return ;
    }*/

    //TODO verifySignature
    //TODO verifyPublicKey
    //TODO verifyAddress

}
