package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.entity.CartEntity;
import com.Phamducdoanh.Backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUser(UserEntity user);
}
