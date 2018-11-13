package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.ProductTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductType and its DTO ProductTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductTypeMapper extends EntityMapper<ProductTypeDTO, ProductType> {



    default ProductType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductType productType = new ProductType();
        productType.setId(id);
        return productType;
    }
}
