package com.Phamducdoanh.Backend.entity;

//Bảng đơn hàng

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;
    @Column(name = "orderDate")
    Date  orderDate;
    @Column(name = "status")
    String status;
    @Column(name="totalAmout", precision = 18, scale = 2)
    BigDecimal totalAmout;
    @Column(name = "shippingAdress")
    String shippingAdress;
    @Column(name = "paymentMethod")
    String paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Userid", nullable = false)
    UserEntity user;

//    MQH 1 - n tới bảng orderdetail
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<OrderDetailEntity> orderDetailEntityList;

//    MQH 1 1 tới bảng payment
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    PaymentEntity payment;
}
