package com.Phamducdoanh.Backend.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequestDTO {
    Long productId;
    Long quantity;
}
