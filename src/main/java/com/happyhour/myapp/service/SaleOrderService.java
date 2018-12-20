package com.happyhour.myapp.service;

import com.happyhour.myapp.service.dto.SaleOrderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SaleOrder.
 */
public interface SaleOrderService {

    /**
     * Save a saleOrder.
     *
     * @param saleOrderDTO the entity to save
     * @return the persisted entity
     */
    SaleOrderDTO save(SaleOrderDTO saleOrderDTO);

    /**
     * Get all the saleOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SaleOrderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" saleOrder.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SaleOrderDTO> findOne(Long id);

    /**
     * Delete the "id" saleOrder.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Page<SaleOrderDTO> findAllByOrderId(Long id, Pageable pageable);

    List<SaleOrderDTO> sumAllSales();
    Page<SaleOrderDTO> filterByDateAndPrice(LocalDate localDate, Double minValue, Double maxValue, Pageable pageable);
    Page<SaleOrderDTO> filterBasePrice(Double minValue, Double maxValue, Pageable pageable);
}
