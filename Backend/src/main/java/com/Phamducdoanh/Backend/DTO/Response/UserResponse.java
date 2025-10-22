package com.Phamducdoanh.Backend.DTO.Response;

import com.Phamducdoanh.Backend.entity.RolesEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
    String userId;
    String username;
    String password;
    String name;
    String email;
    String phone;
    String address;
    List<RolesResponseDTO> roles;
}
