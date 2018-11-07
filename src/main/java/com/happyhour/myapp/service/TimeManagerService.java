package com.happyhour.myapp.service;

import com.happyhour.myapp.service.dto.TimeManagerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TimeManager.
 */
public interface TimeManagerService {

    /**
     * Save a timeManager.
     *
     * @param timeManagerDTO the entity to save
     * @return the persisted entity
     */
    TimeManagerDTO save(TimeManagerDTO timeManagerDTO);

    /**
     * Get all the timeManagers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TimeManagerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" timeManager.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TimeManagerDTO> findOne(Long id);

    /**
     * Delete the "id" timeManager.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
