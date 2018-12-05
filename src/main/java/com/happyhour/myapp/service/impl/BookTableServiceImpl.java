package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.BookTableService;
import com.happyhour.myapp.domain.BookTable;
import com.happyhour.myapp.repository.BookTableRepository;
import com.happyhour.myapp.service.dto.BookTableDTO;
import com.happyhour.myapp.service.mapper.BookTableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing BookTable.
 */
@Service
@Transactional
public class BookTableServiceImpl implements BookTableService {

    private final Logger log = LoggerFactory.getLogger(BookTableServiceImpl.class);

    private final BookTableRepository bookTableRepository;

    private final BookTableMapper bookTableMapper;

    public BookTableServiceImpl(BookTableRepository bookTableRepository, BookTableMapper bookTableMapper) {
        this.bookTableRepository = bookTableRepository;
        this.bookTableMapper = bookTableMapper;
    }

    /**
     * Save a bookTable.
     *
     * @param bookTableDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookTableDTO save(BookTableDTO bookTableDTO) {
        log.debug("Request to save BookTable : {}", bookTableDTO);
        BookTable bookTable = bookTableMapper.toEntity(bookTableDTO);
        bookTable = bookTableRepository.save(bookTable);
        return bookTableMapper.toDto(bookTable);
    }

    /**
     * Get all the bookTables.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookTableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BookTables");
        return bookTableRepository.findAll(pageable)
            .map(bookTableMapper::toDto);
    }


    /**
     * Get one bookTable by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BookTableDTO> findOne(Long id) {
        log.debug("Request to get BookTable : {}", id);
        return bookTableRepository.findById(id)
            .map(bookTableMapper::toDto);
    }

    /**
     * Delete the bookTable by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookTable : {}", id);
        bookTableRepository.deleteById(id);
    }

    @Override
    public BookTableDTO findByIdAndPersons(Long id, Integer persons) {
        return bookTableMapper.toDto(bookTableRepository.findByIdAndPersons(id, persons));
    }

    @Override
    public Page<BookTableDTO> findAllByIdIsNotAndPersons(Long id, Integer persons, Pageable pageable) {
        log.debug("Request to get all BookTables");
        return bookTableRepository.findAllByIdIsNotAndPersons(id, persons, pageable)
            .map(bookTableMapper::toDto);
    }

    @Override
    public Page<BookTableDTO> findAllByPersons(Integer persons, Pageable pageable) {
        log.debug("Request to get all BookTables");
        return bookTableRepository.findAllByPersons(persons, pageable)
            .map(bookTableMapper::toDto);
    }
}
