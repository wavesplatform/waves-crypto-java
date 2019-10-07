package com.wavesplatform.crypto;

public class Seed {

    public static Seed of(byte[] bytes) {
        return new Seed(Bytes.of(bytes));
    }

    private byte[] value;

    public Seed(Bytes bytes) {
        value = bytes.array(); //TODO copy???
    }

    public Bytes bytes() {
        return Bytes.of(value);
    }

    public String utf8() {
        return new String(value);
    }

    public String base58() {
        return Base58.encode(value);
    }

}
