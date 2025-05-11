package com.bookstore.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    // String password;
    String firstName;
    String lastName;
    String email;
    String phone;
    LocalDate dob;
    Set<RoleResponse> roles;
}