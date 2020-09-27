package org.regitiny.catiny.uaa.web.rest;

import org.regitiny.catiny.uaa.RedisTestContainerExtension;
import org.regitiny.catiny.uaa.CatinyUaaApp;
import org.regitiny.catiny.uaa.config.SecurityBeanOverrideConfiguration;
import org.regitiny.catiny.uaa.domain.Master;
import org.regitiny.catiny.uaa.repository.MasterRepository;
import org.regitiny.catiny.uaa.repository.search.MasterSearchRepository;
import org.regitiny.catiny.uaa.service.MasterService;
import org.regitiny.catiny.uaa.service.dto.MasterDTO;
import org.regitiny.catiny.uaa.service.mapper.MasterMapper;
import org.regitiny.catiny.uaa.service.dto.MasterCriteria;
import org.regitiny.catiny.uaa.service.MasterQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MasterResource} REST controller.
 */
@SpringBootTest(classes = CatinyUaaApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class MasterResourceIT {
// // TODO: 27-Sep-20
//    private static final UUID DEFAULT_MASTER_ID = UUID.randomUUID();
//    private static final UUID UPDATED_MASTER_ID = UUID.randomUUID();
//
//    private static final Long DEFAULT_USER_ID = 1L;
//    private static final Long UPDATED_USER_ID = 2L;
//    private static final Long SMALLER_USER_ID = 1L - 1L;
//
//    private static final Long DEFAULT_GROUP_ID = 1L;
//    private static final Long UPDATED_GROUP_ID = 2L;
//    private static final Long SMALLER_GROUP_ID = 1L - 1L;
//
//    private static final Long DEFAULT_COMPANY_ID = 1L;
//    private static final Long UPDATED_COMPANY_ID = 2L;
//    private static final Long SMALLER_COMPANY_ID = 1L - 1L;
//
//    private static final String DEFAULT_MASTER_USER_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_MASTER_USER_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
//    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";
//
//    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
//    private static final String UPDATED_EMAIL = "BBBBBBBBBB";
//
//    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";
//
//    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    private static final Instant DEFAULT_MODIFIED_DATE = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    @Autowired
//    private MasterRepository masterRepository;
//
//    @Autowired
//    private MasterMapper masterMapper;
//
//    @Autowired
//    private MasterService masterService;
//
//    /**
//     * This repository is mocked in the org.regitiny.catiny.uaa.repository.search test package.
//     *
//     * @see org.regitiny.catiny.uaa.repository.search.MasterSearchRepositoryMockConfiguration
//     */
//    @Autowired
//    private MasterSearchRepository mockMasterSearchRepository;
//
//    @Autowired
//    private MasterQueryService masterQueryService;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restMasterMockMvc;
//
//    private Master master;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Master createEntity(EntityManager em) {
//        Master master = new Master()
//            .masterId(DEFAULT_MASTER_ID)
//            .userId(DEFAULT_USER_ID)
//            .groupId(DEFAULT_GROUP_ID)
//            .companyId(DEFAULT_COMPANY_ID)
//            .masterUserName(DEFAULT_MASTER_USER_NAME)
//            .userName(DEFAULT_USER_NAME)
//            .firstName(DEFAULT_FIRST_NAME)
//            .lastName(DEFAULT_LAST_NAME)
//            .imageUrl(DEFAULT_IMAGE_URL)
//            .email(DEFAULT_EMAIL)
//            .groupName(DEFAULT_GROUP_NAME)
//            .companyName(DEFAULT_COMPANY_NAME)
//            .createdDate(DEFAULT_CREATED_DATE)
//            .modifiedDate(DEFAULT_MODIFIED_DATE);
//        return master;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Master createUpdatedEntity(EntityManager em) {
//        Master master = new Master()
//            .masterId(UPDATED_MASTER_ID)
//            .userId(UPDATED_USER_ID)
//            .groupId(UPDATED_GROUP_ID)
//            .companyId(UPDATED_COMPANY_ID)
//            .masterUserName(UPDATED_MASTER_USER_NAME)
//            .userName(UPDATED_USER_NAME)
//            .firstName(UPDATED_FIRST_NAME)
//            .lastName(UPDATED_LAST_NAME)
//            .imageUrl(UPDATED_IMAGE_URL)
//            .email(UPDATED_EMAIL)
//            .groupName(UPDATED_GROUP_NAME)
//            .companyName(UPDATED_COMPANY_NAME)
//            .createdDate(UPDATED_CREATED_DATE)
//            .modifiedDate(UPDATED_MODIFIED_DATE);
//        return master;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        master = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createMaster() throws Exception {
//        int databaseSizeBeforeCreate = masterRepository.findAll().size();
//        // Create the Master
//        MasterDTO masterDTO = masterMapper.toDto(master);
//        restMasterMockMvc.perform(post("/api/masters").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(masterDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the Master in the database
//        List<Master> masterList = masterRepository.findAll();
//        assertThat(masterList).hasSize(databaseSizeBeforeCreate + 1);
//        Master testMaster = masterList.get(masterList.size() - 1);
//        assertThat(testMaster.getMasterId()).isEqualTo(DEFAULT_MASTER_ID);
//        assertThat(testMaster.getUserId()).isEqualTo(DEFAULT_USER_ID);
//        assertThat(testMaster.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
//        assertThat(testMaster.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
//        assertThat(testMaster.getMasterUserName()).isEqualTo(DEFAULT_MASTER_USER_NAME);
//        assertThat(testMaster.getUserName()).isEqualTo(DEFAULT_USER_NAME);
//        assertThat(testMaster.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
//        assertThat(testMaster.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
//        assertThat(testMaster.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
//        assertThat(testMaster.getEmail()).isEqualTo(DEFAULT_EMAIL);
//        assertThat(testMaster.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
//        assertThat(testMaster.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
//        assertThat(testMaster.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
//        assertThat(testMaster.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
//
//        // Validate the Master in Elasticsearch
//        verify(mockMasterSearchRepository, times(1)).save(testMaster);
//    }
//
//    @Test
//    @Transactional
//    public void createMasterWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = masterRepository.findAll().size();
//
//        // Create the Master with an existing ID
//        master.setId(1L);
//        MasterDTO masterDTO = masterMapper.toDto(master);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restMasterMockMvc.perform(post("/api/masters").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(masterDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Master in the database
//        List<Master> masterList = masterRepository.findAll();
//        assertThat(masterList).hasSize(databaseSizeBeforeCreate);
//
//        // Validate the Master in Elasticsearch
//        verify(mockMasterSearchRepository, times(0)).save(master);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkMasterIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = masterRepository.findAll().size();
//        // set the field null
//        master.setMasterId(null);
//
//        // Create the Master, which fails.
//        MasterDTO masterDTO = masterMapper.toDto(master);
//
//
//        restMasterMockMvc.perform(post("/api/masters").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(masterDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<Master> masterList = masterRepository.findAll();
//        assertThat(masterList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkUserIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = masterRepository.findAll().size();
//        // set the field null
//        master.setUserId(null);
//
//        // Create the Master, which fails.
//        MasterDTO masterDTO = masterMapper.toDto(master);
//
//
//        restMasterMockMvc.perform(post("/api/masters").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(masterDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<Master> masterList = masterRepository.findAll();
//        assertThat(masterList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMasters() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList
//        restMasterMockMvc.perform(get("/api/masters?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(master.getId().intValue())))
//            .andExpect(jsonPath("$.[*].masterId").value(hasItem(DEFAULT_MASTER_ID.toString())))
//            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
//            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
//            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
//            .andExpect(jsonPath("$.[*].masterUserName").value(hasItem(DEFAULT_MASTER_USER_NAME)))
//            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
//            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
//            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
//            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
//            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
//            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
//            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
//            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
//            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
//    }
//
//    @Test
//    @Transactional
//    public void getMaster() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get the master
//        restMasterMockMvc.perform(get("/api/masters/{id}", master.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(master.getId().intValue()))
//            .andExpect(jsonPath("$.masterId").value(DEFAULT_MASTER_ID.toString()))
//            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
//            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
//            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
//            .andExpect(jsonPath("$.masterUserName").value(DEFAULT_MASTER_USER_NAME))
//            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
//            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
//            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
//            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
//            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
//            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME))
//            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
//            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
//            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
//    }
//
//
//    @Test
//    @Transactional
//    public void getMastersByIdFiltering() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        Long id = master.getId();
//
//        defaultMasterShouldBeFound("id.equals=" + id);
//        defaultMasterShouldNotBeFound("id.notEquals=" + id);
//
//        defaultMasterShouldBeFound("id.greaterThanOrEqual=" + id);
//        defaultMasterShouldNotBeFound("id.greaterThan=" + id);
//
//        defaultMasterShouldBeFound("id.lessThanOrEqual=" + id);
//        defaultMasterShouldNotBeFound("id.lessThan=" + id);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllMastersByMasterIdIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where masterId equals to DEFAULT_MASTER_ID
//        defaultMasterShouldBeFound("masterId.equals=" + DEFAULT_MASTER_ID);
//
//        // Get all the masterList where masterId equals to UPDATED_MASTER_ID
//        defaultMasterShouldNotBeFound("masterId.equals=" + UPDATED_MASTER_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByMasterIdIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where masterId not equals to DEFAULT_MASTER_ID
//        defaultMasterShouldNotBeFound("masterId.notEquals=" + DEFAULT_MASTER_ID);
//
//        // Get all the masterList where masterId not equals to UPDATED_MASTER_ID
//        defaultMasterShouldBeFound("masterId.notEquals=" + UPDATED_MASTER_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByMasterIdIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where masterId in DEFAULT_MASTER_ID or UPDATED_MASTER_ID
//        defaultMasterShouldBeFound("masterId.in=" + DEFAULT_MASTER_ID + "," + UPDATED_MASTER_ID);
//
//        // Get all the masterList where masterId equals to UPDATED_MASTER_ID
//        defaultMasterShouldNotBeFound("masterId.in=" + UPDATED_MASTER_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByMasterIdIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where masterId is not null
//        defaultMasterShouldBeFound("masterId.specified=true");
//
//        // Get all the masterList where masterId is null
//        defaultMasterShouldNotBeFound("masterId.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByUserIdIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userId equals to DEFAULT_USER_ID
//        defaultMasterShouldBeFound("userId.equals=" + DEFAULT_USER_ID);
//
//        // Get all the masterList where userId equals to UPDATED_USER_ID
//        defaultMasterShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByUserIdIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userId not equals to DEFAULT_USER_ID
//        defaultMasterShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);
//
//        // Get all the masterList where userId not equals to UPDATED_USER_ID
//        defaultMasterShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByUserIdIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
//        defaultMasterShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);
//
//        // Get all the masterList where userId equals to UPDATED_USER_ID
//        defaultMasterShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByUserIdIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userId is not null
//        defaultMasterShouldBeFound("userId.specified=true");
//
//        // Get all the masterList where userId is null
//        defaultMasterShouldNotBeFound("userId.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userId is greater than or equal to DEFAULT_USER_ID
//        defaultMasterShouldBeFound("userId.greaterThanOrEqual=" + DEFAULT_USER_ID);
//
//        // Get all the masterList where userId is greater than or equal to UPDATED_USER_ID
//        defaultMasterShouldNotBeFound("userId.greaterThanOrEqual=" + UPDATED_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByUserIdIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userId is less than or equal to DEFAULT_USER_ID
//        defaultMasterShouldBeFound("userId.lessThanOrEqual=" + DEFAULT_USER_ID);
//
//        // Get all the masterList where userId is less than or equal to SMALLER_USER_ID
//        defaultMasterShouldNotBeFound("userId.lessThanOrEqual=" + SMALLER_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByUserIdIsLessThanSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userId is less than DEFAULT_USER_ID
//        defaultMasterShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);
//
//        // Get all the masterList where userId is less than UPDATED_USER_ID
//        defaultMasterShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByUserIdIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userId is greater than DEFAULT_USER_ID
//        defaultMasterShouldNotBeFound("userId.greaterThan=" + DEFAULT_USER_ID);
//
//        // Get all the masterList where userId is greater than SMALLER_USER_ID
//        defaultMasterShouldBeFound("userId.greaterThan=" + SMALLER_USER_ID);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllMastersByGroupIdIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupId equals to DEFAULT_GROUP_ID
//        defaultMasterShouldBeFound("groupId.equals=" + DEFAULT_GROUP_ID);
//
//        // Get all the masterList where groupId equals to UPDATED_GROUP_ID
//        defaultMasterShouldNotBeFound("groupId.equals=" + UPDATED_GROUP_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByGroupIdIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupId not equals to DEFAULT_GROUP_ID
//        defaultMasterShouldNotBeFound("groupId.notEquals=" + DEFAULT_GROUP_ID);
//
//        // Get all the masterList where groupId not equals to UPDATED_GROUP_ID
//        defaultMasterShouldBeFound("groupId.notEquals=" + UPDATED_GROUP_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByGroupIdIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupId in DEFAULT_GROUP_ID or UPDATED_GROUP_ID
//        defaultMasterShouldBeFound("groupId.in=" + DEFAULT_GROUP_ID + "," + UPDATED_GROUP_ID);
//
//        // Get all the masterList where groupId equals to UPDATED_GROUP_ID
//        defaultMasterShouldNotBeFound("groupId.in=" + UPDATED_GROUP_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByGroupIdIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupId is not null
//        defaultMasterShouldBeFound("groupId.specified=true");
//
//        // Get all the masterList where groupId is null
//        defaultMasterShouldNotBeFound("groupId.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByGroupIdIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupId is greater than or equal to DEFAULT_GROUP_ID
//        defaultMasterShouldBeFound("groupId.greaterThanOrEqual=" + DEFAULT_GROUP_ID);
//
//        // Get all the masterList where groupId is greater than or equal to UPDATED_GROUP_ID
//        defaultMasterShouldNotBeFound("groupId.greaterThanOrEqual=" + UPDATED_GROUP_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByGroupIdIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupId is less than or equal to DEFAULT_GROUP_ID
//        defaultMasterShouldBeFound("groupId.lessThanOrEqual=" + DEFAULT_GROUP_ID);
//
//        // Get all the masterList where groupId is less than or equal to SMALLER_GROUP_ID
//        defaultMasterShouldNotBeFound("groupId.lessThanOrEqual=" + SMALLER_GROUP_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByGroupIdIsLessThanSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupId is less than DEFAULT_GROUP_ID
//        defaultMasterShouldNotBeFound("groupId.lessThan=" + DEFAULT_GROUP_ID);
//
//        // Get all the masterList where groupId is less than UPDATED_GROUP_ID
//        defaultMasterShouldBeFound("groupId.lessThan=" + UPDATED_GROUP_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByGroupIdIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupId is greater than DEFAULT_GROUP_ID
//        defaultMasterShouldNotBeFound("groupId.greaterThan=" + DEFAULT_GROUP_ID);
//
//        // Get all the masterList where groupId is greater than SMALLER_GROUP_ID
//        defaultMasterShouldBeFound("groupId.greaterThan=" + SMALLER_GROUP_ID);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllMastersByCompanyIdIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyId equals to DEFAULT_COMPANY_ID
//        defaultMasterShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);
//
//        // Get all the masterList where companyId equals to UPDATED_COMPANY_ID
//        defaultMasterShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCompanyIdIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyId not equals to DEFAULT_COMPANY_ID
//        defaultMasterShouldNotBeFound("companyId.notEquals=" + DEFAULT_COMPANY_ID);
//
//        // Get all the masterList where companyId not equals to UPDATED_COMPANY_ID
//        defaultMasterShouldBeFound("companyId.notEquals=" + UPDATED_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCompanyIdIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
//        defaultMasterShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);
//
//        // Get all the masterList where companyId equals to UPDATED_COMPANY_ID
//        defaultMasterShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCompanyIdIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyId is not null
//        defaultMasterShouldBeFound("companyId.specified=true");
//
//        // Get all the masterList where companyId is null
//        defaultMasterShouldNotBeFound("companyId.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyId is greater than or equal to DEFAULT_COMPANY_ID
//        defaultMasterShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);
//
//        // Get all the masterList where companyId is greater than or equal to UPDATED_COMPANY_ID
//        defaultMasterShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyId is less than or equal to DEFAULT_COMPANY_ID
//        defaultMasterShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);
//
//        // Get all the masterList where companyId is less than or equal to SMALLER_COMPANY_ID
//        defaultMasterShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCompanyIdIsLessThanSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyId is less than DEFAULT_COMPANY_ID
//        defaultMasterShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);
//
//        // Get all the masterList where companyId is less than UPDATED_COMPANY_ID
//        defaultMasterShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCompanyIdIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyId is greater than DEFAULT_COMPANY_ID
//        defaultMasterShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);
//
//        // Get all the masterList where companyId is greater than SMALLER_COMPANY_ID
//        defaultMasterShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllMastersByMasterUserNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where masterUserName equals to DEFAULT_MASTER_USER_NAME
//        defaultMasterShouldBeFound("masterUserName.equals=" + DEFAULT_MASTER_USER_NAME);
//
//        // Get all the masterList where masterUserName equals to UPDATED_MASTER_USER_NAME
//        defaultMasterShouldNotBeFound("masterUserName.equals=" + UPDATED_MASTER_USER_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByMasterUserNameIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where masterUserName not equals to DEFAULT_MASTER_USER_NAME
//        defaultMasterShouldNotBeFound("masterUserName.notEquals=" + DEFAULT_MASTER_USER_NAME);
//
//        // Get all the masterList where masterUserName not equals to UPDATED_MASTER_USER_NAME
//        defaultMasterShouldBeFound("masterUserName.notEquals=" + UPDATED_MASTER_USER_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByMasterUserNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where masterUserName in DEFAULT_MASTER_USER_NAME or UPDATED_MASTER_USER_NAME
//        defaultMasterShouldBeFound("masterUserName.in=" + DEFAULT_MASTER_USER_NAME + "," + UPDATED_MASTER_USER_NAME);
//
//        // Get all the masterList where masterUserName equals to UPDATED_MASTER_USER_NAME
//        defaultMasterShouldNotBeFound("masterUserName.in=" + UPDATED_MASTER_USER_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByMasterUserNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where masterUserName is not null
//        defaultMasterShouldBeFound("masterUserName.specified=true");
//
//        // Get all the masterList where masterUserName is null
//        defaultMasterShouldNotBeFound("masterUserName.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllMastersByMasterUserNameContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where masterUserName contains DEFAULT_MASTER_USER_NAME
//        defaultMasterShouldBeFound("masterUserName.contains=" + DEFAULT_MASTER_USER_NAME);
//
//        // Get all the masterList where masterUserName contains UPDATED_MASTER_USER_NAME
//        defaultMasterShouldNotBeFound("masterUserName.contains=" + UPDATED_MASTER_USER_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByMasterUserNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where masterUserName does not contain DEFAULT_MASTER_USER_NAME
//        defaultMasterShouldNotBeFound("masterUserName.doesNotContain=" + DEFAULT_MASTER_USER_NAME);
//
//        // Get all the masterList where masterUserName does not contain UPDATED_MASTER_USER_NAME
//        defaultMasterShouldBeFound("masterUserName.doesNotContain=" + UPDATED_MASTER_USER_NAME);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllMastersByUserNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userName equals to DEFAULT_USER_NAME
//        defaultMasterShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);
//
//        // Get all the masterList where userName equals to UPDATED_USER_NAME
//        defaultMasterShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByUserNameIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userName not equals to DEFAULT_USER_NAME
//        defaultMasterShouldNotBeFound("userName.notEquals=" + DEFAULT_USER_NAME);
//
//        // Get all the masterList where userName not equals to UPDATED_USER_NAME
//        defaultMasterShouldBeFound("userName.notEquals=" + UPDATED_USER_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByUserNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
//        defaultMasterShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);
//
//        // Get all the masterList where userName equals to UPDATED_USER_NAME
//        defaultMasterShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByUserNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userName is not null
//        defaultMasterShouldBeFound("userName.specified=true");
//
//        // Get all the masterList where userName is null
//        defaultMasterShouldNotBeFound("userName.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllMastersByUserNameContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userName contains DEFAULT_USER_NAME
//        defaultMasterShouldBeFound("userName.contains=" + DEFAULT_USER_NAME);
//
//        // Get all the masterList where userName contains UPDATED_USER_NAME
//        defaultMasterShouldNotBeFound("userName.contains=" + UPDATED_USER_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByUserNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where userName does not contain DEFAULT_USER_NAME
//        defaultMasterShouldNotBeFound("userName.doesNotContain=" + DEFAULT_USER_NAME);
//
//        // Get all the masterList where userName does not contain UPDATED_USER_NAME
//        defaultMasterShouldBeFound("userName.doesNotContain=" + UPDATED_USER_NAME);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllMastersByFirstNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where firstName equals to DEFAULT_FIRST_NAME
//        defaultMasterShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);
//
//        // Get all the masterList where firstName equals to UPDATED_FIRST_NAME
//        defaultMasterShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByFirstNameIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where firstName not equals to DEFAULT_FIRST_NAME
//        defaultMasterShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);
//
//        // Get all the masterList where firstName not equals to UPDATED_FIRST_NAME
//        defaultMasterShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByFirstNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
//        defaultMasterShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);
//
//        // Get all the masterList where firstName equals to UPDATED_FIRST_NAME
//        defaultMasterShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByFirstNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where firstName is not null
//        defaultMasterShouldBeFound("firstName.specified=true");
//
//        // Get all the masterList where firstName is null
//        defaultMasterShouldNotBeFound("firstName.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllMastersByFirstNameContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where firstName contains DEFAULT_FIRST_NAME
//        defaultMasterShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);
//
//        // Get all the masterList where firstName contains UPDATED_FIRST_NAME
//        defaultMasterShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByFirstNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where firstName does not contain DEFAULT_FIRST_NAME
//        defaultMasterShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);
//
//        // Get all the masterList where firstName does not contain UPDATED_FIRST_NAME
//        defaultMasterShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllMastersByLastNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where lastName equals to DEFAULT_LAST_NAME
//        defaultMasterShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);
//
//        // Get all the masterList where lastName equals to UPDATED_LAST_NAME
//        defaultMasterShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByLastNameIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where lastName not equals to DEFAULT_LAST_NAME
//        defaultMasterShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);
//
//        // Get all the masterList where lastName not equals to UPDATED_LAST_NAME
//        defaultMasterShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByLastNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
//        defaultMasterShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);
//
//        // Get all the masterList where lastName equals to UPDATED_LAST_NAME
//        defaultMasterShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByLastNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where lastName is not null
//        defaultMasterShouldBeFound("lastName.specified=true");
//
//        // Get all the masterList where lastName is null
//        defaultMasterShouldNotBeFound("lastName.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllMastersByLastNameContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where lastName contains DEFAULT_LAST_NAME
//        defaultMasterShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);
//
//        // Get all the masterList where lastName contains UPDATED_LAST_NAME
//        defaultMasterShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByLastNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where lastName does not contain DEFAULT_LAST_NAME
//        defaultMasterShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);
//
//        // Get all the masterList where lastName does not contain UPDATED_LAST_NAME
//        defaultMasterShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllMastersByImageUrlIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where imageUrl equals to DEFAULT_IMAGE_URL
//        defaultMasterShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);
//
//        // Get all the masterList where imageUrl equals to UPDATED_IMAGE_URL
//        defaultMasterShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByImageUrlIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where imageUrl not equals to DEFAULT_IMAGE_URL
//        defaultMasterShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);
//
//        // Get all the masterList where imageUrl not equals to UPDATED_IMAGE_URL
//        defaultMasterShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByImageUrlIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
//        defaultMasterShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);
//
//        // Get all the masterList where imageUrl equals to UPDATED_IMAGE_URL
//        defaultMasterShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByImageUrlIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where imageUrl is not null
//        defaultMasterShouldBeFound("imageUrl.specified=true");
//
//        // Get all the masterList where imageUrl is null
//        defaultMasterShouldNotBeFound("imageUrl.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllMastersByImageUrlContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where imageUrl contains DEFAULT_IMAGE_URL
//        defaultMasterShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);
//
//        // Get all the masterList where imageUrl contains UPDATED_IMAGE_URL
//        defaultMasterShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByImageUrlNotContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where imageUrl does not contain DEFAULT_IMAGE_URL
//        defaultMasterShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);
//
//        // Get all the masterList where imageUrl does not contain UPDATED_IMAGE_URL
//        defaultMasterShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllMastersByEmailIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where email equals to DEFAULT_EMAIL
//        defaultMasterShouldBeFound("email.equals=" + DEFAULT_EMAIL);
//
//        // Get all the masterList where email equals to UPDATED_EMAIL
//        defaultMasterShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByEmailIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where email not equals to DEFAULT_EMAIL
//        defaultMasterShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);
//
//        // Get all the masterList where email not equals to UPDATED_EMAIL
//        defaultMasterShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByEmailIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where email in DEFAULT_EMAIL or UPDATED_EMAIL
//        defaultMasterShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);
//
//        // Get all the masterList where email equals to UPDATED_EMAIL
//        defaultMasterShouldNotBeFound("email.in=" + UPDATED_EMAIL);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByEmailIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where email is not null
//        defaultMasterShouldBeFound("email.specified=true");
//
//        // Get all the masterList where email is null
//        defaultMasterShouldNotBeFound("email.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllMastersByEmailContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where email contains DEFAULT_EMAIL
//        defaultMasterShouldBeFound("email.contains=" + DEFAULT_EMAIL);
//
//        // Get all the masterList where email contains UPDATED_EMAIL
//        defaultMasterShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByEmailNotContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where email does not contain DEFAULT_EMAIL
//        defaultMasterShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);
//
//        // Get all the masterList where email does not contain UPDATED_EMAIL
//        defaultMasterShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllMastersByGroupNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupName equals to DEFAULT_GROUP_NAME
//        defaultMasterShouldBeFound("groupName.equals=" + DEFAULT_GROUP_NAME);
//
//        // Get all the masterList where groupName equals to UPDATED_GROUP_NAME
//        defaultMasterShouldNotBeFound("groupName.equals=" + UPDATED_GROUP_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByGroupNameIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupName not equals to DEFAULT_GROUP_NAME
//        defaultMasterShouldNotBeFound("groupName.notEquals=" + DEFAULT_GROUP_NAME);
//
//        // Get all the masterList where groupName not equals to UPDATED_GROUP_NAME
//        defaultMasterShouldBeFound("groupName.notEquals=" + UPDATED_GROUP_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByGroupNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupName in DEFAULT_GROUP_NAME or UPDATED_GROUP_NAME
//        defaultMasterShouldBeFound("groupName.in=" + DEFAULT_GROUP_NAME + "," + UPDATED_GROUP_NAME);
//
//        // Get all the masterList where groupName equals to UPDATED_GROUP_NAME
//        defaultMasterShouldNotBeFound("groupName.in=" + UPDATED_GROUP_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByGroupNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupName is not null
//        defaultMasterShouldBeFound("groupName.specified=true");
//
//        // Get all the masterList where groupName is null
//        defaultMasterShouldNotBeFound("groupName.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllMastersByGroupNameContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupName contains DEFAULT_GROUP_NAME
//        defaultMasterShouldBeFound("groupName.contains=" + DEFAULT_GROUP_NAME);
//
//        // Get all the masterList where groupName contains UPDATED_GROUP_NAME
//        defaultMasterShouldNotBeFound("groupName.contains=" + UPDATED_GROUP_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByGroupNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where groupName does not contain DEFAULT_GROUP_NAME
//        defaultMasterShouldNotBeFound("groupName.doesNotContain=" + DEFAULT_GROUP_NAME);
//
//        // Get all the masterList where groupName does not contain UPDATED_GROUP_NAME
//        defaultMasterShouldBeFound("groupName.doesNotContain=" + UPDATED_GROUP_NAME);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllMastersByCompanyNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyName equals to DEFAULT_COMPANY_NAME
//        defaultMasterShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);
//
//        // Get all the masterList where companyName equals to UPDATED_COMPANY_NAME
//        defaultMasterShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCompanyNameIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyName not equals to DEFAULT_COMPANY_NAME
//        defaultMasterShouldNotBeFound("companyName.notEquals=" + DEFAULT_COMPANY_NAME);
//
//        // Get all the masterList where companyName not equals to UPDATED_COMPANY_NAME
//        defaultMasterShouldBeFound("companyName.notEquals=" + UPDATED_COMPANY_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCompanyNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
//        defaultMasterShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);
//
//        // Get all the masterList where companyName equals to UPDATED_COMPANY_NAME
//        defaultMasterShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCompanyNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyName is not null
//        defaultMasterShouldBeFound("companyName.specified=true");
//
//        // Get all the masterList where companyName is null
//        defaultMasterShouldNotBeFound("companyName.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllMastersByCompanyNameContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyName contains DEFAULT_COMPANY_NAME
//        defaultMasterShouldBeFound("companyName.contains=" + DEFAULT_COMPANY_NAME);
//
//        // Get all the masterList where companyName contains UPDATED_COMPANY_NAME
//        defaultMasterShouldNotBeFound("companyName.contains=" + UPDATED_COMPANY_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCompanyNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where companyName does not contain DEFAULT_COMPANY_NAME
//        defaultMasterShouldNotBeFound("companyName.doesNotContain=" + DEFAULT_COMPANY_NAME);
//
//        // Get all the masterList where companyName does not contain UPDATED_COMPANY_NAME
//        defaultMasterShouldBeFound("companyName.doesNotContain=" + UPDATED_COMPANY_NAME);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllMastersByCreatedDateIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where createdDate equals to DEFAULT_CREATED_DATE
//        defaultMasterShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);
//
//        // Get all the masterList where createdDate equals to UPDATED_CREATED_DATE
//        defaultMasterShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCreatedDateIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where createdDate not equals to DEFAULT_CREATED_DATE
//        defaultMasterShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);
//
//        // Get all the masterList where createdDate not equals to UPDATED_CREATED_DATE
//        defaultMasterShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCreatedDateIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
//        defaultMasterShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);
//
//        // Get all the masterList where createdDate equals to UPDATED_CREATED_DATE
//        defaultMasterShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByCreatedDateIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where createdDate is not null
//        defaultMasterShouldBeFound("createdDate.specified=true");
//
//        // Get all the masterList where createdDate is null
//        defaultMasterShouldNotBeFound("createdDate.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByModifiedDateIsEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where modifiedDate equals to DEFAULT_MODIFIED_DATE
//        defaultMasterShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);
//
//        // Get all the masterList where modifiedDate equals to UPDATED_MODIFIED_DATE
//        defaultMasterShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByModifiedDateIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
//        defaultMasterShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);
//
//        // Get all the masterList where modifiedDate not equals to UPDATED_MODIFIED_DATE
//        defaultMasterShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByModifiedDateIsInShouldWork() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
//        defaultMasterShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);
//
//        // Get all the masterList where modifiedDate equals to UPDATED_MODIFIED_DATE
//        defaultMasterShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMastersByModifiedDateIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        // Get all the masterList where modifiedDate is not null
//        defaultMasterShouldBeFound("modifiedDate.specified=true");
//
//        // Get all the masterList where modifiedDate is null
//        defaultMasterShouldNotBeFound("modifiedDate.specified=false");
//    }
//    /**
//     * Executes the search, and checks that the default entity is returned.
//     */
//    private void defaultMasterShouldBeFound(String filter) throws Exception {
//        restMasterMockMvc.perform(get("/api/masters?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(master.getId().intValue())))
//            .andExpect(jsonPath("$.[*].masterId").value(hasItem(DEFAULT_MASTER_ID.toString())))
//            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
//            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
//            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
//            .andExpect(jsonPath("$.[*].masterUserName").value(hasItem(DEFAULT_MASTER_USER_NAME)))
//            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
//            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
//            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
//            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
//            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
//            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
//            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
//            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
//            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
//
//        // Check, that the count call also returns 1
//        restMasterMockMvc.perform(get("/api/masters/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("1"));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is not returned.
//     */
//    private void defaultMasterShouldNotBeFound(String filter) throws Exception {
//        restMasterMockMvc.perform(get("/api/masters?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$").isArray())
//            .andExpect(jsonPath("$").isEmpty());
//
//        // Check, that the count call also returns 0
//        restMasterMockMvc.perform(get("/api/masters/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("0"));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingMaster() throws Exception {
//        // Get the master
//        restMasterMockMvc.perform(get("/api/masters/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateMaster() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        int databaseSizeBeforeUpdate = masterRepository.findAll().size();
//
//        // Update the master
//        Master updatedMaster = masterRepository.findById(master.getId()).get();
//        // Disconnect from session so that the updates on updatedMaster are not directly saved in db
//        em.detach(updatedMaster);
//        updatedMaster
//            .masterId(UPDATED_MASTER_ID)
//            .userId(UPDATED_USER_ID)
//            .groupId(UPDATED_GROUP_ID)
//            .companyId(UPDATED_COMPANY_ID)
//            .masterUserName(UPDATED_MASTER_USER_NAME)
//            .userName(UPDATED_USER_NAME)
//            .firstName(UPDATED_FIRST_NAME)
//            .lastName(UPDATED_LAST_NAME)
//            .imageUrl(UPDATED_IMAGE_URL)
//            .email(UPDATED_EMAIL)
//            .groupName(UPDATED_GROUP_NAME)
//            .companyName(UPDATED_COMPANY_NAME)
//            .createdDate(UPDATED_CREATED_DATE)
//            .modifiedDate(UPDATED_MODIFIED_DATE);
//        MasterDTO masterDTO = masterMapper.toDto(updatedMaster);
//
//        restMasterMockMvc.perform(put("/api/masters").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(masterDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the Master in the database
//        List<Master> masterList = masterRepository.findAll();
//        assertThat(masterList).hasSize(databaseSizeBeforeUpdate);
//        Master testMaster = masterList.get(masterList.size() - 1);
//        assertThat(testMaster.getMasterId()).isEqualTo(UPDATED_MASTER_ID);
//        assertThat(testMaster.getUserId()).isEqualTo(UPDATED_USER_ID);
//        assertThat(testMaster.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
//        assertThat(testMaster.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
//        assertThat(testMaster.getMasterUserName()).isEqualTo(UPDATED_MASTER_USER_NAME);
//        assertThat(testMaster.getUserName()).isEqualTo(UPDATED_USER_NAME);
//        assertThat(testMaster.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
//        assertThat(testMaster.getLastName()).isEqualTo(UPDATED_LAST_NAME);
//        assertThat(testMaster.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
//        assertThat(testMaster.getEmail()).isEqualTo(UPDATED_EMAIL);
//        assertThat(testMaster.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
//        assertThat(testMaster.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
//        assertThat(testMaster.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
//        assertThat(testMaster.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
//
//        // Validate the Master in Elasticsearch
//        verify(mockMasterSearchRepository, times(1)).save(testMaster);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingMaster() throws Exception {
//        int databaseSizeBeforeUpdate = masterRepository.findAll().size();
//
//        // Create the Master
//        MasterDTO masterDTO = masterMapper.toDto(master);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restMasterMockMvc.perform(put("/api/masters").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(masterDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Master in the database
//        List<Master> masterList = masterRepository.findAll();
//        assertThat(masterList).hasSize(databaseSizeBeforeUpdate);
//
//        // Validate the Master in Elasticsearch
//        verify(mockMasterSearchRepository, times(0)).save(master);
//    }
//
//    @Test
//    @Transactional
//    public void deleteMaster() throws Exception {
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//
//        int databaseSizeBeforeDelete = masterRepository.findAll().size();
//
//        // Delete the master
//        restMasterMockMvc.perform(delete("/api/masters/{id}", master.getId()).with(csrf())
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Master> masterList = masterRepository.findAll();
//        assertThat(masterList).hasSize(databaseSizeBeforeDelete - 1);
//
//        // Validate the Master in Elasticsearch
//        verify(mockMasterSearchRepository, times(1)).deleteById(master.getId());
//    }
//
//    @Test
//    @Transactional
//    public void searchMaster() throws Exception {
//        // Configure the mock search repository
//        // Initialize the database
//        masterRepository.saveAndFlush(master);
//        when(mockMasterSearchRepository.search(queryStringQuery("id:" + master.getId()), PageRequest.of(0, 20)))
//            .thenReturn(new PageImpl<>(Collections.singletonList(master), PageRequest.of(0, 1), 1));
//
//        // Search the master
//        restMasterMockMvc.perform(get("/api/_search/masters?query=id:" + master.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(master.getId().intValue())))
//            .andExpect(jsonPath("$.[*].masterId").value(hasItem(DEFAULT_MASTER_ID.toString())))
//            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
//            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
//            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
//            .andExpect(jsonPath("$.[*].masterUserName").value(hasItem(DEFAULT_MASTER_USER_NAME)))
//            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
//            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
//            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
//            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
//            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
//            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
//            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
//            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
//            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
//    }
}
