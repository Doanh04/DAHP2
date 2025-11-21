package com.Phamducdoanh.Backend.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.*;

//Bảng User
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
            @Column(name = "userid", updatable = false)
    String userId;
    @Column(name="username", nullable = false, unique = true)
    String username;
    @Column(name = "name", nullable = false, length = 100)
    String name;
    @Column(name = "email", nullable = false, length = 100)
    String email;
    @Column(name = "password", nullable = false, length = 100)
    String password;
    @Column(name = "phone", nullable = false, length = 10)
    String phone;
    @Column(name = "address")
    String address;
    @CreationTimestamp
    @Column(name = "createAt", nullable = false, updatable = false)
    Date createdAt;

//    Tạo quan hệ n - n với role
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "role_name")
    )
    Set<RolesEntity> roles = new HashSet<>();


//    Tạo quan hệ 1-1 với cart
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    CartEntity cart;

//    Tạo mối quan hệ 1 n với Order
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        List<OrderEntity> orders;

    // Các đơn hàng mà user này là người quản lý
    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    List<OrderEntity> managedOrders;

    // Các đơn hàng mà user này là nhân viên bán hàng
    @OneToMany(mappedBy = "salesperson", fetch = FetchType.LAZY)
    List<OrderEntity> salesOrders;
}
