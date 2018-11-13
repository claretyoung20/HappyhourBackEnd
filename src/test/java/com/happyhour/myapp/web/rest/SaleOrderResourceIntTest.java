package com.happyhour.myapp.web.rest;

import com.happyhour.myapp.HappybourBackEndApp;

import com.happyhour.myapp.domain.SaleOrder;
import com.happyhour.myapp.repository.SaleOrderRepository;
import com.happyhour.myapp.service.SaleOrderService;
import com.happyhour.myapp.service.dto.SaleOrderDTO;
import com.happyhour.myapp.service.mapper.SaleOrderMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.happyhour.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SaleOrderResource REST controller.
 *
 * @see SaleOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HappybourBackEndApp.class)
public class SaleOrderResourceIntTest {

    private static final Double DEFAULT_BASE_PRICE = 1D;
    private static final Double UPDATED_BASE_PRICE = 2D;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_DISCOUNT_AMOUNT = 1D;
    private static final Double UPDATED_DISCOUNT_AMOUNT = 2D;

    private static final Double DEFAULT_ORIGINAL_PRICE = 1D;
    private static final Double UPDATED_ORIGINAL_PRICE = 2D;

    @Autowired
    private SaleOrderRepository saleOrderRepository;

    @Autowired
    private SaleOrderMapper saleOrderMapper;
    
    @Autowired
    private SaleOrderService saleOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSaleOrderMockMvc;

    private SaleOrder saleOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SaleOrderResource saleOrderResource = new SaleOrderResource(saleOrderService);
        this.restSaleOrderMockMvc = MockMvcBuilders.standaloneSetup(saleOrderResource)
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
    public static SaleOrder createEntity(EntityManager em) {
        SaleOrder saleOrder = new SaleOrder()
            .basePrice(DEFAULT_BASE_PRICE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .discountAmount(DEFAULT_DISCOUNT_AMOUNT)
            .originalPrice(DEFAULT_ORIGINAL_PRICE);
        return saleOrder;
    }

    @Before
    public void initTest() {
        saleOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaleOrder() throws Exception {
        int databaseSizeBeforeCreate = saleOrderRepository.findAll().size();

        // Create the SaleOrder
        SaleOrderDTO saleOrderDTO = saleOrderMapper.toDto(saleOrder);
        restSaleOrderMockMvc.perform(post("/api/sale-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeCreate + 1);
        SaleOrder testSaleOrder = saleOrderList.get(saleOrderList.size() - 1);
        assertThat(testSaleOrder.getBasePrice()).isEqualTo(DEFAULT_BASE_PRICE);
        assertThat(testSaleOrder.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testSaleOrder.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testSaleOrder.getDiscountAmount()).isEqualTo(DEFAULT_DISCOUNT_AMOUNT);
        assertThat(testSaleOrder.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    public void createSaleOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saleOrderRepository.findAll().size();

        // Create the SaleOrder with an existing ID
        saleOrder.setId(1L);
        SaleOrderDTO saleOrderDTO = saleOrderMapper.toDto(saleOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleOrderMockMvc.perform(post("/api/sale-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSaleOrders() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get all the saleOrderList
        restSaleOrderMockMvc.perform(get("/api/sale-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].basePrice").value(hasItem(DEFAULT_BASE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].discountAmount").value(hasItem(DEFAULT_DISCOUNT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSaleOrder() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        // Get the saleOrder
        restSaleOrderMockMvc.perform(get("/api/sale-orders/{id}", saleOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(saleOrder.getId().intValue()))
            .andExpect(jsonPath("$.basePrice").value(DEFAULT_BASE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.discountAmount").value(DEFAULT_DISCOUNT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.originalPrice").value(DEFAULT_ORIGINAL_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSaleOrder() throws Exception {
        // Get the saleOrder
        restSaleOrderMockMvc.perform(get("/api/sale-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaleOrder() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        int databaseSizeBeforeUpdate = saleOrderRepository.findAll().size();

        // Update the saleOrder
        SaleOrder updatedSaleOrder = saleOrderRepository.findById(saleOrder.getId()).get();
        // Disconnect from session so that the updates on updatedSaleOrder are not directly saved in db
        em.detach(updatedSaleOrder);
        updatedSaleOrder
            .basePrice(UPDATED_BASE_PRICE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .discountAmount(UPDATED_DISCOUNT_AMOUNT)
            .originalPrice(UPDATED_ORIGINAL_PRICE);
        SaleOrderDTO saleOrderDTO = saleOrderMapper.toDto(updatedSaleOrder);

        restSaleOrderMockMvc.perform(put("/api/sale-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleOrderDTO)))
            .andExpect(status().isOk());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeUpdate);
        SaleOrder testSaleOrder = saleOrderList.get(saleOrderList.size() - 1);
        assertThat(testSaleOrder.getBasePrice()).isEqualTo(UPDATED_BASE_PRICE);
        assertThat(testSaleOrder.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testSaleOrder.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testSaleOrder.getDiscountAmount()).isEqualTo(UPDATED_DISCOUNT_AMOUNT);
        assertThat(testSaleOrder.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingSaleOrder() throws Exception {
        int databaseSizeBeforeUpdate = saleOrderRepository.findAll().size();

        // Create the SaleOrder
        SaleOrderDTO saleOrderDTO = saleOrderMapper.toDto(saleOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleOrderMockMvc.perform(put("/api/sale-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SaleOrder in the database
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSaleOrder() throws Exception {
        // Initialize the database
        saleOrderRepository.saveAndFlush(saleOrder);

        int databaseSizeBeforeDelete = saleOrderRepository.findAll().size();

        // Get the saleOrder
        restSaleOrderMockMvc.perform(delete("/api/sale-orders/{id}", saleOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SaleOrder> saleOrderList = saleOrderRepository.findAll();
        assertThat(saleOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleOrder.class);
        SaleOrder saleOrder1 = new SaleOrder();
        saleOrder1.setId(1L);
        SaleOrder saleOrder2 = new SaleOrder();
        saleOrder2.setId(saleOrder1.getId());
        assertThat(saleOrder1).isEqualTo(saleOrder2);
        saleOrder2.setId(2L);
        assertThat(saleOrder1).isNotEqualTo(saleOrder2);
        saleOrder1.setId(null);
        assertThat(saleOrder1).isNotEqualTo(saleOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleOrderDTO.class);
        SaleOrderDTO saleOrderDTO1 = new SaleOrderDTO();
        saleOrderDTO1.setId(1L);
        SaleOrderDTO saleOrderDTO2 = new SaleOrderDTO();
        assertThat(saleOrderDTO1).isNotEqualTo(saleOrderDTO2);
        saleOrderDTO2.setId(saleOrderDTO1.getId());
        assertThat(saleOrderDTO1).isEqualTo(saleOrderDTO2);
        saleOrderDTO2.setId(2L);
        assertThat(saleOrderDTO1).isNotEqualTo(saleOrderDTO2);
        saleOrderDTO1.setId(null);
        assertThat(saleOrderDTO1).isNotEqualTo(saleOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(saleOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(saleOrderMapper.fromId(null)).isNull();
    }
}
