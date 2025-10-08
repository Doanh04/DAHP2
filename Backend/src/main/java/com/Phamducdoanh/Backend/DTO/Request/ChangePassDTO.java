package com.Phamducdoanh.Backend.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassDTO {
    @Size(min = 6, message = "PASSWORD_INVALID")
    String currentPassword;
    @Size(min = 6, message = "PASSWORD_INVALID")
    String newPassword;
}
