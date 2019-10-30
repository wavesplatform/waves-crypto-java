package com.wavesplatform.crypto.base;

import com.wavesplatform.crypto.Bytes;

import java.util.Arrays;

/**
 * Base58 is used to represent byte arrays as a readable string.
 *
 * Most arrays of bytes in the project are encoded by Base58 algorithm with Bitcoin alphabet to make it ease human readable.
 */
@SuppressWarnings("WeakerAccess")
public class Base58 {

    public static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final char ENCODED_ZERO = ALPHABET[0];
    private static final int[] INDEXES = new int[128];

    static {
        Arrays.fill(INDEXES, -1);
        for (int i = 0; i < ALPHABET.length; i++) {
            INDEXES[ALPHABET[i]] = i;
        }
    }

    /**
     * Encodes the given bytes as a base58 string (no checksum is appended).
     *
     * @param source the bytes to encode
     * @return the base58-encoded string
     */
    public static String encode(byte[] source) {
        return new Base58(source).encoded();
    }

    /**
     * Decodes the given base58 string into the original data bytes.
     *
     * @param source the base58-encoded string to decode
     * @return the decoded data bytes
     * @throws IllegalArgumentException if the given string is not a valid base58 string
     */
    public static byte[] decode(String source) throws IllegalArgumentException {
        return new Base58(source).decoded();
    }

    private byte[] bytes;
    private String encoded;

    /**
     * Create Base58 from array of bytes.
     *
     * @param source the bytes to encode
     */
    public Base58(byte[] source) {
        byte[] input = source.clone();
        if (input.length == 0) {
            this.bytes = Bytes.empty();
            this.encoded = "";
        } else {
            // Count leading zeros.
            int zeros = 0;
            while (zeros < input.length && input[zeros] == 0) {
                ++zeros;
            }
            // Convert base-256 digits to base-58 digits (plus conversion to ASCII characters)
            input = Arrays.copyOf(input, input.length); // since we modify it in-place
            char[] encoded = new char[input.length * 2]; // upper bound
            int outputStart = encoded.length;
            for (int inputStart = zeros; inputStart < input.length; ) {
                encoded[--outputStart] = ALPHABET[divmod(input, inputStart, 256, 58)];
                if (input[inputStart] == 0) {
                    ++inputStart; // optimization - skip leading zeros
                }
            }
            // Preserve exactly as many leading encoded zeros in output as there were leading zeros in input.
            while (outputStart < encoded.length && encoded[outputStart] == ENCODED_ZERO) {
                ++outputStart;
            }
            while (--zeros >= 0) {
                encoded[--outputStart] = ENCODED_ZERO;
            }
            // create encoded string (including encoded leading zeros).
            this.bytes = input;
            this.encoded = new String(encoded, outputStart, encoded.length - outputStart);
        }
    }

    /**
     * Create Base58 from base58-encoded string.
     *
     * @param encodedString base58-encoded string
     * @throws IllegalArgumentException if the string is null or can't be parsed as base58 string
     */
    public Base58(String encodedString) throws IllegalArgumentException {
        if (encodedString == null) throw new IllegalArgumentException("Base58 string can't be null");
        if (encodedString.startsWith("base58:")) encodedString = encodedString.substring(7);
        if (encodedString.length() == 0) {
            this.bytes = Bytes.empty();
            this.encoded = encodedString;
        } else {
            // Convert the base58-encoded ASCII chars to a base58 byte sequence (base58 digits).
            byte[] input58 = new byte[encodedString.length()];
            for (int i = 0; i < encodedString.length(); ++i) {
                char c = encodedString.charAt(i);
                int digit = c < 128 ? INDEXES[c] : -1;
                if (digit < 0) {
                    throw new IllegalArgumentException("Illegal character \"" + c + "\" at position " + i);
                }
                input58[i] = (byte) digit;
            }
            // Count leading zeros.
            int zeros = 0;
            while (zeros < input58.length && input58[zeros] == 0) {
                ++zeros;
            }
            // Convert base-58 digits to base-256 digits.
            byte[] decoded = new byte[encodedString.length()];
            int outputStart = decoded.length;
            for (int inputStart = zeros; inputStart < input58.length; ) {
                decoded[--outputStart] = divmod(input58, inputStart, 58, 256);
                if (input58[inputStart] == 0) {
                    ++inputStart; // optimization - skip leading zeros
                }
            }
            // Ignore extra leading zeroes that were added during the calculation.
            while (outputStart < decoded.length && decoded[outputStart] == 0) {
                ++outputStart;
            }
            // Return decoded data (including original number of leading zeros).
            this.bytes = Arrays.copyOfRange(decoded, outputStart - zeros, decoded.length);
            this.encoded = encodedString;
        }
    }

    /**
     * Get encoded string of the bytes.
     *
     * @return base58-encoded string
     */
    public String encoded() {
        return this.encoded;
    }

    /**
     * Get original bytes.
     *
     * @return decoded array of bytes
     */
    public byte[] decoded() {
        return this.bytes;
    }

    /**
     * Divides a number, represented as an array of bytes each containing a single digit
     * in the specified base, by the given divisor. The given number is modified in-place
     * to contain the quotient, and the return value is the remainder.
     *
     * @param number     the number to divide
     * @param firstDigit the index within the array of the first non-zero digit
     *                   (this is used for optimization by skipping the leading zeros)
     * @param base       the base in which the number's digits are represented (up to 256)
     * @param divisor    the number to divide by (up to 256)
     * @return the remainder of the division operation
     */
    private static byte divmod(byte[] number, int firstDigit, int base, int divisor) {
        // this is just long division which accounts for the base of the input digits
        int remainder = 0;
        for (int i = firstDigit; i < number.length; i++) {
            int digit = (int) number[i] & 0xFF;
            int temp = remainder * base + digit;
            number[i] = (byte) (temp / divisor);
            remainder = temp % divisor;
        }
        return (byte) remainder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Base58 base58 = (Base58) o;
        return Arrays.equals(bytes, base58.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return this.encoded();
    }

}
