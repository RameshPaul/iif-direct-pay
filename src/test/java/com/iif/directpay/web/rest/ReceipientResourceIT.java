package com.iif.directpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iif.directpay.IntegrationTest;
import com.iif.directpay.domain.Receipient;
import com.iif.directpay.domain.enumeration.ReceipientPatronRecurringType;
import com.iif.directpay.domain.enumeration.ReceipientPatronStatus;
import com.iif.directpay.repository.ReceipientRepository;
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
 * Integration tests for the {@link ReceipientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReceipientResourceIT {

    private static final Boolean DEFAULT_IS_RECURRING = false;
    private static final Boolean UPDATED_IS_RECURRING = true;

    private static final ReceipientPatronRecurringType DEFAULT_RECURRING_TYPE = ReceipientPatronRecurringType.WEEKLY;
    private static final ReceipientPatronRecurringType UPDATED_RECURRING_TYPE = ReceipientPatronRecurringType.MONTHLY;

    private static final LocalDate DEFAULT_RECURRING_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECURRING_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RECURRING_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECURRING_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ENABLE_REMINDER = false;
    private static final Boolean UPDATED_ENABLE_REMINDER = true;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_AUTO_PAY = false;
    private static final Boolean UPDATED_IS_AUTO_PAY = true;

    private static final Double DEFAULT_AMOUNT_REQUISITE = 1D;
    private static final Double UPDATED_AMOUNT_REQUISITE = 2D;

    private static final ReceipientPatronStatus DEFAULT_STATUS = ReceipientPatronStatus.INACTIVE;
    private static final ReceipientPatronStatus UPDATED_STATUS = ReceipientPatronStatus.ACTIVE;

    private static final Boolean DEFAULT_IS_MANAGED_BY_ORG = false;
    private static final Boolean UPDATED_IS_MANAGED_BY_ORG = true;

    private static final Instant DEFAULT_APPROVED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPROVED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_REJECTED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REJECTED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REJECT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECT_REASON = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ONBOARDED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ONBOARDED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RECCURING_PAUSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECCURING_PAUSE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RECURRING_RESUME_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECURRING_RESUME_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RECURRING_PAUSE_REASON = "AAAAAAAAAA";
    private static final String UPDATED_RECURRING_PAUSE_REASON = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/receipients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReceipientRepository receipientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReceipientMockMvc;

    private Receipient receipient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receipient createEntity(EntityManager em) {
        Receipient receipient = new Receipient()
            .isRecurring(DEFAULT_IS_RECURRING)
            .recurringType(DEFAULT_RECURRING_TYPE)
            .recurringStartDate(DEFAULT_RECURRING_START_DATE)
            .recurringEndDate(DEFAULT_RECURRING_END_DATE)
            .enableReminder(DEFAULT_ENABLE_REMINDER)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .isAutoPay(DEFAULT_IS_AUTO_PAY)
            .amountRequisite(DEFAULT_AMOUNT_REQUISITE)
            .status(DEFAULT_STATUS)
            .isManagedByOrg(DEFAULT_IS_MANAGED_BY_ORG)
            .approvedDateTime(DEFAULT_APPROVED_DATE_TIME)
            .rejectedDateTime(DEFAULT_REJECTED_DATE_TIME)
            .rejectReason(DEFAULT_REJECT_REASON)
            .onboardedDate(DEFAULT_ONBOARDED_DATE)
            .reccuringPauseDate(DEFAULT_RECCURING_PAUSE_DATE)
            .recurringResumeDate(DEFAULT_RECURRING_RESUME_DATE)
            .recurringPauseReason(DEFAULT_RECURRING_PAUSE_REASON)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .deletedDate(DEFAULT_DELETED_DATE);
        return receipient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receipient createUpdatedEntity(EntityManager em) {
        Receipient receipient = new Receipient()
            .isRecurring(UPDATED_IS_RECURRING)
            .recurringType(UPDATED_RECURRING_TYPE)
            .recurringStartDate(UPDATED_RECURRING_START_DATE)
            .recurringEndDate(UPDATED_RECURRING_END_DATE)
            .enableReminder(UPDATED_ENABLE_REMINDER)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isAutoPay(UPDATED_IS_AUTO_PAY)
            .amountRequisite(UPDATED_AMOUNT_REQUISITE)
            .status(UPDATED_STATUS)
            .isManagedByOrg(UPDATED_IS_MANAGED_BY_ORG)
            .approvedDateTime(UPDATED_APPROVED_DATE_TIME)
            .rejectedDateTime(UPDATED_REJECTED_DATE_TIME)
            .rejectReason(UPDATED_REJECT_REASON)
            .onboardedDate(UPDATED_ONBOARDED_DATE)
            .reccuringPauseDate(UPDATED_RECCURING_PAUSE_DATE)
            .recurringResumeDate(UPDATED_RECURRING_RESUME_DATE)
            .recurringPauseReason(UPDATED_RECURRING_PAUSE_REASON)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);
        return receipient;
    }

    @BeforeEach
    public void initTest() {
        receipient = createEntity(em);
    }

    @Test
    @Transactional
    void createReceipient() throws Exception {
        int databaseSizeBeforeCreate = receipientRepository.findAll().size();
        // Create the Receipient
        restReceipientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receipient)))
            .andExpect(status().isCreated());

        // Validate the Receipient in the database
        List<Receipient> receipientList = receipientRepository.findAll();
        assertThat(receipientList).hasSize(databaseSizeBeforeCreate + 1);
        Receipient testReceipient = receipientList.get(receipientList.size() - 1);
        assertThat(testReceipient.getIsRecurring()).isEqualTo(DEFAULT_IS_RECURRING);
        assertThat(testReceipient.getRecurringType()).isEqualTo(DEFAULT_RECURRING_TYPE);
        assertThat(testReceipient.getRecurringStartDate()).isEqualTo(DEFAULT_RECURRING_START_DATE);
        assertThat(testReceipient.getRecurringEndDate()).isEqualTo(DEFAULT_RECURRING_END_DATE);
        assertThat(testReceipient.getEnableReminder()).isEqualTo(DEFAULT_ENABLE_REMINDER);
        assertThat(testReceipient.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testReceipient.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testReceipient.getIsAutoPay()).isEqualTo(DEFAULT_IS_AUTO_PAY);
        assertThat(testReceipient.getAmountRequisite()).isEqualTo(DEFAULT_AMOUNT_REQUISITE);
        assertThat(testReceipient.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReceipient.getIsManagedByOrg()).isEqualTo(DEFAULT_IS_MANAGED_BY_ORG);
        assertThat(testReceipient.getApprovedDateTime()).isEqualTo(DEFAULT_APPROVED_DATE_TIME);
        assertThat(testReceipient.getRejectedDateTime()).isEqualTo(DEFAULT_REJECTED_DATE_TIME);
        assertThat(testReceipient.getRejectReason()).isEqualTo(DEFAULT_REJECT_REASON);
        assertThat(testReceipient.getOnboardedDate()).isEqualTo(DEFAULT_ONBOARDED_DATE);
        assertThat(testReceipient.getReccuringPauseDate()).isEqualTo(DEFAULT_RECCURING_PAUSE_DATE);
        assertThat(testReceipient.getRecurringResumeDate()).isEqualTo(DEFAULT_RECURRING_RESUME_DATE);
        assertThat(testReceipient.getRecurringPauseReason()).isEqualTo(DEFAULT_RECURRING_PAUSE_REASON);
        assertThat(testReceipient.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testReceipient.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testReceipient.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    @Transactional
    void createReceipientWithExistingId() throws Exception {
        // Create the Receipient with an existing ID
        receipient.setId(1L);

        int databaseSizeBeforeCreate = receipientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceipientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receipient)))
            .andExpect(status().isBadRequest());

        // Validate the Receipient in the database
        List<Receipient> receipientList = receipientRepository.findAll();
        assertThat(receipientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRecurringTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = receipientRepository.findAll().size();
        // set the field null
        receipient.setRecurringType(null);

        // Create the Receipient, which fails.

        restReceipientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receipient)))
            .andExpect(status().isBadRequest());

        List<Receipient> receipientList = receipientRepository.findAll();
        assertThat(receipientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReceipients() throws Exception {
        // Initialize the database
        receipientRepository.saveAndFlush(receipient);

        // Get all the receipientList
        restReceipientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receipient.getId().intValue())))
            .andExpect(jsonPath("$.[*].isRecurring").value(hasItem(DEFAULT_IS_RECURRING.booleanValue())))
            .andExpect(jsonPath("$.[*].recurringType").value(hasItem(DEFAULT_RECURRING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].recurringStartDate").value(hasItem(DEFAULT_RECURRING_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].recurringEndDate").value(hasItem(DEFAULT_RECURRING_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].enableReminder").value(hasItem(DEFAULT_ENABLE_REMINDER.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isAutoPay").value(hasItem(DEFAULT_IS_AUTO_PAY.booleanValue())))
            .andExpect(jsonPath("$.[*].amountRequisite").value(hasItem(DEFAULT_AMOUNT_REQUISITE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].isManagedByOrg").value(hasItem(DEFAULT_IS_MANAGED_BY_ORG.booleanValue())))
            .andExpect(jsonPath("$.[*].approvedDateTime").value(hasItem(DEFAULT_APPROVED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].rejectedDateTime").value(hasItem(DEFAULT_REJECTED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].rejectReason").value(hasItem(DEFAULT_REJECT_REASON)))
            .andExpect(jsonPath("$.[*].onboardedDate").value(hasItem(DEFAULT_ONBOARDED_DATE.toString())))
            .andExpect(jsonPath("$.[*].reccuringPauseDate").value(hasItem(DEFAULT_RECCURING_PAUSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].recurringResumeDate").value(hasItem(DEFAULT_RECURRING_RESUME_DATE.toString())))
            .andExpect(jsonPath("$.[*].recurringPauseReason").value(hasItem(DEFAULT_RECURRING_PAUSE_REASON)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));
    }

    @Test
    @Transactional
    void getReceipient() throws Exception {
        // Initialize the database
        receipientRepository.saveAndFlush(receipient);

        // Get the receipient
        restReceipientMockMvc
            .perform(get(ENTITY_API_URL_ID, receipient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receipient.getId().intValue()))
            .andExpect(jsonPath("$.isRecurring").value(DEFAULT_IS_RECURRING.booleanValue()))
            .andExpect(jsonPath("$.recurringType").value(DEFAULT_RECURRING_TYPE.toString()))
            .andExpect(jsonPath("$.recurringStartDate").value(DEFAULT_RECURRING_START_DATE.toString()))
            .andExpect(jsonPath("$.recurringEndDate").value(DEFAULT_RECURRING_END_DATE.toString()))
            .andExpect(jsonPath("$.enableReminder").value(DEFAULT_ENABLE_REMINDER.booleanValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.isAutoPay").value(DEFAULT_IS_AUTO_PAY.booleanValue()))
            .andExpect(jsonPath("$.amountRequisite").value(DEFAULT_AMOUNT_REQUISITE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.isManagedByOrg").value(DEFAULT_IS_MANAGED_BY_ORG.booleanValue()))
            .andExpect(jsonPath("$.approvedDateTime").value(DEFAULT_APPROVED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.rejectedDateTime").value(DEFAULT_REJECTED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.rejectReason").value(DEFAULT_REJECT_REASON))
            .andExpect(jsonPath("$.onboardedDate").value(DEFAULT_ONBOARDED_DATE.toString()))
            .andExpect(jsonPath("$.reccuringPauseDate").value(DEFAULT_RECCURING_PAUSE_DATE.toString()))
            .andExpect(jsonPath("$.recurringResumeDate").value(DEFAULT_RECURRING_RESUME_DATE.toString()))
            .andExpect(jsonPath("$.recurringPauseReason").value(DEFAULT_RECURRING_PAUSE_REASON))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingReceipient() throws Exception {
        // Get the receipient
        restReceipientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReceipient() throws Exception {
        // Initialize the database
        receipientRepository.saveAndFlush(receipient);

        int databaseSizeBeforeUpdate = receipientRepository.findAll().size();

        // Update the receipient
        Receipient updatedReceipient = receipientRepository.findById(receipient.getId()).get();
        // Disconnect from session so that the updates on updatedReceipient are not directly saved in db
        em.detach(updatedReceipient);
        updatedReceipient
            .isRecurring(UPDATED_IS_RECURRING)
            .recurringType(UPDATED_RECURRING_TYPE)
            .recurringStartDate(UPDATED_RECURRING_START_DATE)
            .recurringEndDate(UPDATED_RECURRING_END_DATE)
            .enableReminder(UPDATED_ENABLE_REMINDER)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isAutoPay(UPDATED_IS_AUTO_PAY)
            .amountRequisite(UPDATED_AMOUNT_REQUISITE)
            .status(UPDATED_STATUS)
            .isManagedByOrg(UPDATED_IS_MANAGED_BY_ORG)
            .approvedDateTime(UPDATED_APPROVED_DATE_TIME)
            .rejectedDateTime(UPDATED_REJECTED_DATE_TIME)
            .rejectReason(UPDATED_REJECT_REASON)
            .onboardedDate(UPDATED_ONBOARDED_DATE)
            .reccuringPauseDate(UPDATED_RECCURING_PAUSE_DATE)
            .recurringResumeDate(UPDATED_RECURRING_RESUME_DATE)
            .recurringPauseReason(UPDATED_RECURRING_PAUSE_REASON)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);

        restReceipientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReceipient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReceipient))
            )
            .andExpect(status().isOk());

        // Validate the Receipient in the database
        List<Receipient> receipientList = receipientRepository.findAll();
        assertThat(receipientList).hasSize(databaseSizeBeforeUpdate);
        Receipient testReceipient = receipientList.get(receipientList.size() - 1);
        assertThat(testReceipient.getIsRecurring()).isEqualTo(UPDATED_IS_RECURRING);
        assertThat(testReceipient.getRecurringType()).isEqualTo(UPDATED_RECURRING_TYPE);
        assertThat(testReceipient.getRecurringStartDate()).isEqualTo(UPDATED_RECURRING_START_DATE);
        assertThat(testReceipient.getRecurringEndDate()).isEqualTo(UPDATED_RECURRING_END_DATE);
        assertThat(testReceipient.getEnableReminder()).isEqualTo(UPDATED_ENABLE_REMINDER);
        assertThat(testReceipient.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testReceipient.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testReceipient.getIsAutoPay()).isEqualTo(UPDATED_IS_AUTO_PAY);
        assertThat(testReceipient.getAmountRequisite()).isEqualTo(UPDATED_AMOUNT_REQUISITE);
        assertThat(testReceipient.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReceipient.getIsManagedByOrg()).isEqualTo(UPDATED_IS_MANAGED_BY_ORG);
        assertThat(testReceipient.getApprovedDateTime()).isEqualTo(UPDATED_APPROVED_DATE_TIME);
        assertThat(testReceipient.getRejectedDateTime()).isEqualTo(UPDATED_REJECTED_DATE_TIME);
        assertThat(testReceipient.getRejectReason()).isEqualTo(UPDATED_REJECT_REASON);
        assertThat(testReceipient.getOnboardedDate()).isEqualTo(UPDATED_ONBOARDED_DATE);
        assertThat(testReceipient.getReccuringPauseDate()).isEqualTo(UPDATED_RECCURING_PAUSE_DATE);
        assertThat(testReceipient.getRecurringResumeDate()).isEqualTo(UPDATED_RECURRING_RESUME_DATE);
        assertThat(testReceipient.getRecurringPauseReason()).isEqualTo(UPDATED_RECURRING_PAUSE_REASON);
        assertThat(testReceipient.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReceipient.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testReceipient.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingReceipient() throws Exception {
        int databaseSizeBeforeUpdate = receipientRepository.findAll().size();
        receipient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceipientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, receipient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(receipient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receipient in the database
        List<Receipient> receipientList = receipientRepository.findAll();
        assertThat(receipientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReceipient() throws Exception {
        int databaseSizeBeforeUpdate = receipientRepository.findAll().size();
        receipient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceipientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(receipient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receipient in the database
        List<Receipient> receipientList = receipientRepository.findAll();
        assertThat(receipientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReceipient() throws Exception {
        int databaseSizeBeforeUpdate = receipientRepository.findAll().size();
        receipient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceipientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receipient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Receipient in the database
        List<Receipient> receipientList = receipientRepository.findAll();
        assertThat(receipientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReceipientWithPatch() throws Exception {
        // Initialize the database
        receipientRepository.saveAndFlush(receipient);

        int databaseSizeBeforeUpdate = receipientRepository.findAll().size();

        // Update the receipient using partial update
        Receipient partialUpdatedReceipient = new Receipient();
        partialUpdatedReceipient.setId(receipient.getId());

        partialUpdatedReceipient
            .isRecurring(UPDATED_IS_RECURRING)
            .recurringType(UPDATED_RECURRING_TYPE)
            .enableReminder(UPDATED_ENABLE_REMINDER)
            .startDate(UPDATED_START_DATE)
            .isAutoPay(UPDATED_IS_AUTO_PAY)
            .isManagedByOrg(UPDATED_IS_MANAGED_BY_ORG)
            .rejectReason(UPDATED_REJECT_REASON)
            .onboardedDate(UPDATED_ONBOARDED_DATE)
            .reccuringPauseDate(UPDATED_RECCURING_PAUSE_DATE)
            .recurringPauseReason(UPDATED_RECURRING_PAUSE_REASON)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);

        restReceipientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceipient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReceipient))
            )
            .andExpect(status().isOk());

        // Validate the Receipient in the database
        List<Receipient> receipientList = receipientRepository.findAll();
        assertThat(receipientList).hasSize(databaseSizeBeforeUpdate);
        Receipient testReceipient = receipientList.get(receipientList.size() - 1);
        assertThat(testReceipient.getIsRecurring()).isEqualTo(UPDATED_IS_RECURRING);
        assertThat(testReceipient.getRecurringType()).isEqualTo(UPDATED_RECURRING_TYPE);
        assertThat(testReceipient.getRecurringStartDate()).isEqualTo(DEFAULT_RECURRING_START_DATE);
        assertThat(testReceipient.getRecurringEndDate()).isEqualTo(DEFAULT_RECURRING_END_DATE);
        assertThat(testReceipient.getEnableReminder()).isEqualTo(UPDATED_ENABLE_REMINDER);
        assertThat(testReceipient.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testReceipient.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testReceipient.getIsAutoPay()).isEqualTo(UPDATED_IS_AUTO_PAY);
        assertThat(testReceipient.getAmountRequisite()).isEqualTo(DEFAULT_AMOUNT_REQUISITE);
        assertThat(testReceipient.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReceipient.getIsManagedByOrg()).isEqualTo(UPDATED_IS_MANAGED_BY_ORG);
        assertThat(testReceipient.getApprovedDateTime()).isEqualTo(DEFAULT_APPROVED_DATE_TIME);
        assertThat(testReceipient.getRejectedDateTime()).isEqualTo(DEFAULT_REJECTED_DATE_TIME);
        assertThat(testReceipient.getRejectReason()).isEqualTo(UPDATED_REJECT_REASON);
        assertThat(testReceipient.getOnboardedDate()).isEqualTo(UPDATED_ONBOARDED_DATE);
        assertThat(testReceipient.getReccuringPauseDate()).isEqualTo(UPDATED_RECCURING_PAUSE_DATE);
        assertThat(testReceipient.getRecurringResumeDate()).isEqualTo(DEFAULT_RECURRING_RESUME_DATE);
        assertThat(testReceipient.getRecurringPauseReason()).isEqualTo(UPDATED_RECURRING_PAUSE_REASON);
        assertThat(testReceipient.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReceipient.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testReceipient.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateReceipientWithPatch() throws Exception {
        // Initialize the database
        receipientRepository.saveAndFlush(receipient);

        int databaseSizeBeforeUpdate = receipientRepository.findAll().size();

        // Update the receipient using partial update
        Receipient partialUpdatedReceipient = new Receipient();
        partialUpdatedReceipient.setId(receipient.getId());

        partialUpdatedReceipient
            .isRecurring(UPDATED_IS_RECURRING)
            .recurringType(UPDATED_RECURRING_TYPE)
            .recurringStartDate(UPDATED_RECURRING_START_DATE)
            .recurringEndDate(UPDATED_RECURRING_END_DATE)
            .enableReminder(UPDATED_ENABLE_REMINDER)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isAutoPay(UPDATED_IS_AUTO_PAY)
            .amountRequisite(UPDATED_AMOUNT_REQUISITE)
            .status(UPDATED_STATUS)
            .isManagedByOrg(UPDATED_IS_MANAGED_BY_ORG)
            .approvedDateTime(UPDATED_APPROVED_DATE_TIME)
            .rejectedDateTime(UPDATED_REJECTED_DATE_TIME)
            .rejectReason(UPDATED_REJECT_REASON)
            .onboardedDate(UPDATED_ONBOARDED_DATE)
            .reccuringPauseDate(UPDATED_RECCURING_PAUSE_DATE)
            .recurringResumeDate(UPDATED_RECURRING_RESUME_DATE)
            .recurringPauseReason(UPDATED_RECURRING_PAUSE_REASON)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);

        restReceipientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceipient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReceipient))
            )
            .andExpect(status().isOk());

        // Validate the Receipient in the database
        List<Receipient> receipientList = receipientRepository.findAll();
        assertThat(receipientList).hasSize(databaseSizeBeforeUpdate);
        Receipient testReceipient = receipientList.get(receipientList.size() - 1);
        assertThat(testReceipient.getIsRecurring()).isEqualTo(UPDATED_IS_RECURRING);
        assertThat(testReceipient.getRecurringType()).isEqualTo(UPDATED_RECURRING_TYPE);
        assertThat(testReceipient.getRecurringStartDate()).isEqualTo(UPDATED_RECURRING_START_DATE);
        assertThat(testReceipient.getRecurringEndDate()).isEqualTo(UPDATED_RECURRING_END_DATE);
        assertThat(testReceipient.getEnableReminder()).isEqualTo(UPDATED_ENABLE_REMINDER);
        assertThat(testReceipient.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testReceipient.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testReceipient.getIsAutoPay()).isEqualTo(UPDATED_IS_AUTO_PAY);
        assertThat(testReceipient.getAmountRequisite()).isEqualTo(UPDATED_AMOUNT_REQUISITE);
        assertThat(testReceipient.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReceipient.getIsManagedByOrg()).isEqualTo(UPDATED_IS_MANAGED_BY_ORG);
        assertThat(testReceipient.getApprovedDateTime()).isEqualTo(UPDATED_APPROVED_DATE_TIME);
        assertThat(testReceipient.getRejectedDateTime()).isEqualTo(UPDATED_REJECTED_DATE_TIME);
        assertThat(testReceipient.getRejectReason()).isEqualTo(UPDATED_REJECT_REASON);
        assertThat(testReceipient.getOnboardedDate()).isEqualTo(UPDATED_ONBOARDED_DATE);
        assertThat(testReceipient.getReccuringPauseDate()).isEqualTo(UPDATED_RECCURING_PAUSE_DATE);
        assertThat(testReceipient.getRecurringResumeDate()).isEqualTo(UPDATED_RECURRING_RESUME_DATE);
        assertThat(testReceipient.getRecurringPauseReason()).isEqualTo(UPDATED_RECURRING_PAUSE_REASON);
        assertThat(testReceipient.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReceipient.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testReceipient.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingReceipient() throws Exception {
        int databaseSizeBeforeUpdate = receipientRepository.findAll().size();
        receipient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceipientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, receipient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receipient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receipient in the database
        List<Receipient> receipientList = receipientRepository.findAll();
        assertThat(receipientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReceipient() throws Exception {
        int databaseSizeBeforeUpdate = receipientRepository.findAll().size();
        receipient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceipientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receipient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receipient in the database
        List<Receipient> receipientList = receipientRepository.findAll();
        assertThat(receipientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReceipient() throws Exception {
        int databaseSizeBeforeUpdate = receipientRepository.findAll().size();
        receipient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceipientMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(receipient))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Receipient in the database
        List<Receipient> receipientList = receipientRepository.findAll();
        assertThat(receipientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReceipient() throws Exception {
        // Initialize the database
        receipientRepository.saveAndFlush(receipient);

        int databaseSizeBeforeDelete = receipientRepository.findAll().size();

        // Delete the receipient
        restReceipientMockMvc
            .perform(delete(ENTITY_API_URL_ID, receipient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Receipient> receipientList = receipientRepository.findAll();
        assertThat(receipientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
