package com.Phamducdoanh.Backend.controler.Admin;

import com.Phamducdoanh.Backend.DTO.Request.ProductDTO;
import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.ProductResponseDTO;
import com.Phamducdoanh.Backend.Service.Admin.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    public ApiResponse<List<ProductResponseDTO>> getAllProducts(){
        List<ProductResponseDTO> resultDTO = productService.findAll();
        ApiResponse<List<ProductResponseDTO>> response = ApiResponse.<List<ProductResponseDTO>>builder()
                .code(1000)
                .success(true)
                .message("success")
                .result(resultDTO)
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
    public String deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return "Sản phẩm đã được xóa";
    }
//    API get name
    @GetMapping("/productbyname")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGERMENT')")
    public ApiResponse<List<ProductResponseDTO>> productByName(@RequestParam(name = "productname")
                                                                      String productName){
        List<ProductResponseDTO> resultDTO = productService.findByName(productName);
        ApiResponse<List<ProductResponseDTO>> response = ApiResponse.<List<ProductResponseDTO>>builder()
                .code(1000)
                .success(true)
                .message("success")
                .result(resultDTO)
                .build();
        return response;
    }
}
