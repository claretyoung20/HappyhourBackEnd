package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.RatingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rating and its DTO RatingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RatingMapper extends EntityMapper<RatingDTO, Rating> {



    default Rating fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rating rating = new Rating();
        rating.setId(id);
        return rating;
    }
}
