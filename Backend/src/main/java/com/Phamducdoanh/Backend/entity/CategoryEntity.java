package com.Phamducdoanh.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

//Bảng danh mục sản phẩm
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long categoryId;
    @Column(name = "categoryName", nullable = false)
    String categoryName;
    @Column(name = "description")
    String description;

//    MQH 1-n tới bảng Product
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductEntity> products;
}
