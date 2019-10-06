package com.wavesplatform.crypto;

@SuppressWarnings("WeakerAccess")
public abstract class Base16 {

    public static String encode(Bytes source) {
        return encode(source.value());
    }

    public static String encode(byte[] source) {
        StringBuilder sb = new StringBuilder();
        for (byte b : source)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static Bytes decode(String source) throws IllegalArgumentException {
        if (source.length() % 2 == 1)
            throw new IllegalArgumentException("Invalid hexadecimal string \"" + source + "\"");

        byte[] bytes = new byte[source.length() / 2];
        for (int i = 0; i < source.length(); i += 2)
            bytes[i / 2] = hexToByte(source.substring(i, i + 2));
        return Bytes.of(bytes);
    }

    private static byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    private static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if (digit == -1)
            throw new IllegalArgumentException("Invalid hexadecimal character \"" + hexChar + "\"");
        return digit;
    }

}
