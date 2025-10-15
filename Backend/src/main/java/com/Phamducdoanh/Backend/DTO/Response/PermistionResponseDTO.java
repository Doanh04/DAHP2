package com.Phamducdoanh.Backend.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermistionResponseDTO {
    String name;
    String description;
}
