package com.wavesplatform.crypto;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class Bytes {

    public static byte[] fromInt(int number) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(number);
        return buffer.array();
    }

    public static byte[] fromLong(long number) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(number);
        return buffer.array();
    }

    public static String toUtf8(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] fromUtf8(String string) throws IllegalArgumentException {
        if (string == null) throw new IllegalArgumentException("String cannot be null");
        return string.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] empty() {
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

    //TODO chunk/split/slice. What if chunksSizes less/more than bytes length?

    //TODO bytes clone, equality
    //TODO verifySignature
    //TODO verifyPublicKey
    //TODO verifyAddress

}
