package com.Phamducdoanh.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "permission")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PermisstionEntity {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "permission_name", unique = true, nullable = false)
    String permisionName;
    @Column(name = "description")
    String description;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    Set<RolesEntity> roles = new HashSet<>();
}
