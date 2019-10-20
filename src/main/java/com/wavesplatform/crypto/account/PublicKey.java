package com.wavesplatform.crypto.account;

import com.wavesplatform.crypto.Hash;
import com.wavesplatform.crypto.base.Base58;
import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.java.curve_sigs;

import java.nio.ByteBuffer;
import java.util.Arrays;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PublicKey {

    public static final int LENGTH = 32;
    public static final int SIGNATURE_LENGTH = 64;

    public static PublicKey from(PrivateKey privateKey) {
        return new PublicKey(privateKey);
    }

    public static PublicKey as(String base58Encoded) throws IllegalArgumentException {
        return new PublicKey(base58Encoded);
    }

    public static PublicKey as(byte[] bytes) {
        return new PublicKey(bytes);
    }

    private static final Curve25519 cipher = Curve25519.getInstance(Curve25519.BEST);

    private final byte[] bytes;
    private String encoded;

    public PublicKey(PrivateKey privateKey) {
        this.bytes = new byte[LENGTH];
        curve_sigs.curve25519_keygen(this.bytes, privateKey.bytes());
    }

    public PublicKey(String base58Encoded) throws IllegalArgumentException {
        this(Base58.decode(base58Encoded));
    }

    public PublicKey(byte[] publicKeyBytes) throws IllegalArgumentException {
        if (publicKeyBytes.length != LENGTH) throw new IllegalArgumentException("Public key has wrong size in bytes. "
                + "Expected: " + LENGTH + ", actual: " + publicKeyBytes.length);
        this.bytes = publicKeyBytes.clone();
    }

    public byte[] bytes() {
        return this.bytes.clone();
    }

    public String base58() {
        if (this.encoded == null) this.encoded = Base58.encode(bytes);
        return this.encoded;
    }

    public Address address(byte chainId) {
        return Address.from(this, chainId);
    }

    public boolean isSignatureValid(byte[] message, byte[] signature) throws IllegalArgumentException {
        if (signature.length != SIGNATURE_LENGTH)
            throw new IllegalArgumentException("Signature has wrong size in bytes. "
                    + "Expected: " + SIGNATURE_LENGTH + ", actual: " + signature.length);
        return cipher.verifySignature(bytes, message, signature);
    }

    public boolean equals(byte[] anotherKey) {
        return Arrays.equals(this.bytes, anotherKey);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicKey publicKey = (PublicKey) o;
        return Arrays.equals(bytes, publicKey.bytes);
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
