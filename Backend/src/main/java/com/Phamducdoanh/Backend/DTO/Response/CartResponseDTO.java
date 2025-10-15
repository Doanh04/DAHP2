package com.Phamducdoanh.Backend.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponseDTO {
    Long cartId;
    Date createDate;

    List<CartItemResponseDTO> items;

    Double subTotal;
}
