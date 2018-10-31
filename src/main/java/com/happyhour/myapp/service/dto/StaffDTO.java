package com.happyhour.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Staff entity.
 */
public class StaffDTO implements Serializable {

    private Long id;

    @NotNull
    private String staffCode;

    private Long userId;

    private String userLogin;

    private Long restaurantId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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

        StaffDTO staffDTO = (StaffDTO) o;
        if (staffDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), staffDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StaffDTO{" +
            "id=" + getId() +
            ", staffCode='" + getStaffCode() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            ", restaurant=" + getRestaurantId() +
            "}";
    }
}
