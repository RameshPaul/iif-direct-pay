package com.iif.directpay.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iif.directpay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserDeviceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDevice.class);
        UserDevice userDevice1 = new UserDevice();
        userDevice1.setId(1L);
        UserDevice userDevice2 = new UserDevice();
        userDevice2.setId(userDevice1.getId());
        assertThat(userDevice1).isEqualTo(userDevice2);
        userDevice2.setId(2L);
        assertThat(userDevice1).isNotEqualTo(userDevice2);
        userDevice1.setId(null);
        assertThat(userDevice1).isNotEqualTo(userDevice2);
    }
}
