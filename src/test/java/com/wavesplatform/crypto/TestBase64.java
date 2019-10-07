package com.wavesplatform.crypto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestBase64 {

    private byte[] source = "1, two, 𩸽 ?!".getBytes();
    private String expected = "MSwgdHdvLCDwqbi9ID8h";
    private String withPrefix = "base64:MSwgdHdvLCDwqbi9ID8h";

    @Test
    void encode() {
        assertThat(Base64.encode(source)).isEqualTo(expected);
        assertThat(Base64.encode(Bytes.of(source))).isEqualTo(expected);
    }

    @Test
    void decode() {
        assertThat(Base64.decode(expected).value()).isEqualTo(source);
        assertThat(Base64.decode(withPrefix).value()).isEqualTo(source);
    }

    @Test
    void optionalTail() {
        assertThat(Base64.decode("MSwgdHdvLCDwqbi9ID8hJg==")).isEqualTo(Bytes.of("1, two, 𩸽 ?!&".getBytes()));
        assertThat(Base64.decode("MSwgdHdvLCDwqbi9ID8hJg")).isEqualTo(Bytes.of("1, two, 𩸽 ?!&".getBytes()));
        assertThat(Base64.decode("base64:MSwgdHdvLCDwqbi9ID8hJg")).isEqualTo(Bytes.of("1, two, 𩸽 ?!&".getBytes()));
    }

    @Test
    void empty() {
        assertThat(Base64.encode(new byte[]{})).isEqualTo("");
        assertThat(Base64.decode("").value()).isEqualTo(new byte[]{});
    }

}
