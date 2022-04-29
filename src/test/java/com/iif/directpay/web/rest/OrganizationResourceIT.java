package com.iif.directpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iif.directpay.IntegrationTest;
import com.iif.directpay.domain.Organization;
import com.iif.directpay.domain.enumeration.OrganizationStatus;
import com.iif.directpay.domain.enumeration.OrganizationTaxCategory;
import com.iif.directpay.domain.enumeration.OrganizationType;
import com.iif.directpay.repository.OrganizationRepository;
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
 * Integration tests for the {@link OrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ALIAS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PHONE = 1;
    private static final Integer UPDATED_PHONE = 2;

    private static final Integer DEFAULT_MOBILE = 1;
    private static final Integer UPDATED_MOBILE = 2;

    private static final String DEFAULT_REPRESENTATIVE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REPRESENTATIVE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REPRESENTATIVE_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_REPRESENTATIVE_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_REPRESENTATIVE_PHONE = 1;
    private static final Integer UPDATED_REPRESENTATIVE_PHONE = 2;

    private static final String DEFAULT_REGISTRATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_NUMBER = "BBBBBBBBBB";

    private static final OrganizationType DEFAULT_ORGANIZATION_TYPE = OrganizationType.NON_PROFIT;
    private static final OrganizationType UPDATED_ORGANIZATION_TYPE = OrganizationType.SOCIETY;

    private static final String DEFAULT_ORGANIZATION_TYPE_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_TYPE_OTHER = "BBBBBBBBBB";

    private static final OrganizationTaxCategory DEFAULT_ORGANIZATION_TAX_CATEGORY = OrganizationTaxCategory.NO_TAX_EXEMPTION;
    private static final OrganizationTaxCategory UPDATED_ORGANIZATION_TAX_CATEGORY = OrganizationTaxCategory.E80G;

    private static final String DEFAULT_ORGANIZATION_TAX_CATEGORY_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_TAX_CATEGORY_OTHER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ESTABLISHED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTABLISHED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_TOTAL_EMPLOYEES_NUMBER = 1;
    private static final Integer UPDATED_TOTAL_EMPLOYEES_NUMBER = 2;

    private static final Instant DEFAULT_JOIN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JOIN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_SUBSCRIPTION_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBSCRIPTION_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SUBSCRIPTION_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBSCRIPTION_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final OrganizationStatus DEFAULT_STATUS = OrganizationStatus.INACTIVE;
    private static final OrganizationStatus UPDATED_STATUS = OrganizationStatus.ACTIVE;

    private static final Boolean DEFAULT_IS_VERIFIED = false;
    private static final Boolean UPDATED_IS_VERIFIED = true;

    private static final Instant DEFAULT_ACTIVATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SUSPENDED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUSPENDED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createEntity(EntityManager em) {
        Organization organization = new Organization()
            .name(DEFAULT_NAME)
            .aliasName(DEFAULT_ALIAS_NAME)
            .email(DEFAULT_EMAIL)
            .website(DEFAULT_WEBSITE)
            .phone(DEFAULT_PHONE)
            .mobile(DEFAULT_MOBILE)
            .representativeName(DEFAULT_REPRESENTATIVE_NAME)
            .representativeEmail(DEFAULT_REPRESENTATIVE_EMAIL)
            .representativePhone(DEFAULT_REPRESENTATIVE_PHONE)
            .registrationNumber(DEFAULT_REGISTRATION_NUMBER)
            .organizationType(DEFAULT_ORGANIZATION_TYPE)
            .organizationTypeOther(DEFAULT_ORGANIZATION_TYPE_OTHER)
            .organizationTaxCategory(DEFAULT_ORGANIZATION_TAX_CATEGORY)
            .organizationTaxCategoryOther(DEFAULT_ORGANIZATION_TAX_CATEGORY_OTHER)
            .establishedDate(DEFAULT_ESTABLISHED_DATE)
            .totalEmployeesNumber(DEFAULT_TOTAL_EMPLOYEES_NUMBER)
            .joinDate(DEFAULT_JOIN_DATE)
            .subscriptionStartDate(DEFAULT_SUBSCRIPTION_START_DATE)
            .subscriptionEndDate(DEFAULT_SUBSCRIPTION_END_DATE)
            .status(DEFAULT_STATUS)
            .isVerified(DEFAULT_IS_VERIFIED)
            .activatedDate(DEFAULT_ACTIVATED_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .deletedDate(DEFAULT_DELETED_DATE)
            .suspendedDate(DEFAULT_SUSPENDED_DATE);
        return organization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createUpdatedEntity(EntityManager em) {
        Organization organization = new Organization()
            .name(UPDATED_NAME)
            .aliasName(UPDATED_ALIAS_NAME)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .phone(UPDATED_PHONE)
            .mobile(UPDATED_MOBILE)
            .representativeName(UPDATED_REPRESENTATIVE_NAME)
            .representativeEmail(UPDATED_REPRESENTATIVE_EMAIL)
            .representativePhone(UPDATED_REPRESENTATIVE_PHONE)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .organizationType(UPDATED_ORGANIZATION_TYPE)
            .organizationTypeOther(UPDATED_ORGANIZATION_TYPE_OTHER)
            .organizationTaxCategory(UPDATED_ORGANIZATION_TAX_CATEGORY)
            .organizationTaxCategoryOther(UPDATED_ORGANIZATION_TAX_CATEGORY_OTHER)
            .establishedDate(UPDATED_ESTABLISHED_DATE)
            .totalEmployeesNumber(UPDATED_TOTAL_EMPLOYEES_NUMBER)
            .joinDate(UPDATED_JOIN_DATE)
            .subscriptionStartDate(UPDATED_SUBSCRIPTION_START_DATE)
            .subscriptionEndDate(UPDATED_SUBSCRIPTION_END_DATE)
            .status(UPDATED_STATUS)
            .isVerified(UPDATED_IS_VERIFIED)
            .activatedDate(UPDATED_ACTIVATED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE)
            .suspendedDate(UPDATED_SUSPENDED_DATE);
        return organization;
    }

    @BeforeEach
    public void initTest() {
        organization = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganization() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();
        // Create the Organization
        restOrganizationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate + 1);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganization.getAliasName()).isEqualTo(DEFAULT_ALIAS_NAME);
        assertThat(testOrganization.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrganization.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testOrganization.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testOrganization.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testOrganization.getRepresentativeName()).isEqualTo(DEFAULT_REPRESENTATIVE_NAME);
        assertThat(testOrganization.getRepresentativeEmail()).isEqualTo(DEFAULT_REPRESENTATIVE_EMAIL);
        assertThat(testOrganization.getRepresentativePhone()).isEqualTo(DEFAULT_REPRESENTATIVE_PHONE);
        assertThat(testOrganization.getRegistrationNumber()).isEqualTo(DEFAULT_REGISTRATION_NUMBER);
        assertThat(testOrganization.getOrganizationType()).isEqualTo(DEFAULT_ORGANIZATION_TYPE);
        assertThat(testOrganization.getOrganizationTypeOther()).isEqualTo(DEFAULT_ORGANIZATION_TYPE_OTHER);
        assertThat(testOrganization.getOrganizationTaxCategory()).isEqualTo(DEFAULT_ORGANIZATION_TAX_CATEGORY);
        assertThat(testOrganization.getOrganizationTaxCategoryOther()).isEqualTo(DEFAULT_ORGANIZATION_TAX_CATEGORY_OTHER);
        assertThat(testOrganization.getEstablishedDate()).isEqualTo(DEFAULT_ESTABLISHED_DATE);
        assertThat(testOrganization.getTotalEmployeesNumber()).isEqualTo(DEFAULT_TOTAL_EMPLOYEES_NUMBER);
        assertThat(testOrganization.getJoinDate()).isEqualTo(DEFAULT_JOIN_DATE);
        assertThat(testOrganization.getSubscriptionStartDate()).isEqualTo(DEFAULT_SUBSCRIPTION_START_DATE);
        assertThat(testOrganization.getSubscriptionEndDate()).isEqualTo(DEFAULT_SUBSCRIPTION_END_DATE);
        assertThat(testOrganization.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrganization.getIsVerified()).isEqualTo(DEFAULT_IS_VERIFIED);
        assertThat(testOrganization.getActivatedDate()).isEqualTo(DEFAULT_ACTIVATED_DATE);
        assertThat(testOrganization.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOrganization.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testOrganization.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
        assertThat(testOrganization.getSuspendedDate()).isEqualTo(DEFAULT_SUSPENDED_DATE);
    }

    @Test
    @Transactional
    void createOrganizationWithExistingId() throws Exception {
        // Create the Organization with an existing ID
        organization.setId(1L);

        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setName(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setEmail(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setPhone(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setStatus(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].aliasName").value(hasItem(DEFAULT_ALIAS_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].representativeName").value(hasItem(DEFAULT_REPRESENTATIVE_NAME)))
            .andExpect(jsonPath("$.[*].representativeEmail").value(hasItem(DEFAULT_REPRESENTATIVE_EMAIL)))
            .andExpect(jsonPath("$.[*].representativePhone").value(hasItem(DEFAULT_REPRESENTATIVE_PHONE)))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].organizationType").value(hasItem(DEFAULT_ORGANIZATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].organizationTypeOther").value(hasItem(DEFAULT_ORGANIZATION_TYPE_OTHER)))
            .andExpect(jsonPath("$.[*].organizationTaxCategory").value(hasItem(DEFAULT_ORGANIZATION_TAX_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].organizationTaxCategoryOther").value(hasItem(DEFAULT_ORGANIZATION_TAX_CATEGORY_OTHER)))
            .andExpect(jsonPath("$.[*].establishedDate").value(hasItem(DEFAULT_ESTABLISHED_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalEmployeesNumber").value(hasItem(DEFAULT_TOTAL_EMPLOYEES_NUMBER)))
            .andExpect(jsonPath("$.[*].joinDate").value(hasItem(DEFAULT_JOIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].subscriptionStartDate").value(hasItem(DEFAULT_SUBSCRIPTION_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].subscriptionEndDate").value(hasItem(DEFAULT_SUBSCRIPTION_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].isVerified").value(hasItem(DEFAULT_IS_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].activatedDate").value(hasItem(DEFAULT_ACTIVATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].suspendedDate").value(hasItem(DEFAULT_SUSPENDED_DATE.toString())));
    }

    @Test
    @Transactional
    void getOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get the organization
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organization.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.aliasName").value(DEFAULT_ALIAS_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.representativeName").value(DEFAULT_REPRESENTATIVE_NAME))
            .andExpect(jsonPath("$.representativeEmail").value(DEFAULT_REPRESENTATIVE_EMAIL))
            .andExpect(jsonPath("$.representativePhone").value(DEFAULT_REPRESENTATIVE_PHONE))
            .andExpect(jsonPath("$.registrationNumber").value(DEFAULT_REGISTRATION_NUMBER))
            .andExpect(jsonPath("$.organizationType").value(DEFAULT_ORGANIZATION_TYPE.toString()))
            .andExpect(jsonPath("$.organizationTypeOther").value(DEFAULT_ORGANIZATION_TYPE_OTHER))
            .andExpect(jsonPath("$.organizationTaxCategory").value(DEFAULT_ORGANIZATION_TAX_CATEGORY.toString()))
            .andExpect(jsonPath("$.organizationTaxCategoryOther").value(DEFAULT_ORGANIZATION_TAX_CATEGORY_OTHER))
            .andExpect(jsonPath("$.establishedDate").value(DEFAULT_ESTABLISHED_DATE.toString()))
            .andExpect(jsonPath("$.totalEmployeesNumber").value(DEFAULT_TOTAL_EMPLOYEES_NUMBER))
            .andExpect(jsonPath("$.joinDate").value(DEFAULT_JOIN_DATE.toString()))
            .andExpect(jsonPath("$.subscriptionStartDate").value(DEFAULT_SUBSCRIPTION_START_DATE.toString()))
            .andExpect(jsonPath("$.subscriptionEndDate").value(DEFAULT_SUBSCRIPTION_END_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.isVerified").value(DEFAULT_IS_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.activatedDate").value(DEFAULT_ACTIVATED_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()))
            .andExpect(jsonPath("$.suspendedDate").value(DEFAULT_SUSPENDED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization
        Organization updatedOrganization = organizationRepository.findById(organization.getId()).get();
        // Disconnect from session so that the updates on updatedOrganization are not directly saved in db
        em.detach(updatedOrganization);
        updatedOrganization
            .name(UPDATED_NAME)
            .aliasName(UPDATED_ALIAS_NAME)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .phone(UPDATED_PHONE)
            .mobile(UPDATED_MOBILE)
            .representativeName(UPDATED_REPRESENTATIVE_NAME)
            .representativeEmail(UPDATED_REPRESENTATIVE_EMAIL)
            .representativePhone(UPDATED_REPRESENTATIVE_PHONE)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .organizationType(UPDATED_ORGANIZATION_TYPE)
            .organizationTypeOther(UPDATED_ORGANIZATION_TYPE_OTHER)
            .organizationTaxCategory(UPDATED_ORGANIZATION_TAX_CATEGORY)
            .organizationTaxCategoryOther(UPDATED_ORGANIZATION_TAX_CATEGORY_OTHER)
            .establishedDate(UPDATED_ESTABLISHED_DATE)
            .totalEmployeesNumber(UPDATED_TOTAL_EMPLOYEES_NUMBER)
            .joinDate(UPDATED_JOIN_DATE)
            .subscriptionStartDate(UPDATED_SUBSCRIPTION_START_DATE)
            .subscriptionEndDate(UPDATED_SUBSCRIPTION_END_DATE)
            .status(UPDATED_STATUS)
            .isVerified(UPDATED_IS_VERIFIED)
            .activatedDate(UPDATED_ACTIVATED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE)
            .suspendedDate(UPDATED_SUSPENDED_DATE);

        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrganization.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganization.getAliasName()).isEqualTo(UPDATED_ALIAS_NAME);
        assertThat(testOrganization.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrganization.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testOrganization.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOrganization.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testOrganization.getRepresentativeName()).isEqualTo(UPDATED_REPRESENTATIVE_NAME);
        assertThat(testOrganization.getRepresentativeEmail()).isEqualTo(UPDATED_REPRESENTATIVE_EMAIL);
        assertThat(testOrganization.getRepresentativePhone()).isEqualTo(UPDATED_REPRESENTATIVE_PHONE);
        assertThat(testOrganization.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testOrganization.getOrganizationType()).isEqualTo(UPDATED_ORGANIZATION_TYPE);
        assertThat(testOrganization.getOrganizationTypeOther()).isEqualTo(UPDATED_ORGANIZATION_TYPE_OTHER);
        assertThat(testOrganization.getOrganizationTaxCategory()).isEqualTo(UPDATED_ORGANIZATION_TAX_CATEGORY);
        assertThat(testOrganization.getOrganizationTaxCategoryOther()).isEqualTo(UPDATED_ORGANIZATION_TAX_CATEGORY_OTHER);
        assertThat(testOrganization.getEstablishedDate()).isEqualTo(UPDATED_ESTABLISHED_DATE);
        assertThat(testOrganization.getTotalEmployeesNumber()).isEqualTo(UPDATED_TOTAL_EMPLOYEES_NUMBER);
        assertThat(testOrganization.getJoinDate()).isEqualTo(UPDATED_JOIN_DATE);
        assertThat(testOrganization.getSubscriptionStartDate()).isEqualTo(UPDATED_SUBSCRIPTION_START_DATE);
        assertThat(testOrganization.getSubscriptionEndDate()).isEqualTo(UPDATED_SUBSCRIPTION_END_DATE);
        assertThat(testOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganization.getIsVerified()).isEqualTo(UPDATED_IS_VERIFIED);
        assertThat(testOrganization.getActivatedDate()).isEqualTo(UPDATED_ACTIVATED_DATE);
        assertThat(testOrganization.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrganization.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testOrganization.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
        assertThat(testOrganization.getSuspendedDate()).isEqualTo(UPDATED_SUSPENDED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organization.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organization))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organization))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationWithPatch() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization using partial update
        Organization partialUpdatedOrganization = new Organization();
        partialUpdatedOrganization.setId(organization.getId());

        partialUpdatedOrganization
            .name(UPDATED_NAME)
            .website(UPDATED_WEBSITE)
            .phone(UPDATED_PHONE)
            .representativeEmail(UPDATED_REPRESENTATIVE_EMAIL)
            .representativePhone(UPDATED_REPRESENTATIVE_PHONE)
            .organizationType(UPDATED_ORGANIZATION_TYPE)
            .organizationTaxCategory(UPDATED_ORGANIZATION_TAX_CATEGORY)
            .establishedDate(UPDATED_ESTABLISHED_DATE)
            .subscriptionEndDate(UPDATED_SUBSCRIPTION_END_DATE)
            .isVerified(UPDATED_IS_VERIFIED)
            .activatedDate(UPDATED_ACTIVATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .suspendedDate(UPDATED_SUSPENDED_DATE);

        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganization.getAliasName()).isEqualTo(DEFAULT_ALIAS_NAME);
        assertThat(testOrganization.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrganization.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testOrganization.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOrganization.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testOrganization.getRepresentativeName()).isEqualTo(DEFAULT_REPRESENTATIVE_NAME);
        assertThat(testOrganization.getRepresentativeEmail()).isEqualTo(UPDATED_REPRESENTATIVE_EMAIL);
        assertThat(testOrganization.getRepresentativePhone()).isEqualTo(UPDATED_REPRESENTATIVE_PHONE);
        assertThat(testOrganization.getRegistrationNumber()).isEqualTo(DEFAULT_REGISTRATION_NUMBER);
        assertThat(testOrganization.getOrganizationType()).isEqualTo(UPDATED_ORGANIZATION_TYPE);
        assertThat(testOrganization.getOrganizationTypeOther()).isEqualTo(DEFAULT_ORGANIZATION_TYPE_OTHER);
        assertThat(testOrganization.getOrganizationTaxCategory()).isEqualTo(UPDATED_ORGANIZATION_TAX_CATEGORY);
        assertThat(testOrganization.getOrganizationTaxCategoryOther()).isEqualTo(DEFAULT_ORGANIZATION_TAX_CATEGORY_OTHER);
        assertThat(testOrganization.getEstablishedDate()).isEqualTo(UPDATED_ESTABLISHED_DATE);
        assertThat(testOrganization.getTotalEmployeesNumber()).isEqualTo(DEFAULT_TOTAL_EMPLOYEES_NUMBER);
        assertThat(testOrganization.getJoinDate()).isEqualTo(DEFAULT_JOIN_DATE);
        assertThat(testOrganization.getSubscriptionStartDate()).isEqualTo(DEFAULT_SUBSCRIPTION_START_DATE);
        assertThat(testOrganization.getSubscriptionEndDate()).isEqualTo(UPDATED_SUBSCRIPTION_END_DATE);
        assertThat(testOrganization.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrganization.getIsVerified()).isEqualTo(UPDATED_IS_VERIFIED);
        assertThat(testOrganization.getActivatedDate()).isEqualTo(UPDATED_ACTIVATED_DATE);
        assertThat(testOrganization.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOrganization.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testOrganization.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
        assertThat(testOrganization.getSuspendedDate()).isEqualTo(UPDATED_SUSPENDED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOrganizationWithPatch() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization using partial update
        Organization partialUpdatedOrganization = new Organization();
        partialUpdatedOrganization.setId(organization.getId());

        partialUpdatedOrganization
            .name(UPDATED_NAME)
            .aliasName(UPDATED_ALIAS_NAME)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .phone(UPDATED_PHONE)
            .mobile(UPDATED_MOBILE)
            .representativeName(UPDATED_REPRESENTATIVE_NAME)
            .representativeEmail(UPDATED_REPRESENTATIVE_EMAIL)
            .representativePhone(UPDATED_REPRESENTATIVE_PHONE)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .organizationType(UPDATED_ORGANIZATION_TYPE)
            .organizationTypeOther(UPDATED_ORGANIZATION_TYPE_OTHER)
            .organizationTaxCategory(UPDATED_ORGANIZATION_TAX_CATEGORY)
            .organizationTaxCategoryOther(UPDATED_ORGANIZATION_TAX_CATEGORY_OTHER)
            .establishedDate(UPDATED_ESTABLISHED_DATE)
            .totalEmployeesNumber(UPDATED_TOTAL_EMPLOYEES_NUMBER)
            .joinDate(UPDATED_JOIN_DATE)
            .subscriptionStartDate(UPDATED_SUBSCRIPTION_START_DATE)
            .subscriptionEndDate(UPDATED_SUBSCRIPTION_END_DATE)
            .status(UPDATED_STATUS)
            .isVerified(UPDATED_IS_VERIFIED)
            .activatedDate(UPDATED_ACTIVATED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE)
            .suspendedDate(UPDATED_SUSPENDED_DATE);

        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganization.getAliasName()).isEqualTo(UPDATED_ALIAS_NAME);
        assertThat(testOrganization.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrganization.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testOrganization.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOrganization.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testOrganization.getRepresentativeName()).isEqualTo(UPDATED_REPRESENTATIVE_NAME);
        assertThat(testOrganization.getRepresentativeEmail()).isEqualTo(UPDATED_REPRESENTATIVE_EMAIL);
        assertThat(testOrganization.getRepresentativePhone()).isEqualTo(UPDATED_REPRESENTATIVE_PHONE);
        assertThat(testOrganization.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testOrganization.getOrganizationType()).isEqualTo(UPDATED_ORGANIZATION_TYPE);
        assertThat(testOrganization.getOrganizationTypeOther()).isEqualTo(UPDATED_ORGANIZATION_TYPE_OTHER);
        assertThat(testOrganization.getOrganizationTaxCategory()).isEqualTo(UPDATED_ORGANIZATION_TAX_CATEGORY);
        assertThat(testOrganization.getOrganizationTaxCategoryOther()).isEqualTo(UPDATED_ORGANIZATION_TAX_CATEGORY_OTHER);
        assertThat(testOrganization.getEstablishedDate()).isEqualTo(UPDATED_ESTABLISHED_DATE);
        assertThat(testOrganization.getTotalEmployeesNumber()).isEqualTo(UPDATED_TOTAL_EMPLOYEES_NUMBER);
        assertThat(testOrganization.getJoinDate()).isEqualTo(UPDATED_JOIN_DATE);
        assertThat(testOrganization.getSubscriptionStartDate()).isEqualTo(UPDATED_SUBSCRIPTION_START_DATE);
        assertThat(testOrganization.getSubscriptionEndDate()).isEqualTo(UPDATED_SUBSCRIPTION_END_DATE);
        assertThat(testOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganization.getIsVerified()).isEqualTo(UPDATED_IS_VERIFIED);
        assertThat(testOrganization.getActivatedDate()).isEqualTo(UPDATED_ACTIVATED_DATE);
        assertThat(testOrganization.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrganization.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testOrganization.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
        assertThat(testOrganization.getSuspendedDate()).isEqualTo(UPDATED_SUSPENDED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organization))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organization))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(organization))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeDelete = organizationRepository.findAll().size();

        // Delete the organization
        restOrganizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, organization.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
