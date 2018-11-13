package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.OrderStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderStatus and its DTO OrderStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderStatusMapper extends EntityMapper<OrderStatusDTO, OrderStatus> {



    default OrderStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(id);
        return orderStatus;
    }
}
