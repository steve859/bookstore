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
    String Id;
    String username;
    String firstName;
    String lastName;
    String email;
    String phone;
    LocalDate dob;
    Set<String> roles;
}