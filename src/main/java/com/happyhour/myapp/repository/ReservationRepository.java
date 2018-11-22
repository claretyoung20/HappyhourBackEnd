package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.Reservation;
import com.happyhour.myapp.service.dto.ReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Reservation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(
        value = "SELECT * FROM reservation r " +
            "WHERE r.reserver_date= ?1 " +
            "OR r.customer_id=?1 " +
            "OR r.book_table_id=?1 " +
            "OR r.start_time=?1 " +
            "OR r.end_time=?1 " +
            "OR r.staff_id=?1 " +
            "OR r.status=?1 " +
            "OR r.updated_date=?1",
        nativeQuery = true
    )
    Page<Reservation> findAllReservation(String searchPara, Pageable pageable);

    // Page<Reservation> findAllByTaAndBookTableId(Long id, Pageable pageable);

}
