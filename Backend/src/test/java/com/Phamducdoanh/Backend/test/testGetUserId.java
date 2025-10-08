package com.Phamducdoanh.Backend.test;

import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.DTO.Response.UserResponse;
import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Maper.UserMaper;
import com.Phamducdoanh.Backend.Repository.UserRepository;
import com.Phamducdoanh.Backend.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RestController
@RequestMapping("test")
public class testGetUserId {
    final UserMaper  userMaper;
    final UserRepository userRepository;

    @GetMapping("{userId}")
    public ApiResponse<UserResponse> getUserById(@PathVariable String userId) {
        UserEntity userEntity =userRepository.findByUserId(userId);
        if(userEntity==null){
            throw new AppExeption(ErrorCode.USER_NOTFOUND);
        }
        UserResponse userResponse = userMaper.toUserResponse(userEntity);

        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .code(1000)
                .success(true)
                .message("success")
                .result(userResponse)
                .build();
        return response;
    }
}
