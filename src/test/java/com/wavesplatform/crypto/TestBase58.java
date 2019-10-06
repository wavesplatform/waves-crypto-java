package com.wavesplatform.crypto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestBase58 {

    private byte[] source = "Подъём! 1, two".getBytes();
    private String expected = "3uaRTtZ3ajSxHJJMMN3VBuw1bR5Q";

    @Test
    void encode() {
        assertThat(Base58.encode(source)).isEqualTo(expected);
        assertThat(Base58.encode(Bytes.of(source))).isEqualTo(expected);
    }

    @Test
    void decode() {
        assertThat(Base58.decode(expected).value()).isEqualTo(source);
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
