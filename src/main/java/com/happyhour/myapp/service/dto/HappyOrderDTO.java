package com.happyhour.myapp.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the HappyOrder entity.
 */
public class HappyOrderDTO implements Serializable {

    private Long id;

    private Double baseTotal;

    private Instant dateCreated;

    private Instant dateUpdated;

    private Double totalPrice;

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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", orderStatus=" + getOrderStatusId() +
            ", customer=" + getCustomerId() +
            ", coupon=" + getCouponId() +
            ", restaurant=" + getRestaurantId() +
            ", staff=" + getStaffId() +
            ", staff='" + getStaffStaffCode() + "'" +
            "}";
    }
}
