package com.Phamducdoanh.Backend.Service.User;

import com.Phamducdoanh.Backend.Builder.ProductSearchBuilder;
import com.Phamducdoanh.Backend.DTO.Response.PageResponseDTO;
import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Maper.ProductMaper;
import com.Phamducdoanh.Backend.Maper.UserMaper;
import com.Phamducdoanh.Backend.Repository.ProductCustom;
import com.Phamducdoanh.Backend.Repository.ProductRepository;
import com.Phamducdoanh.Backend.Repository.UserRepository;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class ProductUserService {
    final ProductRepository productRepository;
    final ProductMaper productMaper;
    final ProductCustom  productCustom;

    //    Hàm get all
    public Page<ProductResponseDTO> findAllUser(Pageable pageable){
        Page<ProductEntity> productPage = productRepository.findAll(pageable);
        return productPage.map(productMaper::toProductDTO);
    }
//    Hàm tìm kiếm theo tên
    public PageResponseDTO<ProductResponseDTO> findByName(String productName, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> listName = productRepository.findByProductNameContainingIgnoreCase(productName, pageable);
        if(listName.isEmpty())
            throw new AppExeption(ErrorCode.PRODUCTNAME_NOTFOUND);

       List<ProductResponseDTO> contendDTO = productMaper.toProductDTOList(listName.getContent());

        return PageResponseDTO.<ProductResponseDTO>builder()
                .content(contendDTO)
                .pageNumber(listName.getNumber())
                .pageSize(listName.getSize())
                .totalElements(listName.getTotalElements())
                .totalPages(listName.getTotalPages())
                .build();
    }
//    Hàm tìm kiếm theo danh mục sản phẩm
public PageResponseDTO<ProductResponseDTO> findByCategory_CategoryId(Long categoryId, int page, int size) {
    // Tạo đối tượng Pageable (trang bắt đầu từ 0)
    Pageable pageable = PageRequest.of(page, size);

    //Gọi Repository để lấy Page object
    Page<ProductEntity> productPage = productRepository.findByCategory_CategoryId(categoryId, pageable);

    if (productPage.isEmpty()) {
        throw new AppExeption(ErrorCode.FINDBYCATEGORY_NOTFOUND);
    }

    // Map List<Entity> sang List<DTO>
    List<ProductResponseDTO> contentDTO = productMaper.toProductDTOList(productPage.getContent());

    // Trả về đối tượng PageResponseDTO
    return PageResponseDTO.<ProductResponseDTO>builder()
            .content(contentDTO)
            .pageNumber(productPage.getNumber())
            .pageSize(productPage.getSize())
            .totalElements(productPage.getTotalElements())
            .totalPages(productPage.getTotalPages())
            .build();
}
// Tìm kiếm theo id danh mục, tên, giá
    public List<ProductResponseDTO> findProduct(Long categoryId, String productName, Integer minPrice, Integer maxPrice){
        var listProduct = productRepository.searchProducts(categoryId, productName, minPrice, maxPrice);
        if(listProduct.isEmpty())
            throw new AppExeption(ErrorCode.PRODUCTNAME_NOTFOUND);
        return productMaper.toProductDTOList(listProduct);
    }
//    Tìm theo id
    public ProductResponseDTO findById(Long productId){
        ProductEntity product = productRepository.findByProductId(productId);
        return productMaper.toProductDTO(product);
    }
//    tìm theo filter
    public  List<ProductResponseDTO> filterProduct(ProductSearchBuilder builder){
        var listProduct = productCustom.findProductsCustom(builder);
        return productMaper.toProductDTOList(listProduct);
    }
}
