package com.iif.directpay.web.rest;

import com.iif.directpay.domain.Organization;
import com.iif.directpay.repository.OrganizationRepository;
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
 * REST controller for managing {@link com.iif.directpay.domain.Organization}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrganizationResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationResource.class);

    private static final String ENTITY_NAME = "organization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationRepository organizationRepository;

    public OrganizationResource(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * {@code POST  /organizations} : Create a new organization.
     *
     * @param organization the organization to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organization, or with status {@code 400 (Bad Request)} if the organization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organizations")
    public ResponseEntity<Organization> createOrganization(@Valid @RequestBody Organization organization) throws URISyntaxException {
        log.debug("REST request to save Organization : {}", organization);
        if (organization.getId() != null) {
            throw new BadRequestAlertException("A new organization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Organization result = organizationRepository.save(organization);
        return ResponseEntity
            .created(new URI("/api/organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organizations/:id} : Updates an existing organization.
     *
     * @param id the id of the organization to save.
     * @param organization the organization to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organization,
     * or with status {@code 400 (Bad Request)} if the organization is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organization couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organizations/{id}")
    public ResponseEntity<Organization> updateOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Organization organization
    ) throws URISyntaxException {
        log.debug("REST request to update Organization : {}, {}", id, organization);
        if (organization.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organization.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Organization result = organizationRepository.save(organization);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organization.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /organizations/:id} : Partial updates given fields of an existing organization, field will ignore if it is null
     *
     * @param id the id of the organization to save.
     * @param organization the organization to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organization,
     * or with status {@code 400 (Bad Request)} if the organization is not valid,
     * or with status {@code 404 (Not Found)} if the organization is not found,
     * or with status {@code 500 (Internal Server Error)} if the organization couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organizations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Organization> partialUpdateOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Organization organization
    ) throws URISyntaxException {
        log.debug("REST request to partial update Organization partially : {}, {}", id, organization);
        if (organization.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organization.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Organization> result = organizationRepository
            .findById(organization.getId())
            .map(existingOrganization -> {
                if (organization.getName() != null) {
                    existingOrganization.setName(organization.getName());
                }
                if (organization.getAliasName() != null) {
                    existingOrganization.setAliasName(organization.getAliasName());
                }
                if (organization.getEmail() != null) {
                    existingOrganization.setEmail(organization.getEmail());
                }
                if (organization.getWebsite() != null) {
                    existingOrganization.setWebsite(organization.getWebsite());
                }
                if (organization.getPhone() != null) {
                    existingOrganization.setPhone(organization.getPhone());
                }
                if (organization.getMobile() != null) {
                    existingOrganization.setMobile(organization.getMobile());
                }
                if (organization.getRepresentativeName() != null) {
                    existingOrganization.setRepresentativeName(organization.getRepresentativeName());
                }
                if (organization.getRepresentativeEmail() != null) {
                    existingOrganization.setRepresentativeEmail(organization.getRepresentativeEmail());
                }
                if (organization.getRepresentativePhone() != null) {
                    existingOrganization.setRepresentativePhone(organization.getRepresentativePhone());
                }
                if (organization.getRegistrationNumber() != null) {
                    existingOrganization.setRegistrationNumber(organization.getRegistrationNumber());
                }
                if (organization.getOrganizationType() != null) {
                    existingOrganization.setOrganizationType(organization.getOrganizationType());
                }
                if (organization.getOrganizationTypeOther() != null) {
                    existingOrganization.setOrganizationTypeOther(organization.getOrganizationTypeOther());
                }
                if (organization.getOrganizationTaxCategory() != null) {
                    existingOrganization.setOrganizationTaxCategory(organization.getOrganizationTaxCategory());
                }
                if (organization.getOrganizationTaxCategoryOther() != null) {
                    existingOrganization.setOrganizationTaxCategoryOther(organization.getOrganizationTaxCategoryOther());
                }
                if (organization.getEstablishedDate() != null) {
                    existingOrganization.setEstablishedDate(organization.getEstablishedDate());
                }
                if (organization.getTotalEmployeesNumber() != null) {
                    existingOrganization.setTotalEmployeesNumber(organization.getTotalEmployeesNumber());
                }
                if (organization.getJoinDate() != null) {
                    existingOrganization.setJoinDate(organization.getJoinDate());
                }
                if (organization.getSubscriptionStartDate() != null) {
                    existingOrganization.setSubscriptionStartDate(organization.getSubscriptionStartDate());
                }
                if (organization.getSubscriptionEndDate() != null) {
                    existingOrganization.setSubscriptionEndDate(organization.getSubscriptionEndDate());
                }
                if (organization.getStatus() != null) {
                    existingOrganization.setStatus(organization.getStatus());
                }
                if (organization.getIsVerified() != null) {
                    existingOrganization.setIsVerified(organization.getIsVerified());
                }
                if (organization.getActivatedDate() != null) {
                    existingOrganization.setActivatedDate(organization.getActivatedDate());
                }
                if (organization.getCreatedDate() != null) {
                    existingOrganization.setCreatedDate(organization.getCreatedDate());
                }
                if (organization.getUpdatedDate() != null) {
                    existingOrganization.setUpdatedDate(organization.getUpdatedDate());
                }
                if (organization.getDeletedDate() != null) {
                    existingOrganization.setDeletedDate(organization.getDeletedDate());
                }
                if (organization.getSuspendedDate() != null) {
                    existingOrganization.setSuspendedDate(organization.getSuspendedDate());
                }

                return existingOrganization;
            })
            .map(organizationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organization.getId().toString())
        );
    }

    /**
     * {@code GET  /organizations} : get all the organizations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizations in body.
     */
    @GetMapping("/organizations")
    public List<Organization> getAllOrganizations() {
        log.debug("REST request to get all Organizations");
        return organizationRepository.findAll();
    }

    /**
     * {@code GET  /organizations/:id} : get the "id" organization.
     *
     * @param id the id of the organization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organizations/{id}")
    public ResponseEntity<Organization> getOrganization(@PathVariable Long id) {
        log.debug("REST request to get Organization : {}", id);
        Optional<Organization> organization = organizationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(organization);
    }

    /**
     * {@code DELETE  /organizations/:id} : delete the "id" organization.
     *
     * @param id the id of the organization to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organizations/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        log.debug("REST request to delete Organization : {}", id);
        organizationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
