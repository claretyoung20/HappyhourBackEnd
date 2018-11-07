package com.happyhour.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SocialMedia entity.
 */
public class SocialMediaDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String link;

    private Long restaurantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

        SocialMediaDTO socialMediaDTO = (SocialMediaDTO) o;
        if (socialMediaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), socialMediaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SocialMediaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", link='" + getLink() + "'" +
            ", restaurant=" + getRestaurantId() +
            "}";
    }
}
