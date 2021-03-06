package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.CustomerService;
import com.happyhour.myapp.domain.Customer;
import com.happyhour.myapp.repository.CustomerRepository;
import com.happyhour.myapp.service.UserService;
import com.happyhour.myapp.service.dto.CustomerDTO;
import com.happyhour.myapp.service.dto.UserDTO;
import com.happyhour.myapp.service.mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Customer.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Autowired
    private UserService userService;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        log.debug("Request to save Customer : {}", customerDTO);
        Customer customer = customerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        log.debug("Request to save Customer : {}", customerDTO);
        Customer customer = customerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customerRepository.findAll(pageable)
            .map(customerMapper::toDto);
    }


    /**
     * Get one customer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerDTO> findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id)
            .map(customerMapper::toDto);
    }

    /**
     * Delete the customer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }

    @Override
    public Optional<CustomerDTO> findByUserId(Long id) {
        log.debug("Request to get Customer by user id : {}", id);
        return customerRepository.findByUserId(id)
            .map(customerMapper::toDto);
    }

    @Override
    public List<UserDTO> findAllCustomerUser(Pageable pageable) {
        log.debug("Request to get all Staff");


        List<CustomerDTO> customerDTOS = customerMapper.toDto(customerRepository.findAll());
        List<UserDTO> userList = new ArrayList<>();


        for ( CustomerDTO customerDTO: customerDTOS) {
            Optional<UserDTO> user = userService.findById(customerDTO.getUserId());
            user.ifPresent(userList::add);
        }
        return userList;
    }
}
