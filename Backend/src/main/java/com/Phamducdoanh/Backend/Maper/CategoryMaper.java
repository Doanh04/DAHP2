package com.Phamducdoanh.Backend.Maper;

import com.Phamducdoanh.Backend.DTO.Request.CategoyCreationDTO;
import com.Phamducdoanh.Backend.DTO.Response.CategoryResonseDTO;
import com.Phamducdoanh.Backend.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMaper.class})
public interface CategoryMaper {
//    Hàm Map từ DTO sang entity cho tạo mới
    CategoryEntity toCategoryCreation(CategoyCreationDTO categoryCreationDTO);

//    Hàm Map từ dto sang entity cho update
    void updateCategory(@MappingTarget CategoryEntity category, CategoyCreationDTO categoryCreationDTO);

//    Map đơn lẻ cho categoryId tránh lỗi null
    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(target = "products", source = "products")
    CategoryResonseDTO toCategoryResonseDTO(CategoryEntity categoryEntity);
//    Hàm Map từ entity sang DTO cho việc trả ra client
    List<CategoryResonseDTO> toCategoryResponseDTO(List<CategoryEntity> categoryEntity);
}
