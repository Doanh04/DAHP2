    package com.Phamducdoanh.Backend.controler.Checkout;

    import com.Phamducdoanh.Backend.DTO.Response.ApiResponse;
    import com.Phamducdoanh.Backend.Repository.OrderRepository;
    import com.Phamducdoanh.Backend.Repository.PaymentRepository;
    import com.Phamducdoanh.Backend.VNPay.VNPayUltil;
    import com.Phamducdoanh.Backend.entity.OrderEntity;
    import com.Phamducdoanh.Backend.entity.PaymentEntity;
    import lombok.AccessLevel;
    import lombok.RequiredArgsConstructor;
    import lombok.experimental.FieldDefaults;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.Map;

    @RestController
    @RequestMapping("/api/payment")
    @RequiredArgsConstructor
    @Slf4j
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public class VNPayControler {
        PaymentRepository paymentRepository;
        OrderRepository orderRepository;
        VNPayUltil vnpayUtil;

        @GetMapping("/vnpay-return")
    //    @PreAuthorize("hasAuthority('ROLE_USER')")
        public ApiResponse vnpayReturn(@RequestParam Map<String, String> allParams){

            // Lấy chữ ký gốc
//            String secureHash = allParams.get("vnp_SecureHash");

            // Xóa secureHash khỏi map để build lại rawHash
//            allParams.remove("vnp_SecureHash");
//            allParams.remove("vnp_SecureHashType");

            // Verify
//            if (!vnpayUtil.verifySignature(allParams, secureHash)) {
//                return ApiResponse.builder()
//                        .code(1000)
//                        .message("Sai chữ ký - Invalid Signature")
//                        .build();
//            }

            // Tiếp tục xử lý
            String txnRef = allParams.get("vnp_TxnRef");
            String responseCode = allParams.get("vnp_ResponseCode");

            PaymentEntity payment = paymentRepository.findByTransactionId(txnRef);
            if(payment == null)
                return ApiResponse.builder()
                        .code(1000)
                        .message("Payment not found")
                        .build();

            if("00".equals(responseCode)){
                if(!"PAID".equals(payment.getPaymentStatus())){
                    payment.setPaymentStatus("PAID");
                    OrderEntity order = payment.getOrder();
                    order.setStatus("PAID");
                    paymentRepository.save(payment);
                    orderRepository.save(order);
                }
                return ApiResponse.builder()
                        .code(1000)
                        .message("Payment successfully")
                        .build();
            } else {
                payment.setPaymentStatus("FAILED");
                paymentRepository.save(payment);
                return ApiResponse.builder()
                        .code(1000)
                        .message("Payment failed")
                        .build();
            }
        }
    }
