package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.entity.CategoryEntity;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
//    Tìm kiếm theo id
        CategoryEntity findByCategoryId(Long categoryId);

        @Query("SELECT DISTINCT c FROM CategoryEntity c LEFT JOIN FETCH c.products")
        List<CategoryEntity> findAllWithProducts();
}
