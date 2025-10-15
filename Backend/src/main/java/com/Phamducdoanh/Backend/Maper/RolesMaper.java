package com.Phamducdoanh.Backend.Maper;

import com.Phamducdoanh.Backend.DTO.Request.RolesRequestDTO;
import com.Phamducdoanh.Backend.DTO.Response.RolesResponseDTO;
import com.Phamducdoanh.Backend.entity.RolesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PermisionMaper.class})
public interface RolesMaper {
    @Mapping(target = "permissions", ignore = true)
    RolesEntity toRolesEntity(RolesRequestDTO rolesRequest);

    RolesResponseDTO toRolesResponse(RolesEntity rolesEntity);
}
