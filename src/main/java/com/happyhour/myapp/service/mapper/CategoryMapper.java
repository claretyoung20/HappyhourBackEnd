package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    CategoryDTO toDto(Category category);

    @Mapping(source = "restaurantId", target = "restaurant")
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
