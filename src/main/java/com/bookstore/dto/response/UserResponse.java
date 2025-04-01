package com.bookstore.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String Id;
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    String phone;
    LocalDate dob;
    String role;
}