package com.happyhour.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_comment")
    private String comment;

    @Column(name = "reserver_date")
    private Instant reserverDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "status")
    private String status;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Staff staff;

    @ManyToOne
    @JsonIgnoreProperties("")
    private BookTable bookTable;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public Reservation comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getReserverDate() {
        return reserverDate;
    }

    public Reservation reserverDate(Instant reserverDate) {
        this.reserverDate = reserverDate;
        return this;
    }

    public void setReserverDate(Instant reserverDate) {
        this.reserverDate = reserverDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public Reservation updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getStatus() {
        return status;
    }

    public Reservation status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public Reservation startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Reservation endTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Staff getStaff() {
        return staff;
    }

    public Reservation staff(Staff staff) {
        this.staff = staff;
        return this;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public BookTable getBookTable() {
        return bookTable;
    }

    public Reservation bookTable(BookTable bookTable) {
        this.bookTable = bookTable;
        return this;
    }

    public void setBookTable(BookTable bookTable) {
        this.bookTable = bookTable;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Reservation customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation reservation = (Reservation) o;
        if (reservation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", reserverDate='" + getReserverDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
