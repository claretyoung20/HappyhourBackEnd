package com.happyhour.myapp.service;

import com.happyhour.myapp.service.dto.ContactUsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ContactUs.
 */
public interface ContactUsService {

    /**
     * Save a contactUs.
     *
     * @param contactUsDTO the entity to save
     * @return the persisted entity
     */
    ContactUsDTO save(ContactUsDTO contactUsDTO);

    /**
     * Get all the contactuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ContactUsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" contactUs.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ContactUsDTO> findOne(Long id);

    /**
     * Delete the "id" contactUs.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
