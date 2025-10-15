package com.Phamducdoanh.Backend.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RolesResponseDTO {
    String roleName;
    String description;
    Set<PermistionResponseDTO> permissions;
}
