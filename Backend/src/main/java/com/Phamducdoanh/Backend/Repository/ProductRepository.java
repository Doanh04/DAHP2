package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByProductId(Long productId);
    List<ProductEntity> findByProductNameContainingIgnoreCase(String productName);
    List<ProductEntity> findByCategory_CategoryId(Long categoryId);

// Hàm bắt lỗi không tìm thấy tên trong database
    boolean existsByProductName(String productName);
//    Hàm bắt lỗi tìm theo ID
    boolean existsByCategory_CategoryId(Long categoryId);
//    Hàm bắt lỗi không tìm thấy tên trong database
}
