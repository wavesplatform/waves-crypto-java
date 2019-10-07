package com.wavesplatform.crypto;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class Bytes {

    //TODO lazy

    public static Bytes of(byte[] source) {
        return new Bytes(source);
    }

    public static Bytes of(String utf8String) {
        return new Bytes(utf8String);
    }

    public static Bytes of(long number) {
        return new Bytes(number);
    }

    private final byte[] value;

    public Bytes(byte[] bytes) {
        value = bytes;
    }

    public Bytes(String utf8String) {
        value = utf8String.getBytes(StandardCharsets.UTF_8);
    }

    public Bytes(long number) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(number);
        value = buffer.array();
    }

    public byte[] value() {
        return value;
    }

    public int length() {
        return value().length;
    }

    public String base16() {
        return Base16.encode(value());
    }

    public String base58() {
        return Base58.encode(value());
    }

    public String base64() {
        return Base64.encode(value());
    }

    public String utf8() {
        return new String(value(), StandardCharsets.UTF_8);
    }

    public boolean equals(byte[] bytes) {
        return Arrays.equals(value(), bytes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bytes bytes = (Bytes) o;
        return Arrays.equals(value(), bytes.value());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value());
    }

    @Override
    public String toString() {
        return base58();
    }

}
