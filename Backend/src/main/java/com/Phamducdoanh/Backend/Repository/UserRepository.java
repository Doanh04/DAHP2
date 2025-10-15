package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    boolean existsByUsername(String username);
    UserEntity findByUserId(String userid);

    @Query("""
        SELECT DISTINCT u FROM UserEntity u
        LEFT JOIN FETCH u.roles r
        LEFT JOIN FETCH r.permissions
        WHERE u.username = :username
    """)
    Optional<UserEntity> findByUsernameWithRolesAndPermissions(@Param("username") String username);
    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<UserEntity> findByUsername(String username);
}
