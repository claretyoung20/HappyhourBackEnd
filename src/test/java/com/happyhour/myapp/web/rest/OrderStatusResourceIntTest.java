package com.happyhour.myapp.web.rest;

import com.happyhour.myapp.HappybourBackEndApp;

import com.happyhour.myapp.domain.OrderStatus;
import com.happyhour.myapp.repository.OrderStatusRepository;
import com.happyhour.myapp.service.OrderStatusService;
import com.happyhour.myapp.service.dto.OrderStatusDTO;
import com.happyhour.myapp.service.mapper.OrderStatusMapper;
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
 * Test class for the OrderStatusResource REST controller.
 *
 * @see OrderStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HappybourBackEndApp.class)
public class OrderStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private OrderStatusMapper orderStatusMapper;
    
    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderStatusMockMvc;

    private OrderStatus orderStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderStatusResource orderStatusResource = new OrderStatusResource(orderStatusService);
        this.restOrderStatusMockMvc = MockMvcBuilders.standaloneSetup(orderStatusResource)
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
    public static OrderStatus createEntity(EntityManager em) {
        OrderStatus orderStatus = new OrderStatus()
            .name(DEFAULT_NAME);
        return orderStatus;
    }

    @Before
    public void initTest() {
        orderStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderStatus() throws Exception {
        int databaseSizeBeforeCreate = orderStatusRepository.findAll().size();

        // Create the OrderStatus
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);
        restOrderStatusMockMvc.perform(post("/api/order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeCreate + 1);
        OrderStatus testOrderStatus = orderStatusList.get(orderStatusList.size() - 1);
        assertThat(testOrderStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createOrderStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderStatusRepository.findAll().size();

        // Create the OrderStatus with an existing ID
        orderStatus.setId(1L);
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderStatusMockMvc.perform(post("/api/order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderStatuses() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList
        restOrderStatusMockMvc.perform(get("/api/order-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderStatus() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get the orderStatus
        restOrderStatusMockMvc.perform(get("/api/order-statuses/{id}", orderStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderStatus() throws Exception {
        // Get the orderStatus
        restOrderStatusMockMvc.perform(get("/api/order-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderStatus() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();

        // Update the orderStatus
        OrderStatus updatedOrderStatus = orderStatusRepository.findById(orderStatus.getId()).get();
        // Disconnect from session so that the updates on updatedOrderStatus are not directly saved in db
        em.detach(updatedOrderStatus);
        updatedOrderStatus
            .name(UPDATED_NAME);
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(updatedOrderStatus);

        restOrderStatusMockMvc.perform(put("/api/order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO)))
            .andExpect(status().isOk());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
        OrderStatus testOrderStatus = orderStatusList.get(orderStatusList.size() - 1);
        assertThat(testOrderStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderStatus() throws Exception {
        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();

        // Create the OrderStatus
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderStatusMockMvc.perform(put("/api/order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderStatus() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        int databaseSizeBeforeDelete = orderStatusRepository.findAll().size();

        // Get the orderStatus
        restOrderStatusMockMvc.perform(delete("/api/order-statuses/{id}", orderStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderStatus.class);
        OrderStatus orderStatus1 = new OrderStatus();
        orderStatus1.setId(1L);
        OrderStatus orderStatus2 = new OrderStatus();
        orderStatus2.setId(orderStatus1.getId());
        assertThat(orderStatus1).isEqualTo(orderStatus2);
        orderStatus2.setId(2L);
        assertThat(orderStatus1).isNotEqualTo(orderStatus2);
        orderStatus1.setId(null);
        assertThat(orderStatus1).isNotEqualTo(orderStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderStatusDTO.class);
        OrderStatusDTO orderStatusDTO1 = new OrderStatusDTO();
        orderStatusDTO1.setId(1L);
        OrderStatusDTO orderStatusDTO2 = new OrderStatusDTO();
        assertThat(orderStatusDTO1).isNotEqualTo(orderStatusDTO2);
        orderStatusDTO2.setId(orderStatusDTO1.getId());
        assertThat(orderStatusDTO1).isEqualTo(orderStatusDTO2);
        orderStatusDTO2.setId(2L);
        assertThat(orderStatusDTO1).isNotEqualTo(orderStatusDTO2);
        orderStatusDTO1.setId(null);
        assertThat(orderStatusDTO1).isNotEqualTo(orderStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderStatusMapper.fromId(null)).isNull();
    }
}
