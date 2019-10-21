package com.wavesplatform.crypto.rsa;

import com.wavesplatform.crypto.base.Base58;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.KeyPair;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class RsaKeyPair {

    public static RsaKeyPair from(byte[] privateKeyBytes) {
        return new RsaKeyPair(privateKeyBytes);
    }

    public static RsaKeyPair random() {
        return new RsaKeyPair();
    }

    private final BouncyCastleProvider bcp;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public RsaKeyPair(byte[] privateKeyBytes) throws IllegalArgumentException {
        this.bcp = new BouncyCastleProvider();
        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(privateKeyBytes);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            this.privateKey = kf.generatePrivate(ks);
            RSAPrivateCrtKey privateCrtKey = (RSAPrivateCrtKey) this.privateKey;
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privateCrtKey.getModulus(), privateCrtKey.getPublicExponent());
            this.publicKey = kf.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public RsaKeyPair() {
        this.bcp = new BouncyCastleProvider();

        KeyPairGenerator gen;
        try {
            gen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new Error(e);
        }
        gen.initialize(2048, new SecureRandom());

        KeyPair keys = gen.generateKeyPair();
        this.privateKey = keys.getPrivate();
        this.publicKey = keys.getPublic();
    }

    public byte[] privateKey() {
        return this.privateKey.getEncoded();
    }

    public byte[] publicKey() {
        return this.publicKey.getEncoded();
    }

    public byte[] sign(HashAlg alg, byte[] source) {
        try {
            Signature sig = initJSignature(alg);
            sig.initSign(this.privateKey);
            sig.update(source);
            return sig.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new Error(e);
        }
    }

    public boolean isSignatureValid(HashAlg alg, byte[] message, byte[] proof) {
        try {
            Signature sig = initJSignature(alg);
            sig.initVerify(this.publicKey);
            sig.update(message);
            return sig.verify(proof);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new Error(e);
        }
    }

    private Signature initJSignature(HashAlg alg) throws NoSuchAlgorithmException {
        return Signature.getInstance(alg.value() + "withRSA", bcp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RsaKeyPair keyPair = (RsaKeyPair) o;
        return Arrays.equals(this.privateKey(), keyPair.privateKey()) &&
                Arrays.equals(this.publicKey(), keyPair.publicKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.privateKey(), this.publicKey());
    }

    @Override
    public String toString() {
        return "RsaKeyPair{" +
                "privateKey=" + Base58.encode(this.privateKey()) +
                ", publicKey=" + Base58.encode(this.publicKey()) +
                '}';
    }

}
