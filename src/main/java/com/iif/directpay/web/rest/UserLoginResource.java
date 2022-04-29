package com.iif.directpay.web.rest;

import com.iif.directpay.domain.UserLogin;
import com.iif.directpay.repository.UserLoginRepository;
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
 * REST controller for managing {@link com.iif.directpay.domain.UserLogin}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserLoginResource {

    private final Logger log = LoggerFactory.getLogger(UserLoginResource.class);

    private static final String ENTITY_NAME = "userLogin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserLoginRepository userLoginRepository;

    public UserLoginResource(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    /**
     * {@code POST  /user-logins} : Create a new userLogin.
     *
     * @param userLogin the userLogin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userLogin, or with status {@code 400 (Bad Request)} if the userLogin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-logins")
    public ResponseEntity<UserLogin> createUserLogin(@Valid @RequestBody UserLogin userLogin) throws URISyntaxException {
        log.debug("REST request to save UserLogin : {}", userLogin);
        if (userLogin.getId() != null) {
            throw new BadRequestAlertException("A new userLogin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserLogin result = userLoginRepository.save(userLogin);
        return ResponseEntity
            .created(new URI("/api/user-logins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-logins/:id} : Updates an existing userLogin.
     *
     * @param id the id of the userLogin to save.
     * @param userLogin the userLogin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userLogin,
     * or with status {@code 400 (Bad Request)} if the userLogin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userLogin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-logins/{id}")
    public ResponseEntity<UserLogin> updateUserLogin(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserLogin userLogin
    ) throws URISyntaxException {
        log.debug("REST request to update UserLogin : {}, {}", id, userLogin);
        if (userLogin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userLogin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userLoginRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserLogin result = userLoginRepository.save(userLogin);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userLogin.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-logins/:id} : Partial updates given fields of an existing userLogin, field will ignore if it is null
     *
     * @param id the id of the userLogin to save.
     * @param userLogin the userLogin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userLogin,
     * or with status {@code 400 (Bad Request)} if the userLogin is not valid,
     * or with status {@code 404 (Not Found)} if the userLogin is not found,
     * or with status {@code 500 (Internal Server Error)} if the userLogin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-logins/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserLogin> partialUpdateUserLogin(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserLogin userLogin
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserLogin partially : {}, {}", id, userLogin);
        if (userLogin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userLogin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userLoginRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserLogin> result = userLoginRepository
            .findById(userLogin.getId())
            .map(existingUserLogin -> {
                if (userLogin.getLoginType() != null) {
                    existingUserLogin.setLoginType(userLogin.getLoginType());
                }
                if (userLogin.getEmailOTP() != null) {
                    existingUserLogin.setEmailOTP(userLogin.getEmailOTP());
                }
                if (userLogin.getPhoneOTP() != null) {
                    existingUserLogin.setPhoneOTP(userLogin.getPhoneOTP());
                }
                if (userLogin.getEmailOTPExpiryDate() != null) {
                    existingUserLogin.setEmailOTPExpiryDate(userLogin.getEmailOTPExpiryDate());
                }
                if (userLogin.getPhoneOTPExpiryDate() != null) {
                    existingUserLogin.setPhoneOTPExpiryDate(userLogin.getPhoneOTPExpiryDate());
                }
                if (userLogin.getLocationIP() != null) {
                    existingUserLogin.setLocationIP(userLogin.getLocationIP());
                }
                if (userLogin.getLocationDetails() != null) {
                    existingUserLogin.setLocationDetails(userLogin.getLocationDetails());
                }
                if (userLogin.getLatlog() != null) {
                    existingUserLogin.setLatlog(userLogin.getLatlog());
                }
                if (userLogin.getBrowser() != null) {
                    existingUserLogin.setBrowser(userLogin.getBrowser());
                }
                if (userLogin.getDevice() != null) {
                    existingUserLogin.setDevice(userLogin.getDevice());
                }
                if (userLogin.getLoginDateTime() != null) {
                    existingUserLogin.setLoginDateTime(userLogin.getLoginDateTime());
                }
                if (userLogin.getLoginToken() != null) {
                    existingUserLogin.setLoginToken(userLogin.getLoginToken());
                }
                if (userLogin.getCreatedDate() != null) {
                    existingUserLogin.setCreatedDate(userLogin.getCreatedDate());
                }
                if (userLogin.getUpdatedDate() != null) {
                    existingUserLogin.setUpdatedDate(userLogin.getUpdatedDate());
                }

                return existingUserLogin;
            })
            .map(userLoginRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userLogin.getId().toString())
        );
    }

    /**
     * {@code GET  /user-logins} : get all the userLogins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userLogins in body.
     */
    @GetMapping("/user-logins")
    public List<UserLogin> getAllUserLogins() {
        log.debug("REST request to get all UserLogins");
        return userLoginRepository.findAll();
    }

    /**
     * {@code GET  /user-logins/:id} : get the "id" userLogin.
     *
     * @param id the id of the userLogin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userLogin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-logins/{id}")
    public ResponseEntity<UserLogin> getUserLogin(@PathVariable Long id) {
        log.debug("REST request to get UserLogin : {}", id);
        Optional<UserLogin> userLogin = userLoginRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userLogin);
    }

    /**
     * {@code DELETE  /user-logins/:id} : delete the "id" userLogin.
     *
     * @param id the id of the userLogin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-logins/{id}")
    public ResponseEntity<Void> deleteUserLogin(@PathVariable Long id) {
        log.debug("REST request to delete UserLogin : {}", id);
        userLoginRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
