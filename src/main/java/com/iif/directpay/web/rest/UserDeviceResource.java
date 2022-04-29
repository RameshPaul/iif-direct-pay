package com.iif.directpay.web.rest;

import com.iif.directpay.domain.UserDevice;
import com.iif.directpay.repository.UserDeviceRepository;
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
 * REST controller for managing {@link com.iif.directpay.domain.UserDevice}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserDeviceResource {

    private final Logger log = LoggerFactory.getLogger(UserDeviceResource.class);

    private static final String ENTITY_NAME = "userDevice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserDeviceRepository userDeviceRepository;

    public UserDeviceResource(UserDeviceRepository userDeviceRepository) {
        this.userDeviceRepository = userDeviceRepository;
    }

    /**
     * {@code POST  /user-devices} : Create a new userDevice.
     *
     * @param userDevice the userDevice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDevice, or with status {@code 400 (Bad Request)} if the userDevice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-devices")
    public ResponseEntity<UserDevice> createUserDevice(@Valid @RequestBody UserDevice userDevice) throws URISyntaxException {
        log.debug("REST request to save UserDevice : {}", userDevice);
        if (userDevice.getId() != null) {
            throw new BadRequestAlertException("A new userDevice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDevice result = userDeviceRepository.save(userDevice);
        return ResponseEntity
            .created(new URI("/api/user-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-devices/:id} : Updates an existing userDevice.
     *
     * @param id the id of the userDevice to save.
     * @param userDevice the userDevice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDevice,
     * or with status {@code 400 (Bad Request)} if the userDevice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDevice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-devices/{id}")
    public ResponseEntity<UserDevice> updateUserDevice(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserDevice userDevice
    ) throws URISyntaxException {
        log.debug("REST request to update UserDevice : {}, {}", id, userDevice);
        if (userDevice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userDevice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userDeviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserDevice result = userDeviceRepository.save(userDevice);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDevice.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-devices/:id} : Partial updates given fields of an existing userDevice, field will ignore if it is null
     *
     * @param id the id of the userDevice to save.
     * @param userDevice the userDevice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDevice,
     * or with status {@code 400 (Bad Request)} if the userDevice is not valid,
     * or with status {@code 404 (Not Found)} if the userDevice is not found,
     * or with status {@code 500 (Internal Server Error)} if the userDevice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-devices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserDevice> partialUpdateUserDevice(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserDevice userDevice
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserDevice partially : {}, {}", id, userDevice);
        if (userDevice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userDevice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userDeviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserDevice> result = userDeviceRepository
            .findById(userDevice.getId())
            .map(existingUserDevice -> {
                if (userDevice.getDeviceName() != null) {
                    existingUserDevice.setDeviceName(userDevice.getDeviceName());
                }
                if (userDevice.getDeviceId() != null) {
                    existingUserDevice.setDeviceId(userDevice.getDeviceId());
                }
                if (userDevice.getDeviceToken() != null) {
                    existingUserDevice.setDeviceToken(userDevice.getDeviceToken());
                }
                if (userDevice.getDeviceType() != null) {
                    existingUserDevice.setDeviceType(userDevice.getDeviceType());
                }
                if (userDevice.getDeviceOS() != null) {
                    existingUserDevice.setDeviceOS(userDevice.getDeviceOS());
                }
                if (userDevice.getNotificationEnabled() != null) {
                    existingUserDevice.setNotificationEnabled(userDevice.getNotificationEnabled());
                }
                if (userDevice.getLastActivityDetails() != null) {
                    existingUserDevice.setLastActivityDetails(userDevice.getLastActivityDetails());
                }
                if (userDevice.getLastActiveDate() != null) {
                    existingUserDevice.setLastActiveDate(userDevice.getLastActiveDate());
                }
                if (userDevice.getLastActiveLocation() != null) {
                    existingUserDevice.setLastActiveLocation(userDevice.getLastActiveLocation());
                }
                if (userDevice.getAppVersion() != null) {
                    existingUserDevice.setAppVersion(userDevice.getAppVersion());
                }
                if (userDevice.getForceLogin() != null) {
                    existingUserDevice.setForceLogin(userDevice.getForceLogin());
                }
                if (userDevice.getStatus() != null) {
                    existingUserDevice.setStatus(userDevice.getStatus());
                }
                if (userDevice.getLoginDateTime() != null) {
                    existingUserDevice.setLoginDateTime(userDevice.getLoginDateTime());
                }
                if (userDevice.getExitDate() != null) {
                    existingUserDevice.setExitDate(userDevice.getExitDate());
                }
                if (userDevice.getCreatedDate() != null) {
                    existingUserDevice.setCreatedDate(userDevice.getCreatedDate());
                }
                if (userDevice.getUpdatedDate() != null) {
                    existingUserDevice.setUpdatedDate(userDevice.getUpdatedDate());
                }

                return existingUserDevice;
            })
            .map(userDeviceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDevice.getId().toString())
        );
    }

    /**
     * {@code GET  /user-devices} : get all the userDevices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userDevices in body.
     */
    @GetMapping("/user-devices")
    public List<UserDevice> getAllUserDevices() {
        log.debug("REST request to get all UserDevices");
        return userDeviceRepository.findAll();
    }

    /**
     * {@code GET  /user-devices/:id} : get the "id" userDevice.
     *
     * @param id the id of the userDevice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDevice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-devices/{id}")
    public ResponseEntity<UserDevice> getUserDevice(@PathVariable Long id) {
        log.debug("REST request to get UserDevice : {}", id);
        Optional<UserDevice> userDevice = userDeviceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userDevice);
    }

    /**
     * {@code DELETE  /user-devices/:id} : delete the "id" userDevice.
     *
     * @param id the id of the userDevice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-devices/{id}")
    public ResponseEntity<Void> deleteUserDevice(@PathVariable Long id) {
        log.debug("REST request to delete UserDevice : {}", id);
        userDeviceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
