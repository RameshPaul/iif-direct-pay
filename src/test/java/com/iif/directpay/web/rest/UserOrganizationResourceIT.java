package com.iif.directpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iif.directpay.IntegrationTest;
import com.iif.directpay.domain.UserOrganization;
import com.iif.directpay.domain.enumeration.UserOrganizationStatus;
import com.iif.directpay.repository.UserOrganizationRepository;
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
 * Integration tests for the {@link UserOrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserOrganizationResourceIT {

    private static final LocalDate DEFAULT_JOINING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final UserOrganizationStatus DEFAULT_STATUS = UserOrganizationStatus.INACTIVE;
    private static final UserOrganizationStatus UPDATED_STATUS = UserOrganizationStatus.ACTIVE;

    private static final Instant DEFAULT_SUSPENDED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUSPENDED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserOrganizationRepository userOrganizationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserOrganizationMockMvc;

    private UserOrganization userOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserOrganization createEntity(EntityManager em) {
        UserOrganization userOrganization = new UserOrganization()
            .joiningDate(DEFAULT_JOINING_DATE)
            .exitDate(DEFAULT_EXIT_DATE)
            .status(DEFAULT_STATUS)
            .suspendedDate(DEFAULT_SUSPENDED_DATE)
            .deletedDate(DEFAULT_DELETED_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return userOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserOrganization createUpdatedEntity(EntityManager em) {
        UserOrganization userOrganization = new UserOrganization()
            .joiningDate(UPDATED_JOINING_DATE)
            .exitDate(UPDATED_EXIT_DATE)
            .status(UPDATED_STATUS)
            .suspendedDate(UPDATED_SUSPENDED_DATE)
            .deletedDate(UPDATED_DELETED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return userOrganization;
    }

    @BeforeEach
    public void initTest() {
        userOrganization = createEntity(em);
    }

    @Test
    @Transactional
    void createUserOrganization() throws Exception {
        int databaseSizeBeforeCreate = userOrganizationRepository.findAll().size();
        // Create the UserOrganization
        restUserOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userOrganization))
            )
            .andExpect(status().isCreated());

        // Validate the UserOrganization in the database
        List<UserOrganization> userOrganizationList = userOrganizationRepository.findAll();
        assertThat(userOrganizationList).hasSize(databaseSizeBeforeCreate + 1);
        UserOrganization testUserOrganization = userOrganizationList.get(userOrganizationList.size() - 1);
        assertThat(testUserOrganization.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testUserOrganization.getExitDate()).isEqualTo(DEFAULT_EXIT_DATE);
        assertThat(testUserOrganization.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserOrganization.getSuspendedDate()).isEqualTo(DEFAULT_SUSPENDED_DATE);
        assertThat(testUserOrganization.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
        assertThat(testUserOrganization.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserOrganization.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createUserOrganizationWithExistingId() throws Exception {
        // Create the UserOrganization with an existing ID
        userOrganization.setId(1L);

        int databaseSizeBeforeCreate = userOrganizationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userOrganization))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserOrganization in the database
        List<UserOrganization> userOrganizationList = userOrganizationRepository.findAll();
        assertThat(userOrganizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserOrganizations() throws Exception {
        // Initialize the database
        userOrganizationRepository.saveAndFlush(userOrganization);

        // Get all the userOrganizationList
        restUserOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
            .andExpect(jsonPath("$.[*].exitDate").value(hasItem(DEFAULT_EXIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].suspendedDate").value(hasItem(DEFAULT_SUSPENDED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getUserOrganization() throws Exception {
        // Initialize the database
        userOrganizationRepository.saveAndFlush(userOrganization);

        // Get the userOrganization
        restUserOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, userOrganization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userOrganization.getId().intValue()))
            .andExpect(jsonPath("$.joiningDate").value(DEFAULT_JOINING_DATE.toString()))
            .andExpect(jsonPath("$.exitDate").value(DEFAULT_EXIT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.suspendedDate").value(DEFAULT_SUSPENDED_DATE.toString()))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserOrganization() throws Exception {
        // Get the userOrganization
        restUserOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserOrganization() throws Exception {
        // Initialize the database
        userOrganizationRepository.saveAndFlush(userOrganization);

        int databaseSizeBeforeUpdate = userOrganizationRepository.findAll().size();

        // Update the userOrganization
        UserOrganization updatedUserOrganization = userOrganizationRepository.findById(userOrganization.getId()).get();
        // Disconnect from session so that the updates on updatedUserOrganization are not directly saved in db
        em.detach(updatedUserOrganization);
        updatedUserOrganization
            .joiningDate(UPDATED_JOINING_DATE)
            .exitDate(UPDATED_EXIT_DATE)
            .status(UPDATED_STATUS)
            .suspendedDate(UPDATED_SUSPENDED_DATE)
            .deletedDate(UPDATED_DELETED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserOrganization.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserOrganization))
            )
            .andExpect(status().isOk());

        // Validate the UserOrganization in the database
        List<UserOrganization> userOrganizationList = userOrganizationRepository.findAll();
        assertThat(userOrganizationList).hasSize(databaseSizeBeforeUpdate);
        UserOrganization testUserOrganization = userOrganizationList.get(userOrganizationList.size() - 1);
        assertThat(testUserOrganization.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testUserOrganization.getExitDate()).isEqualTo(UPDATED_EXIT_DATE);
        assertThat(testUserOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserOrganization.getSuspendedDate()).isEqualTo(UPDATED_SUSPENDED_DATE);
        assertThat(testUserOrganization.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
        assertThat(testUserOrganization.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserOrganization.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserOrganization() throws Exception {
        int databaseSizeBeforeUpdate = userOrganizationRepository.findAll().size();
        userOrganization.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userOrganization.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userOrganization))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserOrganization in the database
        List<UserOrganization> userOrganizationList = userOrganizationRepository.findAll();
        assertThat(userOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserOrganization() throws Exception {
        int databaseSizeBeforeUpdate = userOrganizationRepository.findAll().size();
        userOrganization.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userOrganization))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserOrganization in the database
        List<UserOrganization> userOrganizationList = userOrganizationRepository.findAll();
        assertThat(userOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserOrganization() throws Exception {
        int databaseSizeBeforeUpdate = userOrganizationRepository.findAll().size();
        userOrganization.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userOrganization))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserOrganization in the database
        List<UserOrganization> userOrganizationList = userOrganizationRepository.findAll();
        assertThat(userOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserOrganizationWithPatch() throws Exception {
        // Initialize the database
        userOrganizationRepository.saveAndFlush(userOrganization);

        int databaseSizeBeforeUpdate = userOrganizationRepository.findAll().size();

        // Update the userOrganization using partial update
        UserOrganization partialUpdatedUserOrganization = new UserOrganization();
        partialUpdatedUserOrganization.setId(userOrganization.getId());

        partialUpdatedUserOrganization
            .joiningDate(UPDATED_JOINING_DATE)
            .suspendedDate(UPDATED_SUSPENDED_DATE)
            .deletedDate(UPDATED_DELETED_DATE);

        restUserOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserOrganization))
            )
            .andExpect(status().isOk());

        // Validate the UserOrganization in the database
        List<UserOrganization> userOrganizationList = userOrganizationRepository.findAll();
        assertThat(userOrganizationList).hasSize(databaseSizeBeforeUpdate);
        UserOrganization testUserOrganization = userOrganizationList.get(userOrganizationList.size() - 1);
        assertThat(testUserOrganization.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testUserOrganization.getExitDate()).isEqualTo(DEFAULT_EXIT_DATE);
        assertThat(testUserOrganization.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserOrganization.getSuspendedDate()).isEqualTo(UPDATED_SUSPENDED_DATE);
        assertThat(testUserOrganization.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
        assertThat(testUserOrganization.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserOrganization.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserOrganizationWithPatch() throws Exception {
        // Initialize the database
        userOrganizationRepository.saveAndFlush(userOrganization);

        int databaseSizeBeforeUpdate = userOrganizationRepository.findAll().size();

        // Update the userOrganization using partial update
        UserOrganization partialUpdatedUserOrganization = new UserOrganization();
        partialUpdatedUserOrganization.setId(userOrganization.getId());

        partialUpdatedUserOrganization
            .joiningDate(UPDATED_JOINING_DATE)
            .exitDate(UPDATED_EXIT_DATE)
            .status(UPDATED_STATUS)
            .suspendedDate(UPDATED_SUSPENDED_DATE)
            .deletedDate(UPDATED_DELETED_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserOrganization))
            )
            .andExpect(status().isOk());

        // Validate the UserOrganization in the database
        List<UserOrganization> userOrganizationList = userOrganizationRepository.findAll();
        assertThat(userOrganizationList).hasSize(databaseSizeBeforeUpdate);
        UserOrganization testUserOrganization = userOrganizationList.get(userOrganizationList.size() - 1);
        assertThat(testUserOrganization.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testUserOrganization.getExitDate()).isEqualTo(UPDATED_EXIT_DATE);
        assertThat(testUserOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserOrganization.getSuspendedDate()).isEqualTo(UPDATED_SUSPENDED_DATE);
        assertThat(testUserOrganization.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
        assertThat(testUserOrganization.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserOrganization.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserOrganization() throws Exception {
        int databaseSizeBeforeUpdate = userOrganizationRepository.findAll().size();
        userOrganization.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userOrganization))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserOrganization in the database
        List<UserOrganization> userOrganizationList = userOrganizationRepository.findAll();
        assertThat(userOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserOrganization() throws Exception {
        int databaseSizeBeforeUpdate = userOrganizationRepository.findAll().size();
        userOrganization.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userOrganization))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserOrganization in the database
        List<UserOrganization> userOrganizationList = userOrganizationRepository.findAll();
        assertThat(userOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserOrganization() throws Exception {
        int databaseSizeBeforeUpdate = userOrganizationRepository.findAll().size();
        userOrganization.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userOrganization))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserOrganization in the database
        List<UserOrganization> userOrganizationList = userOrganizationRepository.findAll();
        assertThat(userOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserOrganization() throws Exception {
        // Initialize the database
        userOrganizationRepository.saveAndFlush(userOrganization);

        int databaseSizeBeforeDelete = userOrganizationRepository.findAll().size();

        // Delete the userOrganization
        restUserOrganizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, userOrganization.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserOrganization> userOrganizationList = userOrganizationRepository.findAll();
        assertThat(userOrganizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
