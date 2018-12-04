package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.HappyOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


/**
 * Spring Data  repository for the HappyOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HappyOrderRepository extends JpaRepository<HappyOrder, Long> {

    Page<HappyOrder> findAllByOrderStatusId(long id, Pageable pageable);
    Page<HappyOrder> findAllByCustomerIdAndDateCreatedLessThan(long id, LocalDate localDate, Pageable pageable);
    Page<HappyOrder> findAllByCustomerIdAndDateCreatedIsGreaterThanEqual(long id, LocalDate localDate, Pageable pageable);
}
