package com.iif.directpay.web.rest;

import com.iif.directpay.domain.Receipient;
import com.iif.directpay.repository.ReceipientRepository;
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
 * REST controller for managing {@link com.iif.directpay.domain.Receipient}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReceipientResource {

    private final Logger log = LoggerFactory.getLogger(ReceipientResource.class);

    private static final String ENTITY_NAME = "receipient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReceipientRepository receipientRepository;

    public ReceipientResource(ReceipientRepository receipientRepository) {
        this.receipientRepository = receipientRepository;
    }

    /**
     * {@code POST  /receipients} : Create a new receipient.
     *
     * @param receipient the receipient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receipient, or with status {@code 400 (Bad Request)} if the receipient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/receipients")
    public ResponseEntity<Receipient> createReceipient(@Valid @RequestBody Receipient receipient) throws URISyntaxException {
        log.debug("REST request to save Receipient : {}", receipient);
        if (receipient.getId() != null) {
            throw new BadRequestAlertException("A new receipient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Receipient result = receipientRepository.save(receipient);
        return ResponseEntity
            .created(new URI("/api/receipients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /receipients/:id} : Updates an existing receipient.
     *
     * @param id the id of the receipient to save.
     * @param receipient the receipient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receipient,
     * or with status {@code 400 (Bad Request)} if the receipient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the receipient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/receipients/{id}")
    public ResponseEntity<Receipient> updateReceipient(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Receipient receipient
    ) throws URISyntaxException {
        log.debug("REST request to update Receipient : {}, {}", id, receipient);
        if (receipient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receipient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receipientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Receipient result = receipientRepository.save(receipient);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receipient.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /receipients/:id} : Partial updates given fields of an existing receipient, field will ignore if it is null
     *
     * @param id the id of the receipient to save.
     * @param receipient the receipient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receipient,
     * or with status {@code 400 (Bad Request)} if the receipient is not valid,
     * or with status {@code 404 (Not Found)} if the receipient is not found,
     * or with status {@code 500 (Internal Server Error)} if the receipient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/receipients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Receipient> partialUpdateReceipient(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Receipient receipient
    ) throws URISyntaxException {
        log.debug("REST request to partial update Receipient partially : {}, {}", id, receipient);
        if (receipient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receipient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receipientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Receipient> result = receipientRepository
            .findById(receipient.getId())
            .map(existingReceipient -> {
                if (receipient.getIsRecurring() != null) {
                    existingReceipient.setIsRecurring(receipient.getIsRecurring());
                }
                if (receipient.getRecurringType() != null) {
                    existingReceipient.setRecurringType(receipient.getRecurringType());
                }
                if (receipient.getRecurringStartDate() != null) {
                    existingReceipient.setRecurringStartDate(receipient.getRecurringStartDate());
                }
                if (receipient.getRecurringEndDate() != null) {
                    existingReceipient.setRecurringEndDate(receipient.getRecurringEndDate());
                }
                if (receipient.getEnableReminder() != null) {
                    existingReceipient.setEnableReminder(receipient.getEnableReminder());
                }
                if (receipient.getStartDate() != null) {
                    existingReceipient.setStartDate(receipient.getStartDate());
                }
                if (receipient.getEndDate() != null) {
                    existingReceipient.setEndDate(receipient.getEndDate());
                }
                if (receipient.getIsAutoPay() != null) {
                    existingReceipient.setIsAutoPay(receipient.getIsAutoPay());
                }
                if (receipient.getAmountRequisite() != null) {
                    existingReceipient.setAmountRequisite(receipient.getAmountRequisite());
                }
                if (receipient.getStatus() != null) {
                    existingReceipient.setStatus(receipient.getStatus());
                }
                if (receipient.getIsManagedByOrg() != null) {
                    existingReceipient.setIsManagedByOrg(receipient.getIsManagedByOrg());
                }
                if (receipient.getApprovedDateTime() != null) {
                    existingReceipient.setApprovedDateTime(receipient.getApprovedDateTime());
                }
                if (receipient.getRejectedDateTime() != null) {
                    existingReceipient.setRejectedDateTime(receipient.getRejectedDateTime());
                }
                if (receipient.getRejectReason() != null) {
                    existingReceipient.setRejectReason(receipient.getRejectReason());
                }
                if (receipient.getOnboardedDate() != null) {
                    existingReceipient.setOnboardedDate(receipient.getOnboardedDate());
                }
                if (receipient.getReccuringPauseDate() != null) {
                    existingReceipient.setReccuringPauseDate(receipient.getReccuringPauseDate());
                }
                if (receipient.getRecurringResumeDate() != null) {
                    existingReceipient.setRecurringResumeDate(receipient.getRecurringResumeDate());
                }
                if (receipient.getRecurringPauseReason() != null) {
                    existingReceipient.setRecurringPauseReason(receipient.getRecurringPauseReason());
                }
                if (receipient.getCreatedDate() != null) {
                    existingReceipient.setCreatedDate(receipient.getCreatedDate());
                }
                if (receipient.getUpdatedDate() != null) {
                    existingReceipient.setUpdatedDate(receipient.getUpdatedDate());
                }
                if (receipient.getDeletedDate() != null) {
                    existingReceipient.setDeletedDate(receipient.getDeletedDate());
                }

                return existingReceipient;
            })
            .map(receipientRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receipient.getId().toString())
        );
    }

    /**
     * {@code GET  /receipients} : get all the receipients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of receipients in body.
     */
    @GetMapping("/receipients")
    public List<Receipient> getAllReceipients() {
        log.debug("REST request to get all Receipients");
        return receipientRepository.findAll();
    }

    /**
     * {@code GET  /receipients/:id} : get the "id" receipient.
     *
     * @param id the id of the receipient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the receipient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/receipients/{id}")
    public ResponseEntity<Receipient> getReceipient(@PathVariable Long id) {
        log.debug("REST request to get Receipient : {}", id);
        Optional<Receipient> receipient = receipientRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(receipient);
    }

    /**
     * {@code DELETE  /receipients/:id} : delete the "id" receipient.
     *
     * @param id the id of the receipient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/receipients/{id}")
    public ResponseEntity<Void> deleteReceipient(@PathVariable Long id) {
        log.debug("REST request to delete Receipient : {}", id);
        receipientRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
