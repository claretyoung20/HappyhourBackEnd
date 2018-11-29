package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.TimeManager;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TimeManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeManagerRepository extends JpaRepository<TimeManager, Long> {

}
