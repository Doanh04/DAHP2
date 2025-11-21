package com.Phamducdoanh.Backend.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
public class OrderResponseDTO {
     Long orderId;
     LocalDateTime orderDate;
     String payMethod;
     String shippingAddress;
     String status;
     String totalAmount;
     List<OrderDetailResponseDTO> details;
}
