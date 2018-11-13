package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.SaleOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SaleOrder and its DTO SaleOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, HappyOrderMapper.class})
public interface SaleOrderMapper extends EntityMapper<SaleOrderDTO, SaleOrder> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "happyOrder.id", target = "happyOrderId")
    SaleOrderDTO toDto(SaleOrder saleOrder);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "happyOrderId", target = "happyOrder")
    SaleOrder toEntity(SaleOrderDTO saleOrderDTO);

    default SaleOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        SaleOrder saleOrder = new SaleOrder();
        saleOrder.setId(id);
        return saleOrder;
    }
}
