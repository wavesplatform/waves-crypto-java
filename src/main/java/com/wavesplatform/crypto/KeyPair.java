package com.wavesplatform.crypto;

import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.java.curve_sigs;

import java.nio.ByteBuffer;
import java.util.Arrays;

@SuppressWarnings({"WeakerAccess", "unused"})
public class KeyPair {

    private static final Curve25519 cipher = Curve25519.getInstance(Curve25519.BEST);

    private final byte[] privateKey; //TODO Object?
    private final byte[] publicKey; //TODO Object?

    public KeyPair(Seed seed) {
        // account seed from seed & nonce
        byte[] accountSeed = Hash.secureHash(seed.bytesWithNonce());

        // private key from account seed & chainId
        byte[] hashedSeed = Hash.sha256(accountSeed);
        privateKey = Arrays.copyOf(hashedSeed, 32);
        privateKey[0] &= 248;
        privateKey[31] &= 127;
        privateKey[31] |= 64;

        // public key from private key
        byte[] pubKey = new byte[32];
        curve_sigs.curve25519_keygen(pubKey, privateKey);
        publicKey = pubKey;
    }

    public byte[] privateKey() {
        return privateKey; //TODO copy?
    }

    public byte[] publicKey() {
        return publicKey; // TODO copy?
    }

    public byte[] address(byte chainId) { //TODO Address(publicKey, chainId)? ChainId?
        ByteBuffer buf = ByteBuffer.allocate(26);
        byte[] hash = Hash.secureHash(publicKey);
        buf.put((byte) 1).put(chainId).put(hash, 0, 20);
        byte[] checksum = Hash.secureHash(Arrays.copyOfRange(buf.array(), 0 , 22));
        buf.put(checksum, 0, 4);
        return buf.array();
    }

    public byte[] sign(byte[] message) {
        return cipher.calculateSignature(privateKey, message);
    }

    public boolean verify(byte[] message, byte[] signature) {
        return cipher.verifySignature(publicKey, message, signature);
    }

}
