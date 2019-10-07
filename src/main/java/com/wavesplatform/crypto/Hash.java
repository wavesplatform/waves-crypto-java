package com.wavesplatform.crypto;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.Blake2bDigest;
import org.bouncycastle.crypto.digests.KeccakDigest;
import org.bouncycastle.crypto.digests.SHA256Digest;

@SuppressWarnings({"WeakerAccess", "unused", "SameParameterValue"})
public class Hash {

    private static final ThreadLocal<Digest> BLAKE2B256 = new ThreadLocal<>();
    private static final ThreadLocal<Digest> KECCAK256 = new ThreadLocal<>();
    private static final ThreadLocal<Digest> SHA256 = new ThreadLocal<>();

    public static Bytes sha256(byte[] source) {
        return Bytes.of(hash(source, 0, source.length, Hash.SHA256));
    }

    public static Bytes sha256(Bytes source) {
        return sha256(source.array());
    }

    public static Bytes blake(byte[] source) {
        return Bytes.of(hash(source, 0, source.length, Hash.BLAKE2B256));
    }

    public static Bytes blake(Bytes source) {
        return blake(source.array());
    }

    public static Bytes keccak(byte[] source) {
        return Bytes.of(hash(source, 0, source.length, Hash.KECCAK256));
    }

    public static Bytes keccak(Bytes source) {
        return keccak(source.array());
    }

    public static Bytes secureHash(byte[] source) {
        return keccak(blake(source));
    }

    public static Bytes secureHash(Bytes source) {
        return secureHash(source.array());
    }

    private static Digest digest(ThreadLocal<Digest> cache) {
        Digest digest = cache.get();
        if (digest == null) {
            if (cache == BLAKE2B256) {
                digest = new Blake2bDigest(256);
            } else if (cache == KECCAK256) {
                digest = new KeccakDigest(256);
            } else if (cache == SHA256) {
                digest = new SHA256Digest();
            }
            cache.set(digest);
        }
        return digest;
    }

    private static byte[] hash(byte[] message, int ofs, int len, ThreadLocal<Digest> alg) {
        final Digest digest = digest(alg);
        final byte[] result = new byte[digest.getDigestSize()];
        digest.update(message, ofs, len);
        digest.doFinal(result, 0);
        return result;
    }

}
