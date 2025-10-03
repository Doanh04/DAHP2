package com.Phamducdoanh.Backend.controler.Admin;

import com.Phamducdoanh.Backend.DTO.Request.CategoyCreationDTO;
import com.Phamducdoanh.Backend.Maper.CategoryMaper;
import com.Phamducdoanh.Backend.Service.Admin.CategoryService;
import com.Phamducdoanh.Backend.entity.CategoryEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("admin")
public class CategoryControler {
    final CategoryService categoryService;
    final CategoryMaper categoryMaper;
//API tạo mới một category
    @PostMapping("/createcategory")
    CategoryEntity createCategory(@RequestBody CategoyCreationDTO category) {
        CategoryEntity savedCategory = categoryService.createCategory(category);
        return savedCategory;
    }
//    API get all dữ liệu của category
    @GetMapping("/getcategory")
    List<CategoryEntity> getCategory() {
        return categoryService.getAllCategory();
    }
//    API update thông category
    @PutMapping("/{cateoryId}")
    CategoryEntity updateCategory(@PathVariable Long cateoryId, @RequestBody CategoyCreationDTO categoryDTO) {
        return categoryService.updateCategory(cateoryId, categoryDTO);
    }
//    API xóa category
    @DeleteMapping("{cateoryId}")
    String delleteCategory(@PathVariable Long cateoryId) {
        categoryService.deleteCategory(cateoryId);
        return "Category deleted";
    }
}
