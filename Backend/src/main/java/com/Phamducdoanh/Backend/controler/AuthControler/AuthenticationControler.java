package com.Phamducdoanh.Backend.controler.AuthControler;

import com.Phamducdoanh.Backend.DTO.Request.AuthenticationRequestDTO;
import com.Phamducdoanh.Backend.DTO.Request.IntroSpectRequestDTO;
import com.Phamducdoanh.Backend.DTO.Request.UserDTO;
import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.AuthenticationResponseDTO;
import com.Phamducdoanh.Backend.DTO.Response.IntroSpectResponseDTO;
import com.Phamducdoanh.Backend.DTO.Response.UserResponse;
import com.Phamducdoanh.Backend.Service.Auth.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationControler {
    AuthenticationService authenticationService;
//    API log in trả ra token
    @PostMapping("/token")
    ApiResponse<AuthenticationResponseDTO> authentiacate(@RequestBody AuthenticationRequestDTO requestDTO){
        var result = authenticationService.authenticate(requestDTO);
        return ApiResponse.<AuthenticationResponseDTO>builder()
                .code(1000)
                .success(true)
                .result(result)
                .build();
    }
//    API đăng ký tài khoản
    @PostMapping("/createuser")
    public ApiResponse<UserResponse> creatUser(@RequestBody UserDTO userDTO){
        UserResponse resultUser = authenticationService.createUser(userDTO);
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .code(1000)
                .success(true)
                .message("User Created")
                .result(resultUser)
                .build();
        return response;
    }
//API verifi token
    @PostMapping("/introspect")
    ApiResponse<IntroSpectResponseDTO> authentiacate(@RequestBody IntroSpectRequestDTO requestDTO)
            throws ParseException, JOSEException {
        var result = authenticationService.introSpect(requestDTO);
        return ApiResponse.<IntroSpectResponseDTO>builder()
                .code(1000)
                .success(true)
                .result(result)
                .build();
    }
}
