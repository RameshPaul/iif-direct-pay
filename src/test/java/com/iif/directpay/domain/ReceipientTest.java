package com.iif.directpay.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iif.directpay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReceipientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Receipient.class);
        Receipient receipient1 = new Receipient();
        receipient1.setId(1L);
        Receipient receipient2 = new Receipient();
        receipient2.setId(receipient1.getId());
        assertThat(receipient1).isEqualTo(receipient2);
        receipient2.setId(2L);
        assertThat(receipient1).isNotEqualTo(receipient2);
        receipient1.setId(null);
        assertThat(receipient1).isNotEqualTo(receipient2);
    }
}
