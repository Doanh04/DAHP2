package com.Phamducdoanh.Backend.Resolve;

import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Repository.CategoryRepository;
import com.Phamducdoanh.Backend.entity.CategoryEntity;
import jdk.jfr.Name;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Name("mapIdTocategory") // tên để ProductMaper gọi tới
public class CategoryResolve {
    //inject CategoryRepository để truy vấn db
    final CategoryRepository categoryRepository;

    public CategoryEntity mapIdToCategory(Long categoryId){
        if(categoryId == null)
            return null;
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppExeption(ErrorCode.CATEGORY_NOTFOUND));
    }

}
