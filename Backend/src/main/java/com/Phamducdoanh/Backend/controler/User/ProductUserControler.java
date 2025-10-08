package com.Phamducdoanh.Backend.controler.User;

import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.Service.User.ProductUserService;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/dashboard/product")
public class ProductUserControler {
    final ProductUserService productUserService;
//    API get all product
    @GetMapping("/productall")
    public ApiResponse<List<ProductResponseDTO>> findAllProducts(){
        List<ProductResponseDTO> result = productUserService.findAll();
        ApiResponse<List<ProductResponseDTO>> response = ApiResponse.<List<ProductResponseDTO>>builder()
                .code(1000)
                .success(true)
                .message("success")
                .result(result)
                .build();
        return response;
    }
//    API get Product theo id category
    @GetMapping("/categoryid-product/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> findAllProductsByCategoryId(@PathVariable Long categoryId){
        List<ProductResponseDTO> result = productUserService.findByCategory_CategoryId(categoryId);
        ApiResponse<List<ProductResponseDTO>> response = ApiResponse.<List<ProductResponseDTO>>builder()
                .code(1000)
                .success(true)
                .message("success")
                .result(result)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
//    API get Product theo name
    @GetMapping("/getname")
    public ApiResponse<List<ProductResponseDTO>> findByName(@RequestParam(name = "productname")
                                                                   String productName){
        List<ProductResponseDTO> resultDTO = productUserService.findByName(productName);
        ApiResponse<List<ProductResponseDTO>> response = ApiResponse.<List<ProductResponseDTO>>builder()
                .code(1000)
                .success(true)
                .message("success")
                .result(resultDTO)
                .build();
        return response;
    }
}
//Còn thiếu tìm theo giá, id danh mục, id sản phẩm