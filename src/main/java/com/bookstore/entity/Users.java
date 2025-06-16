package com.bookstore.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    String id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "dob")
    LocalDate dob;
    // @ManyToMany(fetch = FetchType.EAGER)
    // @JoinTable(name = "users_roles", // Tên bảng trung gian
    // joinColumns = @JoinColumn(name = "user_id"), // Khóa ngoại từ bảng `users`
    // inverseJoinColumns = @JoinColumn(name = "role_name") // Khóa ngoại từ bảng
    // `roles`
    // )
    // @JsonManagedReference
    @Column(precision = 10, scale = 2, name = "debt_amount_user")
    BigDecimal debtAmount;

    @ManyToMany
    Set<Roles> roles = new HashSet<>();

    public Set<Roles> getRoles() {
        return this.roles;
    }

}
