package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.Rating;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Rating entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

}
