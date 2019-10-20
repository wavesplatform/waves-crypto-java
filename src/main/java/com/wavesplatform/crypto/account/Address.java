package com.wavesplatform.crypto.account;

import com.wavesplatform.crypto.Bytes;
import com.wavesplatform.crypto.Hash;
import com.wavesplatform.crypto.base.Base58;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Address {

    public static Address from(PublicKey publicKey, byte chainId) {
        return new Address(publicKey, chainId);
    }

    public static Address as(Base58 encoded) throws IllegalArgumentException {
        return new Address(encoded);
    }

    public static Address as(byte[] bytes) throws IllegalArgumentException {
        return new Address(bytes);
    }

    public static boolean isCorrect(Base58 encoded, byte chainId) {
        return isCorrect(encoded.decoded(), chainId);
    }

    public static boolean isCorrect(Base58 encoded) {
        return isCorrect(encoded.decoded());
    }

    public static boolean isCorrect(byte[] addressBytes, byte chainId) {
        return isCorrect(addressBytes) && addressBytes[1] == chainId;
    }

    public static boolean isCorrect(byte[] addressBytes) {
        try {
            new Address(addressBytes);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    private byte[] bytes;
    private String encoded;

    public Address(PublicKey publicKey, byte chainId) {
        ByteBuffer buf = ByteBuffer.allocate(26);
        byte[] hash = Hash.secureHash(publicKey.bytes());
        buf.put((byte) 1).put(chainId).put(hash, 0, 20);
        byte[] checksum = Hash.secureHash(Arrays.copyOfRange(buf.array(), 0 , 22));
        buf.put(checksum, 0, 4);

        this.bytes = buf.array();
        this.encoded = Base58.encode(this.bytes);
    }

    public Address(Base58 encoded) throws IllegalArgumentException {
        this(encoded.decoded());
    }

    public Address(byte[] addressBytes) throws IllegalArgumentException {
        if (addressBytes.length != 26)
            throw new IllegalArgumentException("Address has wrong length. " +
                    "Expected: " + 26 + " bytes, actual: " + addressBytes.length + " bytes");
        if (addressBytes[0] != 1)
            throw new IllegalArgumentException("Address has unknown address version " + addressBytes[0]);

        byte[][] parts = Bytes.chunk(addressBytes, 22, 4);
        byte[] checkSum = Hash.secureHash(parts[0]);
        if (!Bytes.equal(parts[1], checkSum))
            throw new IllegalArgumentException("Address has wrong checksum");

        this.bytes = addressBytes;
        this.encoded = Base58.encode(this.bytes);
    }

    public byte chainId() {
        return this.bytes[1];
    }

    public byte[] bytes() {
        return this.bytes;
    }

    public String encoded() {
        return this.encoded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Arrays.equals(bytes, address.bytes);
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
