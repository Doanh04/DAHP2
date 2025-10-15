package com.Phamducdoanh.Backend.controler.Admin;

import com.Phamducdoanh.Backend.DTO.Request.PermisionRequestDTO;
import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.PermistionResponseDTO;
import com.Phamducdoanh.Backend.Service.Admin.PermisionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/admin/permission")
@Slf4j
public class PermisionControler {
    PermisionService  permisionService;

//    Api tạo permision
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/createpermision")
    ApiResponse<PermistionResponseDTO> creat(@RequestBody PermisionRequestDTO permisionRequestDTO) {
        PermistionResponseDTO responseDTOList = permisionService.creatPermision(permisionRequestDTO);
        return ApiResponse.<PermistionResponseDTO>builder()
                .code(200)
                .message("Success")
                .result(responseDTOList)
                .build();
    }

//    API get all permision
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/getpermision")
    ApiResponse<List<PermistionResponseDTO>> listPermision() {
        List<PermistionResponseDTO> responseAll = permisionService.getAll();
        return ApiResponse.<List<PermistionResponseDTO>>builder()
                .code(200)
                .message("Success")
                .result(responseAll)
                .build();
    }

//    API delete permision
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deletepermision/{permisionId}")
    ApiResponse<Void> deletePermision(@PathVariable String permisionId) {
        permisionService.delete(permisionId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("delete Success")
                .build();
    }
}
