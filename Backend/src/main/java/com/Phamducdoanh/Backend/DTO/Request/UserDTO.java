package com.Phamducdoanh.Backend.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    String userId;
    @Size (min = 10, message = "USERNAME_INVALID")
    String username;
    String name;
    String email;
    @Size(min = 6, message = "PASSWORD_INVALID")
    String password;
    String phone;
    String address;
}
