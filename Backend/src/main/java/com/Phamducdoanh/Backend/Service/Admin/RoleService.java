package com.Phamducdoanh.Backend.Service.Admin;

import com.Phamducdoanh.Backend.DTO.Request.RolesRequestDTO;
import com.Phamducdoanh.Backend.DTO.Response.RolesResponseDTO;
import com.Phamducdoanh.Backend.Maper.RolesMaper;
import com.Phamducdoanh.Backend.Repository.PermisionRepository;
import com.Phamducdoanh.Backend.Repository.RolesRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RolesRepository rolesRepository;
    PermisionRepository  permissionRepository;
    RolesMaper rolesMaper;

    public RolesResponseDTO create(RolesRequestDTO request) {
        var role = rolesMaper.toRolesEntity(request);

        var permission = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permission));

        role = rolesRepository.save(role);
        return rolesMaper.toRolesResponse(role);
    }

    public List<RolesResponseDTO> getAll(){
        return rolesRepository.findAll()
                .stream()
                .map(rolesMaper::toRolesResponse)
                .toList();
    }

    public void delete(String roleId){
        rolesRepository.deleteById(roleId);
    }
}
