package com.wavesplatform.crypto.base;

@SuppressWarnings("WeakerAccess")
public class Base64 {

    public static String encode(byte[] source) {
        return new Base64(source).encoded();
    }

    public static byte[] decode(String source) throws IllegalArgumentException {
        return new Base64(source).decoded();
    }

    private byte[] bytes;
    private String encoded;

    public Base64(byte[] source) {
        this.encoded = java.util.Base64.getEncoder().encodeToString(source);
        this.bytes = source.clone();
    }

    public Base64(String encodedString) {
        if (encodedString == null) throw new IllegalArgumentException("Base64 string can't be null");
        if (encodedString.startsWith("base64:")) encodedString = encodedString.substring(7);
        this.bytes = java.util.Base64.getDecoder().decode(encodedString);
        this.encoded = encodedString;
    }

    public String encoded() {
        return this.encoded;
    }

    public byte[] decoded() {
        return this.bytes;
    }

}
