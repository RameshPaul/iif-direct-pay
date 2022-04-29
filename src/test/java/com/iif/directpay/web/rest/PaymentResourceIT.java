package com.iif.directpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iif.directpay.IntegrationTest;
import com.iif.directpay.domain.Payment;
import com.iif.directpay.domain.enumeration.PaymentStatus;
import com.iif.directpay.domain.enumeration.PaymentStatus;
import com.iif.directpay.domain.enumeration.PaymentType;
import com.iif.directpay.domain.enumeration.UserAccountType;
import com.iif.directpay.domain.enumeration.UserAccountType;
import com.iif.directpay.repository.PaymentRepository;
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
 * Integration tests for the {@link PaymentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentResourceIT {

    private static final LocalDate DEFAULT_RECURRING_PERIOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECURRING_PERIOD = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final PaymentType DEFAULT_PAYMENT_TYPE = PaymentType.CREDIT;
    private static final PaymentType UPDATED_PAYMENT_TYPE = PaymentType.DEBIT;

    private static final UserAccountType DEFAULT_PAYMENT_SOURCE = UserAccountType.UPI;
    private static final UserAccountType UPDATED_PAYMENT_SOURCE = UserAccountType.BANK;

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.INITIATED;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.PENDING;

    private static final String DEFAULT_PAYMENT_STATUS_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_STATUS_DETAILS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAYMENT_START_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_START_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PAYMENT_COMPLETE_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_COMPLETE_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PAYMENT_FAILURE_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_FAILURE_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PATRON_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_PATRON_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_AUTO_PAY = false;
    private static final Boolean UPDATED_IS_AUTO_PAY = true;

    private static final UserAccountType DEFAULT_PAYMENT_DESTINATION_SOURCE = UserAccountType.UPI;
    private static final UserAccountType UPDATED_PAYMENT_DESTINATION_SOURCE = UserAccountType.BANK;

    private static final LocalDate DEFAULT_PAYMENT_RECEIVED_DATE_T_IME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_RECEIVED_DATE_T_IME = LocalDate.now(ZoneId.systemDefault());

    private static final PaymentStatus DEFAULT_PAYMENT_RECEIVED_STATUS = PaymentStatus.INITIATED;
    private static final PaymentStatus UPDATED_PAYMENT_RECEIVED_STATUS = PaymentStatus.PENDING;

    private static final String DEFAULT_PAYMENT_RECEIVED_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_RECEIVED_DETAILS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAYMENT_REFUNDED_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_REFUNDED_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USER_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_USER_COMMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FLAGGED_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FLAGGED_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FLAG_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_FLAG_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_FLAGGED_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_FLAGGED_EMAIL_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_FLAGGED_AMOUNT = 1D;
    private static final Double UPDATED_FLAGGED_AMOUNT = 2D;

    private static final LocalDate DEFAULT_FLAG_CREATED_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FLAG_CREATED_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_RECURRING_PAYMENT = false;
    private static final Boolean UPDATED_IS_RECURRING_PAYMENT = true;

    private static final String DEFAULT_TRANSACTION_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_DETAILS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMockMvc;

    private Payment payment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createEntity(EntityManager em) {
        Payment payment = new Payment()
            .recurringPeriod(DEFAULT_RECURRING_PERIOD)
            .amount(DEFAULT_AMOUNT)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .paymentSource(DEFAULT_PAYMENT_SOURCE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .paymentStatusDetails(DEFAULT_PAYMENT_STATUS_DETAILS)
            .paymentStartDateTime(DEFAULT_PAYMENT_START_DATE_TIME)
            .paymentCompleteDateTime(DEFAULT_PAYMENT_COMPLETE_DATE_TIME)
            .paymentFailureDateTime(DEFAULT_PAYMENT_FAILURE_DATE_TIME)
            .patronComment(DEFAULT_PATRON_COMMENT)
            .isAutoPay(DEFAULT_IS_AUTO_PAY)
            .paymentDestinationSource(DEFAULT_PAYMENT_DESTINATION_SOURCE)
            .paymentReceivedDateTIme(DEFAULT_PAYMENT_RECEIVED_DATE_T_IME)
            .paymentReceivedStatus(DEFAULT_PAYMENT_RECEIVED_STATUS)
            .paymentReceivedDetails(DEFAULT_PAYMENT_RECEIVED_DETAILS)
            .paymentRefundedDateTime(DEFAULT_PAYMENT_REFUNDED_DATE_TIME)
            .userComment(DEFAULT_USER_COMMENT)
            .flaggedDateTime(DEFAULT_FLAGGED_DATE_TIME)
            .flagDetails(DEFAULT_FLAG_DETAILS)
            .flaggedEmailId(DEFAULT_FLAGGED_EMAIL_ID)
            .flaggedAmount(DEFAULT_FLAGGED_AMOUNT)
            .flagCreatedDateTime(DEFAULT_FLAG_CREATED_DATE_TIME)
            .isRecurringPayment(DEFAULT_IS_RECURRING_PAYMENT)
            .transactionDetails(DEFAULT_TRANSACTION_DETAILS)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return payment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createUpdatedEntity(EntityManager em) {
        Payment payment = new Payment()
            .recurringPeriod(UPDATED_RECURRING_PERIOD)
            .amount(UPDATED_AMOUNT)
            .transactionId(UPDATED_TRANSACTION_ID)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .paymentSource(UPDATED_PAYMENT_SOURCE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .paymentStatusDetails(UPDATED_PAYMENT_STATUS_DETAILS)
            .paymentStartDateTime(UPDATED_PAYMENT_START_DATE_TIME)
            .paymentCompleteDateTime(UPDATED_PAYMENT_COMPLETE_DATE_TIME)
            .paymentFailureDateTime(UPDATED_PAYMENT_FAILURE_DATE_TIME)
            .patronComment(UPDATED_PATRON_COMMENT)
            .isAutoPay(UPDATED_IS_AUTO_PAY)
            .paymentDestinationSource(UPDATED_PAYMENT_DESTINATION_SOURCE)
            .paymentReceivedDateTIme(UPDATED_PAYMENT_RECEIVED_DATE_T_IME)
            .paymentReceivedStatus(UPDATED_PAYMENT_RECEIVED_STATUS)
            .paymentReceivedDetails(UPDATED_PAYMENT_RECEIVED_DETAILS)
            .paymentRefundedDateTime(UPDATED_PAYMENT_REFUNDED_DATE_TIME)
            .userComment(UPDATED_USER_COMMENT)
            .flaggedDateTime(UPDATED_FLAGGED_DATE_TIME)
            .flagDetails(UPDATED_FLAG_DETAILS)
            .flaggedEmailId(UPDATED_FLAGGED_EMAIL_ID)
            .flaggedAmount(UPDATED_FLAGGED_AMOUNT)
            .flagCreatedDateTime(UPDATED_FLAG_CREATED_DATE_TIME)
            .isRecurringPayment(UPDATED_IS_RECURRING_PAYMENT)
            .transactionDetails(UPDATED_TRANSACTION_DETAILS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return payment;
    }

    @BeforeEach
    public void initTest() {
        payment = createEntity(em);
    }

    @Test
    @Transactional
    void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();
        // Create the Payment
        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getRecurringPeriod()).isEqualTo(DEFAULT_RECURRING_PERIOD);
        assertThat(testPayment.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPayment.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testPayment.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testPayment.getPaymentSource()).isEqualTo(DEFAULT_PAYMENT_SOURCE);
        assertThat(testPayment.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPayment.getPaymentStatusDetails()).isEqualTo(DEFAULT_PAYMENT_STATUS_DETAILS);
        assertThat(testPayment.getPaymentStartDateTime()).isEqualTo(DEFAULT_PAYMENT_START_DATE_TIME);
        assertThat(testPayment.getPaymentCompleteDateTime()).isEqualTo(DEFAULT_PAYMENT_COMPLETE_DATE_TIME);
        assertThat(testPayment.getPaymentFailureDateTime()).isEqualTo(DEFAULT_PAYMENT_FAILURE_DATE_TIME);
        assertThat(testPayment.getPatronComment()).isEqualTo(DEFAULT_PATRON_COMMENT);
        assertThat(testPayment.getIsAutoPay()).isEqualTo(DEFAULT_IS_AUTO_PAY);
        assertThat(testPayment.getPaymentDestinationSource()).isEqualTo(DEFAULT_PAYMENT_DESTINATION_SOURCE);
        assertThat(testPayment.getPaymentReceivedDateTIme()).isEqualTo(DEFAULT_PAYMENT_RECEIVED_DATE_T_IME);
        assertThat(testPayment.getPaymentReceivedStatus()).isEqualTo(DEFAULT_PAYMENT_RECEIVED_STATUS);
        assertThat(testPayment.getPaymentReceivedDetails()).isEqualTo(DEFAULT_PAYMENT_RECEIVED_DETAILS);
        assertThat(testPayment.getPaymentRefundedDateTime()).isEqualTo(DEFAULT_PAYMENT_REFUNDED_DATE_TIME);
        assertThat(testPayment.getUserComment()).isEqualTo(DEFAULT_USER_COMMENT);
        assertThat(testPayment.getFlaggedDateTime()).isEqualTo(DEFAULT_FLAGGED_DATE_TIME);
        assertThat(testPayment.getFlagDetails()).isEqualTo(DEFAULT_FLAG_DETAILS);
        assertThat(testPayment.getFlaggedEmailId()).isEqualTo(DEFAULT_FLAGGED_EMAIL_ID);
        assertThat(testPayment.getFlaggedAmount()).isEqualTo(DEFAULT_FLAGGED_AMOUNT);
        assertThat(testPayment.getFlagCreatedDateTime()).isEqualTo(DEFAULT_FLAG_CREATED_DATE_TIME);
        assertThat(testPayment.getIsRecurringPayment()).isEqualTo(DEFAULT_IS_RECURRING_PAYMENT);
        assertThat(testPayment.getTransactionDetails()).isEqualTo(DEFAULT_TRANSACTION_DETAILS);
        assertThat(testPayment.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayment.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createPaymentWithExistingId() throws Exception {
        // Create the Payment with an existing ID
        payment.setId(1L);

        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].recurringPeriod").value(hasItem(DEFAULT_RECURRING_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentSource").value(hasItem(DEFAULT_PAYMENT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].paymentStatusDetails").value(hasItem(DEFAULT_PAYMENT_STATUS_DETAILS)))
            .andExpect(jsonPath("$.[*].paymentStartDateTime").value(hasItem(DEFAULT_PAYMENT_START_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].paymentCompleteDateTime").value(hasItem(DEFAULT_PAYMENT_COMPLETE_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].paymentFailureDateTime").value(hasItem(DEFAULT_PAYMENT_FAILURE_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].patronComment").value(hasItem(DEFAULT_PATRON_COMMENT)))
            .andExpect(jsonPath("$.[*].isAutoPay").value(hasItem(DEFAULT_IS_AUTO_PAY.booleanValue())))
            .andExpect(jsonPath("$.[*].paymentDestinationSource").value(hasItem(DEFAULT_PAYMENT_DESTINATION_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].paymentReceivedDateTIme").value(hasItem(DEFAULT_PAYMENT_RECEIVED_DATE_T_IME.toString())))
            .andExpect(jsonPath("$.[*].paymentReceivedStatus").value(hasItem(DEFAULT_PAYMENT_RECEIVED_STATUS.toString())))
            .andExpect(jsonPath("$.[*].paymentReceivedDetails").value(hasItem(DEFAULT_PAYMENT_RECEIVED_DETAILS)))
            .andExpect(jsonPath("$.[*].paymentRefundedDateTime").value(hasItem(DEFAULT_PAYMENT_REFUNDED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].userComment").value(hasItem(DEFAULT_USER_COMMENT)))
            .andExpect(jsonPath("$.[*].flaggedDateTime").value(hasItem(DEFAULT_FLAGGED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].flagDetails").value(hasItem(DEFAULT_FLAG_DETAILS)))
            .andExpect(jsonPath("$.[*].flaggedEmailId").value(hasItem(DEFAULT_FLAGGED_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].flaggedAmount").value(hasItem(DEFAULT_FLAGGED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].flagCreatedDateTime").value(hasItem(DEFAULT_FLAG_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].isRecurringPayment").value(hasItem(DEFAULT_IS_RECURRING_PAYMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].transactionDetails").value(hasItem(DEFAULT_TRANSACTION_DETAILS)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL_ID, payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.recurringPeriod").value(DEFAULT_RECURRING_PERIOD.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.paymentSource").value(DEFAULT_PAYMENT_SOURCE.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.paymentStatusDetails").value(DEFAULT_PAYMENT_STATUS_DETAILS))
            .andExpect(jsonPath("$.paymentStartDateTime").value(DEFAULT_PAYMENT_START_DATE_TIME.toString()))
            .andExpect(jsonPath("$.paymentCompleteDateTime").value(DEFAULT_PAYMENT_COMPLETE_DATE_TIME.toString()))
            .andExpect(jsonPath("$.paymentFailureDateTime").value(DEFAULT_PAYMENT_FAILURE_DATE_TIME.toString()))
            .andExpect(jsonPath("$.patronComment").value(DEFAULT_PATRON_COMMENT))
            .andExpect(jsonPath("$.isAutoPay").value(DEFAULT_IS_AUTO_PAY.booleanValue()))
            .andExpect(jsonPath("$.paymentDestinationSource").value(DEFAULT_PAYMENT_DESTINATION_SOURCE.toString()))
            .andExpect(jsonPath("$.paymentReceivedDateTIme").value(DEFAULT_PAYMENT_RECEIVED_DATE_T_IME.toString()))
            .andExpect(jsonPath("$.paymentReceivedStatus").value(DEFAULT_PAYMENT_RECEIVED_STATUS.toString()))
            .andExpect(jsonPath("$.paymentReceivedDetails").value(DEFAULT_PAYMENT_RECEIVED_DETAILS))
            .andExpect(jsonPath("$.paymentRefundedDateTime").value(DEFAULT_PAYMENT_REFUNDED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.userComment").value(DEFAULT_USER_COMMENT))
            .andExpect(jsonPath("$.flaggedDateTime").value(DEFAULT_FLAGGED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.flagDetails").value(DEFAULT_FLAG_DETAILS))
            .andExpect(jsonPath("$.flaggedEmailId").value(DEFAULT_FLAGGED_EMAIL_ID))
            .andExpect(jsonPath("$.flaggedAmount").value(DEFAULT_FLAGGED_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.flagCreatedDateTime").value(DEFAULT_FLAG_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.isRecurringPayment").value(DEFAULT_IS_RECURRING_PAYMENT.booleanValue()))
            .andExpect(jsonPath("$.transactionDetails").value(DEFAULT_TRANSACTION_DETAILS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = paymentRepository.findById(payment.getId()).get();
        // Disconnect from session so that the updates on updatedPayment are not directly saved in db
        em.detach(updatedPayment);
        updatedPayment
            .recurringPeriod(UPDATED_RECURRING_PERIOD)
            .amount(UPDATED_AMOUNT)
            .transactionId(UPDATED_TRANSACTION_ID)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .paymentSource(UPDATED_PAYMENT_SOURCE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .paymentStatusDetails(UPDATED_PAYMENT_STATUS_DETAILS)
            .paymentStartDateTime(UPDATED_PAYMENT_START_DATE_TIME)
            .paymentCompleteDateTime(UPDATED_PAYMENT_COMPLETE_DATE_TIME)
            .paymentFailureDateTime(UPDATED_PAYMENT_FAILURE_DATE_TIME)
            .patronComment(UPDATED_PATRON_COMMENT)
            .isAutoPay(UPDATED_IS_AUTO_PAY)
            .paymentDestinationSource(UPDATED_PAYMENT_DESTINATION_SOURCE)
            .paymentReceivedDateTIme(UPDATED_PAYMENT_RECEIVED_DATE_T_IME)
            .paymentReceivedStatus(UPDATED_PAYMENT_RECEIVED_STATUS)
            .paymentReceivedDetails(UPDATED_PAYMENT_RECEIVED_DETAILS)
            .paymentRefundedDateTime(UPDATED_PAYMENT_REFUNDED_DATE_TIME)
            .userComment(UPDATED_USER_COMMENT)
            .flaggedDateTime(UPDATED_FLAGGED_DATE_TIME)
            .flagDetails(UPDATED_FLAG_DETAILS)
            .flaggedEmailId(UPDATED_FLAGGED_EMAIL_ID)
            .flaggedAmount(UPDATED_FLAGGED_AMOUNT)
            .flagCreatedDateTime(UPDATED_FLAG_CREATED_DATE_TIME)
            .isRecurringPayment(UPDATED_IS_RECURRING_PAYMENT)
            .transactionDetails(UPDATED_TRANSACTION_DETAILS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPayment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPayment))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getRecurringPeriod()).isEqualTo(UPDATED_RECURRING_PERIOD);
        assertThat(testPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayment.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testPayment.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPayment.getPaymentSource()).isEqualTo(UPDATED_PAYMENT_SOURCE);
        assertThat(testPayment.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPayment.getPaymentStatusDetails()).isEqualTo(UPDATED_PAYMENT_STATUS_DETAILS);
        assertThat(testPayment.getPaymentStartDateTime()).isEqualTo(UPDATED_PAYMENT_START_DATE_TIME);
        assertThat(testPayment.getPaymentCompleteDateTime()).isEqualTo(UPDATED_PAYMENT_COMPLETE_DATE_TIME);
        assertThat(testPayment.getPaymentFailureDateTime()).isEqualTo(UPDATED_PAYMENT_FAILURE_DATE_TIME);
        assertThat(testPayment.getPatronComment()).isEqualTo(UPDATED_PATRON_COMMENT);
        assertThat(testPayment.getIsAutoPay()).isEqualTo(UPDATED_IS_AUTO_PAY);
        assertThat(testPayment.getPaymentDestinationSource()).isEqualTo(UPDATED_PAYMENT_DESTINATION_SOURCE);
        assertThat(testPayment.getPaymentReceivedDateTIme()).isEqualTo(UPDATED_PAYMENT_RECEIVED_DATE_T_IME);
        assertThat(testPayment.getPaymentReceivedStatus()).isEqualTo(UPDATED_PAYMENT_RECEIVED_STATUS);
        assertThat(testPayment.getPaymentReceivedDetails()).isEqualTo(UPDATED_PAYMENT_RECEIVED_DETAILS);
        assertThat(testPayment.getPaymentRefundedDateTime()).isEqualTo(UPDATED_PAYMENT_REFUNDED_DATE_TIME);
        assertThat(testPayment.getUserComment()).isEqualTo(UPDATED_USER_COMMENT);
        assertThat(testPayment.getFlaggedDateTime()).isEqualTo(UPDATED_FLAGGED_DATE_TIME);
        assertThat(testPayment.getFlagDetails()).isEqualTo(UPDATED_FLAG_DETAILS);
        assertThat(testPayment.getFlaggedEmailId()).isEqualTo(UPDATED_FLAGGED_EMAIL_ID);
        assertThat(testPayment.getFlaggedAmount()).isEqualTo(UPDATED_FLAGGED_AMOUNT);
        assertThat(testPayment.getFlagCreatedDateTime()).isEqualTo(UPDATED_FLAG_CREATED_DATE_TIME);
        assertThat(testPayment.getIsRecurringPayment()).isEqualTo(UPDATED_IS_RECURRING_PAYMENT);
        assertThat(testPayment.getTransactionDetails()).isEqualTo(UPDATED_TRANSACTION_DETAILS);
        assertThat(testPayment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayment.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentWithPatch() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment using partial update
        Payment partialUpdatedPayment = new Payment();
        partialUpdatedPayment.setId(payment.getId());

        partialUpdatedPayment
            .amount(UPDATED_AMOUNT)
            .paymentSource(UPDATED_PAYMENT_SOURCE)
            .paymentStatusDetails(UPDATED_PAYMENT_STATUS_DETAILS)
            .paymentFailureDateTime(UPDATED_PAYMENT_FAILURE_DATE_TIME)
            .paymentDestinationSource(UPDATED_PAYMENT_DESTINATION_SOURCE)
            .paymentReceivedDetails(UPDATED_PAYMENT_RECEIVED_DETAILS)
            .flagDetails(UPDATED_FLAG_DETAILS)
            .flaggedEmailId(UPDATED_FLAGGED_EMAIL_ID)
            .updatedDate(UPDATED_UPDATED_DATE);

        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayment))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getRecurringPeriod()).isEqualTo(DEFAULT_RECURRING_PERIOD);
        assertThat(testPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayment.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testPayment.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testPayment.getPaymentSource()).isEqualTo(UPDATED_PAYMENT_SOURCE);
        assertThat(testPayment.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPayment.getPaymentStatusDetails()).isEqualTo(UPDATED_PAYMENT_STATUS_DETAILS);
        assertThat(testPayment.getPaymentStartDateTime()).isEqualTo(DEFAULT_PAYMENT_START_DATE_TIME);
        assertThat(testPayment.getPaymentCompleteDateTime()).isEqualTo(DEFAULT_PAYMENT_COMPLETE_DATE_TIME);
        assertThat(testPayment.getPaymentFailureDateTime()).isEqualTo(UPDATED_PAYMENT_FAILURE_DATE_TIME);
        assertThat(testPayment.getPatronComment()).isEqualTo(DEFAULT_PATRON_COMMENT);
        assertThat(testPayment.getIsAutoPay()).isEqualTo(DEFAULT_IS_AUTO_PAY);
        assertThat(testPayment.getPaymentDestinationSource()).isEqualTo(UPDATED_PAYMENT_DESTINATION_SOURCE);
        assertThat(testPayment.getPaymentReceivedDateTIme()).isEqualTo(DEFAULT_PAYMENT_RECEIVED_DATE_T_IME);
        assertThat(testPayment.getPaymentReceivedStatus()).isEqualTo(DEFAULT_PAYMENT_RECEIVED_STATUS);
        assertThat(testPayment.getPaymentReceivedDetails()).isEqualTo(UPDATED_PAYMENT_RECEIVED_DETAILS);
        assertThat(testPayment.getPaymentRefundedDateTime()).isEqualTo(DEFAULT_PAYMENT_REFUNDED_DATE_TIME);
        assertThat(testPayment.getUserComment()).isEqualTo(DEFAULT_USER_COMMENT);
        assertThat(testPayment.getFlaggedDateTime()).isEqualTo(DEFAULT_FLAGGED_DATE_TIME);
        assertThat(testPayment.getFlagDetails()).isEqualTo(UPDATED_FLAG_DETAILS);
        assertThat(testPayment.getFlaggedEmailId()).isEqualTo(UPDATED_FLAGGED_EMAIL_ID);
        assertThat(testPayment.getFlaggedAmount()).isEqualTo(DEFAULT_FLAGGED_AMOUNT);
        assertThat(testPayment.getFlagCreatedDateTime()).isEqualTo(DEFAULT_FLAG_CREATED_DATE_TIME);
        assertThat(testPayment.getIsRecurringPayment()).isEqualTo(DEFAULT_IS_RECURRING_PAYMENT);
        assertThat(testPayment.getTransactionDetails()).isEqualTo(DEFAULT_TRANSACTION_DETAILS);
        assertThat(testPayment.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayment.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePaymentWithPatch() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment using partial update
        Payment partialUpdatedPayment = new Payment();
        partialUpdatedPayment.setId(payment.getId());

        partialUpdatedPayment
            .recurringPeriod(UPDATED_RECURRING_PERIOD)
            .amount(UPDATED_AMOUNT)
            .transactionId(UPDATED_TRANSACTION_ID)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .paymentSource(UPDATED_PAYMENT_SOURCE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .paymentStatusDetails(UPDATED_PAYMENT_STATUS_DETAILS)
            .paymentStartDateTime(UPDATED_PAYMENT_START_DATE_TIME)
            .paymentCompleteDateTime(UPDATED_PAYMENT_COMPLETE_DATE_TIME)
            .paymentFailureDateTime(UPDATED_PAYMENT_FAILURE_DATE_TIME)
            .patronComment(UPDATED_PATRON_COMMENT)
            .isAutoPay(UPDATED_IS_AUTO_PAY)
            .paymentDestinationSource(UPDATED_PAYMENT_DESTINATION_SOURCE)
            .paymentReceivedDateTIme(UPDATED_PAYMENT_RECEIVED_DATE_T_IME)
            .paymentReceivedStatus(UPDATED_PAYMENT_RECEIVED_STATUS)
            .paymentReceivedDetails(UPDATED_PAYMENT_RECEIVED_DETAILS)
            .paymentRefundedDateTime(UPDATED_PAYMENT_REFUNDED_DATE_TIME)
            .userComment(UPDATED_USER_COMMENT)
            .flaggedDateTime(UPDATED_FLAGGED_DATE_TIME)
            .flagDetails(UPDATED_FLAG_DETAILS)
            .flaggedEmailId(UPDATED_FLAGGED_EMAIL_ID)
            .flaggedAmount(UPDATED_FLAGGED_AMOUNT)
            .flagCreatedDateTime(UPDATED_FLAG_CREATED_DATE_TIME)
            .isRecurringPayment(UPDATED_IS_RECURRING_PAYMENT)
            .transactionDetails(UPDATED_TRANSACTION_DETAILS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayment))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getRecurringPeriod()).isEqualTo(UPDATED_RECURRING_PERIOD);
        assertThat(testPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayment.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testPayment.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPayment.getPaymentSource()).isEqualTo(UPDATED_PAYMENT_SOURCE);
        assertThat(testPayment.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPayment.getPaymentStatusDetails()).isEqualTo(UPDATED_PAYMENT_STATUS_DETAILS);
        assertThat(testPayment.getPaymentStartDateTime()).isEqualTo(UPDATED_PAYMENT_START_DATE_TIME);
        assertThat(testPayment.getPaymentCompleteDateTime()).isEqualTo(UPDATED_PAYMENT_COMPLETE_DATE_TIME);
        assertThat(testPayment.getPaymentFailureDateTime()).isEqualTo(UPDATED_PAYMENT_FAILURE_DATE_TIME);
        assertThat(testPayment.getPatronComment()).isEqualTo(UPDATED_PATRON_COMMENT);
        assertThat(testPayment.getIsAutoPay()).isEqualTo(UPDATED_IS_AUTO_PAY);
        assertThat(testPayment.getPaymentDestinationSource()).isEqualTo(UPDATED_PAYMENT_DESTINATION_SOURCE);
        assertThat(testPayment.getPaymentReceivedDateTIme()).isEqualTo(UPDATED_PAYMENT_RECEIVED_DATE_T_IME);
        assertThat(testPayment.getPaymentReceivedStatus()).isEqualTo(UPDATED_PAYMENT_RECEIVED_STATUS);
        assertThat(testPayment.getPaymentReceivedDetails()).isEqualTo(UPDATED_PAYMENT_RECEIVED_DETAILS);
        assertThat(testPayment.getPaymentRefundedDateTime()).isEqualTo(UPDATED_PAYMENT_REFUNDED_DATE_TIME);
        assertThat(testPayment.getUserComment()).isEqualTo(UPDATED_USER_COMMENT);
        assertThat(testPayment.getFlaggedDateTime()).isEqualTo(UPDATED_FLAGGED_DATE_TIME);
        assertThat(testPayment.getFlagDetails()).isEqualTo(UPDATED_FLAG_DETAILS);
        assertThat(testPayment.getFlaggedEmailId()).isEqualTo(UPDATED_FLAGGED_EMAIL_ID);
        assertThat(testPayment.getFlaggedAmount()).isEqualTo(UPDATED_FLAGGED_AMOUNT);
        assertThat(testPayment.getFlagCreatedDateTime()).isEqualTo(UPDATED_FLAG_CREATED_DATE_TIME);
        assertThat(testPayment.getIsRecurringPayment()).isEqualTo(UPDATED_IS_RECURRING_PAYMENT);
        assertThat(testPayment.getTransactionDetails()).isEqualTo(UPDATED_TRANSACTION_DETAILS);
        assertThat(testPayment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayment.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Delete the payment
        restPaymentMockMvc
            .perform(delete(ENTITY_API_URL_ID, payment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
