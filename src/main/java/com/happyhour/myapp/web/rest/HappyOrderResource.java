package com.happyhour.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happyhour.myapp.service.HappyOrderService;
import com.happyhour.myapp.service.dto.SaleOrderDTO;
import com.happyhour.myapp.web.rest.errors.BadRequestAlertException;
import com.happyhour.myapp.web.rest.util.HeaderUtil;
import com.happyhour.myapp.web.rest.util.PaginationUtil;
import com.happyhour.myapp.service.dto.HappyOrderDTO;
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

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing HappyOrder.
 */
@RestController
@RequestMapping("/api")
public class HappyOrderResource {

    private final Logger log = LoggerFactory.getLogger(HappyOrderResource.class);

    private static final String ENTITY_NAME = "happyOrder";

    private final HappyOrderService happyOrderService;

    public HappyOrderResource(HappyOrderService happyOrderService) {
        this.happyOrderService = happyOrderService;
    }

    /**
     * POST  /happy-orders : Create a new happyOrder.
     *
     * @param happyOrderDTO the happyOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new happyOrderDTO, or with status 400 (Bad Request) if the happyOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/happy-orders")
    @Timed
    public ResponseEntity<HappyOrderDTO> createHappyOrder(@RequestBody HappyOrderDTO happyOrderDTO) throws URISyntaxException {
        log.debug("REST request to save HappyOrder : {}", happyOrderDTO);
        if (happyOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new happyOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        happyOrderDTO.setDateUpdated( LocalDate.now());
        happyOrderDTO.setDateCreated( LocalDate.now());
        HappyOrderDTO result = happyOrderService.save(happyOrderDTO);
        return ResponseEntity.created(new URI("/api/happy-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /happy-orders : Updates an existing happyOrder.
     *
     * @param happyOrderDTO the happyOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated happyOrderDTO,
     * or with status 400 (Bad Request) if the happyOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the happyOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/happy-orders")
    @Timed
    public ResponseEntity<HappyOrderDTO> updateHappyOrder(@RequestBody HappyOrderDTO happyOrderDTO) throws URISyntaxException {
        log.debug("REST request to update HappyOrder : {}", happyOrderDTO);
        if (happyOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        happyOrderDTO.setDateUpdated( LocalDate.now());
        HappyOrderDTO result = happyOrderService.save(happyOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, happyOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /happy-orders : get all the happyOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of happyOrders in body
     */
    @GetMapping("/happy-orders")
    @Timed
    public ResponseEntity<List<HappyOrderDTO>> getAllHappyOrders(Pageable pageable) {
        log.debug("REST request to get a page of HappyOrders");
        Page<HappyOrderDTO> page = happyOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/happy-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /happy-orders/:id : get the "id" happyOrder.
     *
     * @param id the id of the happyOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the happyOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/happy-orders/{id}")
    @Timed
    public ResponseEntity<HappyOrderDTO> getHappyOrder(@PathVariable Long id) {
        log.debug("REST request to get HappyOrder : {}", id);
        Optional<HappyOrderDTO> happyOrderDTO = happyOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(happyOrderDTO);
    }

    /**
     * DELETE  /happy-orders/:id : delete the "id" happyOrder.
     *
     * @param id the id of the happyOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/happy-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteHappyOrder(@PathVariable Long id) {
        log.debug("REST request to delete HappyOrder : {}", id);
        happyOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/happy-orders/status/{id}")
    @Timed
    public ResponseEntity<Page<HappyOrderDTO>> getAllHappyOrdersByStatusId(@PathVariable Long id,@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of HappyOrders by status id");
        LocalDate now = LocalDate.now();
        Page<HappyOrderDTO> page = happyOrderService.findAllByOrderStatusId(id, now, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/happy-orders");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/happy-orders/history/{id}")
    @Timed
    public ResponseEntity<List<HappyOrderDTO>> getAllHappyOrdersHistory(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of HappyOrders by customer id and date");
        LocalDate checkDate = LocalDate.now();
        Page<HappyOrderDTO> page = happyOrderService.findAllByCustomerIdAAndDateCreatedLessThan(id,checkDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/happy-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/happy-orders/active/{id}")
    @Timed
    public ResponseEntity<List<HappyOrderDTO>> getAllActveOrder(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of HappyOrders by customer id and date");
        LocalDate checkDate = LocalDate.now();
        Page<HappyOrderDTO> page = happyOrderService.findAllActiveOrder(id,checkDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/happy-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/happy-orders/todayOrder")
    @Timed
    public ResponseEntity<Page<HappyOrderDTO>> getAllActiveHappyOrders(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of HappyOrders");
        LocalDate checkDate = LocalDate.now();
        Page<HappyOrderDTO> page = happyOrderService.todayOrder(checkDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/happy-orders");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/happy-orders/currentHistory")
    @Timed
    public ResponseEntity<Page<HappyOrderDTO>> getAllHistoryHappyOrders(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of HappyOrders");
        LocalDate checkDate = LocalDate.now();
        Page<HappyOrderDTO> page = happyOrderService.orderHistory(checkDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/happy-orders");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/happy-orders/currentCustomerOrders/{id}")
    @Timed
    public ResponseEntity<List<HappyOrderDTO>> getAllCurrentCustomerActiveOrder(@PathVariable Long id) {
        log.debug("REST request to get a page of HappyOrders by customer id and date");
        LocalDate checkDate = LocalDate.now();
        List<HappyOrderDTO> happyOrderDTOS = happyOrderService.findAllByCustomerIdAndDateCreated(id,checkDate);

        if(!happyOrderDTOS.isEmpty()) {
            for (HappyOrderDTO happyOrderDTO: happyOrderDTOS) {
                if(happyOrderDTO.getOrderStatusId() == 4 ||
                    happyOrderDTO.getOrderStatusId() == 5)
                {
                    happyOrderDTOS.remove(happyOrderDTO);
                }
            }
        }

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(happyOrderDTOS, headers, HttpStatus.OK);
    }

    @GetMapping("/happy-orders/sumCancel")
    @Timed
    public ResponseEntity<Integer> sumCancelOrder() {
       Integer sunCancel = happyOrderService.sumCancelOrder();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(sunCancel, headers, HttpStatus.OK);
    }

    @GetMapping("/happy-orders/countCancel")
    @Timed
    public ResponseEntity<Integer> totalCancelOrder() {
        Integer sunCancel = happyOrderService.totalCancelOrder();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(sunCancel, headers, HttpStatus.OK);
    }

    @GetMapping("/happy-orders/sumComplete")
    @Timed
    public ResponseEntity<Integer> sumCompleteOrder() {
        Integer sunCancel = happyOrderService.sumCompleteOrder();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(sunCancel, headers, HttpStatus.OK);
    }

    @GetMapping("/happy-orders/countComplete")
    @Timed
    public ResponseEntity<Integer> totalCompleteOrder() {
        Integer sunCancel = happyOrderService.totalCompleteOrder();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(sunCancel, headers, HttpStatus.OK);
    }

    @GetMapping("/happy-orders/order-report")
    @Timed
    public ResponseEntity<Page<HappyOrderDTO>> getOrderReport(@ApiParam Pageable pageable) {
        Long id = Long.parseLong("4");
        Long idd = Long.parseLong("5");
        Page<HappyOrderDTO> page = happyOrderService.orderReport(id, idd, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/happy-orders");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }
}
