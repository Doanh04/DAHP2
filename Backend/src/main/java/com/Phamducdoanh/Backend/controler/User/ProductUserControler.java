package com.Phamducdoanh.Backend.controler.User;

import com.Phamducdoanh.Backend.Builder.ProductSearchBuilder;
import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.PageResponseDTO;
import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.Service.Admin.ProductService;
import com.Phamducdoanh.Backend.Service.User.ProductUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/dashboard/product")
public class ProductUserControler {
    final ProductUserService productUserService;
    private final ProductService productService;

    //    API get all product
    @GetMapping("/productall")
    public ApiResponse<Page<ProductResponseDTO>> getAllProducts(Pageable pageable){
        Page<ProductResponseDTO> resultPage= productUserService.findAllUser(pageable);
        ApiResponse<Page<ProductResponseDTO>> response = ApiResponse.<Page<ProductResponseDTO>>builder()
                .code(1000)
                .success(true)
                .message("success")
                .result(resultPage)
                .build();
        return response;
    }
//    API get Product theo id category
    @GetMapping("/categoryid-product/{categoryId}")
    public ApiResponse<PageResponseDTO<ProductResponseDTO>> findAllProductsByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        PageResponseDTO<ProductResponseDTO> result = productUserService.findByCategory_CategoryId(categoryId, page, size);

        ApiResponse<PageResponseDTO<ProductResponseDTO>> response = ApiResponse.<PageResponseDTO<ProductResponseDTO>>builder()
                .code(1000)
                .success(true)
                .message("success")
                .result(result) 
                .build();

        return response;
    }
//    API get Product theo name
//    @GetMapping("/getname")
//    public ApiResponse<PageResponseDTO<ProductResponseDTO>> findByName(@RequestParam(name = "productname")
//                                                                   String productName,
//                                                            @RequestParam(defaultValue = "0") int page,
//                                                            @RequestParam(defaultValue = "15") int size){
//        PageResponseDTO<ProductResponseDTO> resultDTO = productUserService.findByName(productName, page, size);
//        ApiResponse<PageResponseDTO<ProductResponseDTO>> response = ApiResponse.<PageResponseDTO<ProductResponseDTO>>builder()
//                .code(1000)
//                .success(true)
//                .message("success")
//                .result(resultDTO)
//                .build();
//        return response;
//    }
//
//    //api filter product
//    @GetMapping("/filterproduct")
//        public ApiResponse<List<ProductResponseDTO>> filterProduct(@RequestParam(name = "categoryId", required = false) Long categoryId,
//                                                                   @RequestParam(name = "productname", required = false) String productName,
//                                                                   @RequestParam(name = "minPrice", required = false) Integer minPrice,
//                                                                   @RequestParam(name = "maxPrice", required = false) Integer maxPrice){
//        List<ProductResponseDTO> result = productUserService.findProduct(categoryId, productName, minPrice, maxPrice);
//        ApiResponse<List<ProductResponseDTO>> response = ApiResponse.<List<ProductResponseDTO>>builder()
//                .code(1000)
//                .success(true)
//                .message("success")
//                .result(result)
//                .build();
//        return response;
//    }
    @GetMapping("{productId}")
    public ApiResponse<ProductResponseDTO> getProductById(@PathVariable Long productId){
        ProductResponseDTO result = productUserService.findById(productId);
        ApiResponse<ProductResponseDTO> response = ApiResponse.<ProductResponseDTO>builder()
                .code(1000)
                .success(true)
                .message("success")
                .result(result)
                .build();
        return response;
    };
//    API filter
    @GetMapping("/filterProduct")
    public ApiResponse<List<ProductResponseDTO>> filterProduct(
                                                        @RequestParam(required = false) String productName,
                                                        @RequestParam(required = false) Long minPrice,
                                                        @RequestParam(required = false) Long maxPrice,
                                                        @RequestParam(required = false) String Brand,
                                                        @RequestParam(required = false) Long categoryId){
        ProductSearchBuilder productSearchBuilder = ProductSearchBuilder.builder()
                .productName(productName)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .Brand(Brand)
                .CategoryId(categoryId)
                .build();
        List<ProductResponseDTO> result = productUserService.filterProduct(productSearchBuilder);
        return ApiResponse.<List<ProductResponseDTO>>builder()
                .code(1000)
                .success(true)
                .message("success")
                .result(result)
                .build();
    }
}
