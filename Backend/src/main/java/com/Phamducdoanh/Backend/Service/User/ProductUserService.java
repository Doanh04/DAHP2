package com.Phamducdoanh.Backend.Service.User;

import com.Phamducdoanh.Backend.DTO.Request.UserDTO;
import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.DTO.Response.UserResponse;
import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Maper.ProductMaper;
import com.Phamducdoanh.Backend.Maper.UserMaper;
import com.Phamducdoanh.Backend.Repository.ProductRepository;
import com.Phamducdoanh.Backend.Repository.UserRepository;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import com.Phamducdoanh.Backend.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class ProductUserService {
    final ProductRepository productRepository;
    final ProductMaper productMaper;
    private final UserRepository userRepository;
    private final UserMaper userMaper;

    //    Hàm get all
    public List<ProductResponseDTO> findAll(){
        List<ProductEntity> listProduct = productRepository.findAll();
        return productMaper.toProductDTOList(listProduct);
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


}
