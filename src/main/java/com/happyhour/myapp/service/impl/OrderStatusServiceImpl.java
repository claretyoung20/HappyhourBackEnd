package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.OrderStatusService;
import com.happyhour.myapp.domain.OrderStatus;
import com.happyhour.myapp.repository.OrderStatusRepository;
import com.happyhour.myapp.service.dto.OrderStatusDTO;
import com.happyhour.myapp.service.mapper.OrderStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing OrderStatus.
 */
@Service
@Transactional
public class OrderStatusServiceImpl implements OrderStatusService {

    private final Logger log = LoggerFactory.getLogger(OrderStatusServiceImpl.class);

    private final OrderStatusRepository orderStatusRepository;

    private final OrderStatusMapper orderStatusMapper;

    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository, OrderStatusMapper orderStatusMapper) {
        this.orderStatusRepository = orderStatusRepository;
        this.orderStatusMapper = orderStatusMapper;
    }

    /**
     * Save a orderStatus.
     *
     * @param orderStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderStatusDTO save(OrderStatusDTO orderStatusDTO) {
        log.debug("Request to save OrderStatus : {}", orderStatusDTO);
        OrderStatus orderStatus = orderStatusMapper.toEntity(orderStatusDTO);
        orderStatus = orderStatusRepository.save(orderStatus);
        return orderStatusMapper.toDto(orderStatus);
    }

    /**
     * Get all the orderStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderStatuses");
        return orderStatusRepository.findAll(pageable)
            .map(orderStatusMapper::toDto);
    }


    /**
     * Get one orderStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderStatusDTO> findOne(Long id) {
        log.debug("Request to get OrderStatus : {}", id);
        return orderStatusRepository.findById(id)
            .map(orderStatusMapper::toDto);
    }

    /**
     * Delete the orderStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderStatus : {}", id);
        orderStatusRepository.deleteById(id);
    }
}
