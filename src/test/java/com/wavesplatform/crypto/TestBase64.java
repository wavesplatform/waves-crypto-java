package com.wavesplatform.crypto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestBase64 {

    private byte[] source = "Подъём! 1, two".getBytes();
    private String expected = "0J/QvtC00YrRkdC8ISAxLCB0d28=";

    @Test
    void encode() {
        assertThat(Base64.encode(source)).isEqualTo(expected);
        assertThat(Base64.encode(Bytes.of(source))).isEqualTo(expected);
    }

    @Test
    void decode() {
        assertThat(Base64.decode(expected).value()).isEqualTo(source);
    }

    @Test
    void empty() {
        assertThat(Base64.encode(new byte[]{})).isEqualTo("");
        assertThat(Base64.decode("").value()).isEqualTo(new byte[]{});
    }

    @Test
    void decodeInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> Base64.decode(expected + "a"));
        assertThrows(IllegalArgumentException.class, () -> Base64.decode(expected + "gh"));
    }

}
