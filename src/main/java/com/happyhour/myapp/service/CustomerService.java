package com.happyhour.myapp.service;

import com.happyhour.myapp.service.dto.CustomerDTO;

import com.happyhour.myapp.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Customer.
 */
public interface CustomerService {

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save
     * @return the persisted entity
     */
    CustomerDTO save(CustomerDTO customerDTO);


    /**
     * Update a customer.
     *
     * @param customerDTO the entity to save
     * @return the persisted entity
     */
    CustomerDTO update(CustomerDTO customerDTO);

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CustomerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" customer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CustomerDTO> findOne(Long id);

    /**
     * Delete the "id" customer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Optional<CustomerDTO> findByUserId(Long id);

    List<UserDTO> findAllCustomerUser(Pageable pageable);
}
