package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the Coupon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

//    @Query (value = "select * from coupon c where c.start_from_date<=:checkDate and c.end_date >=:checkDate",
//    nativeQuery = true)
    Page<Coupon> findAllByIsActiveTrueAndStartFromDateIsLessThanEqualAndEndDateIsGreaterThanEqual(Pageable pageable, LocalDate startDate, LocalDate endDate);

    Page<Coupon> findAllByStartFromDateGreaterThanEqual(Pageable pageable, LocalDate startDate);

    Page<Coupon> findAllByStartFromDateIsLessThanAndEndDateIsLessThan(Pageable pageable, LocalDate startDate, LocalDate endDate);

    List<Coupon> findAllByIsActiveTrueAndStartFromDateIsLessThanAndEndDateIsLessThan(LocalDate startDate, LocalDate endDate);
}
