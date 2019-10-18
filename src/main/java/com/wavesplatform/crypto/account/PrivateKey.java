package com.wavesplatform.crypto.account;

import com.wavesplatform.crypto.Hash;
import com.wavesplatform.crypto.base.Base58;
import org.whispersystems.curve25519.Curve25519;

import java.util.Arrays;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PrivateKey {

    public static PrivateKey from(Seed seed) {
        return new PrivateKey(seed);
    }

    public static PrivateKey from(String base58Encoded) throws IllegalArgumentException {
        return new PrivateKey(base58Encoded);
    }

    public static PrivateKey from(byte[] bytes) {
        return new PrivateKey(bytes);
    }

    private static final Curve25519 cipher = Curve25519.getInstance(Curve25519.BEST);

    private final byte[] bytes;
    private String encoded;
    private PublicKey publicKey;

    public PrivateKey(Seed seed) {
        // account seed from seed & nonce
        byte[] accountSeed = Hash.secureHash(seed.bytesWithNonce());

        // private key from account seed
        byte[] hashedSeed = Hash.sha256(accountSeed);
        this.bytes = Arrays.copyOf(hashedSeed, 32);
        this.bytes[0] &= 248;
        this.bytes[31] &= 127;
        this.bytes[31] |= 64;
    }

    public PrivateKey(String base58Encoded) throws IllegalArgumentException {
        this(Base58.decode(base58Encoded));
    }

    public PrivateKey(byte[] bytes) throws IllegalArgumentException {
        //TODO validate
        this.bytes = bytes;
    }

    public byte[] bytes() {
        return this.bytes.clone();
    }

    public String base58() {
        if (this.encoded == null) this.encoded = Base58.encode(bytes);
        return this.encoded;
    }

    public PublicKey publicKey() {
        if (this.publicKey == null) this.publicKey = PublicKey.from(this);
        return this.publicKey;
    }

    public byte[] address(byte chainId) { //TODO ChainId?
        return this.publicKey().address(chainId);
    }

    public byte[] sign(byte[] message) {
        return cipher.calculateSignature(this.bytes, message);
    }

    public boolean isSignatureValid(byte[] message, byte[] signature) {
        return this.publicKey().isSignatureValid(message, signature);
    }

    public boolean equals(byte[] anotherKey) {
        return Arrays.equals(this.bytes, anotherKey);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivateKey that = (PrivateKey) o;
        return Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return this.base58();
    }

}
