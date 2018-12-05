package com.happyhour.myapp.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Cart entity.
 */
public class CartDTO implements Serializable {

    private Long id;

    private Instant dateCreated;

    private Instant dateUpdated;

    private Integer totalItem;

    private String productName;

    private Double productPrice;

    private Long productId;

    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

        CartDTO cartDTO = (CartDTO) o;
        if (cartDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cartDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CartDTO{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", totalItem=" + getTotalItem() +
            ", productName='" + getProductName() + "'" +
            ", productPrice=" + getProductPrice() +
            ", product=" + getProductId() +
            ", customer=" + getCustomerId() +
            "}";
    }
}
