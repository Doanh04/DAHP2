package com.Phamducdoanh.Backend.Maper;

import com.Phamducdoanh.Backend.DTO.Request.CategoyCreationDTO;
import com.Phamducdoanh.Backend.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMaper {
//    Hàm Map từ DTO sang entity cho tạo mới
    CategoryEntity toCategoryCreation(CategoyCreationDTO categoryCreationDTO);

//    Hàm Map từ dto sang entity cho update
    void updateCategory(@MappingTarget CategoryEntity category, CategoyCreationDTO categoryCreationDTO);

//    Hàm Map từ entity sang DTO cho việc trả ra client
    CategoyCreationDTO toCategoryCreationDTO(CategoryEntity categoryEntity);
}
