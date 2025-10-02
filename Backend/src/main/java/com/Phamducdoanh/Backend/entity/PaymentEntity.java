package com.Phamducdoanh.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

//Bảng thanh toán
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity

@Table(name = "Payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPayment;
    @Column(name = "paymentDate")
    Date  paymentDate;
    @Column(name = "Amount", precision = 18, scale = 2)
    BigDecimal amount;
    @Column(name = "paymentStatus")
    String paymentStatus;
    @Column(name = "transactionId")
    String transactionId;
//    MQH 1 1 tới bảng order
    @OneToOne
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    OrderEntity order;
}
