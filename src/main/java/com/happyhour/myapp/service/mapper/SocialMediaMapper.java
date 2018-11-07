package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.SocialMediaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SocialMedia and its DTO SocialMediaDTO.
 */
@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface SocialMediaMapper extends EntityMapper<SocialMediaDTO, SocialMedia> {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    SocialMediaDTO toDto(SocialMedia socialMedia);

    @Mapping(source = "restaurantId", target = "restaurant")
    SocialMedia toEntity(SocialMediaDTO socialMediaDTO);

    default SocialMedia fromId(Long id) {
        if (id == null) {
            return null;
        }
        SocialMedia socialMedia = new SocialMedia();
        socialMedia.setId(id);
        return socialMedia;
    }
}
