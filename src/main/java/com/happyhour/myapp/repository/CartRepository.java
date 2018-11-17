package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.Cart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Cart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> getByProductIdAndCustomerId(long productId, long customerId);

    List<Cart> getAllByCustomerId(long id);

}
