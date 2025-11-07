package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.Builder.ProductSearchBuilder;
import com.Phamducdoanh.Backend.entity.ProductEntity;

import java.util.List;

public interface ProductCustom {
    List<ProductEntity> findProductsCustom(ProductSearchBuilder builder);
}
