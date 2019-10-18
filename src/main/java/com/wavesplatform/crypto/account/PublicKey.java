package com.wavesplatform.crypto.account;

import com.wavesplatform.crypto.Hash;
import com.wavesplatform.crypto.base.Base58;
import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.java.curve_sigs;

import java.nio.ByteBuffer;
import java.util.Arrays;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PublicKey {

    public static PublicKey from(PrivateKey privateKey) {
        return new PublicKey(privateKey);
    }

    public static PublicKey from(String base58Encoded) throws IllegalArgumentException {
        return new PublicKey(base58Encoded);
    }

    public static PublicKey from(byte[] bytes) {
        return new PublicKey(bytes);
    }

    private static final Curve25519 cipher = Curve25519.getInstance(Curve25519.BEST);

    private final byte[] bytes;
    private String encoded;

    public PublicKey(PrivateKey privateKey) {
        bytes = new byte[32];
        curve_sigs.curve25519_keygen(bytes, privateKey.bytes());
    }

    public PublicKey(String base58Encoded) throws IllegalArgumentException {
        this(Base58.decode(base58Encoded));
    }

    public PublicKey(byte[] bytes) throws IllegalArgumentException {
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

    public byte[] address(byte chainId) { //TODO Address(publicKey, chainId)? ChainId?
        ByteBuffer buf = ByteBuffer.allocate(26);
        byte[] hash = Hash.secureHash(bytes);
        buf.put((byte) 1).put(chainId).put(hash, 0, 20);
        byte[] checksum = Hash.secureHash(Arrays.copyOfRange(buf.array(), 0 , 22));
        buf.put(checksum, 0, 4);
        return buf.array();
    }

    public boolean isSignatureValid(byte[] message, byte[] signature) {
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
