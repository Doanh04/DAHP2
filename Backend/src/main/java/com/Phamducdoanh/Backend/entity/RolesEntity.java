package com.Phamducdoanh.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "roles")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RolesEntity {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "role_name", unique = true, nullable = false)
    String roleName;
    @Column(name = "description")
    String description;

//    Quan hệ n - n với user
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    Set<UserEntity> users = new HashSet<>();

    //      Quạn hệ N- N với permision
    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_name"),
            inverseJoinColumns = @JoinColumn(name = "permission_name")
    )
    Set<PermisstionEntity> permissions = new HashSet<>();


}
