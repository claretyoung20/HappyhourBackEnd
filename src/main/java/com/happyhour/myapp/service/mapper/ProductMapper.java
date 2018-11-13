package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {RestaurantMapper.class, CategoryMapper.class, ProductTypeMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "productType.id", target = "productTypeId")
    ProductDTO toDto(Product product);

    @Mapping(source = "restaurantId", target = "restaurant")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "productTypeId", target = "productType")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
