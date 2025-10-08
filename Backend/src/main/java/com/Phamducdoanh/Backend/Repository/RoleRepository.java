package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RolesEntity, Long> {
    RolesEntity findByRoleName(String roleName);
}
