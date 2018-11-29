package com.happyhour.myapp.service;

import com.happyhour.myapp.service.dto.ProductImageDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ProductImage.
 */
public interface ProductImageService {

    /**
     * Save a productImage.
     *
     * @param productImageDTO the entity to save
     * @return the persisted entity
     */
    ProductImageDTO save(ProductImageDTO productImageDTO);

    /**
     * Get all the productImages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductImageDTO> findAll(Pageable pageable);


    /**
     * Get the "id" productImage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductImageDTO> findOne(Long id);

    /**
     * Delete the "id" productImage.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
