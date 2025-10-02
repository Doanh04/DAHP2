package com.Phamducdoanh.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

//Bảng chi tiết đơn hàng
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orderDetail")
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderDetailId;
    @Column (name = "quantity", precision = 18, scale = 2)
    Long quantity;
    @Column(name = "unitPrice", nullable = false)
    BigDecimal unitPrice;

//MQH n - 1 tới order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false)
    OrderEntity order;

//MQH n - 1 tới product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    ProductEntity productDetail;
}
