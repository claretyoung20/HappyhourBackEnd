package com.happyhour.myapp.web.rest;

import com.happyhour.myapp.HappybourBackEndApp;

import com.happyhour.myapp.domain.BookTable;
import com.happyhour.myapp.repository.BookTableRepository;
import com.happyhour.myapp.service.BookTableService;
import com.happyhour.myapp.service.dto.BookTableDTO;
import com.happyhour.myapp.service.mapper.BookTableMapper;
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
 * Test class for the BookTableResource REST controller.
 *
 * @see BookTableResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HappybourBackEndApp.class)
public class BookTableResourceIntTest {

    private static final Boolean DEFAULT_IS_AVALIABLE = false;
    private static final Boolean UPDATED_IS_AVALIABLE = true;

    private static final Integer DEFAULT_PERSONS = 1;
    private static final Integer UPDATED_PERSONS = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private BookTableRepository bookTableRepository;

    @Autowired
    private BookTableMapper bookTableMapper;
    
    @Autowired
    private BookTableService bookTableService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookTableMockMvc;

    private BookTable bookTable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookTableResource bookTableResource = new BookTableResource(bookTableService);
        this.restBookTableMockMvc = MockMvcBuilders.standaloneSetup(bookTableResource)
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
    public static BookTable createEntity(EntityManager em) {
        BookTable bookTable = new BookTable()
            .isAvaliable(DEFAULT_IS_AVALIABLE)
            .persons(DEFAULT_PERSONS)
            .price(DEFAULT_PRICE)
            .imageUrl(DEFAULT_IMAGE_URL);
        return bookTable;
    }

    @Before
    public void initTest() {
        bookTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookTable() throws Exception {
        int databaseSizeBeforeCreate = bookTableRepository.findAll().size();

        // Create the BookTable
        BookTableDTO bookTableDTO = bookTableMapper.toDto(bookTable);
        restBookTableMockMvc.perform(post("/api/book-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookTableDTO)))
            .andExpect(status().isCreated());

        // Validate the BookTable in the database
        List<BookTable> bookTableList = bookTableRepository.findAll();
        assertThat(bookTableList).hasSize(databaseSizeBeforeCreate + 1);
        BookTable testBookTable = bookTableList.get(bookTableList.size() - 1);
        assertThat(testBookTable.isIsAvaliable()).isEqualTo(DEFAULT_IS_AVALIABLE);
        assertThat(testBookTable.getPersons()).isEqualTo(DEFAULT_PERSONS);
        assertThat(testBookTable.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBookTable.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void createBookTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookTableRepository.findAll().size();

        // Create the BookTable with an existing ID
        bookTable.setId(1L);
        BookTableDTO bookTableDTO = bookTableMapper.toDto(bookTable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookTableMockMvc.perform(post("/api/book-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookTable in the database
        List<BookTable> bookTableList = bookTableRepository.findAll();
        assertThat(bookTableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBookTables() throws Exception {
        // Initialize the database
        bookTableRepository.saveAndFlush(bookTable);

        // Get all the bookTableList
        restBookTableMockMvc.perform(get("/api/book-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].isAvaliable").value(hasItem(DEFAULT_IS_AVALIABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].persons").value(hasItem(DEFAULT_PERSONS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getBookTable() throws Exception {
        // Initialize the database
        bookTableRepository.saveAndFlush(bookTable);

        // Get the bookTable
        restBookTableMockMvc.perform(get("/api/book-tables/{id}", bookTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookTable.getId().intValue()))
            .andExpect(jsonPath("$.isAvaliable").value(DEFAULT_IS_AVALIABLE.booleanValue()))
            .andExpect(jsonPath("$.persons").value(DEFAULT_PERSONS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBookTable() throws Exception {
        // Get the bookTable
        restBookTableMockMvc.perform(get("/api/book-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookTable() throws Exception {
        // Initialize the database
        bookTableRepository.saveAndFlush(bookTable);

        int databaseSizeBeforeUpdate = bookTableRepository.findAll().size();

        // Update the bookTable
        BookTable updatedBookTable = bookTableRepository.findById(bookTable.getId()).get();
        // Disconnect from session so that the updates on updatedBookTable are not directly saved in db
        em.detach(updatedBookTable);
        updatedBookTable
            .isAvaliable(UPDATED_IS_AVALIABLE)
            .persons(UPDATED_PERSONS)
            .price(UPDATED_PRICE)
            .imageUrl(UPDATED_IMAGE_URL);
        BookTableDTO bookTableDTO = bookTableMapper.toDto(updatedBookTable);

        restBookTableMockMvc.perform(put("/api/book-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookTableDTO)))
            .andExpect(status().isOk());

        // Validate the BookTable in the database
        List<BookTable> bookTableList = bookTableRepository.findAll();
        assertThat(bookTableList).hasSize(databaseSizeBeforeUpdate);
        BookTable testBookTable = bookTableList.get(bookTableList.size() - 1);
        assertThat(testBookTable.isIsAvaliable()).isEqualTo(UPDATED_IS_AVALIABLE);
        assertThat(testBookTable.getPersons()).isEqualTo(UPDATED_PERSONS);
        assertThat(testBookTable.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBookTable.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingBookTable() throws Exception {
        int databaseSizeBeforeUpdate = bookTableRepository.findAll().size();

        // Create the BookTable
        BookTableDTO bookTableDTO = bookTableMapper.toDto(bookTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookTableMockMvc.perform(put("/api/book-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookTable in the database
        List<BookTable> bookTableList = bookTableRepository.findAll();
        assertThat(bookTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBookTable() throws Exception {
        // Initialize the database
        bookTableRepository.saveAndFlush(bookTable);

        int databaseSizeBeforeDelete = bookTableRepository.findAll().size();

        // Get the bookTable
        restBookTableMockMvc.perform(delete("/api/book-tables/{id}", bookTable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookTable> bookTableList = bookTableRepository.findAll();
        assertThat(bookTableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookTable.class);
        BookTable bookTable1 = new BookTable();
        bookTable1.setId(1L);
        BookTable bookTable2 = new BookTable();
        bookTable2.setId(bookTable1.getId());
        assertThat(bookTable1).isEqualTo(bookTable2);
        bookTable2.setId(2L);
        assertThat(bookTable1).isNotEqualTo(bookTable2);
        bookTable1.setId(null);
        assertThat(bookTable1).isNotEqualTo(bookTable2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookTableDTO.class);
        BookTableDTO bookTableDTO1 = new BookTableDTO();
        bookTableDTO1.setId(1L);
        BookTableDTO bookTableDTO2 = new BookTableDTO();
        assertThat(bookTableDTO1).isNotEqualTo(bookTableDTO2);
        bookTableDTO2.setId(bookTableDTO1.getId());
        assertThat(bookTableDTO1).isEqualTo(bookTableDTO2);
        bookTableDTO2.setId(2L);
        assertThat(bookTableDTO1).isNotEqualTo(bookTableDTO2);
        bookTableDTO1.setId(null);
        assertThat(bookTableDTO1).isNotEqualTo(bookTableDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookTableMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookTableMapper.fromId(null)).isNull();
    }
}
