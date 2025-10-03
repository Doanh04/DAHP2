package com.Phamducdoanh.Backend.DTO.Request;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoyCreationDTO {
    String categoryName;
    String description;
}
