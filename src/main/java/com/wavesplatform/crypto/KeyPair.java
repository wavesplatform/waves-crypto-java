package com.wavesplatform.crypto;

import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.java.curve_sigs;

import java.nio.ByteBuffer;
import java.util.Arrays;

@SuppressWarnings({"WeakerAccess", "unused"})
public class KeyPair {

    private static final Curve25519 cipher = Curve25519.getInstance(Curve25519.BEST);

    private final Bytes privateKey; //TODO Object? Bytes?
    private final Bytes publicKey; //TODO Object? Bytes?

    public KeyPair(Seed seed) {
        // account seed from seed & nonce
        ByteBuffer buf = ByteBuffer.allocate(seed.bytes().array().length + 4);
        buf.putInt(seed.nonce()).put(seed.bytes().array());
        Bytes accountSeed = Hash.secureHash(Bytes.of(buf.array()));

        // private key from account seed & chainId
        Bytes hashedSeed = Hash.sha256(accountSeed);
        byte[] prKey = Arrays.copyOf(hashedSeed.array(), 32);
        prKey[0] &= 248;
        prKey[31] &= 127;
        prKey[31] |= 64;
        privateKey = Bytes.of(prKey);

        // public key from private key
        byte[] pubKey = new byte[32];
        curve_sigs.curve25519_keygen(pubKey, prKey);
        publicKey = Bytes.of(pubKey);
    }

    public Bytes privateKey() {
        return privateKey; //TODO copy?
    }

    public Bytes publicKey() {
        return publicKey; // TODO copy?
    }

    public Bytes address(byte chainId) { //TODO Address(publicKey, chainId)? ChainId?
        ByteBuffer buf = ByteBuffer.allocate(26);
        Bytes hash = Hash.secureHash(publicKey);
        buf.put((byte) 1).put(chainId).put(hash.array(), 0, 20);
        byte[] checksum = Hash.secureHash(buf.array()).array();
        buf.put(checksum, 0, 4);
        return Bytes.of(buf.array());
    }

    public Bytes sign(Bytes message) {
        return Bytes.of(cipher.calculateSignature(privateKey.array(), message.array()));
    }

    public boolean verify(Bytes message, Bytes signature) {
        return cipher.verifySignature(publicKey.array(), message.array(), signature.array());
    }

}
