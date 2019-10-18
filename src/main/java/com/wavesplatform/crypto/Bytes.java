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
        if (string == null) throw new IllegalArgumentException("String can't be null");
        return string.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] empty() {
        return new byte[0];
    }

    public static boolean empty(byte[]... arrays) {
        return Arrays.stream(arrays).allMatch(a -> a.length == 0);
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

    //TODO split/slice
    public static byte[][] chunk(byte[] source, int... chunkSizes) throws IllegalArgumentException {
        if (chunkSizes.length == 0)
            return new byte[][]{source.clone()};
        if (Arrays.stream(chunkSizes).anyMatch(s -> s < 0))
            throw new IllegalArgumentException("Chunk size can't be negative");
        int chunksTotalSize = Arrays.stream(chunkSizes).sum();
        if (source.length < chunksTotalSize)
            throw new IllegalArgumentException("Array length can't be less than sum of desired chunks. "
                    + "Array length: " + source.length + ", chunks total length: " + chunksTotalSize);

        byte[][] result = new byte[source.length == chunksTotalSize ? chunkSizes.length : chunkSizes.length + 1][];
        int mark = 0;
        for (int i = 0; i < result.length; i++)
            if (i < chunkSizes.length) {
                result[i] = Arrays.copyOfRange(source, mark, mark + chunkSizes[i]);
                mark += chunkSizes[i];
            } else result[i] = Arrays.copyOfRange(source, mark, source.length);
        return result;
    }

    public static boolean equal(byte[]... compared) {
        return compared.length < 2 || Arrays.stream(compared).allMatch(a -> Arrays.equals(a, compared[0]));
    }

}
