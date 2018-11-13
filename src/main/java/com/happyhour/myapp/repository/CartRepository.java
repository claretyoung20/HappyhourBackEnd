package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.Cart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
