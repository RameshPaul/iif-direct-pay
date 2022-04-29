package com.iif.directpay.repository;

import com.iif.directpay.domain.OrganizationSubscriptionPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrganizationSubscriptionPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationSubscriptionPlanRepository extends JpaRepository<OrganizationSubscriptionPlan, Long> {}
