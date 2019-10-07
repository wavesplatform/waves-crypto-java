package com.wavesplatform.crypto;

@SuppressWarnings("WeakerAccess")
public abstract class Base64 {

    public static String encode(Bytes source) {
        return encode(source.value());
    }

    public static String encode(byte[] source) {
        return java.util.Base64.getEncoder().encodeToString(source);
    }

    public static Bytes decode(String source) throws IllegalArgumentException {
        if (source.startsWith("base64:")) source = source.substring(7);
        return Bytes.of(java.util.Base64.getDecoder().decode(source));
    }

}
