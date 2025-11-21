package com.Phamducdoanh.Backend.Service.Order;

import com.Phamducdoanh.Backend.DTO.Request.OrderRequestDTO;
import com.Phamducdoanh.Backend.DTO.Response.OrderResponseDTO;
import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Maper.OrdersMaper;
import com.Phamducdoanh.Backend.Repository.*;
import com.Phamducdoanh.Backend.Service.User.AccoutUserService;
import com.Phamducdoanh.Backend.VNPay.VNPayUltil;
import com.Phamducdoanh.Backend.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderService {
    OrdersMaper ordersMaper;
    ProductRepository  productRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;
    OrderDertailRepository orderDertailRepository;
    private final PaymentRepository paymentRepository;
    VNPayUltil vnPayUltil;

    public String getUserId(){
        // Lấy đối tượng Authentication từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra nếu đối tượng là JwtAuthenticationToken
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            //Lấy claim 'userId' từ Jwt
            Object userIdClaim = jwt.getClaim("userId");

            //Kiểm tra và trả về userId
            if(userIdClaim instanceof String userIdString) {
                return userIdString;
            }
            // Nếu claim tồn tại nhưng không phải String (hoặc null), ngoại lệ
            throw new AppExeption(ErrorCode.UNAUTHENTICATED);
        }

        // Nếu không phải là JwtAuthenticationToken, tức là chưa xác thực
        throw new AppExeption(ErrorCode.UNAUTHENTICATED);
    }

    @Transactional
    public Map<String, Object> checkout(OrderRequestDTO requestDTO, HttpServletRequest request) throws Exception {
        String userId = getUserId();
        UserEntity user = userRepository.findByUserId(userId);
        if(user == null) throw new AppExeption(ErrorCode.USER_NOTFOUND);

        // Tính tổng tiền mới
        BigDecimal total = requestDTO.getItems().stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tạo hoặc cập nhật đơn hàng mới
        OrderEntity order = OrderEntity.builder()
                .user(user)
                .orderDate(new Date())
                .shippingAdress(requestDTO.getShippingAddress())
                .totalAmout(total)
                .build();
        orderRepository.save(order);

        // Xóa các chi tiết cũ (nếu có) và lưu chi tiết mới
        orderDertailRepository.deleteByOrder(order);
        requestDTO.getItems().forEach(item -> {
            ProductEntity product = productRepository.findByProductId(item.getProductId());
            if(product == null) throw new AppExeption(ErrorCode.PRODUCTNAME_NOTFOUND);

            OrderDetailEntity detail = OrderDetailEntity.builder()
                    .order(order)
                    .productDetail(product)
                    .quantity(item.getQuantity())
                    .unitPrice(item.getUnitPrice())
                    .build();
            orderDertailRepository.save(detail);
        });

        // Chuẩn bị kết quả trả về
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", order.getOrderId());
        result.put("totalAmount", total);

        // Xử lý thanh toán
        if("COD".equalsIgnoreCase(requestDTO.getPaymentMethod())){
            PaymentEntity payment = PaymentEntity.builder()
                    .order(order)
                    .amount(total)
                    .paymentDate(new Date())
                    .paymentStatus("WAITING")
                    .build();
            paymentRepository.save(payment);

            OrderEntity orderUpdate = OrderEntity.builder()
                    .user(user)
                    .orderDate(new Date())
                    .paymentMethod("COD")
                    .status("PENDING")
                    .shippingAdress(requestDTO.getShippingAddress())
                    .totalAmout(total)
                    .build();
            orderRepository.save(orderUpdate);
            result.put("paymentMethod", "COD");
        } else if("VNPAY".equalsIgnoreCase(requestDTO.getPaymentMethod())){
            // TẠO transactionId DUY NHẤT ngay cả cho lần đầu, để tránh lỗi nếu user checkout lại
            String newTxnRef = "ORDER_" + order.getOrderId() + "_" + System.currentTimeMillis();

            PaymentEntity payment = PaymentEntity.builder()
                    .order(order)
                    .amount(total)
                    .paymentDate(new Date())
                    .paymentStatus("WAITING")
                    .transactionId(newTxnRef)
                    .build();
            paymentRepository.save(payment);

            OrderEntity orderUpdate = OrderEntity.builder()
                    .user(user)
                    .orderDate(new Date())
                    .paymentMethod("VNPAY")
                    .status("PAID")
                    .shippingAdress(requestDTO.getShippingAddress())
                    .totalAmout(total)
                    .build();
            orderRepository.save(orderUpdate);

            // Truyền transactionId, totalAmount và orderId vào VnpayUtil
            String vnpUrl = vnPayUltil.createPaymentUrl(payment.getTransactionId(), total , request);
            result.put("paymentMethod", "VNPAY");
            result.put("vnpUrl", vnpUrl);
        }

        return result;
    }
//    @Transactional
//    public String regenerateVnpayUrl(Long orderId) {
//        OrderEntity order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new AppExeption(ErrorCode.ORDER_NOTFOUND));
//
//        if(!"PENDING".equals(order.getStatus())) {
//            throw new AppExeption(ErrorCode.ORDER_ALREADY_PAID);
//        }
//        String newTxnRef = "ORDER_" + order.getOrderId() + "_" + System.currentTimeMillis();
//        // Cập nhật payment mới
//        PaymentEntity payment = PaymentEntity.builder()
//                .order(order)
//                .amount(order.getTotalAmout())
//                .paymentStatus("WAITING")
//                .transactionId(newTxnRef) // 👈 Lưu transactionId DUY NHẤT
//                .build();
//        paymentRepository.save(payment);
//
//        // Sinh link VNPay mới
//        String vnpUrl = vnpayUtil.generatePaymentUrl(newTxnRef, order.getTotalAmout(), order.getOrderId());
//        return vnpUrl;
//    }

    public Page<OrderResponseDTO> findAllOrders(Pageable pageable){
        Page<OrderEntity> orderPage = orderRepository.findAll(pageable);
        return orderPage.map(ordersMaper::toOrderResponseDTO);
    }

    public void assignOrder(Long orderId, String salespersonId){
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        UserEntity salesperson = userRepository.findById(salespersonId)
                .orElseThrow(() -> new RuntimeException("Salesperson not found"));
        order.setSalesperson(salesperson);
        order.setStatus("ASSIGNED");
        orderRepository.save(order);
    }

}
