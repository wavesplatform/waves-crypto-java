package com.wavesplatform.crypto.account;

import com.wavesplatform.crypto.Hash;
import com.wavesplatform.crypto.base.Base58;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Address {

    public static Address from(PublicKey publicKey, byte chainId) {
        return new Address(publicKey, chainId);
    }

    public static Address as(String encoded, byte chainId) throws IllegalArgumentException {
        return new Address(encoded, chainId);
    }

    public static Address as(byte[] bytes, byte chainId) throws IllegalArgumentException {
        return new Address(bytes, chainId);
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

    public Address(String encoded, byte chainId) throws IllegalArgumentException {
        this.bytes = Base58.decode(encoded); //TODO validate
        this.encoded = encoded;
    }

    public Address(byte[] addressBytes, byte chainId) throws IllegalArgumentException {
        this.bytes = addressBytes; //TODO validate
        this.encoded = Base58.encode(this.bytes);
    }

    public byte[] bytes() {
        return this.bytes;
    }

    public String encoded() {
        return this.encoded;
    }

    //TODO alias?? Or this is not about crypto?
    //TODO static verifyAddress/isAddress

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
