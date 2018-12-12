package com.happyhour.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BookTable entity.
 */
public class BookTableDTO implements Serializable {

    private Long id;

    private Boolean isAvaliable;

    private Double price;

    private String imageUrl;

    private Long restaurantId;

    private Long tableTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsAvaliable() {
        return isAvaliable;
    }

    public void setIsAvaliable(Boolean isAvaliable) {
        this.isAvaliable = isAvaliable;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getTableTypeId() {
        return tableTypeId;
    }

    public void setTableTypeId(Long tableTypeId) {
        this.tableTypeId = tableTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookTableDTO bookTableDTO = (BookTableDTO) o;
        if (bookTableDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookTableDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookTableDTO{" +
            "id=" + getId() +
            ", isAvaliable='" + isIsAvaliable() + "'" +
            ", price=" + getPrice() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", restaurant=" + getRestaurantId() +
            ", tableType=" + getTableTypeId() +
            "}";
    }
}
