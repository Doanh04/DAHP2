package com.Phamducdoanh.Backend.Repository.ProductRepositoryIMPL;

import com.Phamducdoanh.Backend.Builder.ProductSearchBuilder;
import com.Phamducdoanh.Backend.Repository.ProductCustom;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class ProductIMPL implements ProductCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<ProductEntity> findProductsCustom(ProductSearchBuilder builder) {
        StringBuilder sql = new StringBuilder("SELECT p FROM ProductEntity p WHERE 1=1");

        List<Object> params = new ArrayList<>();
        if (builder.getProductName() != null && !builder.getProductName().isEmpty()) {
            sql.append(" AND LOWER(p.productName) LIKE ?" + (params.size() + 1));
            params.add("%" + builder.getProductName().toLowerCase() + "%");
        }
        if (builder.getMinPrice() != null) {
            sql.append(" AND p.price >= ?" + (params.size() + 1));
            params.add(builder.getMinPrice());
        }
        if (builder.getMaxPrice() != null) {
            sql.append(" AND p.price <= ?" + (params.size() + 1));
            params.add(builder.getMaxPrice());
        }
        if (builder.getBrand() != null && !builder.getBrand().isEmpty()) {
            sql.append(" AND LOWER(p.brand) LIKE ?" + (params.size() + 1));
            params.add("%" + builder.getBrand().toLowerCase() + "%");
        }
        if (builder.getCategoryId() != null) {
            sql.append(" AND p.category.categoryId = ?" + (params.size() + 1));
            params.add(builder.getCategoryId());
        }

        Query query = entityManager.createQuery(sql.toString(), ProductEntity.class);

        for (int i=0; i<params.size(); i++) {
            query.setParameter(i+1, params.get(i));
        }
        return query.getResultList();
    }
}
