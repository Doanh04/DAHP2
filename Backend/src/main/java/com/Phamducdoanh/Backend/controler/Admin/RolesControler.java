package com.Phamducdoanh.Backend.controler.Admin;

import com.Phamducdoanh.Backend.DTO.Request.PermisionRequestDTO;
import com.Phamducdoanh.Backend.DTO.Request.RolesRequestDTO;
import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.PermistionResponseDTO;
import com.Phamducdoanh.Backend.DTO.Response.RolesResponseDTO;
import com.Phamducdoanh.Backend.Service.Admin.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class RolesControler {
    RoleService roleService;

    //    Api tạo roles
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/createroles")
    ApiResponse<RolesResponseDTO> creat(@RequestBody RolesRequestDTO request) {
        RolesResponseDTO responseDTOList = roleService.create(request);
        return ApiResponse.<RolesResponseDTO>builder()
                .code(200)
                .message("Success")
                .result(responseDTOList)
                .build();
    }

    //    API get all permision
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/getroles")
    ApiResponse<List<RolesResponseDTO>> listPermision() {
        List<RolesResponseDTO> responseAll = roleService.getAll();
        return ApiResponse.<List<RolesResponseDTO>>builder()
                .code(200)
                .message("Success")
                .result(responseAll)
                .build();
    }

    //    API delete permision
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleterole")
    ApiResponse<Void> deletePermision(@PathVariable String roleId) {
        roleService.delete(roleId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("delete Success")
                .build();
    }
}
