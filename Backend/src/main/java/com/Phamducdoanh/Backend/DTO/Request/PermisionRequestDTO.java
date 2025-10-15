package com.Phamducdoanh.Backend.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermisionRequestDTO {
    String name;
    String description;
}
