package com.Phamducdoanh.Backend.controler.User;

import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.CategoryResonseDTO;
import com.Phamducdoanh.Backend.DTO.Response.CategoryWithProductsDTO;
import com.Phamducdoanh.Backend.Service.Admin.CategoryService;
import com.Phamducdoanh.Backend.Service.User.ProductTop10inCategory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/dasboard/category")
public class CategoryContrer {
    final CategoryService categoryService;
    final ProductTop10inCategory  productTop10inCategory;
    @GetMapping("/getcategory")
    ApiResponse<List<CategoryResonseDTO>> getCategory() {
        ApiResponse<List<CategoryResonseDTO>> response = new ApiResponse<>();
        response.setResult(categoryService.getAllCategory());
        response.setSuccess(true);
        return response;
    }
    @GetMapping("/top-products")
    public ApiResponse<List<CategoryWithProductsDTO>> getCategoriesWithTopProducts() {
        ApiResponse<List<CategoryWithProductsDTO>> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setResult(productTop10inCategory.getCategoriesWithTopProducts());
        return response;
    }
}
