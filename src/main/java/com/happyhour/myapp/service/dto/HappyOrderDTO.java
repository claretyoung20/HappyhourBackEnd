package com.happyhour.myapp.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the HappyOrder entity.
 */
public class HappyOrderDTO implements Serializable {

    private Long id;

    private Double baseTotal;

    private Double totalPrice;

    private LocalDate dateUpdated;

    private LocalDate dateCreated;

    private Long orderStatusId;

    private Long customerId;

    private Long couponId;

    private Long restaurantId;

    private Long staffId;

    private String staffStaffCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBaseTotal() {
        return baseTotal;
    }

    public void setBaseTotal(Double baseTotal) {
        this.baseTotal = baseTotal;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Long orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffStaffCode() {
        return staffStaffCode;
    }

    public void setStaffStaffCode(String staffStaffCode) {
        this.staffStaffCode = staffStaffCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HappyOrderDTO happyOrderDTO = (HappyOrderDTO) o;
        if (happyOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), happyOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HappyOrderDTO{" +
            "id=" + getId() +
            ", baseTotal=" + getBaseTotal() +
            ", totalPrice=" + getTotalPrice() +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", orderStatus=" + getOrderStatusId() +
            ", customer=" + getCustomerId() +
            ", coupon=" + getCouponId() +
            ", restaurant=" + getRestaurantId() +
            ", staff=" + getStaffId() +
            ", staff='" + getStaffStaffCode() + "'" +
            "}";
    }
}
