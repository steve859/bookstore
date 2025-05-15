package com.bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
// @Table(name = "permissions")
public class Permissions {
    @Id
    @Column(name = "permission_name")
    String name;
    @Column(name = "permission_description")
    String description;
    // @ManyToMany
    // Set<Roles> roles = new HashSet<>();

}
