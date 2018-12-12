package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.Reservation;
import com.happyhour.myapp.service.dto.ReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


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

    // SELECT * FROM reservation r WHERE r.period = 'm' AND r.reserver_date = '2018-12-05';
    List<Reservation> findAllByPeriodAndReserverDateAndStatusNotLike(String period, LocalDate reserve_Date, String status);
    Page<Reservation> findAllByCustomerIdAndReserverDateLessThan(Long id, LocalDate localDate, Pageable pageable);
    Page<Reservation> findAllByCustomerIdAndReserverDateIsGreaterThanEqual(long id, LocalDate localDate, Pageable pageable);

    List<Reservation> findAllByReserverDateLessThanAndStatusNotLike(LocalDate localDate, String status);

    Page<Reservation> findAllByStatus(String status, Pageable pageable);
}
