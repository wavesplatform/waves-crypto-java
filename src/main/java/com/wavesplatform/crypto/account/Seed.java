package com.wavesplatform.crypto.account;

import com.wavesplatform.crypto.Bytes;
import com.wavesplatform.crypto.base.Base58;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Seed {

    public static Seed from(String phrase) throws IllegalArgumentException {
        return new Seed(phrase);
    }

    public static Seed from(String phrase, int nonce) throws IllegalArgumentException {
        return new Seed(phrase, nonce);
    }

    public static Seed from(byte[] phraseBytes) {
        return new Seed(phraseBytes);
    }

    public static Seed from(byte[] phraseBytes, int nonce) {
        return new Seed(phraseBytes, nonce);
    }

    public static Seed random() {
        return random(0);
    }

    public static Seed random(int nonce) {
        return randomBytes(nonce);
    }

    public static Seed randomBytes() {
        return randomBytes(0);
    }

    public static Seed randomBytes(int nonce) {
        byte[] bytes = new byte[120];
        try {
            SecureRandom.getInstanceStrong().nextBytes(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to get random number generator", e);
        }
        return Seed.from(bytes, 0);
    }

    private final byte[] bytes;
    private int nonce;
    private String encoded;
    private PrivateKey privateKey;

    public Seed(String phrase) throws IllegalArgumentException {
        this(phrase, 0);
    }

    public Seed(String phrase, int nonce) throws IllegalArgumentException {
        this(Bytes.fromUtf8(phrase), nonce);
    }

    public Seed(byte[] phraseBytes) {
        this(phraseBytes, 0);
    }

    public Seed(byte[] phraseBytes, int nonce) {
        this.bytes = phraseBytes.clone();
        this.nonce = nonce;
    }

    public byte[] bytes() {
        return bytes.clone();
    }

    public int nonce() {
        return nonce;
    }

    public byte[] bytesWithNonce() {
        return Bytes.concat(Bytes.fromInt(nonce()), bytes());
    }

    public String phrase() {
        return Bytes.toUtf8(bytes);
    }

    public String base58Phrase() {
        if (this.encoded == null) this.encoded = Base58.encode(bytes);
        return this.encoded;
    }

    public PrivateKey privateKey() {
        if (this.privateKey == null) this.privateKey = PrivateKey.from(this);
        return this.privateKey;
    }

    public PublicKey publicKey() {
        return this.privateKey().publicKey();
    }

    public Address address(byte chainId) {
        return this.privateKey().address(chainId);
    }

    public byte[] sign(byte[] message) {
        return this.privateKey().sign(message);
    }

    public boolean isSignatureValid(byte[] message, byte[] signature) throws IllegalArgumentException {
        return this.publicKey().isSignatureValid(message, signature);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seed seed = (Seed) o;
        return nonce == seed.nonce &&
                Arrays.equals(bytes, seed.bytes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nonce);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public String toString() {
        return this.base58Phrase();
    }

}
