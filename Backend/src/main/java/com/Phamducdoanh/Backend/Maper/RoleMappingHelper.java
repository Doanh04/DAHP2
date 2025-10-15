package com.Phamducdoanh.Backend.Maper;

import com.Phamducdoanh.Backend.Repository.RolesRepository;
import com.Phamducdoanh.Backend.entity.RolesEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component // Quan trọng: Để Spring quản lý và inject Repository
@RequiredArgsConstructor
public class RoleMappingHelper {
    private final RolesRepository rolesRepository;

    // Phương thức tùy chỉnh để MapStruct sử dụng
    // Nhận vào List<String> role names và trả về Set<RolesEntity>
    public Set<RolesEntity> map(List<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            return new HashSet<>();
        }

        // Sử dụng findAllById để tìm tất cả các Roles dựa trên tên (khóa chính)
        List<RolesEntity> foundRoles = rolesRepository.findAllById(roleNames);

        // Chuyển List kết quả sang Set
        return new HashSet<>(foundRoles);
    }
}
