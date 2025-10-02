package com.Phamducdoanh.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "cartitem")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cartItemId;
    @Column(name = "quantity", nullable = false)
    Long quantity;
//    QH của n - 1 CartItem và Cart
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartId", nullable = false)
    CartEntity cart;

//    QH n - 1 của CartItem với Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId",nullable = false )
    ProductEntity product;
}
