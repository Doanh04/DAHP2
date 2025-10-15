package com.Phamducdoanh.Backend.controler.Admin;

import com.Phamducdoanh.Backend.DTO.Request.ChangePassDTO;
import com.Phamducdoanh.Backend.DTO.Request.UserDTO;
import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.UserResponse;
import com.Phamducdoanh.Backend.Service.Admin.AccoutAdminService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/admin/user")
public class UserAdminControler {
    final AccoutAdminService accoutAdminService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/getalluser")
    public ApiResponse<List<UserResponse>> getAllUser(){
        List<UserResponse> userResponseList = accoutAdminService.findAll();
        ApiResponse<List<UserResponse>> response = ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .success(true)
                .message("success")
                .result(userResponseList)
                .build();
        return  response;
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping("/updateuser/{iduser}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String iduser, @RequestBody UserDTO userDTO){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(accoutAdminService.updateUser(iduser, userDTO));
        response.setSuccess(true);
        return response;
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping("/updatepass/{iduser}")
    public ApiResponse<UserResponse> udatePass(@PathVariable String iduser, @RequestBody ChangePassDTO changePassDTO){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(accoutAdminService.changePassword(iduser, changePassDTO));
        response.setSuccess(true);
        return response;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleteUser/{iduser}")
    ApiResponse<Void> deleteAccout(@PathVariable String iduser){
        accoutAdminService.deleteAccout(iduser);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("delete Success")
                .build();
    }
}
