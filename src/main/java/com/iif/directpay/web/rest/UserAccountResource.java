package com.iif.directpay.web.rest;

import com.iif.directpay.domain.UserAccount;
import com.iif.directpay.repository.UserAccountRepository;
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
 * REST controller for managing {@link com.iif.directpay.domain.UserAccount}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserAccountResource {

    private final Logger log = LoggerFactory.getLogger(UserAccountResource.class);

    private static final String ENTITY_NAME = "userAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAccountRepository userAccountRepository;

    public UserAccountResource(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    /**
     * {@code POST  /user-accounts} : Create a new userAccount.
     *
     * @param userAccount the userAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAccount, or with status {@code 400 (Bad Request)} if the userAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-accounts")
    public ResponseEntity<UserAccount> createUserAccount(@Valid @RequestBody UserAccount userAccount) throws URISyntaxException {
        log.debug("REST request to save UserAccount : {}", userAccount);
        if (userAccount.getId() != null) {
            throw new BadRequestAlertException("A new userAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAccount result = userAccountRepository.save(userAccount);
        return ResponseEntity
            .created(new URI("/api/user-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-accounts/:id} : Updates an existing userAccount.
     *
     * @param id the id of the userAccount to save.
     * @param userAccount the userAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAccount,
     * or with status {@code 400 (Bad Request)} if the userAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-accounts/{id}")
    public ResponseEntity<UserAccount> updateUserAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserAccount userAccount
    ) throws URISyntaxException {
        log.debug("REST request to update UserAccount : {}, {}", id, userAccount);
        if (userAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAccount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserAccount result = userAccountRepository.save(userAccount);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-accounts/:id} : Partial updates given fields of an existing userAccount, field will ignore if it is null
     *
     * @param id the id of the userAccount to save.
     * @param userAccount the userAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAccount,
     * or with status {@code 400 (Bad Request)} if the userAccount is not valid,
     * or with status {@code 404 (Not Found)} if the userAccount is not found,
     * or with status {@code 500 (Internal Server Error)} if the userAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-accounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserAccount> partialUpdateUserAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserAccount userAccount
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserAccount partially : {}, {}", id, userAccount);
        if (userAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAccount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserAccount> result = userAccountRepository
            .findById(userAccount.getId())
            .map(existingUserAccount -> {
                if (userAccount.getAccountType() != null) {
                    existingUserAccount.setAccountType(userAccount.getAccountType());
                }
                if (userAccount.getUpiAddress() != null) {
                    existingUserAccount.setUpiAddress(userAccount.getUpiAddress());
                }
                if (userAccount.getMobileNumber() != null) {
                    existingUserAccount.setMobileNumber(userAccount.getMobileNumber());
                }
                if (userAccount.getUpiActiveDate() != null) {
                    existingUserAccount.setUpiActiveDate(userAccount.getUpiActiveDate());
                }
                if (userAccount.getUpiStatus() != null) {
                    existingUserAccount.setUpiStatus(userAccount.getUpiStatus());
                }
                if (userAccount.getUpiSuspendedDate() != null) {
                    existingUserAccount.setUpiSuspendedDate(userAccount.getUpiSuspendedDate());
                }
                if (userAccount.getUpiDeletedDate() != null) {
                    existingUserAccount.setUpiDeletedDate(userAccount.getUpiDeletedDate());
                }
                if (userAccount.getUpiAutoDebitEnabled() != null) {
                    existingUserAccount.setUpiAutoDebitEnabled(userAccount.getUpiAutoDebitEnabled());
                }
                if (userAccount.getBankName() != null) {
                    existingUserAccount.setBankName(userAccount.getBankName());
                }
                if (userAccount.getBankAccountNumber() != null) {
                    existingUserAccount.setBankAccountNumber(userAccount.getBankAccountNumber());
                }
                if (userAccount.getBankIFSCCode() != null) {
                    existingUserAccount.setBankIFSCCode(userAccount.getBankIFSCCode());
                }
                if (userAccount.getBankSWIFTCode() != null) {
                    existingUserAccount.setBankSWIFTCode(userAccount.getBankSWIFTCode());
                }
                if (userAccount.getBankBranchAddress() != null) {
                    existingUserAccount.setBankBranchAddress(userAccount.getBankBranchAddress());
                }
                if (userAccount.getBankStatus() != null) {
                    existingUserAccount.setBankStatus(userAccount.getBankStatus());
                }
                if (userAccount.getBankActiveDate() != null) {
                    existingUserAccount.setBankActiveDate(userAccount.getBankActiveDate());
                }
                if (userAccount.getBankSuspendedDate() != null) {
                    existingUserAccount.setBankSuspendedDate(userAccount.getBankSuspendedDate());
                }
                if (userAccount.getBankDeletedDate() != null) {
                    existingUserAccount.setBankDeletedDate(userAccount.getBankDeletedDate());
                }
                if (userAccount.getBankAutoDebitEnabled() != null) {
                    existingUserAccount.setBankAutoDebitEnabled(userAccount.getBankAutoDebitEnabled());
                }
                if (userAccount.getWalletType() != null) {
                    existingUserAccount.setWalletType(userAccount.getWalletType());
                }
                if (userAccount.getWalletAddress() != null) {
                    existingUserAccount.setWalletAddress(userAccount.getWalletAddress());
                }
                if (userAccount.getWalletStatus() != null) {
                    existingUserAccount.setWalletStatus(userAccount.getWalletStatus());
                }
                if (userAccount.getWallterActiveDate() != null) {
                    existingUserAccount.setWallterActiveDate(userAccount.getWallterActiveDate());
                }
                if (userAccount.getWalletSuspendedDate() != null) {
                    existingUserAccount.setWalletSuspendedDate(userAccount.getWalletSuspendedDate());
                }
                if (userAccount.getWalletDeletedDate() != null) {
                    existingUserAccount.setWalletDeletedDate(userAccount.getWalletDeletedDate());
                }
                if (userAccount.getWalletAutoDebitEnabled() != null) {
                    existingUserAccount.setWalletAutoDebitEnabled(userAccount.getWalletAutoDebitEnabled());
                }
                if (userAccount.getCreatedDate() != null) {
                    existingUserAccount.setCreatedDate(userAccount.getCreatedDate());
                }
                if (userAccount.getUpdatedDate() != null) {
                    existingUserAccount.setUpdatedDate(userAccount.getUpdatedDate());
                }

                return existingUserAccount;
            })
            .map(userAccountRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAccount.getId().toString())
        );
    }

    /**
     * {@code GET  /user-accounts} : get all the userAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAccounts in body.
     */
    @GetMapping("/user-accounts")
    public List<UserAccount> getAllUserAccounts() {
        log.debug("REST request to get all UserAccounts");
        return userAccountRepository.findAll();
    }

    /**
     * {@code GET  /user-accounts/:id} : get the "id" userAccount.
     *
     * @param id the id of the userAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-accounts/{id}")
    public ResponseEntity<UserAccount> getUserAccount(@PathVariable Long id) {
        log.debug("REST request to get UserAccount : {}", id);
        Optional<UserAccount> userAccount = userAccountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userAccount);
    }

    /**
     * {@code DELETE  /user-accounts/:id} : delete the "id" userAccount.
     *
     * @param id the id of the userAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-accounts/{id}")
    public ResponseEntity<Void> deleteUserAccount(@PathVariable Long id) {
        log.debug("REST request to delete UserAccount : {}", id);
        userAccountRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
