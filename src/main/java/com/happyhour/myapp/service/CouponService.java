package com.happyhour.myapp.service;

import com.happyhour.myapp.service.dto.CouponDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Coupon.
 */
public interface CouponService {

    /**
     * Save a coupon.
     *
     * @param couponDTO the entity to save
     * @return the persisted entity
     */
    CouponDTO save(CouponDTO couponDTO);

    /**
     * Get all the coupons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CouponDTO> findAll(Pageable pageable);


    /**
     * Get the "id" coupon.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CouponDTO> findOne(Long id);

    /**
     * Delete the "id" coupon.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Page<CouponDTO> findAllValidCoupon(Pageable pageable, LocalDate startDate, LocalDate endDate);

    Page<CouponDTO> isActive(Pageable pageable, LocalDate startDate, LocalDate endDate);
    Page<CouponDTO> history(Pageable pageable, LocalDate startDate, LocalDate endDate);

    List<CouponDTO> cronJobCancel(LocalDate startDate, LocalDate endDate);

}
