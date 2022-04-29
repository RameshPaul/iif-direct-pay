package com.iif.directpay.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iif.directpay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationSubscriptionPlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationSubscriptionPlan.class);
        OrganizationSubscriptionPlan organizationSubscriptionPlan1 = new OrganizationSubscriptionPlan();
        organizationSubscriptionPlan1.setId(1L);
        OrganizationSubscriptionPlan organizationSubscriptionPlan2 = new OrganizationSubscriptionPlan();
        organizationSubscriptionPlan2.setId(organizationSubscriptionPlan1.getId());
        assertThat(organizationSubscriptionPlan1).isEqualTo(organizationSubscriptionPlan2);
        organizationSubscriptionPlan2.setId(2L);
        assertThat(organizationSubscriptionPlan1).isNotEqualTo(organizationSubscriptionPlan2);
        organizationSubscriptionPlan1.setId(null);
        assertThat(organizationSubscriptionPlan1).isNotEqualTo(organizationSubscriptionPlan2);
    }
}
