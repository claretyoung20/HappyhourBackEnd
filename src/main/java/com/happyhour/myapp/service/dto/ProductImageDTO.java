package com.happyhour.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProductImage entity.
 */
public class ProductImageDTO implements Serializable {

    private Long id;

    private String imageUrl;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductImageDTO productImageDTO = (ProductImageDTO) o;
        if (productImageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productImageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductImageDTO{" +
            "id=" + getId() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", product=" + getProductId() +
            "}";
    }
}
