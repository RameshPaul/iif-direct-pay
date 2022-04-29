package com.iif.directpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iif.directpay.IntegrationTest;
import com.iif.directpay.domain.Patron;
import com.iif.directpay.domain.enumeration.ReceipientPatronRecurringType;
import com.iif.directpay.domain.enumeration.ReceipientPatronStatus;
import com.iif.directpay.repository.PatronRepository;
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
 * Integration tests for the {@link PatronResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PatronResourceIT {

    private static final Boolean DEFAULT_IS_RECURRING = false;
    private static final Boolean UPDATED_IS_RECURRING = true;

    private static final ReceipientPatronRecurringType DEFAULT_RECURRING_TYPE = ReceipientPatronRecurringType.WEEKLY;
    private static final ReceipientPatronRecurringType UPDATED_RECURRING_TYPE = ReceipientPatronRecurringType.MONTHLY;

    private static final LocalDate DEFAULT_RECURRING_PERIOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECURRING_PERIOD = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ENABLE_REMINDER = false;
    private static final Boolean UPDATED_ENABLE_REMINDER = true;

    private static final Boolean DEFAULT_IS_AUTO_PAY = false;
    private static final Boolean UPDATED_IS_AUTO_PAY = true;

    private static final Double DEFAULT_AMOUNT_RECEIPIENT_REQUISITE = 1D;
    private static final Double UPDATED_AMOUNT_RECEIPIENT_REQUISITE = 2D;

    private static final Double DEFAULT_AMOUNT_PATRON_PLEDGE = 1D;
    private static final Double UPDATED_AMOUNT_PATRON_PLEDGE = 2D;

    private static final Double DEFAULT_AMOUNT_PATRON_ACTUAL = 1D;
    private static final Double UPDATED_AMOUNT_PATRON_ACTUAL = 2D;

    private static final ReceipientPatronStatus DEFAULT_STATUS = ReceipientPatronStatus.INACTIVE;
    private static final ReceipientPatronStatus UPDATED_STATUS = ReceipientPatronStatus.ACTIVE;

    private static final LocalDate DEFAULT_COMMITED_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMITED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_COMMITED_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMITED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUAL_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUAL_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUAL_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUAL_END_DATE = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/patrons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatronMockMvc;

    private Patron patron;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patron createEntity(EntityManager em) {
        Patron patron = new Patron()
            .isRecurring(DEFAULT_IS_RECURRING)
            .recurringType(DEFAULT_RECURRING_TYPE)
            .recurringPeriod(DEFAULT_RECURRING_PERIOD)
            .enableReminder(DEFAULT_ENABLE_REMINDER)
            .isAutoPay(DEFAULT_IS_AUTO_PAY)
            .amountReceipientRequisite(DEFAULT_AMOUNT_RECEIPIENT_REQUISITE)
            .amountPatronPledge(DEFAULT_AMOUNT_PATRON_PLEDGE)
            .amountPatronActual(DEFAULT_AMOUNT_PATRON_ACTUAL)
            .status(DEFAULT_STATUS)
            .commitedStartDate(DEFAULT_COMMITED_START_DATE)
            .commitedEndDate(DEFAULT_COMMITED_END_DATE)
            .actualStartDate(DEFAULT_ACTUAL_START_DATE)
            .actualEndDate(DEFAULT_ACTUAL_END_DATE)
            .reccuringPauseDate(DEFAULT_RECCURING_PAUSE_DATE)
            .recurringResumeDate(DEFAULT_RECURRING_RESUME_DATE)
            .recurringPauseReason(DEFAULT_RECURRING_PAUSE_REASON)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .deletedDate(DEFAULT_DELETED_DATE);
        return patron;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patron createUpdatedEntity(EntityManager em) {
        Patron patron = new Patron()
            .isRecurring(UPDATED_IS_RECURRING)
            .recurringType(UPDATED_RECURRING_TYPE)
            .recurringPeriod(UPDATED_RECURRING_PERIOD)
            .enableReminder(UPDATED_ENABLE_REMINDER)
            .isAutoPay(UPDATED_IS_AUTO_PAY)
            .amountReceipientRequisite(UPDATED_AMOUNT_RECEIPIENT_REQUISITE)
            .amountPatronPledge(UPDATED_AMOUNT_PATRON_PLEDGE)
            .amountPatronActual(UPDATED_AMOUNT_PATRON_ACTUAL)
            .status(UPDATED_STATUS)
            .commitedStartDate(UPDATED_COMMITED_START_DATE)
            .commitedEndDate(UPDATED_COMMITED_END_DATE)
            .actualStartDate(UPDATED_ACTUAL_START_DATE)
            .actualEndDate(UPDATED_ACTUAL_END_DATE)
            .reccuringPauseDate(UPDATED_RECCURING_PAUSE_DATE)
            .recurringResumeDate(UPDATED_RECURRING_RESUME_DATE)
            .recurringPauseReason(UPDATED_RECURRING_PAUSE_REASON)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);
        return patron;
    }

    @BeforeEach
    public void initTest() {
        patron = createEntity(em);
    }

    @Test
    @Transactional
    void createPatron() throws Exception {
        int databaseSizeBeforeCreate = patronRepository.findAll().size();
        // Create the Patron
        restPatronMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isCreated());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeCreate + 1);
        Patron testPatron = patronList.get(patronList.size() - 1);
        assertThat(testPatron.getIsRecurring()).isEqualTo(DEFAULT_IS_RECURRING);
        assertThat(testPatron.getRecurringType()).isEqualTo(DEFAULT_RECURRING_TYPE);
        assertThat(testPatron.getRecurringPeriod()).isEqualTo(DEFAULT_RECURRING_PERIOD);
        assertThat(testPatron.getEnableReminder()).isEqualTo(DEFAULT_ENABLE_REMINDER);
        assertThat(testPatron.getIsAutoPay()).isEqualTo(DEFAULT_IS_AUTO_PAY);
        assertThat(testPatron.getAmountReceipientRequisite()).isEqualTo(DEFAULT_AMOUNT_RECEIPIENT_REQUISITE);
        assertThat(testPatron.getAmountPatronPledge()).isEqualTo(DEFAULT_AMOUNT_PATRON_PLEDGE);
        assertThat(testPatron.getAmountPatronActual()).isEqualTo(DEFAULT_AMOUNT_PATRON_ACTUAL);
        assertThat(testPatron.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPatron.getCommitedStartDate()).isEqualTo(DEFAULT_COMMITED_START_DATE);
        assertThat(testPatron.getCommitedEndDate()).isEqualTo(DEFAULT_COMMITED_END_DATE);
        assertThat(testPatron.getActualStartDate()).isEqualTo(DEFAULT_ACTUAL_START_DATE);
        assertThat(testPatron.getActualEndDate()).isEqualTo(DEFAULT_ACTUAL_END_DATE);
        assertThat(testPatron.getReccuringPauseDate()).isEqualTo(DEFAULT_RECCURING_PAUSE_DATE);
        assertThat(testPatron.getRecurringResumeDate()).isEqualTo(DEFAULT_RECURRING_RESUME_DATE);
        assertThat(testPatron.getRecurringPauseReason()).isEqualTo(DEFAULT_RECURRING_PAUSE_REASON);
        assertThat(testPatron.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPatron.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testPatron.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    @Transactional
    void createPatronWithExistingId() throws Exception {
        // Create the Patron with an existing ID
        patron.setId(1L);

        int databaseSizeBeforeCreate = patronRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatronMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRecurringTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = patronRepository.findAll().size();
        // set the field null
        patron.setRecurringType(null);

        // Create the Patron, which fails.

        restPatronMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isBadRequest());

        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPatrons() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        // Get all the patronList
        restPatronMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patron.getId().intValue())))
            .andExpect(jsonPath("$.[*].isRecurring").value(hasItem(DEFAULT_IS_RECURRING.booleanValue())))
            .andExpect(jsonPath("$.[*].recurringType").value(hasItem(DEFAULT_RECURRING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].recurringPeriod").value(hasItem(DEFAULT_RECURRING_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].enableReminder").value(hasItem(DEFAULT_ENABLE_REMINDER.booleanValue())))
            .andExpect(jsonPath("$.[*].isAutoPay").value(hasItem(DEFAULT_IS_AUTO_PAY.booleanValue())))
            .andExpect(jsonPath("$.[*].amountReceipientRequisite").value(hasItem(DEFAULT_AMOUNT_RECEIPIENT_REQUISITE.doubleValue())))
            .andExpect(jsonPath("$.[*].amountPatronPledge").value(hasItem(DEFAULT_AMOUNT_PATRON_PLEDGE.doubleValue())))
            .andExpect(jsonPath("$.[*].amountPatronActual").value(hasItem(DEFAULT_AMOUNT_PATRON_ACTUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].commitedStartDate").value(hasItem(DEFAULT_COMMITED_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].commitedEndDate").value(hasItem(DEFAULT_COMMITED_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].actualStartDate").value(hasItem(DEFAULT_ACTUAL_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].actualEndDate").value(hasItem(DEFAULT_ACTUAL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].reccuringPauseDate").value(hasItem(DEFAULT_RECCURING_PAUSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].recurringResumeDate").value(hasItem(DEFAULT_RECURRING_RESUME_DATE.toString())))
            .andExpect(jsonPath("$.[*].recurringPauseReason").value(hasItem(DEFAULT_RECURRING_PAUSE_REASON)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPatron() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        // Get the patron
        restPatronMockMvc
            .perform(get(ENTITY_API_URL_ID, patron.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patron.getId().intValue()))
            .andExpect(jsonPath("$.isRecurring").value(DEFAULT_IS_RECURRING.booleanValue()))
            .andExpect(jsonPath("$.recurringType").value(DEFAULT_RECURRING_TYPE.toString()))
            .andExpect(jsonPath("$.recurringPeriod").value(DEFAULT_RECURRING_PERIOD.toString()))
            .andExpect(jsonPath("$.enableReminder").value(DEFAULT_ENABLE_REMINDER.booleanValue()))
            .andExpect(jsonPath("$.isAutoPay").value(DEFAULT_IS_AUTO_PAY.booleanValue()))
            .andExpect(jsonPath("$.amountReceipientRequisite").value(DEFAULT_AMOUNT_RECEIPIENT_REQUISITE.doubleValue()))
            .andExpect(jsonPath("$.amountPatronPledge").value(DEFAULT_AMOUNT_PATRON_PLEDGE.doubleValue()))
            .andExpect(jsonPath("$.amountPatronActual").value(DEFAULT_AMOUNT_PATRON_ACTUAL.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.commitedStartDate").value(DEFAULT_COMMITED_START_DATE.toString()))
            .andExpect(jsonPath("$.commitedEndDate").value(DEFAULT_COMMITED_END_DATE.toString()))
            .andExpect(jsonPath("$.actualStartDate").value(DEFAULT_ACTUAL_START_DATE.toString()))
            .andExpect(jsonPath("$.actualEndDate").value(DEFAULT_ACTUAL_END_DATE.toString()))
            .andExpect(jsonPath("$.reccuringPauseDate").value(DEFAULT_RECCURING_PAUSE_DATE.toString()))
            .andExpect(jsonPath("$.recurringResumeDate").value(DEFAULT_RECURRING_RESUME_DATE.toString()))
            .andExpect(jsonPath("$.recurringPauseReason").value(DEFAULT_RECURRING_PAUSE_REASON))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPatron() throws Exception {
        // Get the patron
        restPatronMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPatron() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        int databaseSizeBeforeUpdate = patronRepository.findAll().size();

        // Update the patron
        Patron updatedPatron = patronRepository.findById(patron.getId()).get();
        // Disconnect from session so that the updates on updatedPatron are not directly saved in db
        em.detach(updatedPatron);
        updatedPatron
            .isRecurring(UPDATED_IS_RECURRING)
            .recurringType(UPDATED_RECURRING_TYPE)
            .recurringPeriod(UPDATED_RECURRING_PERIOD)
            .enableReminder(UPDATED_ENABLE_REMINDER)
            .isAutoPay(UPDATED_IS_AUTO_PAY)
            .amountReceipientRequisite(UPDATED_AMOUNT_RECEIPIENT_REQUISITE)
            .amountPatronPledge(UPDATED_AMOUNT_PATRON_PLEDGE)
            .amountPatronActual(UPDATED_AMOUNT_PATRON_ACTUAL)
            .status(UPDATED_STATUS)
            .commitedStartDate(UPDATED_COMMITED_START_DATE)
            .commitedEndDate(UPDATED_COMMITED_END_DATE)
            .actualStartDate(UPDATED_ACTUAL_START_DATE)
            .actualEndDate(UPDATED_ACTUAL_END_DATE)
            .reccuringPauseDate(UPDATED_RECCURING_PAUSE_DATE)
            .recurringResumeDate(UPDATED_RECURRING_RESUME_DATE)
            .recurringPauseReason(UPDATED_RECURRING_PAUSE_REASON)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);

        restPatronMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPatron.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPatron))
            )
            .andExpect(status().isOk());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
        Patron testPatron = patronList.get(patronList.size() - 1);
        assertThat(testPatron.getIsRecurring()).isEqualTo(UPDATED_IS_RECURRING);
        assertThat(testPatron.getRecurringType()).isEqualTo(UPDATED_RECURRING_TYPE);
        assertThat(testPatron.getRecurringPeriod()).isEqualTo(UPDATED_RECURRING_PERIOD);
        assertThat(testPatron.getEnableReminder()).isEqualTo(UPDATED_ENABLE_REMINDER);
        assertThat(testPatron.getIsAutoPay()).isEqualTo(UPDATED_IS_AUTO_PAY);
        assertThat(testPatron.getAmountReceipientRequisite()).isEqualTo(UPDATED_AMOUNT_RECEIPIENT_REQUISITE);
        assertThat(testPatron.getAmountPatronPledge()).isEqualTo(UPDATED_AMOUNT_PATRON_PLEDGE);
        assertThat(testPatron.getAmountPatronActual()).isEqualTo(UPDATED_AMOUNT_PATRON_ACTUAL);
        assertThat(testPatron.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPatron.getCommitedStartDate()).isEqualTo(UPDATED_COMMITED_START_DATE);
        assertThat(testPatron.getCommitedEndDate()).isEqualTo(UPDATED_COMMITED_END_DATE);
        assertThat(testPatron.getActualStartDate()).isEqualTo(UPDATED_ACTUAL_START_DATE);
        assertThat(testPatron.getActualEndDate()).isEqualTo(UPDATED_ACTUAL_END_DATE);
        assertThat(testPatron.getReccuringPauseDate()).isEqualTo(UPDATED_RECCURING_PAUSE_DATE);
        assertThat(testPatron.getRecurringResumeDate()).isEqualTo(UPDATED_RECURRING_RESUME_DATE);
        assertThat(testPatron.getRecurringPauseReason()).isEqualTo(UPDATED_RECURRING_PAUSE_REASON);
        assertThat(testPatron.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPatron.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testPatron.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();
        patron.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatronMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patron.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patron))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();
        patron.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatronMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patron))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();
        patron.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatronMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePatronWithPatch() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        int databaseSizeBeforeUpdate = patronRepository.findAll().size();

        // Update the patron using partial update
        Patron partialUpdatedPatron = new Patron();
        partialUpdatedPatron.setId(patron.getId());

        partialUpdatedPatron
            .isRecurring(UPDATED_IS_RECURRING)
            .recurringPeriod(UPDATED_RECURRING_PERIOD)
            .enableReminder(UPDATED_ENABLE_REMINDER)
            .amountPatronPledge(UPDATED_AMOUNT_PATRON_PLEDGE)
            .status(UPDATED_STATUS)
            .commitedStartDate(UPDATED_COMMITED_START_DATE)
            .commitedEndDate(UPDATED_COMMITED_END_DATE)
            .recurringResumeDate(UPDATED_RECURRING_RESUME_DATE)
            .recurringPauseReason(UPDATED_RECURRING_PAUSE_REASON)
            .updatedDate(UPDATED_UPDATED_DATE);

        restPatronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatron.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatron))
            )
            .andExpect(status().isOk());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
        Patron testPatron = patronList.get(patronList.size() - 1);
        assertThat(testPatron.getIsRecurring()).isEqualTo(UPDATED_IS_RECURRING);
        assertThat(testPatron.getRecurringType()).isEqualTo(DEFAULT_RECURRING_TYPE);
        assertThat(testPatron.getRecurringPeriod()).isEqualTo(UPDATED_RECURRING_PERIOD);
        assertThat(testPatron.getEnableReminder()).isEqualTo(UPDATED_ENABLE_REMINDER);
        assertThat(testPatron.getIsAutoPay()).isEqualTo(DEFAULT_IS_AUTO_PAY);
        assertThat(testPatron.getAmountReceipientRequisite()).isEqualTo(DEFAULT_AMOUNT_RECEIPIENT_REQUISITE);
        assertThat(testPatron.getAmountPatronPledge()).isEqualTo(UPDATED_AMOUNT_PATRON_PLEDGE);
        assertThat(testPatron.getAmountPatronActual()).isEqualTo(DEFAULT_AMOUNT_PATRON_ACTUAL);
        assertThat(testPatron.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPatron.getCommitedStartDate()).isEqualTo(UPDATED_COMMITED_START_DATE);
        assertThat(testPatron.getCommitedEndDate()).isEqualTo(UPDATED_COMMITED_END_DATE);
        assertThat(testPatron.getActualStartDate()).isEqualTo(DEFAULT_ACTUAL_START_DATE);
        assertThat(testPatron.getActualEndDate()).isEqualTo(DEFAULT_ACTUAL_END_DATE);
        assertThat(testPatron.getReccuringPauseDate()).isEqualTo(DEFAULT_RECCURING_PAUSE_DATE);
        assertThat(testPatron.getRecurringResumeDate()).isEqualTo(UPDATED_RECURRING_RESUME_DATE);
        assertThat(testPatron.getRecurringPauseReason()).isEqualTo(UPDATED_RECURRING_PAUSE_REASON);
        assertThat(testPatron.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPatron.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testPatron.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePatronWithPatch() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        int databaseSizeBeforeUpdate = patronRepository.findAll().size();

        // Update the patron using partial update
        Patron partialUpdatedPatron = new Patron();
        partialUpdatedPatron.setId(patron.getId());

        partialUpdatedPatron
            .isRecurring(UPDATED_IS_RECURRING)
            .recurringType(UPDATED_RECURRING_TYPE)
            .recurringPeriod(UPDATED_RECURRING_PERIOD)
            .enableReminder(UPDATED_ENABLE_REMINDER)
            .isAutoPay(UPDATED_IS_AUTO_PAY)
            .amountReceipientRequisite(UPDATED_AMOUNT_RECEIPIENT_REQUISITE)
            .amountPatronPledge(UPDATED_AMOUNT_PATRON_PLEDGE)
            .amountPatronActual(UPDATED_AMOUNT_PATRON_ACTUAL)
            .status(UPDATED_STATUS)
            .commitedStartDate(UPDATED_COMMITED_START_DATE)
            .commitedEndDate(UPDATED_COMMITED_END_DATE)
            .actualStartDate(UPDATED_ACTUAL_START_DATE)
            .actualEndDate(UPDATED_ACTUAL_END_DATE)
            .reccuringPauseDate(UPDATED_RECCURING_PAUSE_DATE)
            .recurringResumeDate(UPDATED_RECURRING_RESUME_DATE)
            .recurringPauseReason(UPDATED_RECURRING_PAUSE_REASON)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);

        restPatronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatron.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatron))
            )
            .andExpect(status().isOk());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
        Patron testPatron = patronList.get(patronList.size() - 1);
        assertThat(testPatron.getIsRecurring()).isEqualTo(UPDATED_IS_RECURRING);
        assertThat(testPatron.getRecurringType()).isEqualTo(UPDATED_RECURRING_TYPE);
        assertThat(testPatron.getRecurringPeriod()).isEqualTo(UPDATED_RECURRING_PERIOD);
        assertThat(testPatron.getEnableReminder()).isEqualTo(UPDATED_ENABLE_REMINDER);
        assertThat(testPatron.getIsAutoPay()).isEqualTo(UPDATED_IS_AUTO_PAY);
        assertThat(testPatron.getAmountReceipientRequisite()).isEqualTo(UPDATED_AMOUNT_RECEIPIENT_REQUISITE);
        assertThat(testPatron.getAmountPatronPledge()).isEqualTo(UPDATED_AMOUNT_PATRON_PLEDGE);
        assertThat(testPatron.getAmountPatronActual()).isEqualTo(UPDATED_AMOUNT_PATRON_ACTUAL);
        assertThat(testPatron.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPatron.getCommitedStartDate()).isEqualTo(UPDATED_COMMITED_START_DATE);
        assertThat(testPatron.getCommitedEndDate()).isEqualTo(UPDATED_COMMITED_END_DATE);
        assertThat(testPatron.getActualStartDate()).isEqualTo(UPDATED_ACTUAL_START_DATE);
        assertThat(testPatron.getActualEndDate()).isEqualTo(UPDATED_ACTUAL_END_DATE);
        assertThat(testPatron.getReccuringPauseDate()).isEqualTo(UPDATED_RECCURING_PAUSE_DATE);
        assertThat(testPatron.getRecurringResumeDate()).isEqualTo(UPDATED_RECURRING_RESUME_DATE);
        assertThat(testPatron.getRecurringPauseReason()).isEqualTo(UPDATED_RECURRING_PAUSE_REASON);
        assertThat(testPatron.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPatron.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testPatron.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();
        patron.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patron.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patron))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();
        patron.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patron))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();
        patron.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatronMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePatron() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        int databaseSizeBeforeDelete = patronRepository.findAll().size();

        // Delete the patron
        restPatronMockMvc
            .perform(delete(ENTITY_API_URL_ID, patron.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
