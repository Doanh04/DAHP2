package com.Phamducdoanh.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productId;
    @Column(name = "productname", nullable = false)
    String productName;
    @Column(name = "brand")
    String brand;
    @Column(name = "quantity", nullable = false)
    Long quantity;
    @Column(name="description")
    String description;
//    Convert sạng byte để lưu ảnh vào DB
    @Lob
    @Column(name = "image")
    byte[] image;
    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    Date createdAt;
    @Column(name = "isActive")
    Boolean isActive;

//    QH n-1 tới bảng category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categoryId", nullable = false)
    CategoryEntity category;

//    QH 1 - n tới bảng CartItem
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<CartItemEntity> cartItemsList;

//    QH 1 - n tới bảng order detail
    @OneToMany(mappedBy = "productDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<OrderDetailEntity> orderDetailList;
}
