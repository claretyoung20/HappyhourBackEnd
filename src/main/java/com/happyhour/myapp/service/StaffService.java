package com.happyhour.myapp.service;

import com.happyhour.myapp.service.dto.StaffDTO;

import com.happyhour.myapp.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Staff.
 */
public interface StaffService {

    /**
     * Save a staff.
     *
     * @param staffDTO the entity to save
     * @return the persisted entity
     */
    StaffDTO save(StaffDTO staffDTO);

    /**
     * Get all the staff.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StaffDTO> findAll(Pageable pageable);

    Page<UserDTO> findAllStaffUser(Pageable pageable);

    /**
     * Get the "id" staff.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StaffDTO> findOne(Long id);

    Optional<StaffDTO> findByUserId(Long id);

    /**
     * Delete the "id" staff.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    void deleteByUserId(Long id);
}
