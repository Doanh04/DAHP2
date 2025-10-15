package com.Phamducdoanh.Backend.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDTO {
    Long cartItemId;
    Long quantity;
    Long productId;
    String productName;
    String imageUrl;
    Double unitPrice;
    Double totalPrice;
}
