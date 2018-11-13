package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.SaleOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SaleOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long> {

}
