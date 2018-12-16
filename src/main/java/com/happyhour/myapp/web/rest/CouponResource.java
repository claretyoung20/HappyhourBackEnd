package com.happyhour.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happyhour.myapp.service.CouponService;
import com.happyhour.myapp.service.HappyOrderService;
import com.happyhour.myapp.service.dto.HappyOrderDTO;
import com.happyhour.myapp.web.rest.errors.BadRequestAlertException;
import com.happyhour.myapp.web.rest.util.HeaderUtil;
import com.happyhour.myapp.web.rest.util.PaginationUtil;
import com.happyhour.myapp.service.dto.CouponDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Coupon.
 */
@RestController
@RequestMapping("/api")
public class CouponResource {

    private final Logger log = LoggerFactory.getLogger(CouponResource.class);

    private static final String ENTITY_NAME = "coupon";

    private final CouponService couponService;

    @Autowired
    private HappyOrderService happyOrderService;

    public CouponResource(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     * POST  /coupons : Create a new coupon.
     *
     * @param couponDTO the couponDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new couponDTO, or with status 400 (Bad Request) if the coupon has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coupons")
    @Timed
    public ResponseEntity<CouponDTO> createCoupon(@Valid @RequestBody CouponDTO couponDTO) throws URISyntaxException {
        log.debug("REST request to save Coupon : {}", couponDTO);
        if (couponDTO.getId() != null) {
            throw new BadRequestAlertException("A new coupon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CouponDTO result = couponService.save(couponDTO);
        return ResponseEntity.created(new URI("/api/coupons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coupons : Updates an existing coupon.
     *
     * @param couponDTO the couponDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated couponDTO,
     * or with status 400 (Bad Request) if the couponDTO is not valid,
     * or with status 500 (Internal Server Error) if the couponDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coupons")
    @Timed
    public ResponseEntity<CouponDTO> updateCoupon(@Valid @RequestBody CouponDTO couponDTO) throws URISyntaxException {
        log.debug("REST request to update Coupon : {}", couponDTO);
        if (couponDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CouponDTO result = couponService.save(couponDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, couponDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coupons : get all the coupons.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of coupons in body
     */
    @GetMapping("/coupons")
    @Timed
    public ResponseEntity<List<CouponDTO>> getAllCoupons(Pageable pageable) {
        log.debug("REST request to get a page of Coupons");
        Page<CouponDTO> page = couponService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/coupons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /coupons/:id : get the "id" coupon.
     *
     * @param id the id of the couponDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the couponDTO, or with status 404 (Not Found)
     */
    @GetMapping("/coupons/{id}")
    @Timed
    public ResponseEntity<CouponDTO> getCoupon(@PathVariable Long id) {
        log.debug("REST request to get Coupon : {}", id);
        Optional<CouponDTO> couponDTO = couponService.findOne(id);
        return ResponseUtil.wrapOrNotFound(couponDTO);
    }

    /**
     * DELETE  /coupons/:id : delete the "id" coupon.
     *
     * @param id the id of the couponDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coupons/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        log.debug("REST request to delete Coupon : {}", id);
        couponService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/coupons/valid")
    @Timed
    public ResponseEntity<List<CouponDTO>> getAllCouponsValid(Pageable pageable) {
        log.debug("REST request to get a page of Coupons");
        LocalDate checkDate = LocalDate.now();
        Page<CouponDTO> page = couponService.findAllValidCoupon(pageable, checkDate, checkDate);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/coupons/valid");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/coupons/active")
    @Timed
    public ResponseEntity<List<CouponDTO>> getAllActiveCoupon(Pageable pageable) {
        log.debug("REST request to get a page of Coupons");
        LocalDate checkDate = LocalDate.now();
        Page<CouponDTO> page = couponService.isActive(pageable, checkDate);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/coupons/valid");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/coupons/history")
    @Timed
    public ResponseEntity<List<CouponDTO>> getAllHistoryCoupon(Pageable pageable) {
        log.debug("REST request to get a page of Coupons");
        LocalDate checkDate = LocalDate.now();
        Page<CouponDTO> page = couponService.history(pageable, checkDate, checkDate);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/coupons/valid");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/coupons/applyCoupon")
    @Timed
    public ResponseEntity<CouponDTO> getApplyCoupon(Long id, String code, String amount) {
        log.debug("REST request to get a page of Coupons");

        double amount1 = Double.parseDouble(amount);
        LocalDate checkDate = LocalDate.now();
        CouponDTO couponDTO = couponService.findByCode(code, checkDate);
        if(couponDTO != null) {
            List<HappyOrderDTO> happyOrderDTOS =
                happyOrderService.findByCouponIdAndCustomer(couponDTO.getId(), id);

            if(amount1 < couponDTO.getMaxAmountToApply()) {
                couponDTO = null;
            }

            if(couponDTO != null) {
                if(amount1 < couponDTO.getMaxAmountToApply() &&
                    couponDTO.getNoPerUser() < happyOrderDTOS.size()){

                    couponDTO = null;
                }
            }

        }

        Optional<CouponDTO> couponDTO1 = Optional.ofNullable(couponDTO);
        return ResponseUtil.wrapOrNotFound(couponDTO1);
    }

}
