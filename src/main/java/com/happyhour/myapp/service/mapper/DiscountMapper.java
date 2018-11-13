package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.DiscountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Discount and its DTO DiscountDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface DiscountMapper extends EntityMapper<DiscountDTO, Discount> {

    @Mapping(source = "product.id", target = "productId")
    DiscountDTO toDto(Discount discount);

    @Mapping(source = "productId", target = "product")
    Discount toEntity(DiscountDTO discountDTO);

    default Discount fromId(Long id) {
        if (id == null) {
            return null;
        }
        Discount discount = new Discount();
        discount.setId(id);
        return discount;
    }
}
