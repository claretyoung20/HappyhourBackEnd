package com.happyhour.myapp.service;

import com.happyhour.myapp.service.dto.BookTableDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing BookTable.
 */
public interface BookTableService {

    /**
     * Save a bookTable.
     *
     * @param bookTableDTO the entity to save
     * @return the persisted entity
     */
    BookTableDTO save(BookTableDTO bookTableDTO);

    /**
     * Get all the bookTables.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BookTableDTO> findAll(Pageable pageable);


    /**
     * Get the "id" bookTable.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BookTableDTO> findOne(Long id);

    /**
     * Delete the "id" bookTable.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
