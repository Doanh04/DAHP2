package com.Phamducdoanh.Backend.controler.User;

import com.Phamducdoanh.Backend.DTO.Request.ChangePassDTO;
import com.Phamducdoanh.Backend.DTO.Request.UserDTO;
import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.CategoryResonseDTO;
import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.DTO.Response.UserResponse;
import com.Phamducdoanh.Backend.Service.User.AccoutUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/dashboard/user")
public class UserControler {
    final AccoutUserService accoutUserService;

    @PutMapping("/updateuser/{iduser}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String iduser, @RequestBody UserDTO userDTO){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(accoutUserService.updateUser(iduser, userDTO));
        response.setSuccess(true);
        return response;
    }
    @PutMapping("updatepass/{iduser}")
    public ApiResponse<UserResponse> udatePass(@PathVariable String iduser, @RequestBody ChangePassDTO changePassDTO){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(accoutUserService.changePassword(iduser, changePassDTO));
        response.setSuccess(true);
        return response;
    }
}
