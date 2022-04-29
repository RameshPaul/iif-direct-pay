package com.iif.directpay.web.rest;

import com.iif.directpay.domain.OrganizationSubscriptionPlan;
import com.iif.directpay.repository.OrganizationSubscriptionPlanRepository;
import com.iif.directpay.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.iif.directpay.domain.OrganizationSubscriptionPlan}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrganizationSubscriptionPlanResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationSubscriptionPlanResource.class);

    private static final String ENTITY_NAME = "organizationSubscriptionPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationSubscriptionPlanRepository organizationSubscriptionPlanRepository;

    public OrganizationSubscriptionPlanResource(OrganizationSubscriptionPlanRepository organizationSubscriptionPlanRepository) {
        this.organizationSubscriptionPlanRepository = organizationSubscriptionPlanRepository;
    }

    /**
     * {@code POST  /organization-subscription-plans} : Create a new organizationSubscriptionPlan.
     *
     * @param organizationSubscriptionPlan the organizationSubscriptionPlan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationSubscriptionPlan, or with status {@code 400 (Bad Request)} if the organizationSubscriptionPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organization-subscription-plans")
    public ResponseEntity<OrganizationSubscriptionPlan> createOrganizationSubscriptionPlan(
        @Valid @RequestBody OrganizationSubscriptionPlan organizationSubscriptionPlan
    ) throws URISyntaxException {
        log.debug("REST request to save OrganizationSubscriptionPlan : {}", organizationSubscriptionPlan);
        if (organizationSubscriptionPlan.getId() != null) {
            throw new BadRequestAlertException("A new organizationSubscriptionPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganizationSubscriptionPlan result = organizationSubscriptionPlanRepository.save(organizationSubscriptionPlan);
        return ResponseEntity
            .created(new URI("/api/organization-subscription-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organization-subscription-plans/:id} : Updates an existing organizationSubscriptionPlan.
     *
     * @param id the id of the organizationSubscriptionPlan to save.
     * @param organizationSubscriptionPlan the organizationSubscriptionPlan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationSubscriptionPlan,
     * or with status {@code 400 (Bad Request)} if the organizationSubscriptionPlan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationSubscriptionPlan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organization-subscription-plans/{id}")
    public ResponseEntity<OrganizationSubscriptionPlan> updateOrganizationSubscriptionPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrganizationSubscriptionPlan organizationSubscriptionPlan
    ) throws URISyntaxException {
        log.debug("REST request to update OrganizationSubscriptionPlan : {}, {}", id, organizationSubscriptionPlan);
        if (organizationSubscriptionPlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationSubscriptionPlan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationSubscriptionPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrganizationSubscriptionPlan result = organizationSubscriptionPlanRepository.save(organizationSubscriptionPlan);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationSubscriptionPlan.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /organization-subscription-plans/:id} : Partial updates given fields of an existing organizationSubscriptionPlan, field will ignore if it is null
     *
     * @param id the id of the organizationSubscriptionPlan to save.
     * @param organizationSubscriptionPlan the organizationSubscriptionPlan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationSubscriptionPlan,
     * or with status {@code 400 (Bad Request)} if the organizationSubscriptionPlan is not valid,
     * or with status {@code 404 (Not Found)} if the organizationSubscriptionPlan is not found,
     * or with status {@code 500 (Internal Server Error)} if the organizationSubscriptionPlan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organization-subscription-plans/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganizationSubscriptionPlan> partialUpdateOrganizationSubscriptionPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrganizationSubscriptionPlan organizationSubscriptionPlan
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrganizationSubscriptionPlan partially : {}, {}", id, organizationSubscriptionPlan);
        if (organizationSubscriptionPlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationSubscriptionPlan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationSubscriptionPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganizationSubscriptionPlan> result = organizationSubscriptionPlanRepository
            .findById(organizationSubscriptionPlan.getId())
            .map(existingOrganizationSubscriptionPlan -> {
                if (organizationSubscriptionPlan.getSubscriptionName() != null) {
                    existingOrganizationSubscriptionPlan.setSubscriptionName(organizationSubscriptionPlan.getSubscriptionName());
                }
                if (organizationSubscriptionPlan.getSubscriptionTitle() != null) {
                    existingOrganizationSubscriptionPlan.setSubscriptionTitle(organizationSubscriptionPlan.getSubscriptionTitle());
                }
                if (organizationSubscriptionPlan.getSubscriptionType() != null) {
                    existingOrganizationSubscriptionPlan.setSubscriptionType(organizationSubscriptionPlan.getSubscriptionType());
                }
                if (organizationSubscriptionPlan.getSubscriptionPrice() != null) {
                    existingOrganizationSubscriptionPlan.setSubscriptionPrice(organizationSubscriptionPlan.getSubscriptionPrice());
                }
                if (organizationSubscriptionPlan.getSubscriptionQuantity() != null) {
                    existingOrganizationSubscriptionPlan.setSubscriptionQuantity(organizationSubscriptionPlan.getSubscriptionQuantity());
                }
                if (organizationSubscriptionPlan.getSubscriptionPeriod() != null) {
                    existingOrganizationSubscriptionPlan.setSubscriptionPeriod(organizationSubscriptionPlan.getSubscriptionPeriod());
                }
                if (organizationSubscriptionPlan.getSubscriptionTerms() != null) {
                    existingOrganizationSubscriptionPlan.setSubscriptionTerms(organizationSubscriptionPlan.getSubscriptionTerms());
                }
                if (organizationSubscriptionPlan.getCouponCode() != null) {
                    existingOrganizationSubscriptionPlan.setCouponCode(organizationSubscriptionPlan.getCouponCode());
                }
                if (organizationSubscriptionPlan.getStartDate() != null) {
                    existingOrganizationSubscriptionPlan.setStartDate(organizationSubscriptionPlan.getStartDate());
                }
                if (organizationSubscriptionPlan.getEndDate() != null) {
                    existingOrganizationSubscriptionPlan.setEndDate(organizationSubscriptionPlan.getEndDate());
                }
                if (organizationSubscriptionPlan.getSuspendedDate() != null) {
                    existingOrganizationSubscriptionPlan.setSuspendedDate(organizationSubscriptionPlan.getSuspendedDate());
                }
                if (organizationSubscriptionPlan.getDeletedDate() != null) {
                    existingOrganizationSubscriptionPlan.setDeletedDate(organizationSubscriptionPlan.getDeletedDate());
                }
                if (organizationSubscriptionPlan.getStatus() != null) {
                    existingOrganizationSubscriptionPlan.setStatus(organizationSubscriptionPlan.getStatus());
                }
                if (organizationSubscriptionPlan.getCreatedDate() != null) {
                    existingOrganizationSubscriptionPlan.setCreatedDate(organizationSubscriptionPlan.getCreatedDate());
                }
                if (organizationSubscriptionPlan.getUpdatedDate() != null) {
                    existingOrganizationSubscriptionPlan.setUpdatedDate(organizationSubscriptionPlan.getUpdatedDate());
                }

                return existingOrganizationSubscriptionPlan;
            })
            .map(organizationSubscriptionPlanRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationSubscriptionPlan.getId().toString())
        );
    }

    /**
     * {@code GET  /organization-subscription-plans} : get all the organizationSubscriptionPlans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationSubscriptionPlans in body.
     */
    @GetMapping("/organization-subscription-plans")
    public List<OrganizationSubscriptionPlan> getAllOrganizationSubscriptionPlans() {
        log.debug("REST request to get all OrganizationSubscriptionPlans");
        return organizationSubscriptionPlanRepository.findAll();
    }

    /**
     * {@code GET  /organization-subscription-plans/:id} : get the "id" organizationSubscriptionPlan.
     *
     * @param id the id of the organizationSubscriptionPlan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationSubscriptionPlan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organization-subscription-plans/{id}")
    public ResponseEntity<OrganizationSubscriptionPlan> getOrganizationSubscriptionPlan(@PathVariable Long id) {
        log.debug("REST request to get OrganizationSubscriptionPlan : {}", id);
        Optional<OrganizationSubscriptionPlan> organizationSubscriptionPlan = organizationSubscriptionPlanRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(organizationSubscriptionPlan);
    }

    /**
     * {@code DELETE  /organization-subscription-plans/:id} : delete the "id" organizationSubscriptionPlan.
     *
     * @param id the id of the organizationSubscriptionPlan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organization-subscription-plans/{id}")
    public ResponseEntity<Void> deleteOrganizationSubscriptionPlan(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationSubscriptionPlan : {}", id);
        organizationSubscriptionPlanRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
