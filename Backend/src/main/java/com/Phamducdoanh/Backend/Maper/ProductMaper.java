package com.Phamducdoanh.Backend.Maper;

import com.Phamducdoanh.Backend.DTO.Request.ProductDTO;
import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.Resolve.CategoryResolve;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {CategoryResolve.class})
public interface ProductMaper {
    default byte[] mapBase64ToBytes(String base64ImageWithPrefix) {
        if (base64ImageWithPrefix == null || base64ImageWithPrefix.isEmpty()) {
            return null;
        }

        // 1. Tìm vị trí của dấu phẩy (separator)
        int commaIndex = base64ImageWithPrefix.indexOf(',');
        String pureBase64;

        if (commaIndex != -1) {
            // 2. Cắt bỏ phần tiền tố "data:..."
            pureBase64 = base64ImageWithPrefix.substring(commaIndex + 1);
        } else {
            // Nếu không có tiền tố (Client đã gửi Base64 thuần), sử dụng toàn bộ chuỗi
            pureBase64 = base64ImageWithPrefix;
        }

        try {
            // 3. Decode chuỗi Base64 thuần túy
            return Base64.getDecoder().decode(pureBase64);
        } catch (IllegalArgumentException e) {
            // Ném RuntimeException với thông báo rõ ràng hơn
            throw new RuntimeException("Invalid Base64 string for image.", e);
        }
    }
//    Map từ dto sang entity
    @Mapping(target = "productId", ignore = true)     // Bỏ qua ID khi tạo mới
    @Mapping(target = "createdAt", ignore = true)     // Bỏ qua ngày tạo (thường được sinh tự động)
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapIdToCategory")
    @Mapping(target = "image", source = "image")
    // Bỏ qua các liên kết OneToMany khi tạo/cập nhật
    @Mapping(target = "cartItemsList", ignore = true)
    @Mapping(target = "orderDetailList", ignore = true)
    ProductEntity toProductcreation(ProductDTO productDTO);

//    Hàm map từ DTO sang Entity cho update
    @Mapping(target = "image", source = "image", qualifiedByName = "mapImageToBytes")
    @Mapping(target = "category" ,source = "categoryId", qualifiedByName = "mapIdToCategory")
    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "image", ignore = true)
    @Mapping(target = "cartItemsList", ignore = true)
    @Mapping(target = "orderDetailList", ignore = true)
    void updateProduct(@MappingTarget ProductEntity productEntity, ProductDTO productDTO);

    @Named("mapImageToBytes")
    default byte[] mapImageToBytes(String imageBase64) {
        if (imageBase64 == null || imageBase64.isEmpty()) {
            // Khi Frontend không gửi ảnh mới, Base64 là null.
            // Trả về null để MapStruct KHÔNG CẬP NHẬT TRƯỜNG image.
            return null;
        }

        // Gọi hàm giải mã Base64
        return mapBase64ToBytes(imageBase64);
    }

//    Map từ entity sang DTO
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "categoryName", source = "category.categoryName")
    @Mapping(target = "imageUrl", expression = "java(\"/product/images/\" + productEntity.getProductId())")
    ProductResponseDTO toProductDTO(ProductEntity productEntity);

//    Map từ etity sanng DTO để get dữ liệu
    List<ProductResponseDTO> toProductDTOList(List<ProductEntity> productEntityList);
//    Map request sang  Réponse
//    ProductResponseDTO toProductResponse(Optional<ProductEntity> productDTO);
}
