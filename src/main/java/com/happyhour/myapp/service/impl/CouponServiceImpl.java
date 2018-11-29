package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.CouponService;
import com.happyhour.myapp.domain.Coupon;
import com.happyhour.myapp.repository.CouponRepository;
import com.happyhour.myapp.service.dto.CouponDTO;
import com.happyhour.myapp.service.mapper.CouponMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Coupon.
 */
@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    private final Logger log = LoggerFactory.getLogger(CouponServiceImpl.class);

    private final CouponRepository couponRepository;

    private final CouponMapper couponMapper;

    public CouponServiceImpl(CouponRepository couponRepository, CouponMapper couponMapper) {
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
    }

    /**
     * Save a coupon.
     *
     * @param couponDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CouponDTO save(CouponDTO couponDTO) {
        log.debug("Request to save Coupon : {}", couponDTO);
        Coupon coupon = couponMapper.toEntity(couponDTO);
        coupon = couponRepository.save(coupon);
        return couponMapper.toDto(coupon);
    }

    /**
     * Get all the coupons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CouponDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Coupons");
        return couponRepository.findAll(pageable)
            .map(couponMapper::toDto);
    }


    /**
     * Get one coupon by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CouponDTO> findOne(Long id) {
        log.debug("Request to get Coupon : {}", id);
        return couponRepository.findById(id)
            .map(couponMapper::toDto);
    }

    /**
     * Delete the coupon by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coupon : {}", id);
        couponRepository.deleteById(id);
    }
}
