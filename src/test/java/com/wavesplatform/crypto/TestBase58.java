package com.wavesplatform.crypto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestBase58 {

    private byte[] source = "1, two, 𩸽 ?!".getBytes();
    private String expected = "2NnpkkWj3UX8HwNvRUADE";
    private String withPrefix = "base58:2NnpkkWj3UX8HwNvRUADE";

    @Test
    void encode() {
        assertThat(Base58.encode(source)).isEqualTo(expected);
        assertThat(Base58.encode(Bytes.of(source))).isEqualTo(expected);
    }

    @Test
    void decode() {
        assertThat(Base58.decode(expected).value()).isEqualTo(source);
        assertThat(Base58.decode(withPrefix).value()).isEqualTo(source);
    }

    @Test
    void empty() {
        assertThat(Base58.encode(new byte[]{})).isEqualTo("");
        assertThat(Base58.decode("").value()).isEqualTo(new byte[]{});
    }

    @Test
    void decodeInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> Base58.decode(expected + "0"));
    }

}
