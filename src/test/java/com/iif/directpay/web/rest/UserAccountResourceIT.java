package com.iif.directpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iif.directpay.IntegrationTest;
import com.iif.directpay.domain.UserAccount;
import com.iif.directpay.domain.enumeration.UserAccountBankStatus;
import com.iif.directpay.domain.enumeration.UserAccountType;
import com.iif.directpay.domain.enumeration.UserAccountUpiStatus;
import com.iif.directpay.domain.enumeration.UserAccountWalletStatus;
import com.iif.directpay.domain.enumeration.UserAccountWalletType;
import com.iif.directpay.repository.UserAccountRepository;
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
 * Integration tests for the {@link UserAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserAccountResourceIT {

    private static final UserAccountType DEFAULT_ACCOUNT_TYPE = UserAccountType.UPI;
    private static final UserAccountType UPDATED_ACCOUNT_TYPE = UserAccountType.BANK;

    private static final String DEFAULT_UPI_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_UPI_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_MOBILE_NUMBER = 1;
    private static final Integer UPDATED_MOBILE_NUMBER = 2;

    private static final LocalDate DEFAULT_UPI_ACTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPI_ACTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final UserAccountUpiStatus DEFAULT_UPI_STATUS = UserAccountUpiStatus.INACTIVE;
    private static final UserAccountUpiStatus UPDATED_UPI_STATUS = UserAccountUpiStatus.ACTIVE;

    private static final Instant DEFAULT_UPI_SUSPENDED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPI_SUSPENDED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPI_DELETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPI_DELETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_UPI_AUTO_DEBIT_ENABLED = false;
    private static final Boolean UPDATED_UPI_AUTO_DEBIT_ENABLED = true;

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BANK_ACCOUNT_NUMBER = 1;
    private static final Integer UPDATED_BANK_ACCOUNT_NUMBER = 2;

    private static final String DEFAULT_BANK_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_IFSC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_SWIFT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_SWIFT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_BRANCH_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BANK_BRANCH_ADDRESS = "BBBBBBBBBB";

    private static final UserAccountBankStatus DEFAULT_BANK_STATUS = UserAccountBankStatus.INACTIVE;
    private static final UserAccountBankStatus UPDATED_BANK_STATUS = UserAccountBankStatus.ACTIVE;

    private static final Instant DEFAULT_BANK_ACTIVE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BANK_ACTIVE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BANK_SUSPENDED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BANK_SUSPENDED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BANK_DELETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BANK_DELETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_BANK_AUTO_DEBIT_ENABLED = false;
    private static final Boolean UPDATED_BANK_AUTO_DEBIT_ENABLED = true;

    private static final UserAccountWalletType DEFAULT_WALLET_TYPE = UserAccountWalletType.PAYTM;
    private static final UserAccountWalletType UPDATED_WALLET_TYPE = UserAccountWalletType.GPAY;

    private static final String DEFAULT_WALLET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_ADDRESS = "BBBBBBBBBB";

    private static final UserAccountWalletStatus DEFAULT_WALLET_STATUS = UserAccountWalletStatus.INACTIVE;
    private static final UserAccountWalletStatus UPDATED_WALLET_STATUS = UserAccountWalletStatus.ACTIVE;

    private static final Instant DEFAULT_WALLTER_ACTIVE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WALLTER_ACTIVE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_WALLET_SUSPENDED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WALLET_SUSPENDED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_WALLET_DELETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WALLET_DELETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_WALLET_AUTO_DEBIT_ENABLED = false;
    private static final Boolean UPDATED_WALLET_AUTO_DEBIT_ENABLED = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAccountMockMvc;

    private UserAccount userAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAccount createEntity(EntityManager em) {
        UserAccount userAccount = new UserAccount()
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .upiAddress(DEFAULT_UPI_ADDRESS)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .upiActiveDate(DEFAULT_UPI_ACTIVE_DATE)
            .upiStatus(DEFAULT_UPI_STATUS)
            .upiSuspendedDate(DEFAULT_UPI_SUSPENDED_DATE)
            .upiDeletedDate(DEFAULT_UPI_DELETED_DATE)
            .upiAutoDebitEnabled(DEFAULT_UPI_AUTO_DEBIT_ENABLED)
            .bankName(DEFAULT_BANK_NAME)
            .bankAccountNumber(DEFAULT_BANK_ACCOUNT_NUMBER)
            .bankIFSCCode(DEFAULT_BANK_IFSC_CODE)
            .bankSWIFTCode(DEFAULT_BANK_SWIFT_CODE)
            .bankBranchAddress(DEFAULT_BANK_BRANCH_ADDRESS)
            .bankStatus(DEFAULT_BANK_STATUS)
            .bankActiveDate(DEFAULT_BANK_ACTIVE_DATE)
            .bankSuspendedDate(DEFAULT_BANK_SUSPENDED_DATE)
            .bankDeletedDate(DEFAULT_BANK_DELETED_DATE)
            .bankAutoDebitEnabled(DEFAULT_BANK_AUTO_DEBIT_ENABLED)
            .walletType(DEFAULT_WALLET_TYPE)
            .walletAddress(DEFAULT_WALLET_ADDRESS)
            .walletStatus(DEFAULT_WALLET_STATUS)
            .wallterActiveDate(DEFAULT_WALLTER_ACTIVE_DATE)
            .walletSuspendedDate(DEFAULT_WALLET_SUSPENDED_DATE)
            .walletDeletedDate(DEFAULT_WALLET_DELETED_DATE)
            .walletAutoDebitEnabled(DEFAULT_WALLET_AUTO_DEBIT_ENABLED)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return userAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAccount createUpdatedEntity(EntityManager em) {
        UserAccount userAccount = new UserAccount()
            .accountType(UPDATED_ACCOUNT_TYPE)
            .upiAddress(UPDATED_UPI_ADDRESS)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .upiActiveDate(UPDATED_UPI_ACTIVE_DATE)
            .upiStatus(UPDATED_UPI_STATUS)
            .upiSuspendedDate(UPDATED_UPI_SUSPENDED_DATE)
            .upiDeletedDate(UPDATED_UPI_DELETED_DATE)
            .upiAutoDebitEnabled(UPDATED_UPI_AUTO_DEBIT_ENABLED)
            .bankName(UPDATED_BANK_NAME)
            .bankAccountNumber(UPDATED_BANK_ACCOUNT_NUMBER)
            .bankIFSCCode(UPDATED_BANK_IFSC_CODE)
            .bankSWIFTCode(UPDATED_BANK_SWIFT_CODE)
            .bankBranchAddress(UPDATED_BANK_BRANCH_ADDRESS)
            .bankStatus(UPDATED_BANK_STATUS)
            .bankActiveDate(UPDATED_BANK_ACTIVE_DATE)
            .bankSuspendedDate(UPDATED_BANK_SUSPENDED_DATE)
            .bankDeletedDate(UPDATED_BANK_DELETED_DATE)
            .bankAutoDebitEnabled(UPDATED_BANK_AUTO_DEBIT_ENABLED)
            .walletType(UPDATED_WALLET_TYPE)
            .walletAddress(UPDATED_WALLET_ADDRESS)
            .walletStatus(UPDATED_WALLET_STATUS)
            .wallterActiveDate(UPDATED_WALLTER_ACTIVE_DATE)
            .walletSuspendedDate(UPDATED_WALLET_SUSPENDED_DATE)
            .walletDeletedDate(UPDATED_WALLET_DELETED_DATE)
            .walletAutoDebitEnabled(UPDATED_WALLET_AUTO_DEBIT_ENABLED)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return userAccount;
    }

    @BeforeEach
    public void initTest() {
        userAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createUserAccount() throws Exception {
        int databaseSizeBeforeCreate = userAccountRepository.findAll().size();
        // Create the UserAccount
        restUserAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAccount)))
            .andExpect(status().isCreated());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeCreate + 1);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testUserAccount.getUpiAddress()).isEqualTo(DEFAULT_UPI_ADDRESS);
        assertThat(testUserAccount.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testUserAccount.getUpiActiveDate()).isEqualTo(DEFAULT_UPI_ACTIVE_DATE);
        assertThat(testUserAccount.getUpiStatus()).isEqualTo(DEFAULT_UPI_STATUS);
        assertThat(testUserAccount.getUpiSuspendedDate()).isEqualTo(DEFAULT_UPI_SUSPENDED_DATE);
        assertThat(testUserAccount.getUpiDeletedDate()).isEqualTo(DEFAULT_UPI_DELETED_DATE);
        assertThat(testUserAccount.getUpiAutoDebitEnabled()).isEqualTo(DEFAULT_UPI_AUTO_DEBIT_ENABLED);
        assertThat(testUserAccount.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testUserAccount.getBankAccountNumber()).isEqualTo(DEFAULT_BANK_ACCOUNT_NUMBER);
        assertThat(testUserAccount.getBankIFSCCode()).isEqualTo(DEFAULT_BANK_IFSC_CODE);
        assertThat(testUserAccount.getBankSWIFTCode()).isEqualTo(DEFAULT_BANK_SWIFT_CODE);
        assertThat(testUserAccount.getBankBranchAddress()).isEqualTo(DEFAULT_BANK_BRANCH_ADDRESS);
        assertThat(testUserAccount.getBankStatus()).isEqualTo(DEFAULT_BANK_STATUS);
        assertThat(testUserAccount.getBankActiveDate()).isEqualTo(DEFAULT_BANK_ACTIVE_DATE);
        assertThat(testUserAccount.getBankSuspendedDate()).isEqualTo(DEFAULT_BANK_SUSPENDED_DATE);
        assertThat(testUserAccount.getBankDeletedDate()).isEqualTo(DEFAULT_BANK_DELETED_DATE);
        assertThat(testUserAccount.getBankAutoDebitEnabled()).isEqualTo(DEFAULT_BANK_AUTO_DEBIT_ENABLED);
        assertThat(testUserAccount.getWalletType()).isEqualTo(DEFAULT_WALLET_TYPE);
        assertThat(testUserAccount.getWalletAddress()).isEqualTo(DEFAULT_WALLET_ADDRESS);
        assertThat(testUserAccount.getWalletStatus()).isEqualTo(DEFAULT_WALLET_STATUS);
        assertThat(testUserAccount.getWallterActiveDate()).isEqualTo(DEFAULT_WALLTER_ACTIVE_DATE);
        assertThat(testUserAccount.getWalletSuspendedDate()).isEqualTo(DEFAULT_WALLET_SUSPENDED_DATE);
        assertThat(testUserAccount.getWalletDeletedDate()).isEqualTo(DEFAULT_WALLET_DELETED_DATE);
        assertThat(testUserAccount.getWalletAutoDebitEnabled()).isEqualTo(DEFAULT_WALLET_AUTO_DEBIT_ENABLED);
        assertThat(testUserAccount.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserAccount.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createUserAccountWithExistingId() throws Exception {
        // Create the UserAccount with an existing ID
        userAccount.setId(1L);

        int databaseSizeBeforeCreate = userAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAccount)))
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAccountTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAccountRepository.findAll().size();
        // set the field null
        userAccount.setAccountType(null);

        // Create the UserAccount, which fails.

        restUserAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAccount)))
            .andExpect(status().isBadRequest());

        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserAccounts() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        // Get all the userAccountList
        restUserAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].upiAddress").value(hasItem(DEFAULT_UPI_ADDRESS)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].upiActiveDate").value(hasItem(DEFAULT_UPI_ACTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].upiStatus").value(hasItem(DEFAULT_UPI_STATUS.toString())))
            .andExpect(jsonPath("$.[*].upiSuspendedDate").value(hasItem(DEFAULT_UPI_SUSPENDED_DATE.toString())))
            .andExpect(jsonPath("$.[*].upiDeletedDate").value(hasItem(DEFAULT_UPI_DELETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].upiAutoDebitEnabled").value(hasItem(DEFAULT_UPI_AUTO_DEBIT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].bankAccountNumber").value(hasItem(DEFAULT_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bankIFSCCode").value(hasItem(DEFAULT_BANK_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].bankSWIFTCode").value(hasItem(DEFAULT_BANK_SWIFT_CODE)))
            .andExpect(jsonPath("$.[*].bankBranchAddress").value(hasItem(DEFAULT_BANK_BRANCH_ADDRESS)))
            .andExpect(jsonPath("$.[*].bankStatus").value(hasItem(DEFAULT_BANK_STATUS.toString())))
            .andExpect(jsonPath("$.[*].bankActiveDate").value(hasItem(DEFAULT_BANK_ACTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankSuspendedDate").value(hasItem(DEFAULT_BANK_SUSPENDED_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankDeletedDate").value(hasItem(DEFAULT_BANK_DELETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankAutoDebitEnabled").value(hasItem(DEFAULT_BANK_AUTO_DEBIT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].walletType").value(hasItem(DEFAULT_WALLET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].walletAddress").value(hasItem(DEFAULT_WALLET_ADDRESS)))
            .andExpect(jsonPath("$.[*].walletStatus").value(hasItem(DEFAULT_WALLET_STATUS.toString())))
            .andExpect(jsonPath("$.[*].wallterActiveDate").value(hasItem(DEFAULT_WALLTER_ACTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].walletSuspendedDate").value(hasItem(DEFAULT_WALLET_SUSPENDED_DATE.toString())))
            .andExpect(jsonPath("$.[*].walletDeletedDate").value(hasItem(DEFAULT_WALLET_DELETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].walletAutoDebitEnabled").value(hasItem(DEFAULT_WALLET_AUTO_DEBIT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        // Get the userAccount
        restUserAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, userAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.upiAddress").value(DEFAULT_UPI_ADDRESS))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER))
            .andExpect(jsonPath("$.upiActiveDate").value(DEFAULT_UPI_ACTIVE_DATE.toString()))
            .andExpect(jsonPath("$.upiStatus").value(DEFAULT_UPI_STATUS.toString()))
            .andExpect(jsonPath("$.upiSuspendedDate").value(DEFAULT_UPI_SUSPENDED_DATE.toString()))
            .andExpect(jsonPath("$.upiDeletedDate").value(DEFAULT_UPI_DELETED_DATE.toString()))
            .andExpect(jsonPath("$.upiAutoDebitEnabled").value(DEFAULT_UPI_AUTO_DEBIT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.bankAccountNumber").value(DEFAULT_BANK_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.bankIFSCCode").value(DEFAULT_BANK_IFSC_CODE))
            .andExpect(jsonPath("$.bankSWIFTCode").value(DEFAULT_BANK_SWIFT_CODE))
            .andExpect(jsonPath("$.bankBranchAddress").value(DEFAULT_BANK_BRANCH_ADDRESS))
            .andExpect(jsonPath("$.bankStatus").value(DEFAULT_BANK_STATUS.toString()))
            .andExpect(jsonPath("$.bankActiveDate").value(DEFAULT_BANK_ACTIVE_DATE.toString()))
            .andExpect(jsonPath("$.bankSuspendedDate").value(DEFAULT_BANK_SUSPENDED_DATE.toString()))
            .andExpect(jsonPath("$.bankDeletedDate").value(DEFAULT_BANK_DELETED_DATE.toString()))
            .andExpect(jsonPath("$.bankAutoDebitEnabled").value(DEFAULT_BANK_AUTO_DEBIT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.walletType").value(DEFAULT_WALLET_TYPE.toString()))
            .andExpect(jsonPath("$.walletAddress").value(DEFAULT_WALLET_ADDRESS))
            .andExpect(jsonPath("$.walletStatus").value(DEFAULT_WALLET_STATUS.toString()))
            .andExpect(jsonPath("$.wallterActiveDate").value(DEFAULT_WALLTER_ACTIVE_DATE.toString()))
            .andExpect(jsonPath("$.walletSuspendedDate").value(DEFAULT_WALLET_SUSPENDED_DATE.toString()))
            .andExpect(jsonPath("$.walletDeletedDate").value(DEFAULT_WALLET_DELETED_DATE.toString()))
            .andExpect(jsonPath("$.walletAutoDebitEnabled").value(DEFAULT_WALLET_AUTO_DEBIT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserAccount() throws Exception {
        // Get the userAccount
        restUserAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();

        // Update the userAccount
        UserAccount updatedUserAccount = userAccountRepository.findById(userAccount.getId()).get();
        // Disconnect from session so that the updates on updatedUserAccount are not directly saved in db
        em.detach(updatedUserAccount);
        updatedUserAccount
            .accountType(UPDATED_ACCOUNT_TYPE)
            .upiAddress(UPDATED_UPI_ADDRESS)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .upiActiveDate(UPDATED_UPI_ACTIVE_DATE)
            .upiStatus(UPDATED_UPI_STATUS)
            .upiSuspendedDate(UPDATED_UPI_SUSPENDED_DATE)
            .upiDeletedDate(UPDATED_UPI_DELETED_DATE)
            .upiAutoDebitEnabled(UPDATED_UPI_AUTO_DEBIT_ENABLED)
            .bankName(UPDATED_BANK_NAME)
            .bankAccountNumber(UPDATED_BANK_ACCOUNT_NUMBER)
            .bankIFSCCode(UPDATED_BANK_IFSC_CODE)
            .bankSWIFTCode(UPDATED_BANK_SWIFT_CODE)
            .bankBranchAddress(UPDATED_BANK_BRANCH_ADDRESS)
            .bankStatus(UPDATED_BANK_STATUS)
            .bankActiveDate(UPDATED_BANK_ACTIVE_DATE)
            .bankSuspendedDate(UPDATED_BANK_SUSPENDED_DATE)
            .bankDeletedDate(UPDATED_BANK_DELETED_DATE)
            .bankAutoDebitEnabled(UPDATED_BANK_AUTO_DEBIT_ENABLED)
            .walletType(UPDATED_WALLET_TYPE)
            .walletAddress(UPDATED_WALLET_ADDRESS)
            .walletStatus(UPDATED_WALLET_STATUS)
            .wallterActiveDate(UPDATED_WALLTER_ACTIVE_DATE)
            .walletSuspendedDate(UPDATED_WALLET_SUSPENDED_DATE)
            .walletDeletedDate(UPDATED_WALLET_DELETED_DATE)
            .walletAutoDebitEnabled(UPDATED_WALLET_AUTO_DEBIT_ENABLED)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserAccount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserAccount))
            )
            .andExpect(status().isOk());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testUserAccount.getUpiAddress()).isEqualTo(UPDATED_UPI_ADDRESS);
        assertThat(testUserAccount.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testUserAccount.getUpiActiveDate()).isEqualTo(UPDATED_UPI_ACTIVE_DATE);
        assertThat(testUserAccount.getUpiStatus()).isEqualTo(UPDATED_UPI_STATUS);
        assertThat(testUserAccount.getUpiSuspendedDate()).isEqualTo(UPDATED_UPI_SUSPENDED_DATE);
        assertThat(testUserAccount.getUpiDeletedDate()).isEqualTo(UPDATED_UPI_DELETED_DATE);
        assertThat(testUserAccount.getUpiAutoDebitEnabled()).isEqualTo(UPDATED_UPI_AUTO_DEBIT_ENABLED);
        assertThat(testUserAccount.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testUserAccount.getBankAccountNumber()).isEqualTo(UPDATED_BANK_ACCOUNT_NUMBER);
        assertThat(testUserAccount.getBankIFSCCode()).isEqualTo(UPDATED_BANK_IFSC_CODE);
        assertThat(testUserAccount.getBankSWIFTCode()).isEqualTo(UPDATED_BANK_SWIFT_CODE);
        assertThat(testUserAccount.getBankBranchAddress()).isEqualTo(UPDATED_BANK_BRANCH_ADDRESS);
        assertThat(testUserAccount.getBankStatus()).isEqualTo(UPDATED_BANK_STATUS);
        assertThat(testUserAccount.getBankActiveDate()).isEqualTo(UPDATED_BANK_ACTIVE_DATE);
        assertThat(testUserAccount.getBankSuspendedDate()).isEqualTo(UPDATED_BANK_SUSPENDED_DATE);
        assertThat(testUserAccount.getBankDeletedDate()).isEqualTo(UPDATED_BANK_DELETED_DATE);
        assertThat(testUserAccount.getBankAutoDebitEnabled()).isEqualTo(UPDATED_BANK_AUTO_DEBIT_ENABLED);
        assertThat(testUserAccount.getWalletType()).isEqualTo(UPDATED_WALLET_TYPE);
        assertThat(testUserAccount.getWalletAddress()).isEqualTo(UPDATED_WALLET_ADDRESS);
        assertThat(testUserAccount.getWalletStatus()).isEqualTo(UPDATED_WALLET_STATUS);
        assertThat(testUserAccount.getWallterActiveDate()).isEqualTo(UPDATED_WALLTER_ACTIVE_DATE);
        assertThat(testUserAccount.getWalletSuspendedDate()).isEqualTo(UPDATED_WALLET_SUSPENDED_DATE);
        assertThat(testUserAccount.getWalletDeletedDate()).isEqualTo(UPDATED_WALLET_DELETED_DATE);
        assertThat(testUserAccount.getWalletAutoDebitEnabled()).isEqualTo(UPDATED_WALLET_AUTO_DEBIT_ENABLED);
        assertThat(testUserAccount.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserAccount.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();
        userAccount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAccount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();
        userAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();
        userAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAccountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAccount)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserAccountWithPatch() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();

        // Update the userAccount using partial update
        UserAccount partialUpdatedUserAccount = new UserAccount();
        partialUpdatedUserAccount.setId(userAccount.getId());

        partialUpdatedUserAccount
            .accountType(UPDATED_ACCOUNT_TYPE)
            .upiAddress(UPDATED_UPI_ADDRESS)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .upiActiveDate(UPDATED_UPI_ACTIVE_DATE)
            .upiSuspendedDate(UPDATED_UPI_SUSPENDED_DATE)
            .upiDeletedDate(UPDATED_UPI_DELETED_DATE)
            .bankIFSCCode(UPDATED_BANK_IFSC_CODE)
            .bankActiveDate(UPDATED_BANK_ACTIVE_DATE)
            .bankDeletedDate(UPDATED_BANK_DELETED_DATE)
            .bankAutoDebitEnabled(UPDATED_BANK_AUTO_DEBIT_ENABLED)
            .walletType(UPDATED_WALLET_TYPE)
            .walletAddress(UPDATED_WALLET_ADDRESS)
            .wallterActiveDate(UPDATED_WALLTER_ACTIVE_DATE)
            .walletAutoDebitEnabled(UPDATED_WALLET_AUTO_DEBIT_ENABLED);

        restUserAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAccount))
            )
            .andExpect(status().isOk());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testUserAccount.getUpiAddress()).isEqualTo(UPDATED_UPI_ADDRESS);
        assertThat(testUserAccount.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testUserAccount.getUpiActiveDate()).isEqualTo(UPDATED_UPI_ACTIVE_DATE);
        assertThat(testUserAccount.getUpiStatus()).isEqualTo(DEFAULT_UPI_STATUS);
        assertThat(testUserAccount.getUpiSuspendedDate()).isEqualTo(UPDATED_UPI_SUSPENDED_DATE);
        assertThat(testUserAccount.getUpiDeletedDate()).isEqualTo(UPDATED_UPI_DELETED_DATE);
        assertThat(testUserAccount.getUpiAutoDebitEnabled()).isEqualTo(DEFAULT_UPI_AUTO_DEBIT_ENABLED);
        assertThat(testUserAccount.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testUserAccount.getBankAccountNumber()).isEqualTo(DEFAULT_BANK_ACCOUNT_NUMBER);
        assertThat(testUserAccount.getBankIFSCCode()).isEqualTo(UPDATED_BANK_IFSC_CODE);
        assertThat(testUserAccount.getBankSWIFTCode()).isEqualTo(DEFAULT_BANK_SWIFT_CODE);
        assertThat(testUserAccount.getBankBranchAddress()).isEqualTo(DEFAULT_BANK_BRANCH_ADDRESS);
        assertThat(testUserAccount.getBankStatus()).isEqualTo(DEFAULT_BANK_STATUS);
        assertThat(testUserAccount.getBankActiveDate()).isEqualTo(UPDATED_BANK_ACTIVE_DATE);
        assertThat(testUserAccount.getBankSuspendedDate()).isEqualTo(DEFAULT_BANK_SUSPENDED_DATE);
        assertThat(testUserAccount.getBankDeletedDate()).isEqualTo(UPDATED_BANK_DELETED_DATE);
        assertThat(testUserAccount.getBankAutoDebitEnabled()).isEqualTo(UPDATED_BANK_AUTO_DEBIT_ENABLED);
        assertThat(testUserAccount.getWalletType()).isEqualTo(UPDATED_WALLET_TYPE);
        assertThat(testUserAccount.getWalletAddress()).isEqualTo(UPDATED_WALLET_ADDRESS);
        assertThat(testUserAccount.getWalletStatus()).isEqualTo(DEFAULT_WALLET_STATUS);
        assertThat(testUserAccount.getWallterActiveDate()).isEqualTo(UPDATED_WALLTER_ACTIVE_DATE);
        assertThat(testUserAccount.getWalletSuspendedDate()).isEqualTo(DEFAULT_WALLET_SUSPENDED_DATE);
        assertThat(testUserAccount.getWalletDeletedDate()).isEqualTo(DEFAULT_WALLET_DELETED_DATE);
        assertThat(testUserAccount.getWalletAutoDebitEnabled()).isEqualTo(UPDATED_WALLET_AUTO_DEBIT_ENABLED);
        assertThat(testUserAccount.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserAccount.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserAccountWithPatch() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();

        // Update the userAccount using partial update
        UserAccount partialUpdatedUserAccount = new UserAccount();
        partialUpdatedUserAccount.setId(userAccount.getId());

        partialUpdatedUserAccount
            .accountType(UPDATED_ACCOUNT_TYPE)
            .upiAddress(UPDATED_UPI_ADDRESS)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .upiActiveDate(UPDATED_UPI_ACTIVE_DATE)
            .upiStatus(UPDATED_UPI_STATUS)
            .upiSuspendedDate(UPDATED_UPI_SUSPENDED_DATE)
            .upiDeletedDate(UPDATED_UPI_DELETED_DATE)
            .upiAutoDebitEnabled(UPDATED_UPI_AUTO_DEBIT_ENABLED)
            .bankName(UPDATED_BANK_NAME)
            .bankAccountNumber(UPDATED_BANK_ACCOUNT_NUMBER)
            .bankIFSCCode(UPDATED_BANK_IFSC_CODE)
            .bankSWIFTCode(UPDATED_BANK_SWIFT_CODE)
            .bankBranchAddress(UPDATED_BANK_BRANCH_ADDRESS)
            .bankStatus(UPDATED_BANK_STATUS)
            .bankActiveDate(UPDATED_BANK_ACTIVE_DATE)
            .bankSuspendedDate(UPDATED_BANK_SUSPENDED_DATE)
            .bankDeletedDate(UPDATED_BANK_DELETED_DATE)
            .bankAutoDebitEnabled(UPDATED_BANK_AUTO_DEBIT_ENABLED)
            .walletType(UPDATED_WALLET_TYPE)
            .walletAddress(UPDATED_WALLET_ADDRESS)
            .walletStatus(UPDATED_WALLET_STATUS)
            .wallterActiveDate(UPDATED_WALLTER_ACTIVE_DATE)
            .walletSuspendedDate(UPDATED_WALLET_SUSPENDED_DATE)
            .walletDeletedDate(UPDATED_WALLET_DELETED_DATE)
            .walletAutoDebitEnabled(UPDATED_WALLET_AUTO_DEBIT_ENABLED)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAccount))
            )
            .andExpect(status().isOk());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testUserAccount.getUpiAddress()).isEqualTo(UPDATED_UPI_ADDRESS);
        assertThat(testUserAccount.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testUserAccount.getUpiActiveDate()).isEqualTo(UPDATED_UPI_ACTIVE_DATE);
        assertThat(testUserAccount.getUpiStatus()).isEqualTo(UPDATED_UPI_STATUS);
        assertThat(testUserAccount.getUpiSuspendedDate()).isEqualTo(UPDATED_UPI_SUSPENDED_DATE);
        assertThat(testUserAccount.getUpiDeletedDate()).isEqualTo(UPDATED_UPI_DELETED_DATE);
        assertThat(testUserAccount.getUpiAutoDebitEnabled()).isEqualTo(UPDATED_UPI_AUTO_DEBIT_ENABLED);
        assertThat(testUserAccount.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testUserAccount.getBankAccountNumber()).isEqualTo(UPDATED_BANK_ACCOUNT_NUMBER);
        assertThat(testUserAccount.getBankIFSCCode()).isEqualTo(UPDATED_BANK_IFSC_CODE);
        assertThat(testUserAccount.getBankSWIFTCode()).isEqualTo(UPDATED_BANK_SWIFT_CODE);
        assertThat(testUserAccount.getBankBranchAddress()).isEqualTo(UPDATED_BANK_BRANCH_ADDRESS);
        assertThat(testUserAccount.getBankStatus()).isEqualTo(UPDATED_BANK_STATUS);
        assertThat(testUserAccount.getBankActiveDate()).isEqualTo(UPDATED_BANK_ACTIVE_DATE);
        assertThat(testUserAccount.getBankSuspendedDate()).isEqualTo(UPDATED_BANK_SUSPENDED_DATE);
        assertThat(testUserAccount.getBankDeletedDate()).isEqualTo(UPDATED_BANK_DELETED_DATE);
        assertThat(testUserAccount.getBankAutoDebitEnabled()).isEqualTo(UPDATED_BANK_AUTO_DEBIT_ENABLED);
        assertThat(testUserAccount.getWalletType()).isEqualTo(UPDATED_WALLET_TYPE);
        assertThat(testUserAccount.getWalletAddress()).isEqualTo(UPDATED_WALLET_ADDRESS);
        assertThat(testUserAccount.getWalletStatus()).isEqualTo(UPDATED_WALLET_STATUS);
        assertThat(testUserAccount.getWallterActiveDate()).isEqualTo(UPDATED_WALLTER_ACTIVE_DATE);
        assertThat(testUserAccount.getWalletSuspendedDate()).isEqualTo(UPDATED_WALLET_SUSPENDED_DATE);
        assertThat(testUserAccount.getWalletDeletedDate()).isEqualTo(UPDATED_WALLET_DELETED_DATE);
        assertThat(testUserAccount.getWalletAutoDebitEnabled()).isEqualTo(UPDATED_WALLET_AUTO_DEBIT_ENABLED);
        assertThat(testUserAccount.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserAccount.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();
        userAccount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();
        userAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();
        userAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAccountMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userAccount))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        int databaseSizeBeforeDelete = userAccountRepository.findAll().size();

        // Delete the userAccount
        restUserAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, userAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
