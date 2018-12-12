package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.BookTableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BookTable and its DTO BookTableDTO.
 */
@Mapper(componentModel = "spring", uses = {RestaurantMapper.class, TableTypeMapper.class})
public interface BookTableMapper extends EntityMapper<BookTableDTO, BookTable> {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "tableType.id", target = "tableTypeId")
    BookTableDTO toDto(BookTable bookTable);

    @Mapping(source = "restaurantId", target = "restaurant")
    @Mapping(source = "tableTypeId", target = "tableType")
    BookTable toEntity(BookTableDTO bookTableDTO);

    default BookTable fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookTable bookTable = new BookTable();
        bookTable.setId(id);
        return bookTable;
    }
}
