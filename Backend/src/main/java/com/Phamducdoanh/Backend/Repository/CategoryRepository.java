package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.entity.CategoryEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
//    Tìm kiếm theo id
        CategoryEntity findByCategoryId(Long categoryId);
}
