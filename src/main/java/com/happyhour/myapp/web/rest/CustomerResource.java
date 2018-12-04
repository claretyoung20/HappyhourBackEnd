package com.happyhour.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happyhour.myapp.domain.User;
import com.happyhour.myapp.repository.UserRepository;
import com.happyhour.myapp.security.AuthoritiesConstants;
import com.happyhour.myapp.service.CustomerService;
import com.happyhour.myapp.service.MailService;
import com.happyhour.myapp.service.UserService;
import com.happyhour.myapp.service.dto.UserDTO;
import com.happyhour.myapp.web.rest.errors.BadRequestAlertException;
import com.happyhour.myapp.web.rest.errors.EmailAlreadyUsedException;
import com.happyhour.myapp.web.rest.errors.LoginAlreadyUsedException;
import com.happyhour.myapp.web.rest.util.HeaderUtil;
import com.happyhour.myapp.web.rest.util.PaginationUtil;
import com.happyhour.myapp.service.dto.CustomerDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Customer.
 */
@RestController
@RequestMapping("/api")
public class CustomerResource {

    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    private static final String ENTITY_NAME = "customer";

    private final CustomerService customerService;

    @Autowired
    private  UserService userService;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  MailService mailService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * POST  /customers : Create a new customer.
     *
     * @param customerDTO the customerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerDTO, or with status 400 (Bad Request) if the customer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customers")
    @Timed
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) throws URISyntaxException {
        log.debug("REST request to save Customer : {}", customerDTO);
        if (customerDTO.getId() != null) {
            throw new BadRequestAlertException("A new customer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        customerDTO.setDateCreated(Instant.now());
        customerDTO.setDateUpdated(Instant.now());
        CustomerDTO result = customerService.save(customerDTO);
        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customers : Updates an existing customer.
     *
     * @param customerDTO the customerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerDTO,
     * or with status 400 (Bad Request) if the customerDTO is not valid,
     * or with status 500 (Internal Server Error) if the customerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customers")
    @Timed
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO) throws URISyntaxException {
        log.debug("REST request to update Customer : {}", customerDTO);
        if (customerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        customerDTO.setDateUpdated(Instant.now());
        CustomerDTO result = customerService.update(customerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customers : get all the customers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customers in body
     */
    @GetMapping("/customers")
    @Timed
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(Pageable pageable) {
        log.debug("REST request to get a page of Customers");
        Page<CustomerDTO> page = customerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customers/:id : get the "id" customer.
     *
     * @param id the id of the customerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/customers/{id}")
    @Timed
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {
        log.debug("REST request to get Customer : {}", id);
        Optional<CustomerDTO> customerDTO = customerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerDTO);
    }

    /**
     * DELETE  /customers/:id : delete the "id" customer.
     *
     * @param id the id of the customerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.debug("REST request to delete Customer : {}", id);
        customerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/customers/new")
    @Timed
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User from customer : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }

    @GetMapping("/customers/userId/{id}")
    @Timed
    public ResponseEntity<CustomerDTO> getCustomerByUserId(@PathVariable Long id) {
        log.debug("REST request to get Customer by user id: {}", id);
        Optional<CustomerDTO> customerDTO = customerService.findByUserId(id);
        return ResponseUtil.wrapOrNotFound(customerDTO);
    }

}
