package com.Phamducdoanh.Backend.controler.Admin;

import com.Phamducdoanh.Backend.DTO.Request.ProductDTO;
import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.PageResponseDTO;
import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.Service.Admin.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/admin/product")
public class ProductControler {
    final ProductService productService;
//    Api tạo mới Product
    @PostMapping("/createproduct")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGERMENT')")
    public ApiResponse<ProductResponseDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductResponseDTO resultDTO = productService.createProduct(productDTO);
        ApiResponse<ProductResponseDTO> response = ApiResponse.<ProductResponseDTO>builder()
                .code(1000)
                .success(true)
                .result(resultDTO)
                .message("success")
                .build();
        return response;
    }

//    Api get All dữ liệu Product
    @GetMapping("/getproduct")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGERMENT')")
    public ApiResponse<Page<ProductResponseDTO>> getAllProducts(Pageable pageable){
        Page<ProductResponseDTO> resultPage= productService.findAll(pageable);
        ApiResponse<Page<ProductResponseDTO>> response = ApiResponse.<Page<ProductResponseDTO>>builder()
                .code(1000)
                .success(true)
                .message("success")
                .result(resultPage)
                .build();
        return response;
    }
//    Api Update product
    @PutMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGERMENT')")
    public ApiResponse<ProductResponseDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO){
        ProductResponseDTO resultDTO = productService.updateProduct(productId, productDTO);
        ApiResponse<ProductResponseDTO> response = ApiResponse.<ProductResponseDTO>builder()
                .code(1000)
                .success(true)
                .result(resultDTO)
                .message("success")
                .build();
        return response;
    };

//    API delete
    @DeleteMapping("{productId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGERMENT')")
    public ApiResponse deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return ApiResponse.builder()
                .code(1000)
                .success(true)
                .message("success")
                .build();
    }
//    API get name
    @GetMapping("/productbyname")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGERMENT')")
    public ApiResponse<PageResponseDTO<ProductResponseDTO>> findByName(@RequestParam(name = "productname")
                                                                       String productName,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "15") int size){
        PageResponseDTO<ProductResponseDTO> resultDTO = productService.findByName(productName, page, size);
        ApiResponse<PageResponseDTO<ProductResponseDTO>> response = ApiResponse.<PageResponseDTO<ProductResponseDTO>>builder()
                .code(1000)
                .success(true)
                .message("success")
                .result(resultDTO)
                .build();
        return response;
    }
//
}
