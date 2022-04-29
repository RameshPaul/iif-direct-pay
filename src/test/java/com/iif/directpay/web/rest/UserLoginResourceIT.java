package com.iif.directpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iif.directpay.IntegrationTest;
import com.iif.directpay.domain.UserLogin;
import com.iif.directpay.domain.enumeration.UserLoginType;
import com.iif.directpay.repository.UserLoginRepository;
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
 * Integration tests for the {@link UserLoginResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserLoginResourceIT {

    private static final UserLoginType DEFAULT_LOGIN_TYPE = UserLoginType.EMAIL;
    private static final UserLoginType UPDATED_LOGIN_TYPE = UserLoginType.PHONE;

    private static final String DEFAULT_EMAIL_OTP = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_OTP = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_OTP = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_OTP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EMAIL_OTP_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMAIL_OTP_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PHONE_OTP_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PHONE_OTP_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LOCATION_IP = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_IP = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_LATLOG = "AAAAAAAAAA";
    private static final String UPDATED_LATLOG = "BBBBBBBBBB";

    private static final String DEFAULT_BROWSER = "AAAAAAAAAA";
    private static final String UPDATED_BROWSER = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE = "BBBBBBBBBB";

    private static final Instant DEFAULT_LOGIN_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOGIN_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOGIN_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_TOKEN = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-logins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserLoginMockMvc;

    private UserLogin userLogin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserLogin createEntity(EntityManager em) {
        UserLogin userLogin = new UserLogin()
            .loginType(DEFAULT_LOGIN_TYPE)
            .emailOTP(DEFAULT_EMAIL_OTP)
            .phoneOTP(DEFAULT_PHONE_OTP)
            .emailOTPExpiryDate(DEFAULT_EMAIL_OTP_EXPIRY_DATE)
            .phoneOTPExpiryDate(DEFAULT_PHONE_OTP_EXPIRY_DATE)
            .locationIP(DEFAULT_LOCATION_IP)
            .locationDetails(DEFAULT_LOCATION_DETAILS)
            .latlog(DEFAULT_LATLOG)
            .browser(DEFAULT_BROWSER)
            .device(DEFAULT_DEVICE)
            .loginDateTime(DEFAULT_LOGIN_DATE_TIME)
            .loginToken(DEFAULT_LOGIN_TOKEN)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return userLogin;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserLogin createUpdatedEntity(EntityManager em) {
        UserLogin userLogin = new UserLogin()
            .loginType(UPDATED_LOGIN_TYPE)
            .emailOTP(UPDATED_EMAIL_OTP)
            .phoneOTP(UPDATED_PHONE_OTP)
            .emailOTPExpiryDate(UPDATED_EMAIL_OTP_EXPIRY_DATE)
            .phoneOTPExpiryDate(UPDATED_PHONE_OTP_EXPIRY_DATE)
            .locationIP(UPDATED_LOCATION_IP)
            .locationDetails(UPDATED_LOCATION_DETAILS)
            .latlog(UPDATED_LATLOG)
            .browser(UPDATED_BROWSER)
            .device(UPDATED_DEVICE)
            .loginDateTime(UPDATED_LOGIN_DATE_TIME)
            .loginToken(UPDATED_LOGIN_TOKEN)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return userLogin;
    }

    @BeforeEach
    public void initTest() {
        userLogin = createEntity(em);
    }

    @Test
    @Transactional
    void createUserLogin() throws Exception {
        int databaseSizeBeforeCreate = userLoginRepository.findAll().size();
        // Create the UserLogin
        restUserLoginMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userLogin)))
            .andExpect(status().isCreated());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeCreate + 1);
        UserLogin testUserLogin = userLoginList.get(userLoginList.size() - 1);
        assertThat(testUserLogin.getLoginType()).isEqualTo(DEFAULT_LOGIN_TYPE);
        assertThat(testUserLogin.getEmailOTP()).isEqualTo(DEFAULT_EMAIL_OTP);
        assertThat(testUserLogin.getPhoneOTP()).isEqualTo(DEFAULT_PHONE_OTP);
        assertThat(testUserLogin.getEmailOTPExpiryDate()).isEqualTo(DEFAULT_EMAIL_OTP_EXPIRY_DATE);
        assertThat(testUserLogin.getPhoneOTPExpiryDate()).isEqualTo(DEFAULT_PHONE_OTP_EXPIRY_DATE);
        assertThat(testUserLogin.getLocationIP()).isEqualTo(DEFAULT_LOCATION_IP);
        assertThat(testUserLogin.getLocationDetails()).isEqualTo(DEFAULT_LOCATION_DETAILS);
        assertThat(testUserLogin.getLatlog()).isEqualTo(DEFAULT_LATLOG);
        assertThat(testUserLogin.getBrowser()).isEqualTo(DEFAULT_BROWSER);
        assertThat(testUserLogin.getDevice()).isEqualTo(DEFAULT_DEVICE);
        assertThat(testUserLogin.getLoginDateTime()).isEqualTo(DEFAULT_LOGIN_DATE_TIME);
        assertThat(testUserLogin.getLoginToken()).isEqualTo(DEFAULT_LOGIN_TOKEN);
        assertThat(testUserLogin.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserLogin.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createUserLoginWithExistingId() throws Exception {
        // Create the UserLogin with an existing ID
        userLogin.setId(1L);

        int databaseSizeBeforeCreate = userLoginRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserLoginMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userLogin)))
            .andExpect(status().isBadRequest());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLocationIPIsRequired() throws Exception {
        int databaseSizeBeforeTest = userLoginRepository.findAll().size();
        // set the field null
        userLogin.setLocationIP(null);

        // Create the UserLogin, which fails.

        restUserLoginMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userLogin)))
            .andExpect(status().isBadRequest());

        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationDetailsIsRequired() throws Exception {
        int databaseSizeBeforeTest = userLoginRepository.findAll().size();
        // set the field null
        userLogin.setLocationDetails(null);

        // Create the UserLogin, which fails.

        restUserLoginMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userLogin)))
            .andExpect(status().isBadRequest());

        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLatlogIsRequired() throws Exception {
        int databaseSizeBeforeTest = userLoginRepository.findAll().size();
        // set the field null
        userLogin.setLatlog(null);

        // Create the UserLogin, which fails.

        restUserLoginMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userLogin)))
            .andExpect(status().isBadRequest());

        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBrowserIsRequired() throws Exception {
        int databaseSizeBeforeTest = userLoginRepository.findAll().size();
        // set the field null
        userLogin.setBrowser(null);

        // Create the UserLogin, which fails.

        restUserLoginMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userLogin)))
            .andExpect(status().isBadRequest());

        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeviceIsRequired() throws Exception {
        int databaseSizeBeforeTest = userLoginRepository.findAll().size();
        // set the field null
        userLogin.setDevice(null);

        // Create the UserLogin, which fails.

        restUserLoginMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userLogin)))
            .andExpect(status().isBadRequest());

        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserLogins() throws Exception {
        // Initialize the database
        userLoginRepository.saveAndFlush(userLogin);

        // Get all the userLoginList
        restUserLoginMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userLogin.getId().intValue())))
            .andExpect(jsonPath("$.[*].loginType").value(hasItem(DEFAULT_LOGIN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].emailOTP").value(hasItem(DEFAULT_EMAIL_OTP)))
            .andExpect(jsonPath("$.[*].phoneOTP").value(hasItem(DEFAULT_PHONE_OTP)))
            .andExpect(jsonPath("$.[*].emailOTPExpiryDate").value(hasItem(DEFAULT_EMAIL_OTP_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].phoneOTPExpiryDate").value(hasItem(DEFAULT_PHONE_OTP_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].locationIP").value(hasItem(DEFAULT_LOCATION_IP)))
            .andExpect(jsonPath("$.[*].locationDetails").value(hasItem(DEFAULT_LOCATION_DETAILS)))
            .andExpect(jsonPath("$.[*].latlog").value(hasItem(DEFAULT_LATLOG)))
            .andExpect(jsonPath("$.[*].browser").value(hasItem(DEFAULT_BROWSER)))
            .andExpect(jsonPath("$.[*].device").value(hasItem(DEFAULT_DEVICE)))
            .andExpect(jsonPath("$.[*].loginDateTime").value(hasItem(DEFAULT_LOGIN_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].loginToken").value(hasItem(DEFAULT_LOGIN_TOKEN)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getUserLogin() throws Exception {
        // Initialize the database
        userLoginRepository.saveAndFlush(userLogin);

        // Get the userLogin
        restUserLoginMockMvc
            .perform(get(ENTITY_API_URL_ID, userLogin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userLogin.getId().intValue()))
            .andExpect(jsonPath("$.loginType").value(DEFAULT_LOGIN_TYPE.toString()))
            .andExpect(jsonPath("$.emailOTP").value(DEFAULT_EMAIL_OTP))
            .andExpect(jsonPath("$.phoneOTP").value(DEFAULT_PHONE_OTP))
            .andExpect(jsonPath("$.emailOTPExpiryDate").value(DEFAULT_EMAIL_OTP_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.phoneOTPExpiryDate").value(DEFAULT_PHONE_OTP_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.locationIP").value(DEFAULT_LOCATION_IP))
            .andExpect(jsonPath("$.locationDetails").value(DEFAULT_LOCATION_DETAILS))
            .andExpect(jsonPath("$.latlog").value(DEFAULT_LATLOG))
            .andExpect(jsonPath("$.browser").value(DEFAULT_BROWSER))
            .andExpect(jsonPath("$.device").value(DEFAULT_DEVICE))
            .andExpect(jsonPath("$.loginDateTime").value(DEFAULT_LOGIN_DATE_TIME.toString()))
            .andExpect(jsonPath("$.loginToken").value(DEFAULT_LOGIN_TOKEN))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserLogin() throws Exception {
        // Get the userLogin
        restUserLoginMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserLogin() throws Exception {
        // Initialize the database
        userLoginRepository.saveAndFlush(userLogin);

        int databaseSizeBeforeUpdate = userLoginRepository.findAll().size();

        // Update the userLogin
        UserLogin updatedUserLogin = userLoginRepository.findById(userLogin.getId()).get();
        // Disconnect from session so that the updates on updatedUserLogin are not directly saved in db
        em.detach(updatedUserLogin);
        updatedUserLogin
            .loginType(UPDATED_LOGIN_TYPE)
            .emailOTP(UPDATED_EMAIL_OTP)
            .phoneOTP(UPDATED_PHONE_OTP)
            .emailOTPExpiryDate(UPDATED_EMAIL_OTP_EXPIRY_DATE)
            .phoneOTPExpiryDate(UPDATED_PHONE_OTP_EXPIRY_DATE)
            .locationIP(UPDATED_LOCATION_IP)
            .locationDetails(UPDATED_LOCATION_DETAILS)
            .latlog(UPDATED_LATLOG)
            .browser(UPDATED_BROWSER)
            .device(UPDATED_DEVICE)
            .loginDateTime(UPDATED_LOGIN_DATE_TIME)
            .loginToken(UPDATED_LOGIN_TOKEN)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserLoginMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserLogin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserLogin))
            )
            .andExpect(status().isOk());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeUpdate);
        UserLogin testUserLogin = userLoginList.get(userLoginList.size() - 1);
        assertThat(testUserLogin.getLoginType()).isEqualTo(UPDATED_LOGIN_TYPE);
        assertThat(testUserLogin.getEmailOTP()).isEqualTo(UPDATED_EMAIL_OTP);
        assertThat(testUserLogin.getPhoneOTP()).isEqualTo(UPDATED_PHONE_OTP);
        assertThat(testUserLogin.getEmailOTPExpiryDate()).isEqualTo(UPDATED_EMAIL_OTP_EXPIRY_DATE);
        assertThat(testUserLogin.getPhoneOTPExpiryDate()).isEqualTo(UPDATED_PHONE_OTP_EXPIRY_DATE);
        assertThat(testUserLogin.getLocationIP()).isEqualTo(UPDATED_LOCATION_IP);
        assertThat(testUserLogin.getLocationDetails()).isEqualTo(UPDATED_LOCATION_DETAILS);
        assertThat(testUserLogin.getLatlog()).isEqualTo(UPDATED_LATLOG);
        assertThat(testUserLogin.getBrowser()).isEqualTo(UPDATED_BROWSER);
        assertThat(testUserLogin.getDevice()).isEqualTo(UPDATED_DEVICE);
        assertThat(testUserLogin.getLoginDateTime()).isEqualTo(UPDATED_LOGIN_DATE_TIME);
        assertThat(testUserLogin.getLoginToken()).isEqualTo(UPDATED_LOGIN_TOKEN);
        assertThat(testUserLogin.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserLogin.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserLogin() throws Exception {
        int databaseSizeBeforeUpdate = userLoginRepository.findAll().size();
        userLogin.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserLoginMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userLogin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userLogin))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserLogin() throws Exception {
        int databaseSizeBeforeUpdate = userLoginRepository.findAll().size();
        userLogin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserLoginMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userLogin))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserLogin() throws Exception {
        int databaseSizeBeforeUpdate = userLoginRepository.findAll().size();
        userLogin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserLoginMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userLogin)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserLoginWithPatch() throws Exception {
        // Initialize the database
        userLoginRepository.saveAndFlush(userLogin);

        int databaseSizeBeforeUpdate = userLoginRepository.findAll().size();

        // Update the userLogin using partial update
        UserLogin partialUpdatedUserLogin = new UserLogin();
        partialUpdatedUserLogin.setId(userLogin.getId());

        partialUpdatedUserLogin
            .loginType(UPDATED_LOGIN_TYPE)
            .phoneOTP(UPDATED_PHONE_OTP)
            .locationIP(UPDATED_LOCATION_IP)
            .locationDetails(UPDATED_LOCATION_DETAILS)
            .latlog(UPDATED_LATLOG)
            .browser(UPDATED_BROWSER)
            .loginToken(UPDATED_LOGIN_TOKEN)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserLoginMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserLogin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserLogin))
            )
            .andExpect(status().isOk());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeUpdate);
        UserLogin testUserLogin = userLoginList.get(userLoginList.size() - 1);
        assertThat(testUserLogin.getLoginType()).isEqualTo(UPDATED_LOGIN_TYPE);
        assertThat(testUserLogin.getEmailOTP()).isEqualTo(DEFAULT_EMAIL_OTP);
        assertThat(testUserLogin.getPhoneOTP()).isEqualTo(UPDATED_PHONE_OTP);
        assertThat(testUserLogin.getEmailOTPExpiryDate()).isEqualTo(DEFAULT_EMAIL_OTP_EXPIRY_DATE);
        assertThat(testUserLogin.getPhoneOTPExpiryDate()).isEqualTo(DEFAULT_PHONE_OTP_EXPIRY_DATE);
        assertThat(testUserLogin.getLocationIP()).isEqualTo(UPDATED_LOCATION_IP);
        assertThat(testUserLogin.getLocationDetails()).isEqualTo(UPDATED_LOCATION_DETAILS);
        assertThat(testUserLogin.getLatlog()).isEqualTo(UPDATED_LATLOG);
        assertThat(testUserLogin.getBrowser()).isEqualTo(UPDATED_BROWSER);
        assertThat(testUserLogin.getDevice()).isEqualTo(DEFAULT_DEVICE);
        assertThat(testUserLogin.getLoginDateTime()).isEqualTo(DEFAULT_LOGIN_DATE_TIME);
        assertThat(testUserLogin.getLoginToken()).isEqualTo(UPDATED_LOGIN_TOKEN);
        assertThat(testUserLogin.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserLogin.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserLoginWithPatch() throws Exception {
        // Initialize the database
        userLoginRepository.saveAndFlush(userLogin);

        int databaseSizeBeforeUpdate = userLoginRepository.findAll().size();

        // Update the userLogin using partial update
        UserLogin partialUpdatedUserLogin = new UserLogin();
        partialUpdatedUserLogin.setId(userLogin.getId());

        partialUpdatedUserLogin
            .loginType(UPDATED_LOGIN_TYPE)
            .emailOTP(UPDATED_EMAIL_OTP)
            .phoneOTP(UPDATED_PHONE_OTP)
            .emailOTPExpiryDate(UPDATED_EMAIL_OTP_EXPIRY_DATE)
            .phoneOTPExpiryDate(UPDATED_PHONE_OTP_EXPIRY_DATE)
            .locationIP(UPDATED_LOCATION_IP)
            .locationDetails(UPDATED_LOCATION_DETAILS)
            .latlog(UPDATED_LATLOG)
            .browser(UPDATED_BROWSER)
            .device(UPDATED_DEVICE)
            .loginDateTime(UPDATED_LOGIN_DATE_TIME)
            .loginToken(UPDATED_LOGIN_TOKEN)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserLoginMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserLogin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserLogin))
            )
            .andExpect(status().isOk());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeUpdate);
        UserLogin testUserLogin = userLoginList.get(userLoginList.size() - 1);
        assertThat(testUserLogin.getLoginType()).isEqualTo(UPDATED_LOGIN_TYPE);
        assertThat(testUserLogin.getEmailOTP()).isEqualTo(UPDATED_EMAIL_OTP);
        assertThat(testUserLogin.getPhoneOTP()).isEqualTo(UPDATED_PHONE_OTP);
        assertThat(testUserLogin.getEmailOTPExpiryDate()).isEqualTo(UPDATED_EMAIL_OTP_EXPIRY_DATE);
        assertThat(testUserLogin.getPhoneOTPExpiryDate()).isEqualTo(UPDATED_PHONE_OTP_EXPIRY_DATE);
        assertThat(testUserLogin.getLocationIP()).isEqualTo(UPDATED_LOCATION_IP);
        assertThat(testUserLogin.getLocationDetails()).isEqualTo(UPDATED_LOCATION_DETAILS);
        assertThat(testUserLogin.getLatlog()).isEqualTo(UPDATED_LATLOG);
        assertThat(testUserLogin.getBrowser()).isEqualTo(UPDATED_BROWSER);
        assertThat(testUserLogin.getDevice()).isEqualTo(UPDATED_DEVICE);
        assertThat(testUserLogin.getLoginDateTime()).isEqualTo(UPDATED_LOGIN_DATE_TIME);
        assertThat(testUserLogin.getLoginToken()).isEqualTo(UPDATED_LOGIN_TOKEN);
        assertThat(testUserLogin.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserLogin.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserLogin() throws Exception {
        int databaseSizeBeforeUpdate = userLoginRepository.findAll().size();
        userLogin.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserLoginMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userLogin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userLogin))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserLogin() throws Exception {
        int databaseSizeBeforeUpdate = userLoginRepository.findAll().size();
        userLogin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserLoginMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userLogin))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserLogin() throws Exception {
        int databaseSizeBeforeUpdate = userLoginRepository.findAll().size();
        userLogin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserLoginMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userLogin))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserLogin() throws Exception {
        // Initialize the database
        userLoginRepository.saveAndFlush(userLogin);

        int databaseSizeBeforeDelete = userLoginRepository.findAll().size();

        // Delete the userLogin
        restUserLoginMockMvc
            .perform(delete(ENTITY_API_URL_ID, userLogin.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
