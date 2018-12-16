package com.happyhour.myapp.service;

import com.happyhour.myapp.domain.Reservation;
import com.happyhour.myapp.service.dto.ReservationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Reservation.
 */
public interface ReservationService {

    /**
     * Save a reservation.
     *
     * @param reservationDTO the entity to save
     * @return the persisted entity
     */
    ReservationDTO save(ReservationDTO reservationDTO);

    /**
     * Get all the reservations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ReservationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" reservation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ReservationDTO> findOne(Long id);

    /**
     * Delete the "id" reservation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Page<ReservationDTO> findAllReservation(String searchPara, Pageable pageable);

    List<ReservationDTO> findAllByPeriodAndReserverDate(String period, LocalDate reserve_Date);

    Page<ReservationDTO> findHistory(Long id, LocalDate localDate, Pageable pageable);

    Page<ReservationDTO> findAcitive(Long id, LocalDate localDate, Pageable pageable);

    Page<ReservationDTO> findByStatus(String status,LocalDate localDate, Pageable pageable);

    List<ReservationDTO> cronJobCancel(LocalDate localDate, String status);

    Page<ReservationDTO> historyReservation(LocalDate localDate, Pageable pageable);

    Page<ReservationDTO> activeReservation(LocalDate localDate, Pageable pageable);
}
