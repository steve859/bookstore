package com.bookstore.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
// @Table(name = "roles")
public class Roles {
    @Id
    @Column(name = "role_name")
    String name;
    @Column(name = "role_description")
    String description;
    // @EqualsAndHashCode.Exclude
    // @ToString.Exclude
    @ManyToMany
    Set<Permissions> permissions = new HashSet<>();
    // // @ManyToMany(mappedBy = "roles")
    // // @JsonBackReference
    // Set<Users> users = new HashSet<>();

}
