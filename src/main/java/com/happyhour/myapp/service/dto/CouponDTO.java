package com.happyhour.myapp.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Coupon entity.
 */
public class CouponDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    private Instant dateCreated;

    private Instant dateUpdated;

    private LocalDate endDate;

    private Boolean isActive;

    private Integer noPerUser;

    private Double price;

    private LocalDate startFromDate;

    private Long restaurantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getNoPerUser() {
        return noPerUser;
    }

    public void setNoPerUser(Integer noPerUser) {
        this.noPerUser = noPerUser;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getStartFromDate() {
        return startFromDate;
    }

    public void setStartFromDate(LocalDate startFromDate) {
        this.startFromDate = startFromDate;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CouponDTO couponDTO = (CouponDTO) o;
        if (couponDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), couponDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CouponDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", noPerUser=" + getNoPerUser() +
            ", price=" + getPrice() +
            ", startFromDate='" + getStartFromDate() + "'" +
            ", restaurant=" + getRestaurantId() +
            "}";
    }
}
