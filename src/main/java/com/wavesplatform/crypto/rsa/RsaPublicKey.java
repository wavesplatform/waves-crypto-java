package com.wavesplatform.crypto.rsa;

import com.wavesplatform.crypto.base.Base58;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class RsaPublicKey {

    public static RsaPublicKey from(RsaKeyPair keyPair) {
        return new RsaPublicKey(keyPair);
    }

    public static RsaPublicKey from(byte[] publicKeyBytes) {
        return new RsaPublicKey(publicKeyBytes);
    }

    private final BouncyCastleProvider bcp;
    private final PublicKey key;

    public RsaPublicKey(RsaKeyPair keyPair) {
        this(keyPair.publicKey());
    }

    public RsaPublicKey(byte[] bytes) throws IllegalArgumentException {
        this.bcp = new BouncyCastleProvider();
        X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            this.key = kf.generatePublic(ks);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public byte[] bytes() {
        return this.key.getEncoded();
    }

    public boolean isSignatureValid(HashAlg alg, byte[] message, byte[] proof) {
        try {
            Signature sig = Signature.getInstance(alg.value() + "withRSA", bcp);
            sig.initVerify(this.key);
            sig.update(message);
            return sig.verify(proof);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new Error(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RsaPublicKey publicKey = (RsaPublicKey) o;
        return Arrays.equals(key.getEncoded(), publicKey.key.getEncoded());
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return Base58.encode(this.bytes());
    }

}
