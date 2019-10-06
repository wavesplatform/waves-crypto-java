package com.wavesplatform.crypto;

public class Hash {

    public static Bytes sha256(byte[] source) {
        return new Bytes(source); //TODO
    }

    public static Bytes sha256(Bytes source) {
        return sha256(source);
    }

    public static Bytes blake(byte[] source) {
        return new Bytes(source); //TODO
    }

    public static Bytes blake(Bytes source) {
        return blake(source);
    }

    public static Bytes keccak(byte[] source) {
        return new Bytes(source); //TODO
    }

    public static Bytes keccak(Bytes source) {
        return keccak(source);
    }

}
