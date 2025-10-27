package com.Phamducdoanh.Backend.Service.Admin;

import com.Phamducdoanh.Backend.DTO.Request.ProductDTO;
import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Maper.ProductMaper;
import com.Phamducdoanh.Backend.Repository.ProductRepository;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class ProductService {
    final ProductRepository productRepository;
    final ProductMaper productMaper;

    //Hàm tạo product
    public ProductResponseDTO createProduct(ProductDTO productDTO){
        ProductEntity product = productMaper.toProductcreation(productDTO);
        ProductEntity saved =  productRepository.save(product);
        return productMaper.toProductDTO(saved);
    }

//    Hàm update Product
    public ProductResponseDTO updateProduct(Long productId, ProductDTO productDTO){
        ProductEntity productEntity = productRepository.findByProductId(productId);
        productMaper.updateProduct(productEntity, productDTO);
        ProductEntity saveUpdate = productRepository.save(productEntity);
        return productMaper.toProductDTO(saveUpdate);
    }
//    Hàm xóa product
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }
//    Hàm get All product
    public Page<ProductResponseDTO> findAll(Pageable pageable){
        Page<ProductEntity> productPage = productRepository.findAll(pageable);
        return productPage.map(productMaper::toProductDTO);
    }
//    Hàm get name product
    public List<ProductResponseDTO> findByName(String productName){
        List<ProductEntity> productListName = productRepository.findByProductNameContainingIgnoreCase(productName);
        if(productListName.isEmpty())
            throw new AppExeption(ErrorCode.PRODUCTNAME_NOTFOUND);

        return productMaper.toProductDTOList(productListName);
    }


}
