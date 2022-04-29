package com.iif.directpay.service.mapper;

import com.iif.directpay.domain.SubscriptionPlan;
import com.iif.directpay.service.dto.SubscriptionPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubscriptionPlan} and its DTO {@link SubscriptionPlanDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubscriptionPlanMapper extends EntityMapper<SubscriptionPlanDTO, SubscriptionPlan> {}
