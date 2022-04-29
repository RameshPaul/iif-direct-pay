package com.iif.directpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iif.directpay.IntegrationTest;
import com.iif.directpay.domain.ReceipientRecurring;
import com.iif.directpay.domain.enumeration.ReceipientRecurringStatus;
import com.iif.directpay.repository.ReceipientRecurringRepository;
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
 * Integration tests for the {@link ReceipientRecurringResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReceipientRecurringResourceIT {

    private static final Integer DEFAULT_RECURRING_PERIOD = 1;
    private static final Integer UPDATED_RECURRING_PERIOD = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUNT_REQUISITE = 1D;
    private static final Double UPDATED_AMOUNT_REQUISITE = 2D;

    private static final Double DEFAULT_AMOUNT_PATRON_COMMITED = 1D;
    private static final Double UPDATED_AMOUNT_PATRON_COMMITED = 2D;

    private static final Double DEFAULT_AMOUNT_RECEIVED = 1D;
    private static final Double UPDATED_AMOUNT_RECEIVED = 2D;

    private static final Double DEFAULT_AMOUNT_BALANCE = 1D;
    private static final Double UPDATED_AMOUNT_BALANCE = 2D;

    private static final Integer DEFAULT_TOTAL_PATRONS = 1;
    private static final Integer UPDATED_TOTAL_PATRONS = 2;

    private static final String DEFAULT_DETAILS_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS_TEXT = "BBBBBBBBBB";

    private static final ReceipientRecurringStatus DEFAULT_STATUS = ReceipientRecurringStatus.UNDER_FUNDED;
    private static final ReceipientRecurringStatus UPDATED_STATUS = ReceipientRecurringStatus.FULLY_FUNDED;

    private static final LocalDate DEFAULT_PAUSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAUSE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RESUME_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RESUME_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/receipient-recurrings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReceipientRecurringRepository receipientRecurringRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReceipientRecurringMockMvc;

    private ReceipientRecurring receipientRecurring;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReceipientRecurring createEntity(EntityManager em) {
        ReceipientRecurring receipientRecurring = new ReceipientRecurring()
            .recurringPeriod(DEFAULT_RECURRING_PERIOD)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .amountRequisite(DEFAULT_AMOUNT_REQUISITE)
            .amountPatronCommited(DEFAULT_AMOUNT_PATRON_COMMITED)
            .amountReceived(DEFAULT_AMOUNT_RECEIVED)
            .amountBalance(DEFAULT_AMOUNT_BALANCE)
            .totalPatrons(DEFAULT_TOTAL_PATRONS)
            .detailsText(DEFAULT_DETAILS_TEXT)
            .status(DEFAULT_STATUS)
            .pauseDate(DEFAULT_PAUSE_DATE)
            .resumeDate(DEFAULT_RESUME_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return receipientRecurring;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReceipientRecurring createUpdatedEntity(EntityManager em) {
        ReceipientRecurring receipientRecurring = new ReceipientRecurring()
            .recurringPeriod(UPDATED_RECURRING_PERIOD)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .amountRequisite(UPDATED_AMOUNT_REQUISITE)
            .amountPatronCommited(UPDATED_AMOUNT_PATRON_COMMITED)
            .amountReceived(UPDATED_AMOUNT_RECEIVED)
            .amountBalance(UPDATED_AMOUNT_BALANCE)
            .totalPatrons(UPDATED_TOTAL_PATRONS)
            .detailsText(UPDATED_DETAILS_TEXT)
            .status(UPDATED_STATUS)
            .pauseDate(UPDATED_PAUSE_DATE)
            .resumeDate(UPDATED_RESUME_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return receipientRecurring;
    }

    @BeforeEach
    public void initTest() {
        receipientRecurring = createEntity(em);
    }

    @Test
    @Transactional
    void createReceipientRecurring() throws Exception {
        int databaseSizeBeforeCreate = receipientRecurringRepository.findAll().size();
        // Create the ReceipientRecurring
        restReceipientRecurringMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receipientRecurring))
            )
            .andExpect(status().isCreated());

        // Validate the ReceipientRecurring in the database
        List<ReceipientRecurring> receipientRecurringList = receipientRecurringRepository.findAll();
        assertThat(receipientRecurringList).hasSize(databaseSizeBeforeCreate + 1);
        ReceipientRecurring testReceipientRecurring = receipientRecurringList.get(receipientRecurringList.size() - 1);
        assertThat(testReceipientRecurring.getRecurringPeriod()).isEqualTo(DEFAULT_RECURRING_PERIOD);
        assertThat(testReceipientRecurring.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testReceipientRecurring.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testReceipientRecurring.getAmountRequisite()).isEqualTo(DEFAULT_AMOUNT_REQUISITE);
        assertThat(testReceipientRecurring.getAmountPatronCommited()).isEqualTo(DEFAULT_AMOUNT_PATRON_COMMITED);
        assertThat(testReceipientRecurring.getAmountReceived()).isEqualTo(DEFAULT_AMOUNT_RECEIVED);
        assertThat(testReceipientRecurring.getAmountBalance()).isEqualTo(DEFAULT_AMOUNT_BALANCE);
        assertThat(testReceipientRecurring.getTotalPatrons()).isEqualTo(DEFAULT_TOTAL_PATRONS);
        assertThat(testReceipientRecurring.getDetailsText()).isEqualTo(DEFAULT_DETAILS_TEXT);
        assertThat(testReceipientRecurring.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReceipientRecurring.getPauseDate()).isEqualTo(DEFAULT_PAUSE_DATE);
        assertThat(testReceipientRecurring.getResumeDate()).isEqualTo(DEFAULT_RESUME_DATE);
        assertThat(testReceipientRecurring.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testReceipientRecurring.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createReceipientRecurringWithExistingId() throws Exception {
        // Create the ReceipientRecurring with an existing ID
        receipientRecurring.setId(1L);

        int databaseSizeBeforeCreate = receipientRecurringRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceipientRecurringMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receipientRecurring))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceipientRecurring in the database
        List<ReceipientRecurring> receipientRecurringList = receipientRecurringRepository.findAll();
        assertThat(receipientRecurringList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReceipientRecurrings() throws Exception {
        // Initialize the database
        receipientRecurringRepository.saveAndFlush(receipientRecurring);

        // Get all the receipientRecurringList
        restReceipientRecurringMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receipientRecurring.getId().intValue())))
            .andExpect(jsonPath("$.[*].recurringPeriod").value(hasItem(DEFAULT_RECURRING_PERIOD)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].amountRequisite").value(hasItem(DEFAULT_AMOUNT_REQUISITE.doubleValue())))
            .andExpect(jsonPath("$.[*].amountPatronCommited").value(hasItem(DEFAULT_AMOUNT_PATRON_COMMITED.doubleValue())))
            .andExpect(jsonPath("$.[*].amountReceived").value(hasItem(DEFAULT_AMOUNT_RECEIVED.doubleValue())))
            .andExpect(jsonPath("$.[*].amountBalance").value(hasItem(DEFAULT_AMOUNT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPatrons").value(hasItem(DEFAULT_TOTAL_PATRONS)))
            .andExpect(jsonPath("$.[*].detailsText").value(hasItem(DEFAULT_DETAILS_TEXT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].pauseDate").value(hasItem(DEFAULT_PAUSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].resumeDate").value(hasItem(DEFAULT_RESUME_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getReceipientRecurring() throws Exception {
        // Initialize the database
        receipientRecurringRepository.saveAndFlush(receipientRecurring);

        // Get the receipientRecurring
        restReceipientRecurringMockMvc
            .perform(get(ENTITY_API_URL_ID, receipientRecurring.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receipientRecurring.getId().intValue()))
            .andExpect(jsonPath("$.recurringPeriod").value(DEFAULT_RECURRING_PERIOD))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.amountRequisite").value(DEFAULT_AMOUNT_REQUISITE.doubleValue()))
            .andExpect(jsonPath("$.amountPatronCommited").value(DEFAULT_AMOUNT_PATRON_COMMITED.doubleValue()))
            .andExpect(jsonPath("$.amountReceived").value(DEFAULT_AMOUNT_RECEIVED.doubleValue()))
            .andExpect(jsonPath("$.amountBalance").value(DEFAULT_AMOUNT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.totalPatrons").value(DEFAULT_TOTAL_PATRONS))
            .andExpect(jsonPath("$.detailsText").value(DEFAULT_DETAILS_TEXT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.pauseDate").value(DEFAULT_PAUSE_DATE.toString()))
            .andExpect(jsonPath("$.resumeDate").value(DEFAULT_RESUME_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingReceipientRecurring() throws Exception {
        // Get the receipientRecurring
        restReceipientRecurringMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReceipientRecurring() throws Exception {
        // Initialize the database
        receipientRecurringRepository.saveAndFlush(receipientRecurring);

        int databaseSizeBeforeUpdate = receipientRecurringRepository.findAll().size();

        // Update the receipientRecurring
        ReceipientRecurring updatedReceipientRecurring = receipientRecurringRepository.findById(receipientRecurring.getId()).get();
        // Disconnect from session so that the updates on updatedReceipientRecurring are not directly saved in db
        em.detach(updatedReceipientRecurring);
        updatedReceipientRecurring
            .recurringPeriod(UPDATED_RECURRING_PERIOD)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .amountRequisite(UPDATED_AMOUNT_REQUISITE)
            .amountPatronCommited(UPDATED_AMOUNT_PATRON_COMMITED)
            .amountReceived(UPDATED_AMOUNT_RECEIVED)
            .amountBalance(UPDATED_AMOUNT_BALANCE)
            .totalPatrons(UPDATED_TOTAL_PATRONS)
            .detailsText(UPDATED_DETAILS_TEXT)
            .status(UPDATED_STATUS)
            .pauseDate(UPDATED_PAUSE_DATE)
            .resumeDate(UPDATED_RESUME_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restReceipientRecurringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReceipientRecurring.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReceipientRecurring))
            )
            .andExpect(status().isOk());

        // Validate the ReceipientRecurring in the database
        List<ReceipientRecurring> receipientRecurringList = receipientRecurringRepository.findAll();
        assertThat(receipientRecurringList).hasSize(databaseSizeBeforeUpdate);
        ReceipientRecurring testReceipientRecurring = receipientRecurringList.get(receipientRecurringList.size() - 1);
        assertThat(testReceipientRecurring.getRecurringPeriod()).isEqualTo(UPDATED_RECURRING_PERIOD);
        assertThat(testReceipientRecurring.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testReceipientRecurring.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testReceipientRecurring.getAmountRequisite()).isEqualTo(UPDATED_AMOUNT_REQUISITE);
        assertThat(testReceipientRecurring.getAmountPatronCommited()).isEqualTo(UPDATED_AMOUNT_PATRON_COMMITED);
        assertThat(testReceipientRecurring.getAmountReceived()).isEqualTo(UPDATED_AMOUNT_RECEIVED);
        assertThat(testReceipientRecurring.getAmountBalance()).isEqualTo(UPDATED_AMOUNT_BALANCE);
        assertThat(testReceipientRecurring.getTotalPatrons()).isEqualTo(UPDATED_TOTAL_PATRONS);
        assertThat(testReceipientRecurring.getDetailsText()).isEqualTo(UPDATED_DETAILS_TEXT);
        assertThat(testReceipientRecurring.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReceipientRecurring.getPauseDate()).isEqualTo(UPDATED_PAUSE_DATE);
        assertThat(testReceipientRecurring.getResumeDate()).isEqualTo(UPDATED_RESUME_DATE);
        assertThat(testReceipientRecurring.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReceipientRecurring.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingReceipientRecurring() throws Exception {
        int databaseSizeBeforeUpdate = receipientRecurringRepository.findAll().size();
        receipientRecurring.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceipientRecurringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, receipientRecurring.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(receipientRecurring))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceipientRecurring in the database
        List<ReceipientRecurring> receipientRecurringList = receipientRecurringRepository.findAll();
        assertThat(receipientRecurringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReceipientRecurring() throws Exception {
        int databaseSizeBeforeUpdate = receipientRecurringRepository.findAll().size();
        receipientRecurring.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceipientRecurringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(receipientRecurring))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceipientRecurring in the database
        List<ReceipientRecurring> receipientRecurringList = receipientRecurringRepository.findAll();
        assertThat(receipientRecurringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReceipientRecurring() throws Exception {
        int databaseSizeBeforeUpdate = receipientRecurringRepository.findAll().size();
        receipientRecurring.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceipientRecurringMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receipientRecurring))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReceipientRecurring in the database
        List<ReceipientRecurring> receipientRecurringList = receipientRecurringRepository.findAll();
        assertThat(receipientRecurringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReceipientRecurringWithPatch() throws Exception {
        // Initialize the database
        receipientRecurringRepository.saveAndFlush(receipientRecurring);

        int databaseSizeBeforeUpdate = receipientRecurringRepository.findAll().size();

        // Update the receipientRecurring using partial update
        ReceipientRecurring partialUpdatedReceipientRecurring = new ReceipientRecurring();
        partialUpdatedReceipientRecurring.setId(receipientRecurring.getId());

        partialUpdatedReceipientRecurring
            .startDate(UPDATED_START_DATE)
            .amountRequisite(UPDATED_AMOUNT_REQUISITE)
            .amountBalance(UPDATED_AMOUNT_BALANCE)
            .totalPatrons(UPDATED_TOTAL_PATRONS)
            .resumeDate(UPDATED_RESUME_DATE);

        restReceipientRecurringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceipientRecurring.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReceipientRecurring))
            )
            .andExpect(status().isOk());

        // Validate the ReceipientRecurring in the database
        List<ReceipientRecurring> receipientRecurringList = receipientRecurringRepository.findAll();
        assertThat(receipientRecurringList).hasSize(databaseSizeBeforeUpdate);
        ReceipientRecurring testReceipientRecurring = receipientRecurringList.get(receipientRecurringList.size() - 1);
        assertThat(testReceipientRecurring.getRecurringPeriod()).isEqualTo(DEFAULT_RECURRING_PERIOD);
        assertThat(testReceipientRecurring.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testReceipientRecurring.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testReceipientRecurring.getAmountRequisite()).isEqualTo(UPDATED_AMOUNT_REQUISITE);
        assertThat(testReceipientRecurring.getAmountPatronCommited()).isEqualTo(DEFAULT_AMOUNT_PATRON_COMMITED);
        assertThat(testReceipientRecurring.getAmountReceived()).isEqualTo(DEFAULT_AMOUNT_RECEIVED);
        assertThat(testReceipientRecurring.getAmountBalance()).isEqualTo(UPDATED_AMOUNT_BALANCE);
        assertThat(testReceipientRecurring.getTotalPatrons()).isEqualTo(UPDATED_TOTAL_PATRONS);
        assertThat(testReceipientRecurring.getDetailsText()).isEqualTo(DEFAULT_DETAILS_TEXT);
        assertThat(testReceipientRecurring.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReceipientRecurring.getPauseDate()).isEqualTo(DEFAULT_PAUSE_DATE);
        assertThat(testReceipientRecurring.getResumeDate()).isEqualTo(UPDATED_RESUME_DATE);
        assertThat(testReceipientRecurring.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testReceipientRecurring.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateReceipientRecurringWithPatch() throws Exception {
        // Initialize the database
        receipientRecurringRepository.saveAndFlush(receipientRecurring);

        int databaseSizeBeforeUpdate = receipientRecurringRepository.findAll().size();

        // Update the receipientRecurring using partial update
        ReceipientRecurring partialUpdatedReceipientRecurring = new ReceipientRecurring();
        partialUpdatedReceipientRecurring.setId(receipientRecurring.getId());

        partialUpdatedReceipientRecurring
            .recurringPeriod(UPDATED_RECURRING_PERIOD)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .amountRequisite(UPDATED_AMOUNT_REQUISITE)
            .amountPatronCommited(UPDATED_AMOUNT_PATRON_COMMITED)
            .amountReceived(UPDATED_AMOUNT_RECEIVED)
            .amountBalance(UPDATED_AMOUNT_BALANCE)
            .totalPatrons(UPDATED_TOTAL_PATRONS)
            .detailsText(UPDATED_DETAILS_TEXT)
            .status(UPDATED_STATUS)
            .pauseDate(UPDATED_PAUSE_DATE)
            .resumeDate(UPDATED_RESUME_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restReceipientRecurringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceipientRecurring.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReceipientRecurring))
            )
            .andExpect(status().isOk());

        // Validate the ReceipientRecurring in the database
        List<ReceipientRecurring> receipientRecurringList = receipientRecurringRepository.findAll();
        assertThat(receipientRecurringList).hasSize(databaseSizeBeforeUpdate);
        ReceipientRecurring testReceipientRecurring = receipientRecurringList.get(receipientRecurringList.size() - 1);
        assertThat(testReceipientRecurring.getRecurringPeriod()).isEqualTo(UPDATED_RECURRING_PERIOD);
        assertThat(testReceipientRecurring.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testReceipientRecurring.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testReceipientRecurring.getAmountRequisite()).isEqualTo(UPDATED_AMOUNT_REQUISITE);
        assertThat(testReceipientRecurring.getAmountPatronCommited()).isEqualTo(UPDATED_AMOUNT_PATRON_COMMITED);
        assertThat(testReceipientRecurring.getAmountReceived()).isEqualTo(UPDATED_AMOUNT_RECEIVED);
        assertThat(testReceipientRecurring.getAmountBalance()).isEqualTo(UPDATED_AMOUNT_BALANCE);
        assertThat(testReceipientRecurring.getTotalPatrons()).isEqualTo(UPDATED_TOTAL_PATRONS);
        assertThat(testReceipientRecurring.getDetailsText()).isEqualTo(UPDATED_DETAILS_TEXT);
        assertThat(testReceipientRecurring.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReceipientRecurring.getPauseDate()).isEqualTo(UPDATED_PAUSE_DATE);
        assertThat(testReceipientRecurring.getResumeDate()).isEqualTo(UPDATED_RESUME_DATE);
        assertThat(testReceipientRecurring.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReceipientRecurring.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingReceipientRecurring() throws Exception {
        int databaseSizeBeforeUpdate = receipientRecurringRepository.findAll().size();
        receipientRecurring.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceipientRecurringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, receipientRecurring.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receipientRecurring))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceipientRecurring in the database
        List<ReceipientRecurring> receipientRecurringList = receipientRecurringRepository.findAll();
        assertThat(receipientRecurringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReceipientRecurring() throws Exception {
        int databaseSizeBeforeUpdate = receipientRecurringRepository.findAll().size();
        receipientRecurring.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceipientRecurringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receipientRecurring))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceipientRecurring in the database
        List<ReceipientRecurring> receipientRecurringList = receipientRecurringRepository.findAll();
        assertThat(receipientRecurringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReceipientRecurring() throws Exception {
        int databaseSizeBeforeUpdate = receipientRecurringRepository.findAll().size();
        receipientRecurring.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceipientRecurringMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receipientRecurring))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReceipientRecurring in the database
        List<ReceipientRecurring> receipientRecurringList = receipientRecurringRepository.findAll();
        assertThat(receipientRecurringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReceipientRecurring() throws Exception {
        // Initialize the database
        receipientRecurringRepository.saveAndFlush(receipientRecurring);

        int databaseSizeBeforeDelete = receipientRecurringRepository.findAll().size();

        // Delete the receipientRecurring
        restReceipientRecurringMockMvc
            .perform(delete(ENTITY_API_URL_ID, receipientRecurring.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReceipientRecurring> receipientRecurringList = receipientRecurringRepository.findAll();
        assertThat(receipientRecurringList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
