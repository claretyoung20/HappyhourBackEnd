package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.SaleOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the SaleOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long> {

    Page<SaleOrder> findAllByHappyOrderId(long id, Pageable pageable);

    @Query(value = "SELECT id, date_created, date_updated, product_id, happy_order_id, SUM(base_price) base_price FROM sale_order s GROUP BY s.product_id",
    nativeQuery = true)
    List<SaleOrder> sumAllSale();

    Page<SaleOrder>
    findAllByDateCreatedAndBasePriceGreaterThanEqualAndBasePriceLessThanEqual(LocalDate localDate,
                                                                                 Double minValue, Double maxValue, Pageable pageable);


    Page<SaleOrder> findAllByBasePriceGreaterThanEqualAndBasePriceLessThanEqual(Double minValue,
                                                                                Double maxValue, Pageable pageable);
}
