package com.wavesplatform.crypto;

@SuppressWarnings("unused")
public class Seed {

    public static Seed random() {
        return random(0);
    }

    public static Seed random(int nonce) {
        return new Seed("", 0); //TODO
    }

    //TODO wallet encrypt/decrypt + secure memory for seed/wallet/privateKey

    private byte[] bytes;
    private int nonce;

    public Seed(String phrase) {
        this(phrase, 0);
    }

    public Seed(String phrase, int nonce) {
        this(phrase.getBytes(), nonce);
    }

    public Seed(byte[] phraseBytes) {
        this(phraseBytes, 0);
    }

    public Seed(byte[] phraseBytes, int nonce) {
        bytes = phraseBytes;
        this.nonce = nonce;
    }

    public int nonce() {
        return nonce;
    }

    public byte[] bytes() {
        return bytes;
    } // TODO copy?

    public byte[] bytesWithNonce() {
        return Bytes.concat(Bytes.fromInt(nonce()), bytes());
    }

    public String utf8() {
        return Bytes.toUtf8(bytes);
    }

    public String base58() {
        return Base58.encode(bytes);
    }

    //TODO keys(nonce) from array of nonces?
    public KeyPair keys() {
        return new KeyPair(this); //TODO lazy + privateKey() + publicKey()
    }

    public byte[] address(byte chainId) { //TODO Address, ChainId
        return keys().address(chainId);
    }

    public byte[] sign(byte[] message) {
        return keys().sign(message);
    }

    //TODO toString, ...

}
