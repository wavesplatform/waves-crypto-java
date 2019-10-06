package com.wavesplatform.crypto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestBase16 {

    private byte[] source = "Подъём! 1, two".getBytes();
    private String expected = "d09fd0bed0b4d18ad191d0bc2120312c2074776f";

    @Test
    void encode() {
        assertThat(Base16.encode(source)).isEqualTo(expected);
        assertThat(Base16.encode(Bytes.of(source))).isEqualTo(expected);
    }

    @Test
    void decode() {
        assertThat(Base16.decode(expected).value()).isEqualTo(source);
    }

    @Test
    void empty() {
        assertThat(Base16.encode(new byte[]{})).isEqualTo("");
        assertThat(Base16.decode("").value()).isEqualTo(new byte[]{});
    }

    @Test
    void decodeInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> Base16.decode(expected + "a"));
        assertThrows(IllegalArgumentException.class, () -> Base16.decode(expected + "gh"));
    }

}
