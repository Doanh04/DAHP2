package com.Phamducdoanh.Backend.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponseDTO {
    Long productId;
    String productName;
    String brand;
    Long quantity;
    String description;
    //    Convert sạng byte để lưu ảnh vào DB
    byte[] image;
    Date createdAt;
    Boolean isActive;
    //Khóa ngoại
    private Long categoryId;
    private String categoryName;
}
