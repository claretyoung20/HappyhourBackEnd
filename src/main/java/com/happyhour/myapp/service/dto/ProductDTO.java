package com.happyhour.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private Instant createdDate;

    private String description;

    @NotNull
    private String name;

    @NotNull
    private Double price;

    private Instant updatedDate;

    private Boolean isAvailable;

    private Boolean showOnHomepage;

    @Lob
    private byte[] prodct_image;
    private String prodct_imageContentType;

    private Long restaurantId;

    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Boolean isShowOnHomepage() {
        return showOnHomepage;
    }

    public void setShowOnHomepage(Boolean showOnHomepage) {
        this.showOnHomepage = showOnHomepage;
    }

    public byte[] getProdct_image() {
        return prodct_image;
    }

    public void setProdct_image(byte[] prodct_image) {
        this.prodct_image = prodct_image;
    }

    public String getProdct_imageContentType() {
        return prodct_imageContentType;
    }

    public void setProdct_imageContentType(String prodct_imageContentType) {
        this.prodct_imageContentType = prodct_imageContentType;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", isAvailable='" + isIsAvailable() + "'" +
            ", showOnHomepage='" + isShowOnHomepage() + "'" +
            ", prodct_image='" + getProdct_image() + "'" +
            ", restaurant=" + getRestaurantId() +
            ", category=" + getCategoryId() +
            "}";
    }
}
