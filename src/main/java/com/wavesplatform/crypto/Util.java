package com.wavesplatform.crypto;

import java.nio.ByteBuffer;
import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class Util {

    public static Bytes concat(final Bytes... bytes) {
        byte[][] arrays = new byte[bytes.length][];
        for (int i = 0; i < bytes.length; i++) {
            arrays[i] = bytes[i].array();
        }
        return Bytes.of(concat(arrays));
    }

    public static byte[] concat(final byte[]... arrays) {
        byte[] total = new byte[0];
        for (byte[] a : arrays) {
            byte[] joinedArray = Arrays.copyOf(total, total.length + a.length);
            System.arraycopy(a, 0, joinedArray, total.length, a.length);
            total = joinedArray;
        }
        return total;
    }

    public static Bytes intToBytes(int number) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(number);
        return Bytes.of(buffer.array());
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
