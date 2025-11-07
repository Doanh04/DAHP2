package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByProductId(Long productId);
    Page<ProductEntity> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);
    Page<ProductEntity> findByCategory_CategoryId(Long categoryId, Pageable pageable);

// Hàm bắt lỗi không tìm thấy tên trong database
    boolean existsByProductName(String productName);
//    Hàm bắt lỗi tìm theo ID
    boolean existsByCategory_CategoryId(Long categoryId);


    @Query("SELECT p FROM ProductEntity p " +
            "WHERE (:categoryId IS NULL OR p.category.categoryId = :categoryId) " +
            "  AND (:productName IS NULL OR p.productName LIKE %:productName%) " +
            "  AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "  AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<ProductEntity> searchProducts(
            @Param("categoryId") Long categoryId,
            @Param("productName") String productName,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice
    );

    // Lấy 10 sản phẩm đầu tiên
    List<ProductEntity> findTop10ByCategory_CategoryIdOrderByProductIdDesc(Long categoryId);
}
