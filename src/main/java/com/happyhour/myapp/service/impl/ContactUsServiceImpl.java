package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.ContactUsService;
import com.happyhour.myapp.domain.ContactUs;
import com.happyhour.myapp.repository.ContactUsRepository;
import com.happyhour.myapp.service.dto.ContactUsDTO;
import com.happyhour.myapp.service.mapper.ContactUsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ContactUs.
 */
@Service
@Transactional
public class ContactUsServiceImpl implements ContactUsService {

    private final Logger log = LoggerFactory.getLogger(ContactUsServiceImpl.class);

    private final ContactUsRepository contactUsRepository;

    private final ContactUsMapper contactUsMapper;

    public ContactUsServiceImpl(ContactUsRepository contactUsRepository, ContactUsMapper contactUsMapper) {
        this.contactUsRepository = contactUsRepository;
        this.contactUsMapper = contactUsMapper;
    }

    /**
     * Save a contactUs.
     *
     * @param contactUsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContactUsDTO save(ContactUsDTO contactUsDTO) {
        log.debug("Request to save ContactUs : {}", contactUsDTO);
        ContactUs contactUs = contactUsMapper.toEntity(contactUsDTO);
        contactUs = contactUsRepository.save(contactUs);
        return contactUsMapper.toDto(contactUs);
    }

    /**
     * Get all the contactuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContactUsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contactuses");
        return contactUsRepository.findAll(pageable)
            .map(contactUsMapper::toDto);
    }


    /**
     * Get one contactUs by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContactUsDTO> findOne(Long id) {
        log.debug("Request to get ContactUs : {}", id);
        return contactUsRepository.findById(id)
            .map(contactUsMapper::toDto);
    }

    /**
     * Delete the contactUs by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactUs : {}", id);
        contactUsRepository.deleteById(id);
    }
}
