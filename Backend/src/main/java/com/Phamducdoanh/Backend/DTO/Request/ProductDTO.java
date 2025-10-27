package com.Phamducdoanh.Backend.DTO.Request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Base64;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {
    String productName;
    String brand;
    Long quantity;
    String description;
    Integer price;
    Boolean isActive;
    //    Convert sạng byte để lưu ảnh vào DB
    String image;
    //Khóa ngoại
    Long categoryId;
}
