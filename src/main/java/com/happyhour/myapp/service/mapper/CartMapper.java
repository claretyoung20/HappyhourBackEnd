package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.CartDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cart and its DTO CartDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, CustomerMapper.class})
public interface CartMapper extends EntityMapper<CartDTO, Cart> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "customer.id", target = "customerId")
    CartDTO toDto(Cart cart);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "customerId", target = "customer")
    Cart toEntity(CartDTO cartDTO);

    default Cart fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cart cart = new Cart();
        cart.setId(id);
        return cart;
    }
}
