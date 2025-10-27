package com.Phamducdoanh.Backend.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWithProductsDTO {
    private Long categoryId;
    private String categoryName;
    private List<ProductResponseDTO> topProducts;
}
