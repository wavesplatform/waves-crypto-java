package com.wavesplatform.crypto;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class Bytes {

    //TODO lazy
    //TODO random(length)

    public static Bytes of(byte[] source) {
        return new Bytes(source);
    }

    public static Bytes of(String utf8String) {
        return new Bytes(utf8String);
    }

    public static Bytes of(long number) {
        return new Bytes(number);
    }

    private final byte[] array;

    public Bytes(byte[] bytes) {
        array = bytes; //TODO copy???
    }

    public Bytes(String utf8String) {
        array = utf8String.getBytes(StandardCharsets.UTF_8);
    }

    public Bytes(long number) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(number);
        array = buffer.array();
    }

    public byte[] array() {
        return array;
    }

    public int length() {
        return array().length;
    }

    public String base16() {
        return Base16.encode(array());
    }

    public String base58() {
        return Base58.encode(array());
    }

    public String base64() {
        return Base64.encode(array());
    }

    public String utf8() {
        return new String(array(), StandardCharsets.UTF_8);
    }

    public boolean equals(byte[] bytes) {
        return Arrays.equals(array(), bytes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bytes bytes = (Bytes) o;
        return Arrays.equals(array(), bytes.array());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array());
    }

    @Override
    public String toString() {
        return base58();
    }

}
