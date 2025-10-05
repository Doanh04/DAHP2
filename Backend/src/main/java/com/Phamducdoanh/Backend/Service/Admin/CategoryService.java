package com.Phamducdoanh.Backend.Service.Admin;

import com.Phamducdoanh.Backend.DTO.Request.CategoyCreationDTO;
import com.Phamducdoanh.Backend.DTO.Response.CategoryResonseDTO;
import com.Phamducdoanh.Backend.Maper.CategoryMaper;
import com.Phamducdoanh.Backend.Repository.CategoryRepository;
import com.Phamducdoanh.Backend.entity.CategoryEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class CategoryService {
    final CategoryRepository categoryRepository;
    final CategoryMaper categoryMaper;

//    Hàm tạo Category
    public CategoryEntity createCategory(CategoyCreationDTO categoryDTO) {
        CategoryEntity category = categoryMaper.toCategoryCreation(categoryDTO);

        return categoryRepository.save(category);
    }
//    Hàm update Category
    public CategoryEntity updateCategory(Long categoryId ,CategoyCreationDTO categoryCreationDTO){
        CategoryEntity category = categoryRepository.findByCategoryId(categoryId);

        categoryMaper.updateCategory(category, categoryCreationDTO);
        return categoryRepository.save(category);
    }
//    Hàm lấy danh sách các category
    public List<CategoryResonseDTO> getAllCategory(){
        List<CategoryEntity> listCategory = categoryRepository.findAll();
        return categoryMaper.toCategoryResponseDTO(listCategory);
    }
//    Hàm xóa category
    public  void deleteCategory(Long categoryId){
        categoryRepository.deleteById(categoryId);
    }
}
