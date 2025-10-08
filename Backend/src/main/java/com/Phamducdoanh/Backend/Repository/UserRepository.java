package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    boolean existsByUsername(String username);
    UserEntity findByUserId(String userid);
    Optional<UserEntity> findByUsername(String username);
}
