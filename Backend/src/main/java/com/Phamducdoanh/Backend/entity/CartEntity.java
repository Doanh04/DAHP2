package com.Phamducdoanh.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cartId;
    @Column(name = "createdate")
    Date createDate;

//    Tạo mối quan hệ 1 1 tới user
    @OneToOne
    @JoinColumn(name = "Userid", referencedColumnName = "Userid", unique = true)
    private UserEntity user;

//    Tạo mối quan hệ 1 - n tới bảng cart item
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<CartItemEntity> cartItemEntityList = new ArrayList<>();
}
