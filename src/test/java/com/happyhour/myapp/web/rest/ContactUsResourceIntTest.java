package com.happyhour.myapp.web.rest;

import com.happyhour.myapp.HappybourBackEndApp;

import com.happyhour.myapp.domain.ContactUs;
import com.happyhour.myapp.repository.ContactUsRepository;
import com.happyhour.myapp.service.ContactUsService;
import com.happyhour.myapp.service.dto.ContactUsDTO;
import com.happyhour.myapp.service.mapper.ContactUsMapper;
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
 * Test class for the ContactUsResource REST controller.
 *
 * @see ContactUsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HappybourBackEndApp.class)
public class ContactUsResourceIntTest {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private ContactUsRepository contactUsRepository;

    @Autowired
    private ContactUsMapper contactUsMapper;
    
    @Autowired
    private ContactUsService contactUsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContactUsMockMvc;

    private ContactUs contactUs;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactUsResource contactUsResource = new ContactUsResource(contactUsService);
        this.restContactUsMockMvc = MockMvcBuilders.standaloneSetup(contactUsResource)
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
    public static ContactUs createEntity(EntityManager em) {
        ContactUs contactUs = new ContactUs()
            .fullName(DEFAULT_FULL_NAME)
            .dateCreated(DEFAULT_DATE_CREATED)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .comment(DEFAULT_COMMENT);
        return contactUs;
    }

    @Before
    public void initTest() {
        contactUs = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactUs() throws Exception {
        int databaseSizeBeforeCreate = contactUsRepository.findAll().size();

        // Create the ContactUs
        ContactUsDTO contactUsDTO = contactUsMapper.toDto(contactUs);
        restContactUsMockMvc.perform(post("/api/contactuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactUsDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactUs in the database
        List<ContactUs> contactUsList = contactUsRepository.findAll();
        assertThat(contactUsList).hasSize(databaseSizeBeforeCreate + 1);
        ContactUs testContactUs = contactUsList.get(contactUsList.size() - 1);
        assertThat(testContactUs.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testContactUs.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testContactUs.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContactUs.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testContactUs.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createContactUsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactUsRepository.findAll().size();

        // Create the ContactUs with an existing ID
        contactUs.setId(1L);
        ContactUsDTO contactUsDTO = contactUsMapper.toDto(contactUs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactUsMockMvc.perform(post("/api/contactuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactUsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactUs in the database
        List<ContactUs> contactUsList = contactUsRepository.findAll();
        assertThat(contactUsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactUsRepository.findAll().size();
        // set the field null
        contactUs.setFullName(null);

        // Create the ContactUs, which fails.
        ContactUsDTO contactUsDTO = contactUsMapper.toDto(contactUs);

        restContactUsMockMvc.perform(post("/api/contactuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactUsDTO)))
            .andExpect(status().isBadRequest());

        List<ContactUs> contactUsList = contactUsRepository.findAll();
        assertThat(contactUsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactuses() throws Exception {
        // Initialize the database
        contactUsRepository.saveAndFlush(contactUs);

        // Get all the contactUsList
        restContactUsMockMvc.perform(get("/api/contactuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactUs.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getContactUs() throws Exception {
        // Initialize the database
        contactUsRepository.saveAndFlush(contactUs);

        // Get the contactUs
        restContactUsMockMvc.perform(get("/api/contactuses/{id}", contactUs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactUs.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContactUs() throws Exception {
        // Get the contactUs
        restContactUsMockMvc.perform(get("/api/contactuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactUs() throws Exception {
        // Initialize the database
        contactUsRepository.saveAndFlush(contactUs);

        int databaseSizeBeforeUpdate = contactUsRepository.findAll().size();

        // Update the contactUs
        ContactUs updatedContactUs = contactUsRepository.findById(contactUs.getId()).get();
        // Disconnect from session so that the updates on updatedContactUs are not directly saved in db
        em.detach(updatedContactUs);
        updatedContactUs
            .fullName(UPDATED_FULL_NAME)
            .dateCreated(UPDATED_DATE_CREATED)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .comment(UPDATED_COMMENT);
        ContactUsDTO contactUsDTO = contactUsMapper.toDto(updatedContactUs);

        restContactUsMockMvc.perform(put("/api/contactuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactUsDTO)))
            .andExpect(status().isOk());

        // Validate the ContactUs in the database
        List<ContactUs> contactUsList = contactUsRepository.findAll();
        assertThat(contactUsList).hasSize(databaseSizeBeforeUpdate);
        ContactUs testContactUs = contactUsList.get(contactUsList.size() - 1);
        assertThat(testContactUs.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testContactUs.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testContactUs.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactUs.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testContactUs.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingContactUs() throws Exception {
        int databaseSizeBeforeUpdate = contactUsRepository.findAll().size();

        // Create the ContactUs
        ContactUsDTO contactUsDTO = contactUsMapper.toDto(contactUs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactUsMockMvc.perform(put("/api/contactuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactUsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactUs in the database
        List<ContactUs> contactUsList = contactUsRepository.findAll();
        assertThat(contactUsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactUs() throws Exception {
        // Initialize the database
        contactUsRepository.saveAndFlush(contactUs);

        int databaseSizeBeforeDelete = contactUsRepository.findAll().size();

        // Get the contactUs
        restContactUsMockMvc.perform(delete("/api/contactuses/{id}", contactUs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactUs> contactUsList = contactUsRepository.findAll();
        assertThat(contactUsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactUs.class);
        ContactUs contactUs1 = new ContactUs();
        contactUs1.setId(1L);
        ContactUs contactUs2 = new ContactUs();
        contactUs2.setId(contactUs1.getId());
        assertThat(contactUs1).isEqualTo(contactUs2);
        contactUs2.setId(2L);
        assertThat(contactUs1).isNotEqualTo(contactUs2);
        contactUs1.setId(null);
        assertThat(contactUs1).isNotEqualTo(contactUs2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactUsDTO.class);
        ContactUsDTO contactUsDTO1 = new ContactUsDTO();
        contactUsDTO1.setId(1L);
        ContactUsDTO contactUsDTO2 = new ContactUsDTO();
        assertThat(contactUsDTO1).isNotEqualTo(contactUsDTO2);
        contactUsDTO2.setId(contactUsDTO1.getId());
        assertThat(contactUsDTO1).isEqualTo(contactUsDTO2);
        contactUsDTO2.setId(2L);
        assertThat(contactUsDTO1).isNotEqualTo(contactUsDTO2);
        contactUsDTO1.setId(null);
        assertThat(contactUsDTO1).isNotEqualTo(contactUsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contactUsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contactUsMapper.fromId(null)).isNull();
    }
}
