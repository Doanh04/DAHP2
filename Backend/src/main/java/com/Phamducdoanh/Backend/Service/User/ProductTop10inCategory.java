package com.Phamducdoanh.Backend.Service.User;

import com.Phamducdoanh.Backend.DTO.Response.CategoryWithProductsDTO;
import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.Maper.ProductMaper;
import com.Phamducdoanh.Backend.Repository.CategoryRepository;
import com.Phamducdoanh.Backend.Repository.ProductRepository;
import com.Phamducdoanh.Backend.entity.CategoryEntity;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductTop10inCategory {
    ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    ProductMaper productMaper;

    public List<CategoryWithProductsDTO> getCategoriesWithTopProducts() {
        // 1. Lấy tất cả danh mục
        List<CategoryEntity> categories = categoryRepository.findAll();

        return categories.stream().map(category -> {
            // 2. Lấy Top 10 sản phẩm cho danh mục hiện tại
            List<ProductEntity> topEntities = productRepository.findTop10ByCategory_CategoryIdOrderByProductIdDesc(category.getCategoryId());

            // 3. Ánh xạ (Map) sang DTO sản phẩm
            List<ProductResponseDTO> topProductDTOs = productMaper.toProductDTOList(topEntities);

            // 4. Tạo DTO kết quả
            return new CategoryWithProductsDTO(
                    category.getCategoryId(),
                    category.getCategoryName(),
                    topProductDTOs
            );
        }).collect(Collectors.toList());
    }
}
