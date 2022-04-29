package com.iif.directpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iif.directpay.IntegrationTest;
import com.iif.directpay.domain.OrganizationSubscriptionPlan;
import com.iif.directpay.domain.enumeration.SubscriptionStatus;
import com.iif.directpay.domain.enumeration.SubscriptionType;
import com.iif.directpay.repository.OrganizationSubscriptionPlanRepository;
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
 * Integration tests for the {@link OrganizationSubscriptionPlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizationSubscriptionPlanResourceIT {

    private static final String DEFAULT_SUBSCRIPTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUBSCRIPTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUBSCRIPTION_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUBSCRIPTION_TITLE = "BBBBBBBBBB";

    private static final SubscriptionType DEFAULT_SUBSCRIPTION_TYPE = SubscriptionType.FREE;
    private static final SubscriptionType UPDATED_SUBSCRIPTION_TYPE = SubscriptionType.PAID;

    private static final Double DEFAULT_SUBSCRIPTION_PRICE = 1D;
    private static final Double UPDATED_SUBSCRIPTION_PRICE = 2D;

    private static final Integer DEFAULT_SUBSCRIPTION_QUANTITY = 1;
    private static final Integer UPDATED_SUBSCRIPTION_QUANTITY = 2;

    private static final String DEFAULT_SUBSCRIPTION_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_SUBSCRIPTION_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_SUBSCRIPTION_TERMS = "AAAAAAAAAA";
    private static final String UPDATED_SUBSCRIPTION_TERMS = "BBBBBBBBBB";

    private static final String DEFAULT_COUPON_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUPON_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_SUSPENDED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUSPENDED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final SubscriptionStatus DEFAULT_STATUS = SubscriptionStatus.INACTIVE;
    private static final SubscriptionStatus UPDATED_STATUS = SubscriptionStatus.ACTIVE;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/organization-subscription-plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganizationSubscriptionPlanRepository organizationSubscriptionPlanRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationSubscriptionPlanMockMvc;

    private OrganizationSubscriptionPlan organizationSubscriptionPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationSubscriptionPlan createEntity(EntityManager em) {
        OrganizationSubscriptionPlan organizationSubscriptionPlan = new OrganizationSubscriptionPlan()
            .subscriptionName(DEFAULT_SUBSCRIPTION_NAME)
            .subscriptionTitle(DEFAULT_SUBSCRIPTION_TITLE)
            .subscriptionType(DEFAULT_SUBSCRIPTION_TYPE)
            .subscriptionPrice(DEFAULT_SUBSCRIPTION_PRICE)
            .subscriptionQuantity(DEFAULT_SUBSCRIPTION_QUANTITY)
            .subscriptionPeriod(DEFAULT_SUBSCRIPTION_PERIOD)
            .subscriptionTerms(DEFAULT_SUBSCRIPTION_TERMS)
            .couponCode(DEFAULT_COUPON_CODE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .suspendedDate(DEFAULT_SUSPENDED_DATE)
            .deletedDate(DEFAULT_DELETED_DATE)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return organizationSubscriptionPlan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationSubscriptionPlan createUpdatedEntity(EntityManager em) {
        OrganizationSubscriptionPlan organizationSubscriptionPlan = new OrganizationSubscriptionPlan()
            .subscriptionName(UPDATED_SUBSCRIPTION_NAME)
            .subscriptionTitle(UPDATED_SUBSCRIPTION_TITLE)
            .subscriptionType(UPDATED_SUBSCRIPTION_TYPE)
            .subscriptionPrice(UPDATED_SUBSCRIPTION_PRICE)
            .subscriptionQuantity(UPDATED_SUBSCRIPTION_QUANTITY)
            .subscriptionPeriod(UPDATED_SUBSCRIPTION_PERIOD)
            .subscriptionTerms(UPDATED_SUBSCRIPTION_TERMS)
            .couponCode(UPDATED_COUPON_CODE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .suspendedDate(UPDATED_SUSPENDED_DATE)
            .deletedDate(UPDATED_DELETED_DATE)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return organizationSubscriptionPlan;
    }

    @BeforeEach
    public void initTest() {
        organizationSubscriptionPlan = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganizationSubscriptionPlan() throws Exception {
        int databaseSizeBeforeCreate = organizationSubscriptionPlanRepository.findAll().size();
        // Create the OrganizationSubscriptionPlan
        restOrganizationSubscriptionPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isCreated());

        // Validate the OrganizationSubscriptionPlan in the database
        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeCreate + 1);
        OrganizationSubscriptionPlan testOrganizationSubscriptionPlan = organizationSubscriptionPlanList.get(
            organizationSubscriptionPlanList.size() - 1
        );
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionName()).isEqualTo(DEFAULT_SUBSCRIPTION_NAME);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionTitle()).isEqualTo(DEFAULT_SUBSCRIPTION_TITLE);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionType()).isEqualTo(DEFAULT_SUBSCRIPTION_TYPE);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionPrice()).isEqualTo(DEFAULT_SUBSCRIPTION_PRICE);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionQuantity()).isEqualTo(DEFAULT_SUBSCRIPTION_QUANTITY);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionPeriod()).isEqualTo(DEFAULT_SUBSCRIPTION_PERIOD);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionTerms()).isEqualTo(DEFAULT_SUBSCRIPTION_TERMS);
        assertThat(testOrganizationSubscriptionPlan.getCouponCode()).isEqualTo(DEFAULT_COUPON_CODE);
        assertThat(testOrganizationSubscriptionPlan.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testOrganizationSubscriptionPlan.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testOrganizationSubscriptionPlan.getSuspendedDate()).isEqualTo(DEFAULT_SUSPENDED_DATE);
        assertThat(testOrganizationSubscriptionPlan.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
        assertThat(testOrganizationSubscriptionPlan.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrganizationSubscriptionPlan.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOrganizationSubscriptionPlan.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createOrganizationSubscriptionPlanWithExistingId() throws Exception {
        // Create the OrganizationSubscriptionPlan with an existing ID
        organizationSubscriptionPlan.setId(1L);

        int databaseSizeBeforeCreate = organizationSubscriptionPlanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationSubscriptionPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationSubscriptionPlan in the database
        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSubscriptionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationSubscriptionPlanRepository.findAll().size();
        // set the field null
        organizationSubscriptionPlan.setSubscriptionName(null);

        // Create the OrganizationSubscriptionPlan, which fails.

        restOrganizationSubscriptionPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSubscriptionTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationSubscriptionPlanRepository.findAll().size();
        // set the field null
        organizationSubscriptionPlan.setSubscriptionTitle(null);

        // Create the OrganizationSubscriptionPlan, which fails.

        restOrganizationSubscriptionPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSubscriptionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationSubscriptionPlanRepository.findAll().size();
        // set the field null
        organizationSubscriptionPlan.setSubscriptionType(null);

        // Create the OrganizationSubscriptionPlan, which fails.

        restOrganizationSubscriptionPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSubscriptionPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationSubscriptionPlanRepository.findAll().size();
        // set the field null
        organizationSubscriptionPlan.setSubscriptionPrice(null);

        // Create the OrganizationSubscriptionPlan, which fails.

        restOrganizationSubscriptionPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSubscriptionQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationSubscriptionPlanRepository.findAll().size();
        // set the field null
        organizationSubscriptionPlan.setSubscriptionQuantity(null);

        // Create the OrganizationSubscriptionPlan, which fails.

        restOrganizationSubscriptionPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSubscriptionPeriodIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationSubscriptionPlanRepository.findAll().size();
        // set the field null
        organizationSubscriptionPlan.setSubscriptionPeriod(null);

        // Create the OrganizationSubscriptionPlan, which fails.

        restOrganizationSubscriptionPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationSubscriptionPlanRepository.findAll().size();
        // set the field null
        organizationSubscriptionPlan.setStartDate(null);

        // Create the OrganizationSubscriptionPlan, which fails.

        restOrganizationSubscriptionPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationSubscriptionPlanRepository.findAll().size();
        // set the field null
        organizationSubscriptionPlan.setEndDate(null);

        // Create the OrganizationSubscriptionPlan, which fails.

        restOrganizationSubscriptionPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationSubscriptionPlanRepository.findAll().size();
        // set the field null
        organizationSubscriptionPlan.setStatus(null);

        // Create the OrganizationSubscriptionPlan, which fails.

        restOrganizationSubscriptionPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganizationSubscriptionPlans() throws Exception {
        // Initialize the database
        organizationSubscriptionPlanRepository.saveAndFlush(organizationSubscriptionPlan);

        // Get all the organizationSubscriptionPlanList
        restOrganizationSubscriptionPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationSubscriptionPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].subscriptionName").value(hasItem(DEFAULT_SUBSCRIPTION_NAME)))
            .andExpect(jsonPath("$.[*].subscriptionTitle").value(hasItem(DEFAULT_SUBSCRIPTION_TITLE)))
            .andExpect(jsonPath("$.[*].subscriptionType").value(hasItem(DEFAULT_SUBSCRIPTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].subscriptionPrice").value(hasItem(DEFAULT_SUBSCRIPTION_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].subscriptionQuantity").value(hasItem(DEFAULT_SUBSCRIPTION_QUANTITY)))
            .andExpect(jsonPath("$.[*].subscriptionPeriod").value(hasItem(DEFAULT_SUBSCRIPTION_PERIOD)))
            .andExpect(jsonPath("$.[*].subscriptionTerms").value(hasItem(DEFAULT_SUBSCRIPTION_TERMS)))
            .andExpect(jsonPath("$.[*].couponCode").value(hasItem(DEFAULT_COUPON_CODE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].suspendedDate").value(hasItem(DEFAULT_SUSPENDED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getOrganizationSubscriptionPlan() throws Exception {
        // Initialize the database
        organizationSubscriptionPlanRepository.saveAndFlush(organizationSubscriptionPlan);

        // Get the organizationSubscriptionPlan
        restOrganizationSubscriptionPlanMockMvc
            .perform(get(ENTITY_API_URL_ID, organizationSubscriptionPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organizationSubscriptionPlan.getId().intValue()))
            .andExpect(jsonPath("$.subscriptionName").value(DEFAULT_SUBSCRIPTION_NAME))
            .andExpect(jsonPath("$.subscriptionTitle").value(DEFAULT_SUBSCRIPTION_TITLE))
            .andExpect(jsonPath("$.subscriptionType").value(DEFAULT_SUBSCRIPTION_TYPE.toString()))
            .andExpect(jsonPath("$.subscriptionPrice").value(DEFAULT_SUBSCRIPTION_PRICE.doubleValue()))
            .andExpect(jsonPath("$.subscriptionQuantity").value(DEFAULT_SUBSCRIPTION_QUANTITY))
            .andExpect(jsonPath("$.subscriptionPeriod").value(DEFAULT_SUBSCRIPTION_PERIOD))
            .andExpect(jsonPath("$.subscriptionTerms").value(DEFAULT_SUBSCRIPTION_TERMS))
            .andExpect(jsonPath("$.couponCode").value(DEFAULT_COUPON_CODE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.suspendedDate").value(DEFAULT_SUSPENDED_DATE.toString()))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOrganizationSubscriptionPlan() throws Exception {
        // Get the organizationSubscriptionPlan
        restOrganizationSubscriptionPlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrganizationSubscriptionPlan() throws Exception {
        // Initialize the database
        organizationSubscriptionPlanRepository.saveAndFlush(organizationSubscriptionPlan);

        int databaseSizeBeforeUpdate = organizationSubscriptionPlanRepository.findAll().size();

        // Update the organizationSubscriptionPlan
        OrganizationSubscriptionPlan updatedOrganizationSubscriptionPlan = organizationSubscriptionPlanRepository
            .findById(organizationSubscriptionPlan.getId())
            .get();
        // Disconnect from session so that the updates on updatedOrganizationSubscriptionPlan are not directly saved in db
        em.detach(updatedOrganizationSubscriptionPlan);
        updatedOrganizationSubscriptionPlan
            .subscriptionName(UPDATED_SUBSCRIPTION_NAME)
            .subscriptionTitle(UPDATED_SUBSCRIPTION_TITLE)
            .subscriptionType(UPDATED_SUBSCRIPTION_TYPE)
            .subscriptionPrice(UPDATED_SUBSCRIPTION_PRICE)
            .subscriptionQuantity(UPDATED_SUBSCRIPTION_QUANTITY)
            .subscriptionPeriod(UPDATED_SUBSCRIPTION_PERIOD)
            .subscriptionTerms(UPDATED_SUBSCRIPTION_TERMS)
            .couponCode(UPDATED_COUPON_CODE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .suspendedDate(UPDATED_SUSPENDED_DATE)
            .deletedDate(UPDATED_DELETED_DATE)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restOrganizationSubscriptionPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrganizationSubscriptionPlan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrganizationSubscriptionPlan))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationSubscriptionPlan in the database
        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeUpdate);
        OrganizationSubscriptionPlan testOrganizationSubscriptionPlan = organizationSubscriptionPlanList.get(
            organizationSubscriptionPlanList.size() - 1
        );
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionName()).isEqualTo(UPDATED_SUBSCRIPTION_NAME);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionTitle()).isEqualTo(UPDATED_SUBSCRIPTION_TITLE);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionType()).isEqualTo(UPDATED_SUBSCRIPTION_TYPE);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionPrice()).isEqualTo(UPDATED_SUBSCRIPTION_PRICE);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionQuantity()).isEqualTo(UPDATED_SUBSCRIPTION_QUANTITY);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionPeriod()).isEqualTo(UPDATED_SUBSCRIPTION_PERIOD);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionTerms()).isEqualTo(UPDATED_SUBSCRIPTION_TERMS);
        assertThat(testOrganizationSubscriptionPlan.getCouponCode()).isEqualTo(UPDATED_COUPON_CODE);
        assertThat(testOrganizationSubscriptionPlan.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testOrganizationSubscriptionPlan.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testOrganizationSubscriptionPlan.getSuspendedDate()).isEqualTo(UPDATED_SUSPENDED_DATE);
        assertThat(testOrganizationSubscriptionPlan.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
        assertThat(testOrganizationSubscriptionPlan.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganizationSubscriptionPlan.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrganizationSubscriptionPlan.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOrganizationSubscriptionPlan() throws Exception {
        int databaseSizeBeforeUpdate = organizationSubscriptionPlanRepository.findAll().size();
        organizationSubscriptionPlan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationSubscriptionPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationSubscriptionPlan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationSubscriptionPlan in the database
        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganizationSubscriptionPlan() throws Exception {
        int databaseSizeBeforeUpdate = organizationSubscriptionPlanRepository.findAll().size();
        organizationSubscriptionPlan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationSubscriptionPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationSubscriptionPlan in the database
        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganizationSubscriptionPlan() throws Exception {
        int databaseSizeBeforeUpdate = organizationSubscriptionPlanRepository.findAll().size();
        organizationSubscriptionPlan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationSubscriptionPlanMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationSubscriptionPlan in the database
        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationSubscriptionPlanWithPatch() throws Exception {
        // Initialize the database
        organizationSubscriptionPlanRepository.saveAndFlush(organizationSubscriptionPlan);

        int databaseSizeBeforeUpdate = organizationSubscriptionPlanRepository.findAll().size();

        // Update the organizationSubscriptionPlan using partial update
        OrganizationSubscriptionPlan partialUpdatedOrganizationSubscriptionPlan = new OrganizationSubscriptionPlan();
        partialUpdatedOrganizationSubscriptionPlan.setId(organizationSubscriptionPlan.getId());

        partialUpdatedOrganizationSubscriptionPlan
            .subscriptionTitle(UPDATED_SUBSCRIPTION_TITLE)
            .subscriptionPrice(UPDATED_SUBSCRIPTION_PRICE)
            .subscriptionPeriod(UPDATED_SUBSCRIPTION_PERIOD)
            .subscriptionTerms(UPDATED_SUBSCRIPTION_TERMS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .deletedDate(UPDATED_DELETED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restOrganizationSubscriptionPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationSubscriptionPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganizationSubscriptionPlan))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationSubscriptionPlan in the database
        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeUpdate);
        OrganizationSubscriptionPlan testOrganizationSubscriptionPlan = organizationSubscriptionPlanList.get(
            organizationSubscriptionPlanList.size() - 1
        );
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionName()).isEqualTo(DEFAULT_SUBSCRIPTION_NAME);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionTitle()).isEqualTo(UPDATED_SUBSCRIPTION_TITLE);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionType()).isEqualTo(DEFAULT_SUBSCRIPTION_TYPE);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionPrice()).isEqualTo(UPDATED_SUBSCRIPTION_PRICE);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionQuantity()).isEqualTo(DEFAULT_SUBSCRIPTION_QUANTITY);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionPeriod()).isEqualTo(UPDATED_SUBSCRIPTION_PERIOD);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionTerms()).isEqualTo(UPDATED_SUBSCRIPTION_TERMS);
        assertThat(testOrganizationSubscriptionPlan.getCouponCode()).isEqualTo(DEFAULT_COUPON_CODE);
        assertThat(testOrganizationSubscriptionPlan.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testOrganizationSubscriptionPlan.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testOrganizationSubscriptionPlan.getSuspendedDate()).isEqualTo(DEFAULT_SUSPENDED_DATE);
        assertThat(testOrganizationSubscriptionPlan.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
        assertThat(testOrganizationSubscriptionPlan.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrganizationSubscriptionPlan.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrganizationSubscriptionPlan.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOrganizationSubscriptionPlanWithPatch() throws Exception {
        // Initialize the database
        organizationSubscriptionPlanRepository.saveAndFlush(organizationSubscriptionPlan);

        int databaseSizeBeforeUpdate = organizationSubscriptionPlanRepository.findAll().size();

        // Update the organizationSubscriptionPlan using partial update
        OrganizationSubscriptionPlan partialUpdatedOrganizationSubscriptionPlan = new OrganizationSubscriptionPlan();
        partialUpdatedOrganizationSubscriptionPlan.setId(organizationSubscriptionPlan.getId());

        partialUpdatedOrganizationSubscriptionPlan
            .subscriptionName(UPDATED_SUBSCRIPTION_NAME)
            .subscriptionTitle(UPDATED_SUBSCRIPTION_TITLE)
            .subscriptionType(UPDATED_SUBSCRIPTION_TYPE)
            .subscriptionPrice(UPDATED_SUBSCRIPTION_PRICE)
            .subscriptionQuantity(UPDATED_SUBSCRIPTION_QUANTITY)
            .subscriptionPeriod(UPDATED_SUBSCRIPTION_PERIOD)
            .subscriptionTerms(UPDATED_SUBSCRIPTION_TERMS)
            .couponCode(UPDATED_COUPON_CODE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .suspendedDate(UPDATED_SUSPENDED_DATE)
            .deletedDate(UPDATED_DELETED_DATE)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restOrganizationSubscriptionPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationSubscriptionPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganizationSubscriptionPlan))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationSubscriptionPlan in the database
        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeUpdate);
        OrganizationSubscriptionPlan testOrganizationSubscriptionPlan = organizationSubscriptionPlanList.get(
            organizationSubscriptionPlanList.size() - 1
        );
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionName()).isEqualTo(UPDATED_SUBSCRIPTION_NAME);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionTitle()).isEqualTo(UPDATED_SUBSCRIPTION_TITLE);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionType()).isEqualTo(UPDATED_SUBSCRIPTION_TYPE);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionPrice()).isEqualTo(UPDATED_SUBSCRIPTION_PRICE);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionQuantity()).isEqualTo(UPDATED_SUBSCRIPTION_QUANTITY);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionPeriod()).isEqualTo(UPDATED_SUBSCRIPTION_PERIOD);
        assertThat(testOrganizationSubscriptionPlan.getSubscriptionTerms()).isEqualTo(UPDATED_SUBSCRIPTION_TERMS);
        assertThat(testOrganizationSubscriptionPlan.getCouponCode()).isEqualTo(UPDATED_COUPON_CODE);
        assertThat(testOrganizationSubscriptionPlan.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testOrganizationSubscriptionPlan.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testOrganizationSubscriptionPlan.getSuspendedDate()).isEqualTo(UPDATED_SUSPENDED_DATE);
        assertThat(testOrganizationSubscriptionPlan.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
        assertThat(testOrganizationSubscriptionPlan.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganizationSubscriptionPlan.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrganizationSubscriptionPlan.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOrganizationSubscriptionPlan() throws Exception {
        int databaseSizeBeforeUpdate = organizationSubscriptionPlanRepository.findAll().size();
        organizationSubscriptionPlan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationSubscriptionPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationSubscriptionPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationSubscriptionPlan in the database
        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganizationSubscriptionPlan() throws Exception {
        int databaseSizeBeforeUpdate = organizationSubscriptionPlanRepository.findAll().size();
        organizationSubscriptionPlan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationSubscriptionPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationSubscriptionPlan in the database
        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganizationSubscriptionPlan() throws Exception {
        int databaseSizeBeforeUpdate = organizationSubscriptionPlanRepository.findAll().size();
        organizationSubscriptionPlan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationSubscriptionPlanMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationSubscriptionPlan))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationSubscriptionPlan in the database
        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganizationSubscriptionPlan() throws Exception {
        // Initialize the database
        organizationSubscriptionPlanRepository.saveAndFlush(organizationSubscriptionPlan);

        int databaseSizeBeforeDelete = organizationSubscriptionPlanRepository.findAll().size();

        // Delete the organizationSubscriptionPlan
        restOrganizationSubscriptionPlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, organizationSubscriptionPlan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganizationSubscriptionPlan> organizationSubscriptionPlanList = organizationSubscriptionPlanRepository.findAll();
        assertThat(organizationSubscriptionPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
