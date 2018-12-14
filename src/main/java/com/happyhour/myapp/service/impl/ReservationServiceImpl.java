package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.ReservationService;
import com.happyhour.myapp.domain.Reservation;
import com.happyhour.myapp.repository.ReservationRepository;
import com.happyhour.myapp.service.dto.ReservationDTO;
import com.happyhour.myapp.service.mapper.ReservationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Reservation.
 */
@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    /**
     * Save a reservation.
     *
     * @param reservationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReservationDTO save(ReservationDTO reservationDTO) {
        log.debug("Request to save Reservation : {}", reservationDTO);
        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        reservation = reservationRepository.save(reservation);
        return reservationMapper.toDto(reservation);
    }

    /**
     * Get all the reservations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReservationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reservations");
        return reservationRepository.findAll(pageable)
            .map(reservationMapper::toDto);
    }


    /**
     * Get one reservation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReservationDTO> findOne(Long id) {
        log.debug("Request to get Reservation : {}", id);
        return reservationRepository.findById(id)
            .map(reservationMapper::toDto);
    }

    /**
     * Delete the reservation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reservation : {}", id);
        reservationRepository.deleteById(id);
    }

    @Override
    public Page<ReservationDTO> findAllReservation(String searchPara, Pageable pageable) {
        log.debug("Request to get all Reservations");
        return reservationRepository.findAllReservation(searchPara, pageable)
            .map(reservationMapper::toDto);
    }

    @Override
    public List<ReservationDTO> findAllByPeriodAndReserverDate(String period, LocalDate reserve_Date) {
        String status = "cancel";
        return reservationMapper.toDto(reservationRepository.findAllByPeriodAndReserverDateAndStatusNotLike(period, reserve_Date, status));
    }

    @Override
    public Page<ReservationDTO> findHistory(Long id, LocalDate localDate, Pageable pageable) {
        log.debug("Request to get all Reservations");
        return reservationRepository.findAllByCustomerIdAndReserverDateLessThan(id, localDate, pageable)
            .map(reservationMapper::toDto);
    }

    @Override
    public Page<ReservationDTO> findAcitive(Long id, LocalDate localDate, Pageable pageable) {
        return reservationRepository.findAllByCustomerIdAndReserverDateIsGreaterThanEqual(id, localDate, pageable)
            .map(reservationMapper::toDto);
    }

    @Override
    public List<ReservationDTO> cronJobCancel(LocalDate localDate, String status) {
        return reservationMapper.toDto(reservationRepository.findAllByReserverDateLessThanAndStatusNotLike(localDate, status));
    }

    @Override
    public Page<ReservationDTO> findByStatus(String status, Pageable pageable) {
        log.debug("Request to get all Reservations by status");
        return reservationRepository.findAllByStatus(status, pageable)
            .map(reservationMapper::toDto);
    }

    @Override
    public Page<ReservationDTO> historyReservation(LocalDate localDate, Pageable pageable) {
        return reservationRepository.findAllByReserverDateLessThan(localDate, pageable)
            .map(reservationMapper::toDto);
    }

    @Override
    public Page<ReservationDTO> activeReservation(LocalDate localDate, Pageable pageable) {
        return reservationRepository.findAllByReserverDateGreaterThanEqual(localDate, pageable)
            .map(reservationMapper::toDto);
    }
}
