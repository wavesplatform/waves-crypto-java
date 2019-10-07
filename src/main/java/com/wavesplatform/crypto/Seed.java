package com.wavesplatform.crypto;

public class Seed {

    public static Seed random() {
        return random(0);
    }

    public static Seed random(int nonce) {
        return new Seed("", 0); //TODO
    }

    private Bytes bytes;
    private int nonce;

    public Seed(String phrase) {
        this(phrase, 0);
    }

    public Seed(String phrase, int nonce) {
        this(Bytes.of(phrase), nonce);
    }

    public Seed(Bytes phraseBytes) {
        this(phraseBytes, 0);
    }

    public Seed(Bytes phraseBytes, int nonce) {
        if (nonce < 0 || nonce > 65535) //TODO -+ or int with validation?
            throw new IllegalArgumentException("Nonce must be in [0..65535] but actual: " + nonce);
        bytes = phraseBytes;
        this.nonce = nonce;
    }

    public Bytes bytes() {
        return bytes;
    }

    public String utf8() {
        return bytes.utf8();
    }

    public String base58() {
        return bytes.base58();
    }

    //TODO keyPair() + lazy
    //TODO address(chainId)
    //TODO sign

}
