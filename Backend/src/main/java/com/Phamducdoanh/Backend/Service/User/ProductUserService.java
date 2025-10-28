package com.Phamducdoanh.Backend.Service.User;

import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Maper.ProductMaper;
import com.Phamducdoanh.Backend.Maper.UserMaper;
import com.Phamducdoanh.Backend.Repository.ProductRepository;
import com.Phamducdoanh.Backend.Repository.UserRepository;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class ProductUserService {
    final ProductRepository productRepository;
    final ProductMaper productMaper;

    //    Hàm get all
    public Page<ProductResponseDTO> findAll(Pageable pageable){
        Page<ProductEntity> productPage = productRepository.findAll(pageable);
        return productPage.map(productMaper::toProductDTO);
    }
//    Hàm tìm kiếm theo tên
    public List<ProductResponseDTO> findByName(String productName){
        List<ProductEntity> listName = productRepository.findByProductNameContainingIgnoreCase(productName);
        if(listName.isEmpty())
            throw new AppExeption(ErrorCode.PRODUCTNAME_NOTFOUND);

        return productMaper.toProductDTOList(listName);
    }
//    Hàm tìm kiếm theo danh mục sản phẩm
    public List<ProductResponseDTO> findByCategory_CategoryId(Long categoryId){
        List<ProductEntity> listByCategory = productRepository.findByCategory_CategoryId(categoryId);
        if(listByCategory.isEmpty())
            throw  new AppExeption(ErrorCode.FINDBYCATEGORY_NOTFOUND);
        return productMaper.toProductDTOList(listByCategory);
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
}
