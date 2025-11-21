package com.Phamducdoanh.Backend.controler.Checkout;

import com.Phamducdoanh.Backend.DTO.Request.OrderRequestDTO;
import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
import com.Phamducdoanh.Backend.DTO.Response.OrderResponseDTO;
import com.Phamducdoanh.Backend.Service.Order.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Order {
    OrderService orderService;

    @PostMapping("/checkout")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ApiResponse<Map<String, Object>> checkout(@RequestBody OrderRequestDTO requestDTO, HttpServletRequest request) throws Exception {
        Map<String, Object> result = orderService.checkout(requestDTO, request);
        ApiResponse<Map<String, Object>> response = ApiResponse.<Map<String, Object>>builder()
                .code(1000)
                .result(result)
                .success(true)
                .build();
        return response;
    }
    @GetMapping("/getOrder")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')||hasAuthority('ROLE_MANAGERMENT')||hasAuthority('ROLE_STAFF')")
    public ApiResponse<Page<OrderResponseDTO>> getOrder(Pageable pageable){
        Page<OrderResponseDTO> order = orderService.findAllOrders(pageable);
         ApiResponse<Page<OrderResponseDTO>> response = ApiResponse.<Page<OrderResponseDTO>>builder()
                 .code(1000)
                 .success(true)
                 .result(order)
                 .build();
         return response;
    }
}
