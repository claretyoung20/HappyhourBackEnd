package com.happyhour.myapp.web.rest;

import com.happyhour.myapp.HappybourBackEndApp;

import com.happyhour.myapp.domain.Coupon;
import com.happyhour.myapp.repository.CouponRepository;
import com.happyhour.myapp.service.CouponService;
import com.happyhour.myapp.service.dto.CouponDTO;
import com.happyhour.myapp.service.mapper.CouponMapper;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.happyhour.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CouponResource REST controller.
 *
 * @see CouponResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HappybourBackEndApp.class)
public class CouponResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Integer DEFAULT_NO_PER_USER = 1;
    private static final Integer UPDATED_NO_PER_USER = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final LocalDate DEFAULT_START_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MAX_AMOUNT_TO_APPLY = 1D;
    private static final Double UPDATED_MAX_AMOUNT_TO_APPLY = 2D;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMapper couponMapper;
    
    @Autowired
    private CouponService couponService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCouponMockMvc;

    private Coupon coupon;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CouponResource couponResource = new CouponResource(couponService);
        this.restCouponMockMvc = MockMvcBuilders.standaloneSetup(couponResource)
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
    public static Coupon createEntity(EntityManager em) {
        Coupon coupon = new Coupon()
            .code(DEFAULT_CODE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .endDate(DEFAULT_END_DATE)
            .isActive(DEFAULT_IS_ACTIVE)
            .noPerUser(DEFAULT_NO_PER_USER)
            .price(DEFAULT_PRICE)
            .startFromDate(DEFAULT_START_FROM_DATE)
            .maxAmountToApply(DEFAULT_MAX_AMOUNT_TO_APPLY);
        return coupon;
    }

    @Before
    public void initTest() {
        coupon = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoupon() throws Exception {
        int databaseSizeBeforeCreate = couponRepository.findAll().size();

        // Create the Coupon
        CouponDTO couponDTO = couponMapper.toDto(coupon);
        restCouponMockMvc.perform(post("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(couponDTO)))
            .andExpect(status().isCreated());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeCreate + 1);
        Coupon testCoupon = couponList.get(couponList.size() - 1);
        assertThat(testCoupon.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCoupon.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCoupon.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testCoupon.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testCoupon.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testCoupon.getNoPerUser()).isEqualTo(DEFAULT_NO_PER_USER);
        assertThat(testCoupon.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testCoupon.getStartFromDate()).isEqualTo(DEFAULT_START_FROM_DATE);
        assertThat(testCoupon.getMaxAmountToApply()).isEqualTo(DEFAULT_MAX_AMOUNT_TO_APPLY);
    }

    @Test
    @Transactional
    public void createCouponWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = couponRepository.findAll().size();

        // Create the Coupon with an existing ID
        coupon.setId(1L);
        CouponDTO couponDTO = couponMapper.toDto(coupon);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCouponMockMvc.perform(post("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(couponDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponRepository.findAll().size();
        // set the field null
        coupon.setCode(null);

        // Create the Coupon, which fails.
        CouponDTO couponDTO = couponMapper.toDto(coupon);

        restCouponMockMvc.perform(post("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(couponDTO)))
            .andExpect(status().isBadRequest());

        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoupons() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        // Get all the couponList
        restCouponMockMvc.perform(get("/api/coupons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coupon.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].noPerUser").value(hasItem(DEFAULT_NO_PER_USER)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].startFromDate").value(hasItem(DEFAULT_START_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].maxAmountToApply").value(hasItem(DEFAULT_MAX_AMOUNT_TO_APPLY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCoupon() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        // Get the coupon
        restCouponMockMvc.perform(get("/api/coupons/{id}", coupon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coupon.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.noPerUser").value(DEFAULT_NO_PER_USER))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.startFromDate").value(DEFAULT_START_FROM_DATE.toString()))
            .andExpect(jsonPath("$.maxAmountToApply").value(DEFAULT_MAX_AMOUNT_TO_APPLY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoupon() throws Exception {
        // Get the coupon
        restCouponMockMvc.perform(get("/api/coupons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoupon() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        int databaseSizeBeforeUpdate = couponRepository.findAll().size();

        // Update the coupon
        Coupon updatedCoupon = couponRepository.findById(coupon.getId()).get();
        // Disconnect from session so that the updates on updatedCoupon are not directly saved in db
        em.detach(updatedCoupon);
        updatedCoupon
            .code(UPDATED_CODE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .noPerUser(UPDATED_NO_PER_USER)
            .price(UPDATED_PRICE)
            .startFromDate(UPDATED_START_FROM_DATE)
            .maxAmountToApply(UPDATED_MAX_AMOUNT_TO_APPLY);
        CouponDTO couponDTO = couponMapper.toDto(updatedCoupon);

        restCouponMockMvc.perform(put("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(couponDTO)))
            .andExpect(status().isOk());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeUpdate);
        Coupon testCoupon = couponList.get(couponList.size() - 1);
        assertThat(testCoupon.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCoupon.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCoupon.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testCoupon.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testCoupon.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testCoupon.getNoPerUser()).isEqualTo(UPDATED_NO_PER_USER);
        assertThat(testCoupon.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testCoupon.getStartFromDate()).isEqualTo(UPDATED_START_FROM_DATE);
        assertThat(testCoupon.getMaxAmountToApply()).isEqualTo(UPDATED_MAX_AMOUNT_TO_APPLY);
    }

    @Test
    @Transactional
    public void updateNonExistingCoupon() throws Exception {
        int databaseSizeBeforeUpdate = couponRepository.findAll().size();

        // Create the Coupon
        CouponDTO couponDTO = couponMapper.toDto(coupon);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCouponMockMvc.perform(put("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(couponDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoupon() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        int databaseSizeBeforeDelete = couponRepository.findAll().size();

        // Get the coupon
        restCouponMockMvc.perform(delete("/api/coupons/{id}", coupon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coupon.class);
        Coupon coupon1 = new Coupon();
        coupon1.setId(1L);
        Coupon coupon2 = new Coupon();
        coupon2.setId(coupon1.getId());
        assertThat(coupon1).isEqualTo(coupon2);
        coupon2.setId(2L);
        assertThat(coupon1).isNotEqualTo(coupon2);
        coupon1.setId(null);
        assertThat(coupon1).isNotEqualTo(coupon2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CouponDTO.class);
        CouponDTO couponDTO1 = new CouponDTO();
        couponDTO1.setId(1L);
        CouponDTO couponDTO2 = new CouponDTO();
        assertThat(couponDTO1).isNotEqualTo(couponDTO2);
        couponDTO2.setId(couponDTO1.getId());
        assertThat(couponDTO1).isEqualTo(couponDTO2);
        couponDTO2.setId(2L);
        assertThat(couponDTO1).isNotEqualTo(couponDTO2);
        couponDTO1.setId(null);
        assertThat(couponDTO1).isNotEqualTo(couponDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(couponMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(couponMapper.fromId(null)).isNull();
    }
}
