package com.Phamducdoanh.Backend.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequestDTO {
    String userId;
    String paymentMethod; //COD hoặc VIETQR
    String shippingAddress;
    List<ItemDTO> items;
}
