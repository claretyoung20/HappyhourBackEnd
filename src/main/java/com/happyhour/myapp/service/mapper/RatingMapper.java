package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.RatingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rating and its DTO RatingDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class, RestaurantMapper.class})
public interface RatingMapper extends EntityMapper<RatingDTO, Rating> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "restaurant.id", target = "restaurantId")
    RatingDTO toDto(Rating rating);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "restaurantId", target = "restaurant")
    Rating toEntity(RatingDTO ratingDTO);

    default Rating fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rating rating = new Rating();
        rating.setId(id);
        return rating;
    }
}
