package com.Phamducdoanh.Backend.controler.User;

import com.Phamducdoanh.Backend.Service.User.ProductUserService;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("dashboard")
public class ProductUserControler {
    final ProductUserService productUserService;
//    API get all product
    @GetMapping("/productall")
    public List<ProductEntity> findAllProducts(){
        return productUserService.findAll();
    }
//    API get Product theo id category
    @GetMapping("categoryid-product")
    public List<ProductEntity> findAllProductsByCategoryId(@PathVariable Long categoryId){
        return productUserService.findByCategory_CategoryId(categoryId);
    }
//    API get Product theo name
    @GetMapping("getname")
    public List<ProductEntity> findByName(@RequestParam String productName){
        return productUserService.findByName(productName);
    }
}
