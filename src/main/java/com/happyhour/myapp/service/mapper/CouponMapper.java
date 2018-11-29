package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.CouponDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Coupon and its DTO CouponDTO.
 */
@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface CouponMapper extends EntityMapper<CouponDTO, Coupon> {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    CouponDTO toDto(Coupon coupon);

    @Mapping(source = "restaurantId", target = "restaurant")
    Coupon toEntity(CouponDTO couponDTO);

    default Coupon fromId(Long id) {
        if (id == null) {
            return null;
        }
        Coupon coupon = new Coupon();
        coupon.setId(id);
        return coupon;
    }
}
