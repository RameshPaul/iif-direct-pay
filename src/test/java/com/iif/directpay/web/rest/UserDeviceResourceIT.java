package com.iif.directpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iif.directpay.IntegrationTest;
import com.iif.directpay.domain.UserDevice;
import com.iif.directpay.domain.enumeration.UserDeviceStatus;
import com.iif.directpay.domain.enumeration.UserDeviceType;
import com.iif.directpay.repository.UserDeviceRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserDeviceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserDeviceResourceIT {

    private static final String DEFAULT_DEVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_TOKEN = "BBBBBBBBBB";

    private static final UserDeviceType DEFAULT_DEVICE_TYPE = UserDeviceType.MOBILE_PHONE;
    private static final UserDeviceType UPDATED_DEVICE_TYPE = UserDeviceType.TABLET;

    private static final String DEFAULT_DEVICE_OS = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_OS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NOTIFICATION_ENABLED = false;
    private static final Boolean UPDATED_NOTIFICATION_ENABLED = true;

    private static final String DEFAULT_LAST_ACTIVITY_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_LAST_ACTIVITY_DETAILS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_ACTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_ACTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LAST_ACTIVE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LAST_ACTIVE_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_APP_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_APP_VERSION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FORCE_LOGIN = false;
    private static final Boolean UPDATED_FORCE_LOGIN = true;

    private static final UserDeviceStatus DEFAULT_STATUS = UserDeviceStatus.INACTIVE;
    private static final UserDeviceStatus UPDATED_STATUS = UserDeviceStatus.ACTIVE;

    private static final Instant DEFAULT_LOGIN_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOGIN_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXIT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXIT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-devices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserDeviceMockMvc;

    private UserDevice userDevice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDevice createEntity(EntityManager em) {
        UserDevice userDevice = new UserDevice()
            .deviceName(DEFAULT_DEVICE_NAME)
            .deviceId(DEFAULT_DEVICE_ID)
            .deviceToken(DEFAULT_DEVICE_TOKEN)
            .deviceType(DEFAULT_DEVICE_TYPE)
            .deviceOS(DEFAULT_DEVICE_OS)
            .notificationEnabled(DEFAULT_NOTIFICATION_ENABLED)
            .lastActivityDetails(DEFAULT_LAST_ACTIVITY_DETAILS)
            .lastActiveDate(DEFAULT_LAST_ACTIVE_DATE)
            .lastActiveLocation(DEFAULT_LAST_ACTIVE_LOCATION)
            .appVersion(DEFAULT_APP_VERSION)
            .forceLogin(DEFAULT_FORCE_LOGIN)
            .status(DEFAULT_STATUS)
            .loginDateTime(DEFAULT_LOGIN_DATE_TIME)
            .exitDate(DEFAULT_EXIT_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return userDevice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDevice createUpdatedEntity(EntityManager em) {
        UserDevice userDevice = new UserDevice()
            .deviceName(UPDATED_DEVICE_NAME)
            .deviceId(UPDATED_DEVICE_ID)
            .deviceToken(UPDATED_DEVICE_TOKEN)
            .deviceType(UPDATED_DEVICE_TYPE)
            .deviceOS(UPDATED_DEVICE_OS)
            .notificationEnabled(UPDATED_NOTIFICATION_ENABLED)
            .lastActivityDetails(UPDATED_LAST_ACTIVITY_DETAILS)
            .lastActiveDate(UPDATED_LAST_ACTIVE_DATE)
            .lastActiveLocation(UPDATED_LAST_ACTIVE_LOCATION)
            .appVersion(UPDATED_APP_VERSION)
            .forceLogin(UPDATED_FORCE_LOGIN)
            .status(UPDATED_STATUS)
            .loginDateTime(UPDATED_LOGIN_DATE_TIME)
            .exitDate(UPDATED_EXIT_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return userDevice;
    }

    @BeforeEach
    public void initTest() {
        userDevice = createEntity(em);
    }

    @Test
    @Transactional
    void createUserDevice() throws Exception {
        int databaseSizeBeforeCreate = userDeviceRepository.findAll().size();
        // Create the UserDevice
        restUserDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDevice)))
            .andExpect(status().isCreated());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        UserDevice testUserDevice = userDeviceList.get(userDeviceList.size() - 1);
        assertThat(testUserDevice.getDeviceName()).isEqualTo(DEFAULT_DEVICE_NAME);
        assertThat(testUserDevice.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testUserDevice.getDeviceToken()).isEqualTo(DEFAULT_DEVICE_TOKEN);
        assertThat(testUserDevice.getDeviceType()).isEqualTo(DEFAULT_DEVICE_TYPE);
        assertThat(testUserDevice.getDeviceOS()).isEqualTo(DEFAULT_DEVICE_OS);
        assertThat(testUserDevice.getNotificationEnabled()).isEqualTo(DEFAULT_NOTIFICATION_ENABLED);
        assertThat(testUserDevice.getLastActivityDetails()).isEqualTo(DEFAULT_LAST_ACTIVITY_DETAILS);
        assertThat(testUserDevice.getLastActiveDate()).isEqualTo(DEFAULT_LAST_ACTIVE_DATE);
        assertThat(testUserDevice.getLastActiveLocation()).isEqualTo(DEFAULT_LAST_ACTIVE_LOCATION);
        assertThat(testUserDevice.getAppVersion()).isEqualTo(DEFAULT_APP_VERSION);
        assertThat(testUserDevice.getForceLogin()).isEqualTo(DEFAULT_FORCE_LOGIN);
        assertThat(testUserDevice.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserDevice.getLoginDateTime()).isEqualTo(DEFAULT_LOGIN_DATE_TIME);
        assertThat(testUserDevice.getExitDate()).isEqualTo(DEFAULT_EXIT_DATE);
        assertThat(testUserDevice.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserDevice.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createUserDeviceWithExistingId() throws Exception {
        // Create the UserDevice with an existing ID
        userDevice.setId(1L);

        int databaseSizeBeforeCreate = userDeviceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDevice)))
            .andExpect(status().isBadRequest());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDeviceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDeviceRepository.findAll().size();
        // set the field null
        userDevice.setDeviceName(null);

        // Create the UserDevice, which fails.

        restUserDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDevice)))
            .andExpect(status().isBadRequest());

        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeviceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDeviceRepository.findAll().size();
        // set the field null
        userDevice.setDeviceId(null);

        // Create the UserDevice, which fails.

        restUserDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDevice)))
            .andExpect(status().isBadRequest());

        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeviceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDeviceRepository.findAll().size();
        // set the field null
        userDevice.setDeviceType(null);

        // Create the UserDevice, which fails.

        restUserDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDevice)))
            .andExpect(status().isBadRequest());

        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeviceOSIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDeviceRepository.findAll().size();
        // set the field null
        userDevice.setDeviceOS(null);

        // Create the UserDevice, which fails.

        restUserDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDevice)))
            .andExpect(status().isBadRequest());

        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotificationEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDeviceRepository.findAll().size();
        // set the field null
        userDevice.setNotificationEnabled(null);

        // Create the UserDevice, which fails.

        restUserDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDevice)))
            .andExpect(status().isBadRequest());

        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastActivityDetailsIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDeviceRepository.findAll().size();
        // set the field null
        userDevice.setLastActivityDetails(null);

        // Create the UserDevice, which fails.

        restUserDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDevice)))
            .andExpect(status().isBadRequest());

        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastActiveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDeviceRepository.findAll().size();
        // set the field null
        userDevice.setLastActiveDate(null);

        // Create the UserDevice, which fails.

        restUserDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDevice)))
            .andExpect(status().isBadRequest());

        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastActiveLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDeviceRepository.findAll().size();
        // set the field null
        userDevice.setLastActiveLocation(null);

        // Create the UserDevice, which fails.

        restUserDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDevice)))
            .andExpect(status().isBadRequest());

        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAppVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDeviceRepository.findAll().size();
        // set the field null
        userDevice.setAppVersion(null);

        // Create the UserDevice, which fails.

        restUserDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDevice)))
            .andExpect(status().isBadRequest());

        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkForceLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDeviceRepository.findAll().size();
        // set the field null
        userDevice.setForceLogin(null);

        // Create the UserDevice, which fails.

        restUserDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDevice)))
            .andExpect(status().isBadRequest());

        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserDevices() throws Exception {
        // Initialize the database
        userDeviceRepository.saveAndFlush(userDevice);

        // Get all the userDeviceList
        restUserDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME)))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID)))
            .andExpect(jsonPath("$.[*].deviceToken").value(hasItem(DEFAULT_DEVICE_TOKEN)))
            .andExpect(jsonPath("$.[*].deviceType").value(hasItem(DEFAULT_DEVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].deviceOS").value(hasItem(DEFAULT_DEVICE_OS)))
            .andExpect(jsonPath("$.[*].notificationEnabled").value(hasItem(DEFAULT_NOTIFICATION_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastActivityDetails").value(hasItem(DEFAULT_LAST_ACTIVITY_DETAILS)))
            .andExpect(jsonPath("$.[*].lastActiveDate").value(hasItem(DEFAULT_LAST_ACTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastActiveLocation").value(hasItem(DEFAULT_LAST_ACTIVE_LOCATION)))
            .andExpect(jsonPath("$.[*].appVersion").value(hasItem(DEFAULT_APP_VERSION)))
            .andExpect(jsonPath("$.[*].forceLogin").value(hasItem(DEFAULT_FORCE_LOGIN.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loginDateTime").value(hasItem(DEFAULT_LOGIN_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].exitDate").value(hasItem(DEFAULT_EXIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getUserDevice() throws Exception {
        // Initialize the database
        userDeviceRepository.saveAndFlush(userDevice);

        // Get the userDevice
        restUserDeviceMockMvc
            .perform(get(ENTITY_API_URL_ID, userDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userDevice.getId().intValue()))
            .andExpect(jsonPath("$.deviceName").value(DEFAULT_DEVICE_NAME))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID))
            .andExpect(jsonPath("$.deviceToken").value(DEFAULT_DEVICE_TOKEN))
            .andExpect(jsonPath("$.deviceType").value(DEFAULT_DEVICE_TYPE.toString()))
            .andExpect(jsonPath("$.deviceOS").value(DEFAULT_DEVICE_OS))
            .andExpect(jsonPath("$.notificationEnabled").value(DEFAULT_NOTIFICATION_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.lastActivityDetails").value(DEFAULT_LAST_ACTIVITY_DETAILS))
            .andExpect(jsonPath("$.lastActiveDate").value(DEFAULT_LAST_ACTIVE_DATE.toString()))
            .andExpect(jsonPath("$.lastActiveLocation").value(DEFAULT_LAST_ACTIVE_LOCATION))
            .andExpect(jsonPath("$.appVersion").value(DEFAULT_APP_VERSION))
            .andExpect(jsonPath("$.forceLogin").value(DEFAULT_FORCE_LOGIN.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.loginDateTime").value(DEFAULT_LOGIN_DATE_TIME.toString()))
            .andExpect(jsonPath("$.exitDate").value(DEFAULT_EXIT_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserDevice() throws Exception {
        // Get the userDevice
        restUserDeviceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserDevice() throws Exception {
        // Initialize the database
        userDeviceRepository.saveAndFlush(userDevice);

        int databaseSizeBeforeUpdate = userDeviceRepository.findAll().size();

        // Update the userDevice
        UserDevice updatedUserDevice = userDeviceRepository.findById(userDevice.getId()).get();
        // Disconnect from session so that the updates on updatedUserDevice are not directly saved in db
        em.detach(updatedUserDevice);
        updatedUserDevice
            .deviceName(UPDATED_DEVICE_NAME)
            .deviceId(UPDATED_DEVICE_ID)
            .deviceToken(UPDATED_DEVICE_TOKEN)
            .deviceType(UPDATED_DEVICE_TYPE)
            .deviceOS(UPDATED_DEVICE_OS)
            .notificationEnabled(UPDATED_NOTIFICATION_ENABLED)
            .lastActivityDetails(UPDATED_LAST_ACTIVITY_DETAILS)
            .lastActiveDate(UPDATED_LAST_ACTIVE_DATE)
            .lastActiveLocation(UPDATED_LAST_ACTIVE_LOCATION)
            .appVersion(UPDATED_APP_VERSION)
            .forceLogin(UPDATED_FORCE_LOGIN)
            .status(UPDATED_STATUS)
            .loginDateTime(UPDATED_LOGIN_DATE_TIME)
            .exitDate(UPDATED_EXIT_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserDevice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserDevice))
            )
            .andExpect(status().isOk());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeUpdate);
        UserDevice testUserDevice = userDeviceList.get(userDeviceList.size() - 1);
        assertThat(testUserDevice.getDeviceName()).isEqualTo(UPDATED_DEVICE_NAME);
        assertThat(testUserDevice.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testUserDevice.getDeviceToken()).isEqualTo(UPDATED_DEVICE_TOKEN);
        assertThat(testUserDevice.getDeviceType()).isEqualTo(UPDATED_DEVICE_TYPE);
        assertThat(testUserDevice.getDeviceOS()).isEqualTo(UPDATED_DEVICE_OS);
        assertThat(testUserDevice.getNotificationEnabled()).isEqualTo(UPDATED_NOTIFICATION_ENABLED);
        assertThat(testUserDevice.getLastActivityDetails()).isEqualTo(UPDATED_LAST_ACTIVITY_DETAILS);
        assertThat(testUserDevice.getLastActiveDate()).isEqualTo(UPDATED_LAST_ACTIVE_DATE);
        assertThat(testUserDevice.getLastActiveLocation()).isEqualTo(UPDATED_LAST_ACTIVE_LOCATION);
        assertThat(testUserDevice.getAppVersion()).isEqualTo(UPDATED_APP_VERSION);
        assertThat(testUserDevice.getForceLogin()).isEqualTo(UPDATED_FORCE_LOGIN);
        assertThat(testUserDevice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserDevice.getLoginDateTime()).isEqualTo(UPDATED_LOGIN_DATE_TIME);
        assertThat(testUserDevice.getExitDate()).isEqualTo(UPDATED_EXIT_DATE);
        assertThat(testUserDevice.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserDevice.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserDevice() throws Exception {
        int databaseSizeBeforeUpdate = userDeviceRepository.findAll().size();
        userDevice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userDevice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userDevice))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserDevice() throws Exception {
        int databaseSizeBeforeUpdate = userDeviceRepository.findAll().size();
        userDevice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userDevice))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserDevice() throws Exception {
        int databaseSizeBeforeUpdate = userDeviceRepository.findAll().size();
        userDevice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserDeviceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDevice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserDeviceWithPatch() throws Exception {
        // Initialize the database
        userDeviceRepository.saveAndFlush(userDevice);

        int databaseSizeBeforeUpdate = userDeviceRepository.findAll().size();

        // Update the userDevice using partial update
        UserDevice partialUpdatedUserDevice = new UserDevice();
        partialUpdatedUserDevice.setId(userDevice.getId());

        partialUpdatedUserDevice
            .deviceName(UPDATED_DEVICE_NAME)
            .notificationEnabled(UPDATED_NOTIFICATION_ENABLED)
            .lastActivityDetails(UPDATED_LAST_ACTIVITY_DETAILS)
            .lastActiveLocation(UPDATED_LAST_ACTIVE_LOCATION)
            .appVersion(UPDATED_APP_VERSION);

        restUserDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserDevice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserDevice))
            )
            .andExpect(status().isOk());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeUpdate);
        UserDevice testUserDevice = userDeviceList.get(userDeviceList.size() - 1);
        assertThat(testUserDevice.getDeviceName()).isEqualTo(UPDATED_DEVICE_NAME);
        assertThat(testUserDevice.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testUserDevice.getDeviceToken()).isEqualTo(DEFAULT_DEVICE_TOKEN);
        assertThat(testUserDevice.getDeviceType()).isEqualTo(DEFAULT_DEVICE_TYPE);
        assertThat(testUserDevice.getDeviceOS()).isEqualTo(DEFAULT_DEVICE_OS);
        assertThat(testUserDevice.getNotificationEnabled()).isEqualTo(UPDATED_NOTIFICATION_ENABLED);
        assertThat(testUserDevice.getLastActivityDetails()).isEqualTo(UPDATED_LAST_ACTIVITY_DETAILS);
        assertThat(testUserDevice.getLastActiveDate()).isEqualTo(DEFAULT_LAST_ACTIVE_DATE);
        assertThat(testUserDevice.getLastActiveLocation()).isEqualTo(UPDATED_LAST_ACTIVE_LOCATION);
        assertThat(testUserDevice.getAppVersion()).isEqualTo(UPDATED_APP_VERSION);
        assertThat(testUserDevice.getForceLogin()).isEqualTo(DEFAULT_FORCE_LOGIN);
        assertThat(testUserDevice.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserDevice.getLoginDateTime()).isEqualTo(DEFAULT_LOGIN_DATE_TIME);
        assertThat(testUserDevice.getExitDate()).isEqualTo(DEFAULT_EXIT_DATE);
        assertThat(testUserDevice.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserDevice.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserDeviceWithPatch() throws Exception {
        // Initialize the database
        userDeviceRepository.saveAndFlush(userDevice);

        int databaseSizeBeforeUpdate = userDeviceRepository.findAll().size();

        // Update the userDevice using partial update
        UserDevice partialUpdatedUserDevice = new UserDevice();
        partialUpdatedUserDevice.setId(userDevice.getId());

        partialUpdatedUserDevice
            .deviceName(UPDATED_DEVICE_NAME)
            .deviceId(UPDATED_DEVICE_ID)
            .deviceToken(UPDATED_DEVICE_TOKEN)
            .deviceType(UPDATED_DEVICE_TYPE)
            .deviceOS(UPDATED_DEVICE_OS)
            .notificationEnabled(UPDATED_NOTIFICATION_ENABLED)
            .lastActivityDetails(UPDATED_LAST_ACTIVITY_DETAILS)
            .lastActiveDate(UPDATED_LAST_ACTIVE_DATE)
            .lastActiveLocation(UPDATED_LAST_ACTIVE_LOCATION)
            .appVersion(UPDATED_APP_VERSION)
            .forceLogin(UPDATED_FORCE_LOGIN)
            .status(UPDATED_STATUS)
            .loginDateTime(UPDATED_LOGIN_DATE_TIME)
            .exitDate(UPDATED_EXIT_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserDevice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserDevice))
            )
            .andExpect(status().isOk());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeUpdate);
        UserDevice testUserDevice = userDeviceList.get(userDeviceList.size() - 1);
        assertThat(testUserDevice.getDeviceName()).isEqualTo(UPDATED_DEVICE_NAME);
        assertThat(testUserDevice.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testUserDevice.getDeviceToken()).isEqualTo(UPDATED_DEVICE_TOKEN);
        assertThat(testUserDevice.getDeviceType()).isEqualTo(UPDATED_DEVICE_TYPE);
        assertThat(testUserDevice.getDeviceOS()).isEqualTo(UPDATED_DEVICE_OS);
        assertThat(testUserDevice.getNotificationEnabled()).isEqualTo(UPDATED_NOTIFICATION_ENABLED);
        assertThat(testUserDevice.getLastActivityDetails()).isEqualTo(UPDATED_LAST_ACTIVITY_DETAILS);
        assertThat(testUserDevice.getLastActiveDate()).isEqualTo(UPDATED_LAST_ACTIVE_DATE);
        assertThat(testUserDevice.getLastActiveLocation()).isEqualTo(UPDATED_LAST_ACTIVE_LOCATION);
        assertThat(testUserDevice.getAppVersion()).isEqualTo(UPDATED_APP_VERSION);
        assertThat(testUserDevice.getForceLogin()).isEqualTo(UPDATED_FORCE_LOGIN);
        assertThat(testUserDevice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserDevice.getLoginDateTime()).isEqualTo(UPDATED_LOGIN_DATE_TIME);
        assertThat(testUserDevice.getExitDate()).isEqualTo(UPDATED_EXIT_DATE);
        assertThat(testUserDevice.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserDevice.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserDevice() throws Exception {
        int databaseSizeBeforeUpdate = userDeviceRepository.findAll().size();
        userDevice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userDevice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userDevice))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserDevice() throws Exception {
        int databaseSizeBeforeUpdate = userDeviceRepository.findAll().size();
        userDevice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userDevice))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserDevice() throws Exception {
        int databaseSizeBeforeUpdate = userDeviceRepository.findAll().size();
        userDevice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userDevice))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserDevice() throws Exception {
        // Initialize the database
        userDeviceRepository.saveAndFlush(userDevice);

        int databaseSizeBeforeDelete = userDeviceRepository.findAll().size();

        // Delete the userDevice
        restUserDeviceMockMvc
            .perform(delete(ENTITY_API_URL_ID, userDevice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
