package com.bookstore.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
    String phone;
    List<String> roles;
}
