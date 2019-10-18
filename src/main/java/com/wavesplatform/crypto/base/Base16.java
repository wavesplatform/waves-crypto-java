package com.wavesplatform.crypto.base;

@SuppressWarnings("WeakerAccess")
public class Base16 {

    public static String encode(byte[] source) {
        return new Base16(source).encoded();
    }

    public static byte[] decode(String encodedString) throws IllegalArgumentException {
        return new Base16(encodedString).decoded();
    }

    private byte[] bytes;
    private String encoded;

    public Base16(byte[] source) {
        byte[] input = source.clone();
        StringBuilder sb = new StringBuilder();
        for (byte b : input)
            sb.append(String.format("%02x", b));

        this.encoded = sb.toString();
        this.bytes = input;
    }

    public Base16(String encodedString) throws IllegalArgumentException {
        if (encodedString == null) throw new IllegalArgumentException("Base16 string can't be null");
        if (encodedString.startsWith("base16:")) encodedString = encodedString.substring(7);
        if (encodedString.length() % 2 == 1)
            throw new IllegalArgumentException("Invalid base16 string \"" + encodedString + "\"");

        byte[] bytes = new byte[encodedString.length() / 2];
        for (int i = 0; i < encodedString.length(); i += 2)
            bytes[i / 2] = hexToByte(encodedString.substring(i, i + 2));

        this.encoded = encodedString;
        this.bytes = bytes;
    }

    public byte[] decoded() {
        return this.bytes;
    }

    public String encoded() {
        return this.encoded;
    }

    @Override
    public String toString() {
        return encoded();
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
