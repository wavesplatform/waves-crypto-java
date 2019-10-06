package com.wavesplatform.crypto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestUtil {

    @Test
    void concat() {
        assertThat(Util.concat(new byte[]{1, 2}, new byte[]{3, 4})).isEqualTo(new byte[]{1, 2, 3, 4});
        assertThat(Util.concat(new byte[]{1, 2}, new byte[]{3, 4})).isNotEqualTo(new byte[]{3, 4, 1, 2});
        assertThat(Util.concat(Bytes.of(new byte[]{1, 2}), Bytes.of(new byte[]{3, 4})))
                .isEqualTo(Bytes.of(new byte[]{1, 2, 3, 4}));
    }

}
