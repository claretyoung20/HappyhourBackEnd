package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.TimeManagerService;
import com.happyhour.myapp.domain.TimeManager;
import com.happyhour.myapp.repository.TimeManagerRepository;
import com.happyhour.myapp.service.dto.TimeManagerDTO;
import com.happyhour.myapp.service.mapper.TimeManagerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TimeManager.
 */
@Service
@Transactional
public class TimeManagerServiceImpl implements TimeManagerService {

    private final Logger log = LoggerFactory.getLogger(TimeManagerServiceImpl.class);

    private final TimeManagerRepository timeManagerRepository;

    private final TimeManagerMapper timeManagerMapper;

    public TimeManagerServiceImpl(TimeManagerRepository timeManagerRepository, TimeManagerMapper timeManagerMapper) {
        this.timeManagerRepository = timeManagerRepository;
        this.timeManagerMapper = timeManagerMapper;
    }

    /**
     * Save a timeManager.
     *
     * @param timeManagerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TimeManagerDTO save(TimeManagerDTO timeManagerDTO) {
        log.debug("Request to save TimeManager : {}", timeManagerDTO);
        TimeManager timeManager = timeManagerMapper.toEntity(timeManagerDTO);
        timeManager = timeManagerRepository.save(timeManager);
        return timeManagerMapper.toDto(timeManager);
    }

    /**
     * Get all the timeManagers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TimeManagerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TimeManagers");
        return timeManagerRepository.findAll(pageable)
            .map(timeManagerMapper::toDto);
    }


    /**
     * Get one timeManager by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TimeManagerDTO> findOne(Long id) {
        log.debug("Request to get TimeManager : {}", id);
        return timeManagerRepository.findById(id)
            .map(timeManagerMapper::toDto);
    }

    /**
     * Delete the timeManager by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimeManager : {}", id);
        timeManagerRepository.deleteById(id);
    }
}
