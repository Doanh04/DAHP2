package com.Phamducdoanh.Backend.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RolesRequestDTO {
    String roleName;
    String description;
    Set<String> permissions;
}
