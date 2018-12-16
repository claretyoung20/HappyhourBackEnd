package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.Staff;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Staff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByUserId(long id);
    void deleteByUserId(Long userId);
}
