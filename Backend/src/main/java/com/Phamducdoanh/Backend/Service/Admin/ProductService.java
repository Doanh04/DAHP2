package com.Phamducdoanh.Backend.Service.Admin;

import com.Phamducdoanh.Backend.DTO.Request.ProductDTO;
import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.Maper.ProductMaper;
import com.Phamducdoanh.Backend.Repository.ProductRepository;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

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
        ProductEntity saveUpdate = productRepository.save(productEntity);
        return productMaper.toProductDTO(saveUpdate);
    }
//    Hàm xóa product
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }
//    Hàm get All product
    public List<ProductResponseDTO> findAll(){
        List<ProductEntity> productList = productRepository.findAll();
        return productMaper.toProductDTOList(productList);
    }
//    Hàm get name product
    public List<ProductEntity> findByName(String productName){
        return productRepository.findByProductName(productName);
    }

}
