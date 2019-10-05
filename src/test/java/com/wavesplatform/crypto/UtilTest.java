package com.wavesplatform.crypto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UtilTest {

    @Test
    void concat() {
        assertThat(Util.concat(new byte[]{1, 2}, new byte[]{3, 4})).isEqualTo(new byte[]{1, 2, 3, 4});
    }

}
