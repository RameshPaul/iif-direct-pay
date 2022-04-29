package com.iif.directpay.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iif.directpay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReceipientRecurringTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceipientRecurring.class);
        ReceipientRecurring receipientRecurring1 = new ReceipientRecurring();
        receipientRecurring1.setId(1L);
        ReceipientRecurring receipientRecurring2 = new ReceipientRecurring();
        receipientRecurring2.setId(receipientRecurring1.getId());
        assertThat(receipientRecurring1).isEqualTo(receipientRecurring2);
        receipientRecurring2.setId(2L);
        assertThat(receipientRecurring1).isNotEqualTo(receipientRecurring2);
        receipientRecurring1.setId(null);
        assertThat(receipientRecurring1).isNotEqualTo(receipientRecurring2);
    }
}
