package com.happyhour.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happyhour.myapp.service.SaleOrderService;
import com.happyhour.myapp.web.rest.errors.BadRequestAlertException;
import com.happyhour.myapp.web.rest.util.HeaderUtil;
import com.happyhour.myapp.web.rest.util.PaginationUtil;
import com.happyhour.myapp.service.dto.SaleOrderDTO;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SaleOrder.
 */
@RestController
@RequestMapping("/api")
public class SaleOrderResource {

    private final Logger log = LoggerFactory.getLogger(SaleOrderResource.class);

    private static final String ENTITY_NAME = "saleOrder";

    private final SaleOrderService saleOrderService;

    public SaleOrderResource(SaleOrderService saleOrderService) {
        this.saleOrderService = saleOrderService;
    }

    /**
     * POST  /sale-orders : Create a new saleOrder.
     *
     * @param saleOrderDTO the saleOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new saleOrderDTO, or with status 400 (Bad Request) if the saleOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sale-orders")
    @Timed
    public ResponseEntity<SaleOrderDTO> createSaleOrder(@RequestBody SaleOrderDTO saleOrderDTO) throws URISyntaxException {
        log.debug("REST request to save SaleOrder : {}", saleOrderDTO);
        if (saleOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new saleOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        saleOrderDTO.setDateUpdated(LocalDate.now());
        saleOrderDTO.setDateCreated(LocalDate.now());
        SaleOrderDTO result = saleOrderService.save(saleOrderDTO);
        return ResponseEntity.created(new URI("/api/sale-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sale-orders : Updates an existing saleOrder.
     *
     * @param saleOrderDTO the saleOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated saleOrderDTO,
     * or with status 400 (Bad Request) if the saleOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the saleOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sale-orders")
    @Timed
    public ResponseEntity<SaleOrderDTO> updateSaleOrder(@RequestBody SaleOrderDTO saleOrderDTO) throws URISyntaxException {
        log.debug("REST request to update SaleOrder : {}", saleOrderDTO);
        if (saleOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        saleOrderDTO.setDateUpdated(LocalDate.now());
        SaleOrderDTO result = saleOrderService.save(saleOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, saleOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sale-orders : get all the saleOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of saleOrders in body
     */
    @GetMapping("/sale-orders")
    @Timed
    public ResponseEntity<Page<SaleOrderDTO>> getAllSaleOrders(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SaleOrders");
        Page<SaleOrderDTO> page = saleOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sale-orders");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    /**
     * GET  /sale-orders/:id : get the "id" saleOrder.
     *
     * @param id the id of the saleOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the saleOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sale-orders/{id}")
    @Timed
    public ResponseEntity<SaleOrderDTO> getSaleOrder(@PathVariable Long id) {
        log.debug("REST request to get SaleOrder : {}", id);
        Optional<SaleOrderDTO> saleOrderDTO = saleOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saleOrderDTO);
    }

    /**
     * DELETE  /sale-orders/:id : delete the "id" saleOrder.
     *
     * @param id the id of the saleOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sale-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteSaleOrder(@PathVariable Long id) {
        log.debug("REST request to delete SaleOrder : {}", id);
        saleOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/sale-orders/order/{id}")
    @Timed
    public ResponseEntity<List<SaleOrderDTO>> getAllSaleOrdersByOrderId(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of SaleOrders");
        Page<SaleOrderDTO> page = saleOrderService.findAllByOrderId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sale-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/sale-orders/charts")
    @Timed
    public ResponseEntity<List<SaleOrderDTO>> getAllSaleOrdersByOrderId() {
        log.debug("REST request to get a page of SaleOrders");
        List<SaleOrderDTO> page = saleOrderService.sumAllSales();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/sale-orders/filter-one")
    @Timed
    public ResponseEntity<Page<SaleOrderDTO>> getAllByDateAndPrice(String checkDate, String minValue, String maxValue, Pageable pageable) {
        log.debug("REST request to get a page of sale-orders");
        double iMax = Double.parseDouble(maxValue);
        double iMin = Double.parseDouble(minValue);
        LocalDate iCheckDate = LocalDate.parse(checkDate);
        Page<SaleOrderDTO> page = saleOrderService.filterByDateAndPrice(iCheckDate, iMin, iMax, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sale-orders");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/sale-orders/filter-two")
    @Timed
    public ResponseEntity<Page<SaleOrderDTO>> getAllPrice(String minValue, String maxValue, Pageable pageable) {
        log.debug("REST request to get a page of sale-orders");
        double iMax = Double.parseDouble(maxValue);
        double iMin = Double.parseDouble(minValue);
        Page<SaleOrderDTO> page = saleOrderService.filterBasePrice(iMin,iMax, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sale-orders");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }
}
