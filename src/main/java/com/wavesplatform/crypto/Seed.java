package com.wavesplatform.crypto;

import java.nio.charset.StandardCharsets;

public class Seed {

    public static Seed random() {
        return random(0);
    }

    public static Seed random(int nonce) {
        return new Seed("", 0); //TODO
    }

    private byte[] value;
    private int nonce;

    public Seed(String phrase) {
        this(phrase, 0);
    }

    public Seed(String phrase, int nonce) {
        value = phrase.getBytes(StandardCharsets.UTF_8);
        if (nonce < 0 || nonce > 65535) //TODO -+ or int with validation?
            throw new IllegalArgumentException("Nonce must be in [0..65535] but actual: " + nonce);
        this.nonce = nonce;
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

    //TODO keyPair() + lazy
    //TODO address(chainId)
    //TODO sign

}
