package com.happyhour.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
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

    @Column(name = "status")
    private String status;

    @Column(name = "reserver_date")
    private LocalDate reserverDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "period")
    private String period;

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

    public LocalDate getReserverDate() {
        return reserverDate;
    }

    public Reservation reserverDate(LocalDate reserverDate) {
        this.reserverDate = reserverDate;
        return this;
    }

    public void setReserverDate(LocalDate reserverDate) {
        this.reserverDate = reserverDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public Reservation updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getPeriod() {
        return period;
    }

    public Reservation period(String period) {
        this.period = period;
        return this;
    }

    public void setPeriod(String period) {
        this.period = period;
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
            ", status='" + getStatus() + "'" +
            ", reserverDate='" + getReserverDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", period='" + getPeriod() + "'" +
            "}";
    }
}
