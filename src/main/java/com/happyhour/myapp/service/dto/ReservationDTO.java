package com.happyhour.myapp.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Reservation entity.
 */
public class ReservationDTO implements Serializable {

    private Long id;

    private String comment;

    private String status;

    private LocalDate reserverDate;

    private LocalDate updatedDate;

    private String period;

    private Long staffId;

    private Long bookTableId;

    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getReserverDate() {
        return reserverDate;
    }

    public void setReserverDate(LocalDate reserverDate) {
        this.reserverDate = reserverDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getBookTableId() {
        return bookTableId;
    }

    public void setBookTableId(Long bookTableId) {
        this.bookTableId = bookTableId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReservationDTO reservationDTO = (ReservationDTO) o;
        if (reservationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", status='" + getStatus() + "'" +
            ", reserverDate='" + getReserverDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", period='" + getPeriod() + "'" +
            ", staff=" + getStaffId() +
            ", bookTable=" + getBookTableId() +
            ", customer=" + getCustomerId() +
            "}";
    }
}
