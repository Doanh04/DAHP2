package com.Phamducdoanh.Backend.controler.Cart;

import com.Phamducdoanh.Backend.DTO.Request.CartItemRequestDTO;
import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.CartResponseDTO;
import com.Phamducdoanh.Backend.Service.Cart.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
@RestController
@RequestMapping("user/cart")
@Slf4j
@RequiredArgsConstructor
public class CartControler {
    CartService  cartService;
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ApiResponse<CartResponseDTO>> addOrUpdateCart(@RequestBody CartItemRequestDTO request){
        CartResponseDTO cartDetail = cartService.addOrUpdateItem(
                request.getProductId(),
                request.getQuantity()
        );
        ApiResponse<CartResponseDTO> response = ApiResponse.<CartResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .message("OK")
                .result(cartDetail)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getCartItem")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ApiResponse<CartResponseDTO>> getCartDetail(){
        CartResponseDTO cartDetail = cartService.getCartDetails();

        ApiResponse<CartResponseDTO> response = ApiResponse.<CartResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .message("OK")
                .result(cartDetail)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleleCartItem")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ApiResponse<CartResponseDTO>> removeItem(
            @PathVariable Long cartItemId) {


        CartResponseDTO cartDetails = cartService.deleteItem(cartItemId);

        ApiResponse<CartResponseDTO> response = ApiResponse.<CartResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Sản phẩm đã được xóa")
                .result(cartDetails)
                .build();

        return ResponseEntity.ok(response);
    }
}
