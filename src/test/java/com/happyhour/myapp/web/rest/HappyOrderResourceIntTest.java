package com.happyhour.myapp.web.rest;

import com.happyhour.myapp.HappybourBackEndApp;

import com.happyhour.myapp.domain.HappyOrder;
import com.happyhour.myapp.domain.BookTable;
import com.happyhour.myapp.repository.HappyOrderRepository;
import com.happyhour.myapp.service.HappyOrderService;
import com.happyhour.myapp.service.dto.HappyOrderDTO;
import com.happyhour.myapp.service.mapper.HappyOrderMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.happyhour.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HappyOrderResource REST controller.
 *
 * @see HappyOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HappybourBackEndApp.class)
public class HappyOrderResourceIntTest {

    private static final Double DEFAULT_BASE_TOTAL = 1D;
    private static final Double UPDATED_BASE_TOTAL = 2D;

    private static final Double DEFAULT_TOTAL_PRICE = 1D;
    private static final Double UPDATED_TOTAL_PRICE = 2D;

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private HappyOrderRepository happyOrderRepository;

    @Autowired
    private HappyOrderMapper happyOrderMapper;
    
    @Autowired
    private HappyOrderService happyOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHappyOrderMockMvc;

    private HappyOrder happyOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HappyOrderResource happyOrderResource = new HappyOrderResource(happyOrderService);
        this.restHappyOrderMockMvc = MockMvcBuilders.standaloneSetup(happyOrderResource)
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
    public static HappyOrder createEntity(EntityManager em) {
        HappyOrder happyOrder = new HappyOrder()
            .baseTotal(DEFAULT_BASE_TOTAL)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateCreated(DEFAULT_DATE_CREATED);
        // Add required entity
        BookTable bookTable = BookTableResourceIntTest.createEntity(em);
        em.persist(bookTable);
        em.flush();
        happyOrder.setBookTable(bookTable);
        return happyOrder;
    }

    @Before
    public void initTest() {
        happyOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createHappyOrder() throws Exception {
        int databaseSizeBeforeCreate = happyOrderRepository.findAll().size();

        // Create the HappyOrder
        HappyOrderDTO happyOrderDTO = happyOrderMapper.toDto(happyOrder);
        restHappyOrderMockMvc.perform(post("/api/happy-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(happyOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the HappyOrder in the database
        List<HappyOrder> happyOrderList = happyOrderRepository.findAll();
        assertThat(happyOrderList).hasSize(databaseSizeBeforeCreate + 1);
        HappyOrder testHappyOrder = happyOrderList.get(happyOrderList.size() - 1);
        assertThat(testHappyOrder.getBaseTotal()).isEqualTo(DEFAULT_BASE_TOTAL);
        assertThat(testHappyOrder.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
//        assertThat(testHappyOrder.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
//        assertThat(testHappyOrder.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void createHappyOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = happyOrderRepository.findAll().size();

        // Create the HappyOrder with an existing ID
        happyOrder.setId(1L);
        HappyOrderDTO happyOrderDTO = happyOrderMapper.toDto(happyOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHappyOrderMockMvc.perform(post("/api/happy-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(happyOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HappyOrder in the database
        List<HappyOrder> happyOrderList = happyOrderRepository.findAll();
        assertThat(happyOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHappyOrders() throws Exception {
        // Initialize the database
        happyOrderRepository.saveAndFlush(happyOrder);

        // Get all the happyOrderList
        restHappyOrderMockMvc.perform(get("/api/happy-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(happyOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].baseTotal").value(hasItem(DEFAULT_BASE_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getHappyOrder() throws Exception {
        // Initialize the database
        happyOrderRepository.saveAndFlush(happyOrder);

        // Get the happyOrder
        restHappyOrderMockMvc.perform(get("/api/happy-orders/{id}", happyOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(happyOrder.getId().intValue()))
            .andExpect(jsonPath("$.baseTotal").value(DEFAULT_BASE_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHappyOrder() throws Exception {
        // Get the happyOrder
        restHappyOrderMockMvc.perform(get("/api/happy-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHappyOrder() throws Exception {
        // Initialize the database
        happyOrderRepository.saveAndFlush(happyOrder);

        int databaseSizeBeforeUpdate = happyOrderRepository.findAll().size();

        // Update the happyOrder
        HappyOrder updatedHappyOrder = happyOrderRepository.findById(happyOrder.getId()).get();
        // Disconnect from session so that the updates on updatedHappyOrder are not directly saved in db
        em.detach(updatedHappyOrder);
        updatedHappyOrder
            .baseTotal(UPDATED_BASE_TOTAL)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateCreated(UPDATED_DATE_CREATED);
        HappyOrderDTO happyOrderDTO = happyOrderMapper.toDto(updatedHappyOrder);

        restHappyOrderMockMvc.perform(put("/api/happy-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(happyOrderDTO)))
            .andExpect(status().isOk());

        // Validate the HappyOrder in the database
        List<HappyOrder> happyOrderList = happyOrderRepository.findAll();
        assertThat(happyOrderList).hasSize(databaseSizeBeforeUpdate);
        HappyOrder testHappyOrder = happyOrderList.get(happyOrderList.size() - 1);
        assertThat(testHappyOrder.getBaseTotal()).isEqualTo(UPDATED_BASE_TOTAL);
        assertThat(testHappyOrder.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testHappyOrder.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testHappyOrder.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingHappyOrder() throws Exception {
        int databaseSizeBeforeUpdate = happyOrderRepository.findAll().size();

        // Create the HappyOrder
        HappyOrderDTO happyOrderDTO = happyOrderMapper.toDto(happyOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHappyOrderMockMvc.perform(put("/api/happy-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(happyOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HappyOrder in the database
        List<HappyOrder> happyOrderList = happyOrderRepository.findAll();
        assertThat(happyOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHappyOrder() throws Exception {
        // Initialize the database
        happyOrderRepository.saveAndFlush(happyOrder);

        int databaseSizeBeforeDelete = happyOrderRepository.findAll().size();

        // Get the happyOrder
        restHappyOrderMockMvc.perform(delete("/api/happy-orders/{id}", happyOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HappyOrder> happyOrderList = happyOrderRepository.findAll();
        assertThat(happyOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HappyOrder.class);
        HappyOrder happyOrder1 = new HappyOrder();
        happyOrder1.setId(1L);
        HappyOrder happyOrder2 = new HappyOrder();
        happyOrder2.setId(happyOrder1.getId());
        assertThat(happyOrder1).isEqualTo(happyOrder2);
        happyOrder2.setId(2L);
        assertThat(happyOrder1).isNotEqualTo(happyOrder2);
        happyOrder1.setId(null);
        assertThat(happyOrder1).isNotEqualTo(happyOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HappyOrderDTO.class);
        HappyOrderDTO happyOrderDTO1 = new HappyOrderDTO();
        happyOrderDTO1.setId(1L);
        HappyOrderDTO happyOrderDTO2 = new HappyOrderDTO();
        assertThat(happyOrderDTO1).isNotEqualTo(happyOrderDTO2);
        happyOrderDTO2.setId(happyOrderDTO1.getId());
        assertThat(happyOrderDTO1).isEqualTo(happyOrderDTO2);
        happyOrderDTO2.setId(2L);
        assertThat(happyOrderDTO1).isNotEqualTo(happyOrderDTO2);
        happyOrderDTO1.setId(null);
        assertThat(happyOrderDTO1).isNotEqualTo(happyOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(happyOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(happyOrderMapper.fromId(null)).isNull();
    }
}
