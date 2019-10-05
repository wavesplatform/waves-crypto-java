package com.wavesplatform.crypto;

public class Base58 {

    private byte[] value;
    private String encoded;

    public static String encode(byte[] source) {
        return new Base58(source).toString();
    }

    public static byte[] decode(String source) {
        return new Base58(source).toBytes();
    }

    public Base58(byte[] bytes) {
        bytes = value;
    }

    public Base58(String encoded) {
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
