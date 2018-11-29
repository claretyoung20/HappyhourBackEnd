package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.ReservationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reservation and its DTO ReservationDTO.
 */
@Mapper(componentModel = "spring", uses = {StaffMapper.class, BookTableMapper.class, CustomerMapper.class})
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {

    @Mapping(source = "staff.id", target = "staffId")
    @Mapping(source = "bookTable.id", target = "bookTableId")
    @Mapping(source = "customer.id", target = "customerId")
    ReservationDTO toDto(Reservation reservation);

    @Mapping(source = "staffId", target = "staff")
    @Mapping(source = "bookTableId", target = "bookTable")
    @Mapping(source = "customerId", target = "customer")
    Reservation toEntity(ReservationDTO reservationDTO);

    default Reservation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setId(id);
        return reservation;
    }
}
