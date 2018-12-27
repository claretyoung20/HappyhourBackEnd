package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.HappyOrderService;
import com.happyhour.myapp.domain.HappyOrder;
import com.happyhour.myapp.repository.HappyOrderRepository;
import com.happyhour.myapp.service.dto.HappyOrderDTO;
import com.happyhour.myapp.service.mapper.HappyOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing HappyOrder.
 */
@Service
@Transactional
public class HappyOrderServiceImpl implements HappyOrderService {

    private final Logger log = LoggerFactory.getLogger(HappyOrderServiceImpl.class);

    private final HappyOrderRepository happyOrderRepository;

    private final HappyOrderMapper happyOrderMapper;

    public HappyOrderServiceImpl(HappyOrderRepository happyOrderRepository, HappyOrderMapper happyOrderMapper) {
        this.happyOrderRepository = happyOrderRepository;
        this.happyOrderMapper = happyOrderMapper;
    }

    /**
     * Save a happyOrder.
     *
     * @param happyOrderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HappyOrderDTO save(HappyOrderDTO happyOrderDTO) {
        log.debug("Request to save HappyOrder : {}", happyOrderDTO);
        HappyOrder happyOrder = happyOrderMapper.toEntity(happyOrderDTO);
        happyOrder = happyOrderRepository.save(happyOrder);
        return happyOrderMapper.toDto(happyOrder);
    }

    /**
     * Get all the happyOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HappyOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HappyOrders");
        return happyOrderRepository.findAll(pageable)
            .map(happyOrderMapper::toDto);
    }


    /**
     * Get one happyOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HappyOrderDTO> findOne(Long id) {
        log.debug("Request to get HappyOrder : {}", id);
        return happyOrderRepository.findById(id)
            .map(happyOrderMapper::toDto);
    }

    /**
     * Delete the happyOrder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HappyOrder : {}", id);
        happyOrderRepository.deleteById(id);
    }

    @Override
    public Page<HappyOrderDTO> findAllByOrderStatusId(long id, LocalDate localDate, Pageable pageable) {
        log.debug("Request to get all HappyOrders by status id");
        return happyOrderRepository.findAllByOrderStatusIdAndDateCreated(id,localDate, pageable)
            .map(happyOrderMapper::toDto);
    }

    @Override
    public Page<HappyOrderDTO> findAllByCustomerIdAAndDateCreatedLessThan(long id, LocalDate localDate, Pageable pageable) {
        log.debug("Request to get all HappyOrders by status id");
        return happyOrderRepository.findAllByCustomerIdAndDateCreatedLessThan(id, localDate, pageable)
            .map(happyOrderMapper::toDto);
    }

    @Override
    public Page<HappyOrderDTO> findAllActiveOrder(long id, LocalDate localDate, Pageable pageable) {
        log.debug("Request to get all HappyOrders by status id");
        return happyOrderRepository.findAllByCustomerIdAndDateCreatedIsGreaterThanEqual(id, localDate, pageable)
            .map(happyOrderMapper::toDto);
    }

    @Override
    public List<HappyOrderDTO> cronJobCacel(LocalDate localDate, Long id) {
        return happyOrderMapper.toDto(happyOrderRepository.findAllByDateCreatedIsLessThanAndOrderStatusId(localDate));
    }

    @Override
    public Page<HappyOrderDTO> todayOrder(LocalDate localDate, Pageable pageable) {
        log.debug("Request to get all HappyOrders");
        return happyOrderRepository.findAllByDateCreated(localDate, pageable)
            .map(happyOrderMapper::toDto);
    }

    @Override
    public Page<HappyOrderDTO> orderHistory(LocalDate localDate, Pageable pageable) {
        log.debug("Request to get all HappyOrders");
        return happyOrderRepository.findAllByDateCreatedIsLessThan(localDate, pageable)
            .map(happyOrderMapper::toDto);
    }

    @Override
    public List<HappyOrderDTO> findAllByCustomerIdAndDateCreated(Long id, LocalDate localDate) {
        return happyOrderMapper.toDto(happyOrderRepository.findAllByCustomerIdAndDateCreated(id, localDate));
    }


    @Override
    public List<HappyOrderDTO> findByCouponIdAndCustomer(Long id, Long customerId) {
        return happyOrderMapper.toDto(
            happyOrderRepository.findAllByCouponIdAndCustomerId(id, customerId));
    }

    @Override
    public Integer sumCompleteOrder() {
        return happyOrderRepository.sumCompleteOrder();
    }

    @Override
    public Integer totalCompleteOrder() {
        return happyOrderRepository.totalCompleteOrder();
    }

    @Override
    public Integer sumCancelOrder() {
        return happyOrderRepository.sumCancelOrder();
    }

    @Override
    public Integer totalCancelOrder() {
        return happyOrderRepository.totalCancelOrder();
    }

    @Override
    public Page<HappyOrderDTO> orderReport(Long id, Long idd, Pageable pageable) {
        return happyOrderRepository.findAllByOrderStatusIdOrOrderStatusId(id, idd, pageable)
            .map(happyOrderMapper::toDto);
    }
}
