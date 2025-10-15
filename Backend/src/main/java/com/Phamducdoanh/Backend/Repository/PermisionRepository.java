package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.entity.PermisstionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisionRepository extends JpaRepository<PermisstionEntity, String> {
}
