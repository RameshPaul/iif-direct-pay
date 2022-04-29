package com.iif.directpay.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iif.directpay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserLoginTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserLogin.class);
        UserLogin userLogin1 = new UserLogin();
        userLogin1.setId(1L);
        UserLogin userLogin2 = new UserLogin();
        userLogin2.setId(userLogin1.getId());
        assertThat(userLogin1).isEqualTo(userLogin2);
        userLogin2.setId(2L);
        assertThat(userLogin1).isNotEqualTo(userLogin2);
        userLogin1.setId(null);
        assertThat(userLogin1).isNotEqualTo(userLogin2);
    }
}
