package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.Builder.ProductSearchBuilder;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCustom {
    List<ProductEntity> findProductsCustom(ProductSearchBuilder builder);
}
