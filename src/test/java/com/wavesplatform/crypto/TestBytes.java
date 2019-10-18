package com.wavesplatform.crypto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestBytes {

    @Test
    void concat() {
        assertThat(Bytes.concat(new byte[]{1, 2}, new byte[]{3, 4})).isEqualTo(new byte[]{1, 2, 3, 4});
        assertThat(Bytes.concat(new byte[]{1, 2}, new byte[]{3, 4})).isNotEqualTo(new byte[]{3, 4, 1, 2});
    }

}
