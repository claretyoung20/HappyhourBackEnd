package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.HappyOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HappyOrder and its DTO HappyOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {OrderStatusMapper.class, CustomerMapper.class, CouponMapper.class, RestaurantMapper.class, StaffMapper.class, BookTableMapper.class})
public interface HappyOrderMapper extends EntityMapper<HappyOrderDTO, HappyOrder> {

    @Mapping(source = "orderStatus.id", target = "orderStatusId")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "coupon.id", target = "couponId")
    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "staff.id", target = "staffId")
    @Mapping(source = "staff.staffCode", target = "staffStaffCode")
    @Mapping(source = "bookTable.id", target = "bookTableId")
    HappyOrderDTO toDto(HappyOrder happyOrder);

    @Mapping(source = "orderStatusId", target = "orderStatus")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "couponId", target = "coupon")
    @Mapping(source = "restaurantId", target = "restaurant")
    @Mapping(source = "staffId", target = "staff")
    @Mapping(source = "bookTableId", target = "bookTable")
    HappyOrder toEntity(HappyOrderDTO happyOrderDTO);

    default HappyOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        HappyOrder happyOrder = new HappyOrder();
        happyOrder.setId(id);
        return happyOrder;
    }
}
