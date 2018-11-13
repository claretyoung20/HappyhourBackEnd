package com.happyhour.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Coupon.
 */
@Entity
@Table(name = "coupon")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "no_per_user")
    private Integer noPerUser;

    @Column(name = "price")
    private Double price;

    @Column(name = "start_from_date")
    private LocalDate startFromDate;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Coupon code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Coupon dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public Coupon dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Coupon endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Coupon isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getNoPerUser() {
        return noPerUser;
    }

    public Coupon noPerUser(Integer noPerUser) {
        this.noPerUser = noPerUser;
        return this;
    }

    public void setNoPerUser(Integer noPerUser) {
        this.noPerUser = noPerUser;
    }

    public Double getPrice() {
        return price;
    }

    public Coupon price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getStartFromDate() {
        return startFromDate;
    }

    public Coupon startFromDate(LocalDate startFromDate) {
        this.startFromDate = startFromDate;
        return this;
    }

    public void setStartFromDate(LocalDate startFromDate) {
        this.startFromDate = startFromDate;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Coupon restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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
        Coupon coupon = (Coupon) o;
        if (coupon.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coupon.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Coupon{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", noPerUser=" + getNoPerUser() +
            ", price=" + getPrice() +
            ", startFromDate='" + getStartFromDate() + "'" +
            "}";
    }
}
