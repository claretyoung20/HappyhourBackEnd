package com.happyhour.myapp.web.rest;

import com.happyhour.myapp.HappybourBackEndApp;

import com.happyhour.myapp.domain.TimeManager;
import com.happyhour.myapp.repository.TimeManagerRepository;
import com.happyhour.myapp.service.TimeManagerService;
import com.happyhour.myapp.service.dto.TimeManagerDTO;
import com.happyhour.myapp.service.mapper.TimeManagerMapper;
import com.happyhour.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.happyhour.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TimeManagerResource REST controller.
 *
 * @see TimeManagerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HappybourBackEndApp.class)
public class TimeManagerResourceIntTest {

    private static final String DEFAULT_DAY = "AAAAAAAAAA";
    private static final String UPDATED_DAY = "BBBBBBBBBB";

    private static final String DEFAULT_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_END_TIME = "AAAAAAAAAA";
    private static final String UPDATED_END_TIME = "BBBBBBBBBB";

    @Autowired
    private TimeManagerRepository timeManagerRepository;

    @Autowired
    private TimeManagerMapper timeManagerMapper;
    
    @Autowired
    private TimeManagerService timeManagerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimeManagerMockMvc;

    private TimeManager timeManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimeManagerResource timeManagerResource = new TimeManagerResource(timeManagerService);
        this.restTimeManagerMockMvc = MockMvcBuilders.standaloneSetup(timeManagerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeManager createEntity(EntityManager em) {
        TimeManager timeManager = new TimeManager()
            .day(DEFAULT_DAY)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return timeManager;
    }

    @Before
    public void initTest() {
        timeManager = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeManager() throws Exception {
        int databaseSizeBeforeCreate = timeManagerRepository.findAll().size();

        // Create the TimeManager
        TimeManagerDTO timeManagerDTO = timeManagerMapper.toDto(timeManager);
        restTimeManagerMockMvc.perform(post("/api/time-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeManagerDTO)))
            .andExpect(status().isCreated());

        // Validate the TimeManager in the database
        List<TimeManager> timeManagerList = timeManagerRepository.findAll();
        assertThat(timeManagerList).hasSize(databaseSizeBeforeCreate + 1);
        TimeManager testTimeManager = timeManagerList.get(timeManagerList.size() - 1);
        assertThat(testTimeManager.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testTimeManager.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTimeManager.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createTimeManagerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeManagerRepository.findAll().size();

        // Create the TimeManager with an existing ID
        timeManager.setId(1L);
        TimeManagerDTO timeManagerDTO = timeManagerMapper.toDto(timeManager);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeManagerMockMvc.perform(post("/api/time-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeManagerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeManager in the database
        List<TimeManager> timeManagerList = timeManagerRepository.findAll();
        assertThat(timeManagerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeManagerRepository.findAll().size();
        // set the field null
        timeManager.setDay(null);

        // Create the TimeManager, which fails.
        TimeManagerDTO timeManagerDTO = timeManagerMapper.toDto(timeManager);

        restTimeManagerMockMvc.perform(post("/api/time-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeManagerDTO)))
            .andExpect(status().isBadRequest());

        List<TimeManager> timeManagerList = timeManagerRepository.findAll();
        assertThat(timeManagerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeManagerRepository.findAll().size();
        // set the field null
        timeManager.setStartTime(null);

        // Create the TimeManager, which fails.
        TimeManagerDTO timeManagerDTO = timeManagerMapper.toDto(timeManager);

        restTimeManagerMockMvc.perform(post("/api/time-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeManagerDTO)))
            .andExpect(status().isBadRequest());

        List<TimeManager> timeManagerList = timeManagerRepository.findAll();
        assertThat(timeManagerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTimeManagers() throws Exception {
        // Initialize the database
        timeManagerRepository.saveAndFlush(timeManager);

        // Get all the timeManagerList
        restTimeManagerMockMvc.perform(get("/api/time-managers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getTimeManager() throws Exception {
        // Initialize the database
        timeManagerRepository.saveAndFlush(timeManager);

        // Get the timeManager
        restTimeManagerMockMvc.perform(get("/api/time-managers/{id}", timeManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeManager.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeManager() throws Exception {
        // Get the timeManager
        restTimeManagerMockMvc.perform(get("/api/time-managers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeManager() throws Exception {
        // Initialize the database
        timeManagerRepository.saveAndFlush(timeManager);

        int databaseSizeBeforeUpdate = timeManagerRepository.findAll().size();

        // Update the timeManager
        TimeManager updatedTimeManager = timeManagerRepository.findById(timeManager.getId()).get();
        // Disconnect from session so that the updates on updatedTimeManager are not directly saved in db
        em.detach(updatedTimeManager);
        updatedTimeManager
            .day(UPDATED_DAY)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        TimeManagerDTO timeManagerDTO = timeManagerMapper.toDto(updatedTimeManager);

        restTimeManagerMockMvc.perform(put("/api/time-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeManagerDTO)))
            .andExpect(status().isOk());

        // Validate the TimeManager in the database
        List<TimeManager> timeManagerList = timeManagerRepository.findAll();
        assertThat(timeManagerList).hasSize(databaseSizeBeforeUpdate);
        TimeManager testTimeManager = timeManagerList.get(timeManagerList.size() - 1);
        assertThat(testTimeManager.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testTimeManager.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTimeManager.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeManager() throws Exception {
        int databaseSizeBeforeUpdate = timeManagerRepository.findAll().size();

        // Create the TimeManager
        TimeManagerDTO timeManagerDTO = timeManagerMapper.toDto(timeManager);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeManagerMockMvc.perform(put("/api/time-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeManagerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeManager in the database
        List<TimeManager> timeManagerList = timeManagerRepository.findAll();
        assertThat(timeManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimeManager() throws Exception {
        // Initialize the database
        timeManagerRepository.saveAndFlush(timeManager);

        int databaseSizeBeforeDelete = timeManagerRepository.findAll().size();

        // Get the timeManager
        restTimeManagerMockMvc.perform(delete("/api/time-managers/{id}", timeManager.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeManager> timeManagerList = timeManagerRepository.findAll();
        assertThat(timeManagerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeManager.class);
        TimeManager timeManager1 = new TimeManager();
        timeManager1.setId(1L);
        TimeManager timeManager2 = new TimeManager();
        timeManager2.setId(timeManager1.getId());
        assertThat(timeManager1).isEqualTo(timeManager2);
        timeManager2.setId(2L);
        assertThat(timeManager1).isNotEqualTo(timeManager2);
        timeManager1.setId(null);
        assertThat(timeManager1).isNotEqualTo(timeManager2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeManagerDTO.class);
        TimeManagerDTO timeManagerDTO1 = new TimeManagerDTO();
        timeManagerDTO1.setId(1L);
        TimeManagerDTO timeManagerDTO2 = new TimeManagerDTO();
        assertThat(timeManagerDTO1).isNotEqualTo(timeManagerDTO2);
        timeManagerDTO2.setId(timeManagerDTO1.getId());
        assertThat(timeManagerDTO1).isEqualTo(timeManagerDTO2);
        timeManagerDTO2.setId(2L);
        assertThat(timeManagerDTO1).isNotEqualTo(timeManagerDTO2);
        timeManagerDTO1.setId(null);
        assertThat(timeManagerDTO1).isNotEqualTo(timeManagerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(timeManagerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(timeManagerMapper.fromId(null)).isNull();
    }
}
