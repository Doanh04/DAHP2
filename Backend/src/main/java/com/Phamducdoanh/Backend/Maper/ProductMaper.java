package com.Phamducdoanh.Backend.Maper;

import com.Phamducdoanh.Backend.DTO.Request.ProductDTO;
import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.Resolve.CategoryResolve;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryResolve.class})
public interface ProductMaper {
//    Map từ dto sang entity
    @Mapping(target = "productId", ignore = true)     // Bỏ qua ID khi tạo mới
    @Mapping(target = "createdAt", ignore = true)     // Bỏ qua ngày tạo (thường được sinh tự động)
    @Mapping(target = "category" ,// Lấy từ tên khóa ngoại dc map trong Product entity
              source = "categoryId"  )// Lấy từ tên khóa ngoại được truyền từ ProductDTO

    // Bỏ qua các liên kết OneToMany khi tạo/cập nhật
    @Mapping(target = "cartItemsList", ignore = true)
    @Mapping(target = "orderDetailList", ignore = true)
    ProductEntity toProductcreation(ProductDTO productDTO);

//    Hàm map từ DTO sang Entity cho update
    void updateProduct(@MappingTarget ProductEntity productEntity, ProductDTO productDTO);

//    Map từ entity sang DTO
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "categoryName", source = "category.categoryName")
    @Mapping(target = "imageUrl", expression = "java(\"/api/images/\" + productEntity.getProductId())")
    ProductResponseDTO toProductDTO(ProductEntity productEntity);

//    Map từ etity sanng DTO để get dữ liệu
    List<ProductResponseDTO> toProductDTOList(List<ProductEntity> productEntityList);
}
