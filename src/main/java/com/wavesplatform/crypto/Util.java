package com.wavesplatform.crypto;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Util {

    public static byte[] intToBytes(int number) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(number);
        return buffer.array();
    }

    public static byte[] longToBytes(long number) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(number);
        return buffer.array();
    }

    public static String bytesToUtf8(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] utf8ToBytes(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] emptyBytes() {
        return new byte[0];
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

    /*TODO what use cases? What if chunksSizes less/more than bytes length?
    public static byte[][] split(final byte[] bytes, int... chunkSizes) {
        int chunk = 2;
        for(int i=0;i<bytes.length;i+=chunk){
            System.out.println(Arrays.toString(Arrays.copyOfRange(bytes, i, Math.min(bytes.length,i+chunk))));
        }
        return ;
    }*/

    //TODO bytes clone, equality
    //TODO verifySignature
    //TODO verifyPublicKey
    //TODO verifyAddress

}
