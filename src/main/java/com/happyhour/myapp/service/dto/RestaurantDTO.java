package com.happyhour.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Restaurant entity.
 */
public class RestaurantDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    private Boolean isActive;

    private String name;

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

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RestaurantDTO restaurantDTO = (RestaurantDTO) o;
        if (restaurantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restaurantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RestaurantDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
