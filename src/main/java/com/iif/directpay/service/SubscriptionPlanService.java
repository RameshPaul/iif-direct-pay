package com.iif.directpay.service;

import com.iif.directpay.domain.SubscriptionPlan;
import com.iif.directpay.repository.SubscriptionPlanRepository;
import com.iif.directpay.service.dto.SubscriptionPlanDTO;
import com.iif.directpay.service.mapper.SubscriptionPlanMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubscriptionPlan}.
 */
@Service
@Transactional
public class SubscriptionPlanService {

    private final Logger log = LoggerFactory.getLogger(SubscriptionPlanService.class);

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    private final SubscriptionPlanMapper subscriptionPlanMapper;

    public SubscriptionPlanService(SubscriptionPlanRepository subscriptionPlanRepository, SubscriptionPlanMapper subscriptionPlanMapper) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.subscriptionPlanMapper = subscriptionPlanMapper;
    }

    /**
     * Save a subscriptionPlan.
     *
     * @param subscriptionPlanDTO the entity to save.
     * @return the persisted entity.
     */
    public SubscriptionPlanDTO save(SubscriptionPlanDTO subscriptionPlanDTO) {
        log.debug("Request to save SubscriptionPlan : {}", subscriptionPlanDTO);
        SubscriptionPlan subscriptionPlan = subscriptionPlanMapper.toEntity(subscriptionPlanDTO);
        subscriptionPlan = subscriptionPlanRepository.save(subscriptionPlan);
        return subscriptionPlanMapper.toDto(subscriptionPlan);
    }

    /**
     * Update a subscriptionPlan.
     *
     * @param subscriptionPlanDTO the entity to save.
     * @return the persisted entity.
     */
    public SubscriptionPlanDTO update(SubscriptionPlanDTO subscriptionPlanDTO) {
        log.debug("Request to save SubscriptionPlan : {}", subscriptionPlanDTO);
        SubscriptionPlan subscriptionPlan = subscriptionPlanMapper.toEntity(subscriptionPlanDTO);
        subscriptionPlan = subscriptionPlanRepository.save(subscriptionPlan);
        return subscriptionPlanMapper.toDto(subscriptionPlan);
    }

    /**
     * Partially update a subscriptionPlan.
     *
     * @param subscriptionPlanDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubscriptionPlanDTO> partialUpdate(SubscriptionPlanDTO subscriptionPlanDTO) {
        log.debug("Request to partially update SubscriptionPlan : {}", subscriptionPlanDTO);

        return subscriptionPlanRepository
            .findById(subscriptionPlanDTO.getId())
            .map(existingSubscriptionPlan -> {
                subscriptionPlanMapper.partialUpdate(existingSubscriptionPlan, subscriptionPlanDTO);

                return existingSubscriptionPlan;
            })
            .map(subscriptionPlanRepository::save)
            .map(subscriptionPlanMapper::toDto);
    }

    /**
     * Get all the subscriptionPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SubscriptionPlanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubscriptionPlans");
        return subscriptionPlanRepository.findAll(pageable).map(subscriptionPlanMapper::toDto);
    }

    /**
     * Get one subscriptionPlan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubscriptionPlanDTO> findOne(Long id) {
        log.debug("Request to get SubscriptionPlan : {}", id);
        return subscriptionPlanRepository.findById(id).map(subscriptionPlanMapper::toDto);
    }

    /**
     * Delete the subscriptionPlan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SubscriptionPlan : {}", id);
        subscriptionPlanRepository.deleteById(id);
    }
}
