package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategoryIdAndIsAvailableTrue(long id, Pageable pageable);

    Product findById(long id);

    Page<Product> findAllByShowOnHomepageTrueAndIsAvailableTrue(Pageable pageable);

    Page<Product> findAllByIsAvailableTrue(Pageable pageable);

    Page<Product> findAllByIsAvailableFalse(Pageable pageable);
}
