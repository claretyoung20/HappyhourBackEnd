package com.happyhour.myapp.service;

import com.happyhour.myapp.service.dto.TableTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TableType.
 */
public interface TableTypeService {

    /**
     * Save a tableType.
     *
     * @param tableTypeDTO the entity to save
     * @return the persisted entity
     */
    TableTypeDTO save(TableTypeDTO tableTypeDTO);

    /**
     * Get all the tableTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TableTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tableType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TableTypeDTO> findOne(Long id);

    /**
     * Delete the "id" tableType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
