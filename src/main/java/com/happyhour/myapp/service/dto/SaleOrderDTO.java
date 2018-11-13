package com.happyhour.myapp.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SaleOrder entity.
 */
public class SaleOrderDTO implements Serializable {

    private Long id;

    private Double basePrice;

    private Instant dateCreated;

    private Instant dateUpdated;

    private Double discountAmount;

    private Double originalPrice;

    private Long productId;

    private Long happyOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
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

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getHappyOrderId() {
        return happyOrderId;
    }

    public void setHappyOrderId(Long happyOrderId) {
        this.happyOrderId = happyOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SaleOrderDTO saleOrderDTO = (SaleOrderDTO) o;
        if (saleOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saleOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SaleOrderDTO{" +
            "id=" + getId() +
            ", basePrice=" + getBasePrice() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", discountAmount=" + getDiscountAmount() +
            ", originalPrice=" + getOriginalPrice() +
            ", product=" + getProductId() +
            ", happyOrder=" + getHappyOrderId() +
            "}";
    }
}
