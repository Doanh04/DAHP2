package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.entity.CartEntity;
import com.Phamducdoanh.Backend.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    Optional<CartItemEntity> findByCartAndProduct_ProductId(CartEntity cart, Long productId);
}
