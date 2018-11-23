package com.happyhour.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happyhour.myapp.domain.Cart;
import com.happyhour.myapp.service.CartService;
import com.happyhour.myapp.service.ProductService;
import com.happyhour.myapp.service.dto.ProductDTO;
import com.happyhour.myapp.service.mapper.CartMapper;
import com.happyhour.myapp.web.rest.errors.BadRequestAlertException;
import com.happyhour.myapp.web.rest.util.HeaderUtil;
import com.happyhour.myapp.web.rest.util.PaginationUtil;
import com.happyhour.myapp.service.dto.CartDTO;
import com.sun.deploy.util.ArrayUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ListUtils;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cart.
 */
@RestController
@RequestMapping("/api")
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);

    private static final String ENTITY_NAME = "cart";

    private final CartService cartService;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductService productService;

    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * POST  /carts : Create a new cart.
     *
     * @param cartDTO the cartDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cartDTO, or with status 400 (Bad Request) if the cart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carts")
    @Timed
    public ResponseEntity<CartDTO> createCart(@RequestBody CartDTO cartDTO) throws URISyntaxException {
        log.debug("REST request to save Cart : {}", cartDTO);
        if (cartDTO.getId() != null) {
            throw new BadRequestAlertException("A new cart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cartDTO.setDateCreated(Instant.now());
        cartDTO.setDateUpdated(Instant.now());
        CartDTO result = cartService.save(cartDTO);
        return ResponseEntity.created(new URI("/api/carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carts : Updates an existing cart.
     *
     * @param cartDTO the cartDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cartDTO,
     * or with status 400 (Bad Request) if the cartDTO is not valid,
     * or with status 500 (Internal Server Error) if the cartDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carts")
    @Timed
    public ResponseEntity<CartDTO> updateCart(@RequestBody CartDTO cartDTO) throws URISyntaxException {
        log.debug("REST request to update Cart : {}", cartDTO);
        if (cartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        cartDTO.setDateUpdated(Instant.now());
        CartDTO result = cartService.save(cartDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cartDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carts : get all the carts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of carts in body
     */
    @GetMapping("/carts")
    @Timed
    public ResponseEntity<List<CartDTO>> getAllCarts(Pageable pageable) {
        log.debug("REST request to get a page of Carts");
        Page<CartDTO> page = cartService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /carts/:id : get the "id" cart.
     *
     * @param id the id of the cartDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cartDTO, or with status 404 (Not Found)
     */
    @GetMapping("/carts/{id}")
    @Timed
    public ResponseEntity<CartDTO> getCart(@PathVariable Long id) {
        log.debug("REST request to get Cart : {}", id);
        Optional<CartDTO> cartDTO = cartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cartDTO);
    }

    /**
     * DELETE  /carts/:id : delete the "id" cart.
     *
     * @param id the id of the cartDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        log.debug("REST request to delete Cart : {}", id);
        cartService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/carts/exist")
    @Timed
    public ResponseEntity<CartDTO> getCartByProductIdAbdCustomerId(long productId, long customerId) {
        log.debug("REST request to get Cart by productId and customerId: {}",productId, customerId);
        Optional<CartDTO> cartDTO = cartService.getByProductIdAndCustomerId(productId, customerId);
        return ResponseUtil.wrapOrNotFound(cartDTO);
    }

    @GetMapping("/carts/customer/{id}")
    @Timed
    public ResponseEntity<List<CartDTO>> getCartsByCustomer(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get Cart by customer id: {}", id);
        Page<CartDTO> page = cartService.getAllByCustomerId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/carts/customer/{id}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    // return product
    @GetMapping("/carts/customer/product/{id}")
    @Timed
    public ResponseEntity<List<ProductDTO>> getAllProductFromCart(@PathVariable Long id) {
        log.debug("REST request to get Cart by customer id: {}", id);
        List<CartDTO> cartDTOS = cartService.findAllByCustomerId(id);

        List<ProductDTO> productDTOS = new ArrayList<>();

        for (CartDTO cartDTO: cartDTOS) {
            productDTOS.add(productService.findById(cartDTO.getProductId()));
        }

//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/carts/customer/{id}");
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(productDTOS, headers, HttpStatus.OK);
    }
}
