package com.happyhour.myapp.web.rest;

import com.happyhour.myapp.HappybourBackEndApp;

import com.happyhour.myapp.domain.TableType;
import com.happyhour.myapp.repository.TableTypeRepository;
import com.happyhour.myapp.service.TableTypeService;
import com.happyhour.myapp.service.dto.TableTypeDTO;
import com.happyhour.myapp.service.mapper.TableTypeMapper;
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
 * Test class for the TableTypeResource REST controller.
 *
 * @see TableTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HappybourBackEndApp.class)
public class TableTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TableTypeRepository tableTypeRepository;

    @Autowired
    private TableTypeMapper tableTypeMapper;
    
    @Autowired
    private TableTypeService tableTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTableTypeMockMvc;

    private TableType tableType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TableTypeResource tableTypeResource = new TableTypeResource(tableTypeService);
        this.restTableTypeMockMvc = MockMvcBuilders.standaloneSetup(tableTypeResource)
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
    public static TableType createEntity(EntityManager em) {
        TableType tableType = new TableType()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return tableType;
    }

    @Before
    public void initTest() {
        tableType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTableType() throws Exception {
        int databaseSizeBeforeCreate = tableTypeRepository.findAll().size();

        // Create the TableType
        TableTypeDTO tableTypeDTO = tableTypeMapper.toDto(tableType);
        restTableTypeMockMvc.perform(post("/api/table-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the TableType in the database
        List<TableType> tableTypeList = tableTypeRepository.findAll();
        assertThat(tableTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TableType testTableType = tableTypeList.get(tableTypeList.size() - 1);
        assertThat(testTableType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTableType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTableTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tableTypeRepository.findAll().size();

        // Create the TableType with an existing ID
        tableType.setId(1L);
        TableTypeDTO tableTypeDTO = tableTypeMapper.toDto(tableType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTableTypeMockMvc.perform(post("/api/table-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TableType in the database
        List<TableType> tableTypeList = tableTypeRepository.findAll();
        assertThat(tableTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tableTypeRepository.findAll().size();
        // set the field null
        tableType.setCode(null);

        // Create the TableType, which fails.
        TableTypeDTO tableTypeDTO = tableTypeMapper.toDto(tableType);

        restTableTypeMockMvc.perform(post("/api/table-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TableType> tableTypeList = tableTypeRepository.findAll();
        assertThat(tableTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTableTypes() throws Exception {
        // Initialize the database
        tableTypeRepository.saveAndFlush(tableType);

        // Get all the tableTypeList
        restTableTypeMockMvc.perform(get("/api/table-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tableType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getTableType() throws Exception {
        // Initialize the database
        tableTypeRepository.saveAndFlush(tableType);

        // Get the tableType
        restTableTypeMockMvc.perform(get("/api/table-types/{id}", tableType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tableType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTableType() throws Exception {
        // Get the tableType
        restTableTypeMockMvc.perform(get("/api/table-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTableType() throws Exception {
        // Initialize the database
        tableTypeRepository.saveAndFlush(tableType);

        int databaseSizeBeforeUpdate = tableTypeRepository.findAll().size();

        // Update the tableType
        TableType updatedTableType = tableTypeRepository.findById(tableType.getId()).get();
        // Disconnect from session so that the updates on updatedTableType are not directly saved in db
        em.detach(updatedTableType);
        updatedTableType
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        TableTypeDTO tableTypeDTO = tableTypeMapper.toDto(updatedTableType);

        restTableTypeMockMvc.perform(put("/api/table-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableTypeDTO)))
            .andExpect(status().isOk());

        // Validate the TableType in the database
        List<TableType> tableTypeList = tableTypeRepository.findAll();
        assertThat(tableTypeList).hasSize(databaseSizeBeforeUpdate);
        TableType testTableType = tableTypeList.get(tableTypeList.size() - 1);
        assertThat(testTableType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTableType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTableType() throws Exception {
        int databaseSizeBeforeUpdate = tableTypeRepository.findAll().size();

        // Create the TableType
        TableTypeDTO tableTypeDTO = tableTypeMapper.toDto(tableType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTableTypeMockMvc.perform(put("/api/table-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TableType in the database
        List<TableType> tableTypeList = tableTypeRepository.findAll();
        assertThat(tableTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTableType() throws Exception {
        // Initialize the database
        tableTypeRepository.saveAndFlush(tableType);

        int databaseSizeBeforeDelete = tableTypeRepository.findAll().size();

        // Get the tableType
        restTableTypeMockMvc.perform(delete("/api/table-types/{id}", tableType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TableType> tableTypeList = tableTypeRepository.findAll();
        assertThat(tableTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TableType.class);
        TableType tableType1 = new TableType();
        tableType1.setId(1L);
        TableType tableType2 = new TableType();
        tableType2.setId(tableType1.getId());
        assertThat(tableType1).isEqualTo(tableType2);
        tableType2.setId(2L);
        assertThat(tableType1).isNotEqualTo(tableType2);
        tableType1.setId(null);
        assertThat(tableType1).isNotEqualTo(tableType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TableTypeDTO.class);
        TableTypeDTO tableTypeDTO1 = new TableTypeDTO();
        tableTypeDTO1.setId(1L);
        TableTypeDTO tableTypeDTO2 = new TableTypeDTO();
        assertThat(tableTypeDTO1).isNotEqualTo(tableTypeDTO2);
        tableTypeDTO2.setId(tableTypeDTO1.getId());
        assertThat(tableTypeDTO1).isEqualTo(tableTypeDTO2);
        tableTypeDTO2.setId(2L);
        assertThat(tableTypeDTO1).isNotEqualTo(tableTypeDTO2);
        tableTypeDTO1.setId(null);
        assertThat(tableTypeDTO1).isNotEqualTo(tableTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tableTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tableTypeMapper.fromId(null)).isNull();
    }
}
