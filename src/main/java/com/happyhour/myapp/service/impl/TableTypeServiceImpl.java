package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.TableTypeService;
import com.happyhour.myapp.domain.TableType;
import com.happyhour.myapp.repository.TableTypeRepository;
import com.happyhour.myapp.service.dto.TableTypeDTO;
import com.happyhour.myapp.service.mapper.TableTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TableType.
 */
@Service
@Transactional
public class TableTypeServiceImpl implements TableTypeService {

    private final Logger log = LoggerFactory.getLogger(TableTypeServiceImpl.class);

    private final TableTypeRepository tableTypeRepository;

    private final TableTypeMapper tableTypeMapper;

    public TableTypeServiceImpl(TableTypeRepository tableTypeRepository, TableTypeMapper tableTypeMapper) {
        this.tableTypeRepository = tableTypeRepository;
        this.tableTypeMapper = tableTypeMapper;
    }

    /**
     * Save a tableType.
     *
     * @param tableTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TableTypeDTO save(TableTypeDTO tableTypeDTO) {
        log.debug("Request to save TableType : {}", tableTypeDTO);
        TableType tableType = tableTypeMapper.toEntity(tableTypeDTO);
        tableType = tableTypeRepository.save(tableType);
        return tableTypeMapper.toDto(tableType);
    }

    /**
     * Get all the tableTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TableTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TableTypes");
        return tableTypeRepository.findAll(pageable)
            .map(tableTypeMapper::toDto);
    }


    /**
     * Get one tableType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TableTypeDTO> findOne(Long id) {
        log.debug("Request to get TableType : {}", id);
        return tableTypeRepository.findById(id)
            .map(tableTypeMapper::toDto);
    }

    /**
     * Delete the tableType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TableType : {}", id);
        tableTypeRepository.deleteById(id);
    }
}
