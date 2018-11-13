package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.Customer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailAndPassword(String email, String password);
}
