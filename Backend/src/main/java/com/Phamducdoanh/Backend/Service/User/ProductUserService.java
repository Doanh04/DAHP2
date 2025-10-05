package com.Phamducdoanh.Backend.Service.User;

import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
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
public class ProductUserService {
    final ProductRepository productRepository;

//    Hàm get all
    public List<ProductEntity> findAll(){
        return productRepository.findAll();
    }
//    Hàm tìm kiếm theo tên
    public List<ProductEntity> findByName(String productName){
        if(productRepository.existsByProductName(productName))
            throw new AppExeption(ErrorCode.PRODUCTNAME_NOTFOUND);

        return productRepository.findByProductName(productName);
    }
//    Hàm tìm kiếm theo danh mục sản phẩm
    public List<ProductEntity> findByCategory_CategoryId(Long categoryId){
        if(productRepository.existsByCategory_CategoryId(categoryId))
            throw  new AppExeption(ErrorCode.FINDBYCATEGORY_NOTFOUND);
        return productRepository.findByCategory_CategoryId(categoryId);
    }
}
