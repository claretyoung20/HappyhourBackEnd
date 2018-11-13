package com.happyhour.myapp.service;

import com.happyhour.myapp.service.dto.HappyOrderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing HappyOrder.
 */
public interface HappyOrderService {

    /**
     * Save a happyOrder.
     *
     * @param happyOrderDTO the entity to save
     * @return the persisted entity
     */
    HappyOrderDTO save(HappyOrderDTO happyOrderDTO);

    /**
     * Get all the happyOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HappyOrderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" happyOrder.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HappyOrderDTO> findOne(Long id);

    /**
     * Delete the "id" happyOrder.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
