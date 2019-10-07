package com.wavesplatform.crypto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestBase16 {

    private byte[] source = "1, two, 𩸽 ?!".getBytes();
    private String expected = "312c2074776f2c20f0a9b8bd203f21";
    private String withPrefix = "base16:312c2074776f2c20f0a9b8bd203f21";

    @Test
    void encode() {
        assertThat(Base16.encode(source)).isEqualTo(expected);
        assertThat(Base16.encode(Bytes.of(source))).isEqualTo(expected);
    }

    @Test
    void decode() {
        assertThat(Base16.decode(expected).array()).isEqualTo(source);
        assertThat(Base16.decode(withPrefix).array()).isEqualTo(source);
    }

    @Test
    void empty() {
        assertThat(Base16.encode(new byte[]{})).isEqualTo("");
        assertThat(Base16.decode("").array()).isEqualTo(new byte[]{});
    }

    @Test
    void decodeInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> Base16.decode(expected + "a"));
        assertThrows(IllegalArgumentException.class, () -> Base16.decode(expected + "gh"));
    }

}
