package com.bookstore.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.*;
import lombok.Singular;
import lombok.experimental.FieldDefaults;


@Data
public class UserCreationRequest {
    @Getter
    @Size(min = 4, message = "INVALID_USERNAME")
    String username;
    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;
    String firstName;
    String lastName;

}
