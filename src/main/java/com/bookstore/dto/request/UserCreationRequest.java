package com.bookstore.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.*;
import lombok.Singular;
import lombok.experimental.FieldDefaults;
import java.util.Set;
import com.bookstore.entity.Role;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 4, message = "INVALID_USERNAME")
    String username;
    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;
    String firstName;
    String lastName;
    String email;
    String phone;
    LocalDate dob;

}
