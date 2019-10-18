package com.wavesplatform.crypto;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.wavesplatform.crypto.HashAlg.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TestRsa {

    @Test
    void signatures() {
        Rsa rsa = new Rsa();
        byte[] source245b = (String.join("", Collections.nCopies(122, "ё")) + "b").getBytes();
        byte[] source32kb = (String.join("", Collections.nCopies(16382, "ё")) + "bbb").getBytes();

        byte [] noalgSign = rsa.sign(NOALG, source245b);
        byte [] md5Sign = rsa.sign(MD5, source32kb);
        byte [] sha1Sign = rsa.sign(SHA1, source32kb);
        byte [] sha224Sign = rsa.sign(SHA224, source32kb);
        byte [] sha256Sign = rsa.sign(SHA256, source32kb);
        byte [] sha384Sign = rsa.sign(SHA384, source32kb);
        byte [] sha512Sign = rsa.sign(SHA512, source32kb);
        byte [] sha3_224Sign = rsa.sign(SHA3_224, source32kb);
        byte [] sha3_256Sign = rsa.sign(SHA3_256, source32kb);
        byte [] sha3_384Sign = rsa.sign(SHA3_384, source32kb);
        byte [] sha3_512Sign = rsa.sign(SHA3_512, source32kb);

        assertAll(
                () -> assertThat(rsa.isSignatureValid(NOALG, source245b, noalgSign)).isTrue(),
                () -> assertThat(rsa.isSignatureValid(MD5, source32kb, md5Sign)).isTrue(),
                () -> assertThat(rsa.isSignatureValid(SHA1, source32kb, sha1Sign)).isTrue(),
                () -> assertThat(rsa.isSignatureValid(SHA224, source32kb, sha224Sign)).isTrue(),
                () -> assertThat(rsa.isSignatureValid(SHA256, source32kb, sha256Sign)).isTrue(),
                () -> assertThat(rsa.isSignatureValid(SHA384, source32kb, sha384Sign)).isTrue(),
                () -> assertThat(rsa.isSignatureValid(SHA512, source32kb, sha512Sign)).isTrue(),
                () -> assertThat(rsa.isSignatureValid(SHA3_224, source32kb, sha3_224Sign)).isTrue(),
                () -> assertThat(rsa.isSignatureValid(SHA3_256, source32kb, sha3_256Sign)).isTrue(),
                () -> assertThat(rsa.isSignatureValid(SHA3_384, source32kb, sha3_384Sign)).isTrue(),
                () -> assertThat(rsa.isSignatureValid(SHA3_512, source32kb, sha3_512Sign)).isTrue(),

                () -> assertThat(rsa.isSignatureValid(NOALG, source245b, sha1Sign)).isFalse(),
                () -> assertThat(rsa.isSignatureValid(MD5, source32kb, noalgSign)).isFalse(),
                () -> assertThat(rsa.isSignatureValid(SHA1, source32kb, md5Sign)).isFalse(),
                () -> assertThat(rsa.isSignatureValid(SHA224, source32kb, sha512Sign)).isFalse(),
                () -> assertThat(rsa.isSignatureValid(SHA256, source32kb, sha224Sign)).isFalse(),
                () -> assertThat(rsa.isSignatureValid(SHA384, source32kb, sha256Sign)).isFalse(),
                () -> assertThat(rsa.isSignatureValid(SHA512, source32kb, sha384Sign)).isFalse(),
                () -> assertThat(rsa.isSignatureValid(SHA3_224, source32kb, sha3_512Sign)).isFalse(),
                () -> assertThat(rsa.isSignatureValid(SHA3_256, source32kb, sha3_224Sign)).isFalse(),
                () -> assertThat(rsa.isSignatureValid(SHA3_384, source32kb, sha3_256Sign)).isFalse(),
                () -> assertThat(rsa.isSignatureValid(SHA3_512, source32kb, sha3_384Sign)).isFalse()
        );
    }

}
