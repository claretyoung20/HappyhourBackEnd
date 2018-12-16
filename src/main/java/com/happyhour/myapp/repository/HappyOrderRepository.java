package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.HappyOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the HappyOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HappyOrderRepository extends JpaRepository<HappyOrder, Long> {

    Page<HappyOrder> findAllByOrderStatusIdAndDateCreated(long id,LocalDate localDate, Pageable pageable);
    Page<HappyOrder> findAllByCustomerIdAndDateCreatedLessThan(long id, LocalDate localDate, Pageable pageable);
    Page<HappyOrder> findAllByCustomerIdAndDateCreatedIsGreaterThanEqual(long id, LocalDate localDate, Pageable pageable);

    @Query (value = "select * from happy_order o where o.date_created<:checkDate and (o.order_status_id = 1 or o.order_status_id = 2) ",
    nativeQuery = true)
    List<HappyOrder> findAllByDateCreatedIsLessThanAndOrderStatusId(@Param("checkDate") LocalDate checkDate);

    Page<HappyOrder> findAllByDateCreated(LocalDate localDate, Pageable pageable);
    Page<HappyOrder> findAllByDateCreatedIsLessThan(LocalDate localDate, Pageable pageable);

    List<HappyOrder> findAllByCustomerIdAndDateCreated(Long id, LocalDate localDate);

    List<HappyOrder> findAllByCouponIdAndCustomerId(Long id, Long customerId);
}
