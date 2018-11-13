package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.DiscountService;
import com.happyhour.myapp.domain.Discount;
import com.happyhour.myapp.repository.DiscountRepository;
import com.happyhour.myapp.service.dto.DiscountDTO;
import com.happyhour.myapp.service.mapper.DiscountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Discount.
 */
@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final Logger log = LoggerFactory.getLogger(DiscountServiceImpl.class);

    private final DiscountRepository discountRepository;

    private final DiscountMapper discountMapper;

    public DiscountServiceImpl(DiscountRepository discountRepository, DiscountMapper discountMapper) {
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
    }

    /**
     * Save a discount.
     *
     * @param discountDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DiscountDTO save(DiscountDTO discountDTO) {
        log.debug("Request to save Discount : {}", discountDTO);
        Discount discount = discountMapper.toEntity(discountDTO);
        discount = discountRepository.save(discount);
        return discountMapper.toDto(discount);
    }

    /**
     * Get all the discounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiscountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Discounts");
        return discountRepository.findAll(pageable)
            .map(discountMapper::toDto);
    }


    /**
     * Get one discount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiscountDTO> findOne(Long id) {
        log.debug("Request to get Discount : {}", id);
        return discountRepository.findById(id)
            .map(discountMapper::toDto);
    }

    /**
     * Delete the discount by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Discount : {}", id);
        discountRepository.deleteById(id);
    }
}
