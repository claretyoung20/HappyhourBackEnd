package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.TimeManagerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TimeManager and its DTO TimeManagerDTO.
 */
@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface TimeManagerMapper extends EntityMapper<TimeManagerDTO, TimeManager> {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    TimeManagerDTO toDto(TimeManager timeManager);

    @Mapping(source = "restaurantId", target = "restaurant")
    TimeManager toEntity(TimeManagerDTO timeManagerDTO);

    default TimeManager fromId(Long id) {
        if (id == null) {
            return null;
        }
        TimeManager timeManager = new TimeManager();
        timeManager.setId(id);
        return timeManager;
    }
}
