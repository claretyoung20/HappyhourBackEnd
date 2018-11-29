package com.happyhour.myapp.web.rest;

import com.happyhour.myapp.HappybourBackEndApp;

import com.happyhour.myapp.domain.ProductImage;
import com.happyhour.myapp.repository.ProductImageRepository;
import com.happyhour.myapp.service.ProductImageService;
import com.happyhour.myapp.service.dto.ProductImageDTO;
import com.happyhour.myapp.service.mapper.ProductImageMapper;
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
 * Test class for the ProductImageResource REST controller.
 *
 * @see ProductImageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HappybourBackEndApp.class)
public class ProductImageResourceIntTest {

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductImageMapper productImageMapper;
    
    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductImageMockMvc;

    private ProductImage productImage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductImageResource productImageResource = new ProductImageResource(productImageService);
        this.restProductImageMockMvc = MockMvcBuilders.standaloneSetup(productImageResource)
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
    public static ProductImage createEntity(EntityManager em) {
        ProductImage productImage = new ProductImage()
            .imageUrl(DEFAULT_IMAGE_URL);
        return productImage;
    }

    @Before
    public void initTest() {
        productImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductImage() throws Exception {
        int databaseSizeBeforeCreate = productImageRepository.findAll().size();

        // Create the ProductImage
        ProductImageDTO productImageDTO = productImageMapper.toDto(productImage);
        restProductImageMockMvc.perform(post("/api/product-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productImageDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductImage in the database
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeCreate + 1);
        ProductImage testProductImage = productImageList.get(productImageList.size() - 1);
        assertThat(testProductImage.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void createProductImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productImageRepository.findAll().size();

        // Create the ProductImage with an existing ID
        productImage.setId(1L);
        ProductImageDTO productImageDTO = productImageMapper.toDto(productImage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductImageMockMvc.perform(post("/api/product-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductImage in the database
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductImages() throws Exception {
        // Initialize the database
        productImageRepository.saveAndFlush(productImage);

        // Get all the productImageList
        restProductImageMockMvc.perform(get("/api/product-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getProductImage() throws Exception {
        // Initialize the database
        productImageRepository.saveAndFlush(productImage);

        // Get the productImage
        restProductImageMockMvc.perform(get("/api/product-images/{id}", productImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productImage.getId().intValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductImage() throws Exception {
        // Get the productImage
        restProductImageMockMvc.perform(get("/api/product-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductImage() throws Exception {
        // Initialize the database
        productImageRepository.saveAndFlush(productImage);

        int databaseSizeBeforeUpdate = productImageRepository.findAll().size();

        // Update the productImage
        ProductImage updatedProductImage = productImageRepository.findById(productImage.getId()).get();
        // Disconnect from session so that the updates on updatedProductImage are not directly saved in db
        em.detach(updatedProductImage);
        updatedProductImage
            .imageUrl(UPDATED_IMAGE_URL);
        ProductImageDTO productImageDTO = productImageMapper.toDto(updatedProductImage);

        restProductImageMockMvc.perform(put("/api/product-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productImageDTO)))
            .andExpect(status().isOk());

        // Validate the ProductImage in the database
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeUpdate);
        ProductImage testProductImage = productImageList.get(productImageList.size() - 1);
        assertThat(testProductImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingProductImage() throws Exception {
        int databaseSizeBeforeUpdate = productImageRepository.findAll().size();

        // Create the ProductImage
        ProductImageDTO productImageDTO = productImageMapper.toDto(productImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductImageMockMvc.perform(put("/api/product-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductImage in the database
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductImage() throws Exception {
        // Initialize the database
        productImageRepository.saveAndFlush(productImage);

        int databaseSizeBeforeDelete = productImageRepository.findAll().size();

        // Get the productImage
        restProductImageMockMvc.perform(delete("/api/product-images/{id}", productImage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductImage.class);
        ProductImage productImage1 = new ProductImage();
        productImage1.setId(1L);
        ProductImage productImage2 = new ProductImage();
        productImage2.setId(productImage1.getId());
        assertThat(productImage1).isEqualTo(productImage2);
        productImage2.setId(2L);
        assertThat(productImage1).isNotEqualTo(productImage2);
        productImage1.setId(null);
        assertThat(productImage1).isNotEqualTo(productImage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductImageDTO.class);
        ProductImageDTO productImageDTO1 = new ProductImageDTO();
        productImageDTO1.setId(1L);
        ProductImageDTO productImageDTO2 = new ProductImageDTO();
        assertThat(productImageDTO1).isNotEqualTo(productImageDTO2);
        productImageDTO2.setId(productImageDTO1.getId());
        assertThat(productImageDTO1).isEqualTo(productImageDTO2);
        productImageDTO2.setId(2L);
        assertThat(productImageDTO1).isNotEqualTo(productImageDTO2);
        productImageDTO1.setId(null);
        assertThat(productImageDTO1).isNotEqualTo(productImageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productImageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productImageMapper.fromId(null)).isNull();
    }
}
