package com.Phamducdoanh.Backend.controler.Admin;

import com.Phamducdoanh.Backend.DTO.Request.CategoyCreationDTO;
import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.CategoryResonseDTO;
import com.Phamducdoanh.Backend.Maper.CategoryMaper;
import com.Phamducdoanh.Backend.Service.Admin.CategoryService;
import com.Phamducdoanh.Backend.entity.CategoryEntity;
import com.Phamducdoanh.Backend.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/admin/category")
public class CategoryControler {
    final CategoryService categoryService;
    final CategoryMaper categoryMaper;
//API tạo mới một category
    @PostMapping("/createcategory")
    ApiResponse<CategoryResonseDTO> createCategory(@RequestBody CategoyCreationDTO category) {
        ApiResponse<CategoryResonseDTO> response = new ApiResponse<>();
        response.setResult(categoryService.createCategory(category));
        response.setSuccess(true);
        return response;
    }
//    API get all dữ liệu của category
    @GetMapping("/getcategory")
    ApiResponse<List<CategoryResonseDTO>> getCategory() {
        ApiResponse<List<CategoryResonseDTO>> response = new ApiResponse<>();
        response.setResult(categoryService.getAllCategory());
        response.setSuccess(true);
        return response;
    }
//    API update thông category
    @PutMapping("/{cateoryId}")
    ApiResponse<CategoryResonseDTO> updateCategory(@PathVariable Long cateoryId, @RequestBody CategoyCreationDTO categoryDTO) {
        ApiResponse<CategoryResonseDTO> response = new ApiResponse<>();
        response.setResult(categoryService.updateCategory(cateoryId, categoryDTO));
        response.setSuccess(true);
        return response;
    }
//    API xóa category
    @DeleteMapping("{cateoryId}")
    String delleteCategory(@PathVariable Long cateoryId) {
       categoryService.deleteCategory(cateoryId);
       return "Category deleted successfully";
    }
}
