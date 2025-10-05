package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByProductId(Long productId);
    List<ProductEntity> findByProductName(String productName);
    List<ProductEntity> findByCategory_CategoryId(Long categoryId);

//    Hàm bắt lỗi trùng tên
    boolean existsByProductName(String productName);
//    Hàm bắt lỗi tìm theo ID
    boolean existsByCategory_CategoryId(Long categoryId);
}
