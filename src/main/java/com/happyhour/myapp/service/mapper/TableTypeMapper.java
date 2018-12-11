package com.happyhour.myapp.service.mapper;

import com.happyhour.myapp.domain.*;
import com.happyhour.myapp.service.dto.TableTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TableType and its DTO TableTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TableTypeMapper extends EntityMapper<TableTypeDTO, TableType> {



    default TableType fromId(Long id) {
        if (id == null) {
            return null;
        }
        TableType tableType = new TableType();
        tableType.setId(id);
        return tableType;
    }
}
