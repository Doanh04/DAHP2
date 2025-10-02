package com.Phamducdoanh.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

//Bảng User
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String Userid;
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
    @Column(name = "createAt")
    Date createdAt;

//    Tạo quan hệ n - 1 với role
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", nullable = false)
//    Biến tham chiếu tới maped ở bảng roles
     RolesEntity role;

//    Tạo quan hệ 1-1 với cart
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    CartEntity cart;

//    Tạo mối quan hệ 1 n với Order
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        List<OrderEntity> orders;
}
