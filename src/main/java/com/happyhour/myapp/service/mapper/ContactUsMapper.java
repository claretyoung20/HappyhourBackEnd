package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.ContactUsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContactUs and its DTO ContactUsDTO.
 */
@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface ContactUsMapper extends EntityMapper<ContactUsDTO, ContactUs> {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    ContactUsDTO toDto(ContactUs contactUs);

    @Mapping(source = "restaurantId", target = "restaurant")
    ContactUs toEntity(ContactUsDTO contactUsDTO);

    default ContactUs fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactUs contactUs = new ContactUs();
        contactUs.setId(id);
        return contactUs;
    }
}
