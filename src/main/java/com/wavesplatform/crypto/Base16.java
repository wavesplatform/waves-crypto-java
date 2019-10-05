package com.wavesplatform.crypto;

public class Base16 {

    private byte[] value;
    private String encoded;

    public static String encode(byte[] source) {
        return new Base16(source).toString();
    }

    public static byte[] decode(String source) {
        return new Base16(source).toBytes();
    }

    public Base16(byte[] bytes) {
        bytes = value;
    }

    public Base16(String encoded) {
        //TODO check validity
        this.encoded = encoded;
    }

    public byte[] toBytes() {
        //TODO lazy
        return value;
    }

    @Override
    public String toString() {
        //TODO encoded lazy;
        return encoded;
    }

    //TODO hash, equals

}
