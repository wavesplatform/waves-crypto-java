package com.wavesplatform.crypto;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("FieldCanBeLocal")
class TestBytes {

    private byte[] source1 = "test".getBytes();
    private byte[] source2 = "test".getBytes();
    private byte[] source3 = "some".getBytes();
    private String string = "1, two, 𩸽 ?!";
    private long number = 100500;

    @Test
    void length() {
        assertThat(Bytes.of(string.getBytes(StandardCharsets.UTF_8)).length()).isEqualTo(15);
    }

    @Test
    void equality() {
        assertThat(Bytes.of(source1)).isEqualTo(Bytes.of(source1));
        assertThat(Bytes.of(source1)).isEqualTo(Bytes.of(source2));
        assertThat(Bytes.of(source1).value()).isEqualTo(Bytes.of(source2).value());
        assertThat(Bytes.of(source1).equals(source1)).isTrue();
        assertThat(Bytes.of(source1).equals(source2)).isTrue();
        assertThat(Bytes.of(source1).equals(Bytes.of(source2).value())).isTrue();
        assertThat(Bytes.of(source1).equals(source3)).isFalse();
    }

    @Test
    void baseXX() {
        assertThat(Bytes.of(source1).base16()).isEqualTo(Base16.encode(source1));
        assertThat(Bytes.of(source1).base58()).isEqualTo(Base58.encode(source1));
        assertThat(Bytes.of(source1).base64()).isEqualTo(Base64.encode(source1));
    }

    @Test
    void utf8() {
        assertThat(Bytes.of(string).utf8()).isEqualTo(string);
    }

    @Test
    void number() {
        assertThat(Bytes.of(number).value()).isEqualTo(new byte[]{0, 0, 0, 0, 0, 1, -120, -108});
    }

}
