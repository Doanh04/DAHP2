package com.Phamducdoanh.Backend.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

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
            @Column(name = "userid", insertable = false, updatable = false)
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
