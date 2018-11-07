package com.happyhour.myapp.service;

import com.happyhour.myapp.service.dto.SocialMediaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SocialMedia.
 */
public interface SocialMediaService {

    /**
     * Save a socialMedia.
     *
     * @param socialMediaDTO the entity to save
     * @return the persisted entity
     */
    SocialMediaDTO save(SocialMediaDTO socialMediaDTO);

    /**
     * Get all the socialMedias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SocialMediaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" socialMedia.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SocialMediaDTO> findOne(Long id);

    /**
     * Delete the "id" socialMedia.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
