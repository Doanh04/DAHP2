package com.Phamducdoanh.Backend.Maper;

import com.Phamducdoanh.Backend.DTO.Request.PermisionRequestDTO;
import com.Phamducdoanh.Backend.DTO.Response.PermistionResponseDTO;
import com.Phamducdoanh.Backend.entity.PermisstionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermisionMaper {
    @Mapping(source = "name", target = "permisionName")
    PermisstionEntity toPermisionEntity(PermisionRequestDTO permisionRequestDTO);

    @Mapping(source = "permisionName", target = "name")
    PermistionResponseDTO toPerMisionResponse(PermisstionEntity permisstionEntity);
}
