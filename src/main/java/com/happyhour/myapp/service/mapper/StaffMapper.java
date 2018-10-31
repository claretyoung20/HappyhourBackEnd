package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.StaffDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Staff and its DTO StaffDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, RestaurantMapper.class})
public interface StaffMapper extends EntityMapper<StaffDTO, Staff> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "restaurant.id", target = "restaurantId")
    StaffDTO toDto(Staff staff);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "restaurantId", target = "restaurant")
    Staff toEntity(StaffDTO staffDTO);

    default Staff fromId(Long id) {
        if (id == null) {
            return null;
        }
        Staff staff = new Staff();
        staff.setId(id);
        return staff;
    }
}
