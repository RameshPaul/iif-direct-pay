package com.iif.directpay.web.rest;

import com.iif.directpay.domain.ReceipientRecurring;
import com.iif.directpay.repository.ReceipientRecurringRepository;
import com.iif.directpay.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.iif.directpay.domain.ReceipientRecurring}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReceipientRecurringResource {

    private final Logger log = LoggerFactory.getLogger(ReceipientRecurringResource.class);

    private static final String ENTITY_NAME = "receipientRecurring";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReceipientRecurringRepository receipientRecurringRepository;

    public ReceipientRecurringResource(ReceipientRecurringRepository receipientRecurringRepository) {
        this.receipientRecurringRepository = receipientRecurringRepository;
    }

    /**
     * {@code POST  /receipient-recurrings} : Create a new receipientRecurring.
     *
     * @param receipientRecurring the receipientRecurring to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receipientRecurring, or with status {@code 400 (Bad Request)} if the receipientRecurring has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/receipient-recurrings")
    public ResponseEntity<ReceipientRecurring> createReceipientRecurring(@RequestBody ReceipientRecurring receipientRecurring)
        throws URISyntaxException {
        log.debug("REST request to save ReceipientRecurring : {}", receipientRecurring);
        if (receipientRecurring.getId() != null) {
            throw new BadRequestAlertException("A new receipientRecurring cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReceipientRecurring result = receipientRecurringRepository.save(receipientRecurring);
        return ResponseEntity
            .created(new URI("/api/receipient-recurrings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /receipient-recurrings/:id} : Updates an existing receipientRecurring.
     *
     * @param id the id of the receipientRecurring to save.
     * @param receipientRecurring the receipientRecurring to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receipientRecurring,
     * or with status {@code 400 (Bad Request)} if the receipientRecurring is not valid,
     * or with status {@code 500 (Internal Server Error)} if the receipientRecurring couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/receipient-recurrings/{id}")
    public ResponseEntity<ReceipientRecurring> updateReceipientRecurring(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReceipientRecurring receipientRecurring
    ) throws URISyntaxException {
        log.debug("REST request to update ReceipientRecurring : {}, {}", id, receipientRecurring);
        if (receipientRecurring.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receipientRecurring.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receipientRecurringRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReceipientRecurring result = receipientRecurringRepository.save(receipientRecurring);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receipientRecurring.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /receipient-recurrings/:id} : Partial updates given fields of an existing receipientRecurring, field will ignore if it is null
     *
     * @param id the id of the receipientRecurring to save.
     * @param receipientRecurring the receipientRecurring to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receipientRecurring,
     * or with status {@code 400 (Bad Request)} if the receipientRecurring is not valid,
     * or with status {@code 404 (Not Found)} if the receipientRecurring is not found,
     * or with status {@code 500 (Internal Server Error)} if the receipientRecurring couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/receipient-recurrings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReceipientRecurring> partialUpdateReceipientRecurring(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReceipientRecurring receipientRecurring
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReceipientRecurring partially : {}, {}", id, receipientRecurring);
        if (receipientRecurring.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receipientRecurring.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receipientRecurringRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReceipientRecurring> result = receipientRecurringRepository
            .findById(receipientRecurring.getId())
            .map(existingReceipientRecurring -> {
                if (receipientRecurring.getRecurringPeriod() != null) {
                    existingReceipientRecurring.setRecurringPeriod(receipientRecurring.getRecurringPeriod());
                }
                if (receipientRecurring.getStartDate() != null) {
                    existingReceipientRecurring.setStartDate(receipientRecurring.getStartDate());
                }
                if (receipientRecurring.getEndDate() != null) {
                    existingReceipientRecurring.setEndDate(receipientRecurring.getEndDate());
                }
                if (receipientRecurring.getAmountRequisite() != null) {
                    existingReceipientRecurring.setAmountRequisite(receipientRecurring.getAmountRequisite());
                }
                if (receipientRecurring.getAmountPatronCommited() != null) {
                    existingReceipientRecurring.setAmountPatronCommited(receipientRecurring.getAmountPatronCommited());
                }
                if (receipientRecurring.getAmountReceived() != null) {
                    existingReceipientRecurring.setAmountReceived(receipientRecurring.getAmountReceived());
                }
                if (receipientRecurring.getAmountBalance() != null) {
                    existingReceipientRecurring.setAmountBalance(receipientRecurring.getAmountBalance());
                }
                if (receipientRecurring.getTotalPatrons() != null) {
                    existingReceipientRecurring.setTotalPatrons(receipientRecurring.getTotalPatrons());
                }
                if (receipientRecurring.getDetailsText() != null) {
                    existingReceipientRecurring.setDetailsText(receipientRecurring.getDetailsText());
                }
                if (receipientRecurring.getStatus() != null) {
                    existingReceipientRecurring.setStatus(receipientRecurring.getStatus());
                }
                if (receipientRecurring.getPauseDate() != null) {
                    existingReceipientRecurring.setPauseDate(receipientRecurring.getPauseDate());
                }
                if (receipientRecurring.getResumeDate() != null) {
                    existingReceipientRecurring.setResumeDate(receipientRecurring.getResumeDate());
                }
                if (receipientRecurring.getCreatedDate() != null) {
                    existingReceipientRecurring.setCreatedDate(receipientRecurring.getCreatedDate());
                }
                if (receipientRecurring.getUpdatedDate() != null) {
                    existingReceipientRecurring.setUpdatedDate(receipientRecurring.getUpdatedDate());
                }

                return existingReceipientRecurring;
            })
            .map(receipientRecurringRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receipientRecurring.getId().toString())
        );
    }

    /**
     * {@code GET  /receipient-recurrings} : get all the receipientRecurrings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of receipientRecurrings in body.
     */
    @GetMapping("/receipient-recurrings")
    public List<ReceipientRecurring> getAllReceipientRecurrings() {
        log.debug("REST request to get all ReceipientRecurrings");
        return receipientRecurringRepository.findAll();
    }

    /**
     * {@code GET  /receipient-recurrings/:id} : get the "id" receipientRecurring.
     *
     * @param id the id of the receipientRecurring to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the receipientRecurring, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/receipient-recurrings/{id}")
    public ResponseEntity<ReceipientRecurring> getReceipientRecurring(@PathVariable Long id) {
        log.debug("REST request to get ReceipientRecurring : {}", id);
        Optional<ReceipientRecurring> receipientRecurring = receipientRecurringRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(receipientRecurring);
    }

    /**
     * {@code DELETE  /receipient-recurrings/:id} : delete the "id" receipientRecurring.
     *
     * @param id the id of the receipientRecurring to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/receipient-recurrings/{id}")
    public ResponseEntity<Void> deleteReceipientRecurring(@PathVariable Long id) {
        log.debug("REST request to delete ReceipientRecurring : {}", id);
        receipientRecurringRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
