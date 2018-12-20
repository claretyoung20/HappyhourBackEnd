package com.happyhour.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the BookTable entity.
 */
public class BookTableDTO implements Serializable {

    private Long id;

    private Boolean isAvaliable;

    private Double price;

    private String imageUrl;

    
    @Lob
    private byte[] table_image;
    private String table_imageContentType;

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

    public byte[] getTable_image() {
        return table_image;
    }

    public void setTable_image(byte[] table_image) {
        this.table_image = table_image;
    }

    public String getTable_imageContentType() {
        return table_imageContentType;
    }

    public void setTable_imageContentType(String table_imageContentType) {
        this.table_imageContentType = table_imageContentType;
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
            ", table_image='" + getTable_image() + "'" +
            ", restaurant=" + getRestaurantId() +
            ", tableType=" + getTableTypeId() +
            "}";
    }
}
