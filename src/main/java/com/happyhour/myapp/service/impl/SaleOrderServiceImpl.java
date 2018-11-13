package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.SaleOrderService;
import com.happyhour.myapp.domain.SaleOrder;
import com.happyhour.myapp.repository.SaleOrderRepository;
import com.happyhour.myapp.service.dto.SaleOrderDTO;
import com.happyhour.myapp.service.mapper.SaleOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SaleOrder.
 */
@Service
@Transactional
public class SaleOrderServiceImpl implements SaleOrderService {

    private final Logger log = LoggerFactory.getLogger(SaleOrderServiceImpl.class);

    private final SaleOrderRepository saleOrderRepository;

    private final SaleOrderMapper saleOrderMapper;

    public SaleOrderServiceImpl(SaleOrderRepository saleOrderRepository, SaleOrderMapper saleOrderMapper) {
        this.saleOrderRepository = saleOrderRepository;
        this.saleOrderMapper = saleOrderMapper;
    }

    /**
     * Save a saleOrder.
     *
     * @param saleOrderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SaleOrderDTO save(SaleOrderDTO saleOrderDTO) {
        log.debug("Request to save SaleOrder : {}", saleOrderDTO);
        SaleOrder saleOrder = saleOrderMapper.toEntity(saleOrderDTO);
        saleOrder = saleOrderRepository.save(saleOrder);
        return saleOrderMapper.toDto(saleOrder);
    }

    /**
     * Get all the saleOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SaleOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SaleOrders");
        return saleOrderRepository.findAll(pageable)
            .map(saleOrderMapper::toDto);
    }


    /**
     * Get one saleOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SaleOrderDTO> findOne(Long id) {
        log.debug("Request to get SaleOrder : {}", id);
        return saleOrderRepository.findById(id)
            .map(saleOrderMapper::toDto);
    }

    /**
     * Delete the saleOrder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SaleOrder : {}", id);
        saleOrderRepository.deleteById(id);
    }
}
