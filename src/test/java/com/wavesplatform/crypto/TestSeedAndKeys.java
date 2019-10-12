package com.wavesplatform.crypto;

import org.junit.jupiter.api.Test;

import static com.wavesplatform.crypto.ChainId.TESTNET;
import static org.assertj.core.api.Assertions.assertThat;

class TestSeedAndKeys {

    private String phrase = "blame vacant regret company chase trip grant funny brisk innocent";
    private String privateKey = "3j2aMHzh9azPphzuW7aF3cmUefGEQC9dcWYXYCyoPcJg";
    private String publicKey = "8cj6YzvQPhSHGvnjupNTW8zrADTT8CMAAd2xTuej84gB";
    private String address = "3Ms87NGAAaPWZux233TB9A3TXps4LDkyJWN";

    @Test
    void seedAndKeys() {
        Seed seed = new Seed(phrase);

        assertThat(seed.utf8()).isEqualTo(phrase);
        assertThat(seed.nonce()).isEqualTo(0);
        assertThat(seed.keys().privateKey().base58()).isEqualTo(privateKey);
        assertThat(seed.keys().publicKey().base58()).isEqualTo(publicKey);
        assertThat(seed.keys().address(TESTNET).base58()).isEqualTo(address);
    }

}
