package com.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.bookstore.dto.request.UserCreationRequest;
import com.bookstore.dto.response.UserResponse;
import com.bookstore.entity.Users;
import com.bookstore.exception.AppException;
import com.bookstore.repository.UserRepository;

@SpringBootTest

@TestPropertySource("/test.properties")

public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
    private UserCreationRequest request;
    private UserResponse userResponse;
    private Users user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(1990, 1, 1);
        request = UserCreationRequest.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .dob(dob)
                .build();
        userResponse = UserResponse.builder()
                .id("1234")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();
        user = Users.builder()
                .id("1234")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();

    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        // WHEN
        var response = userService.createUser(request);
        // THEN
        Assertions.assertThat(response.getId()).isEqualTo("cf0600f538b3");
        Assertions.assertThat(response.getUsername()).isEqualTo("john");
    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));
        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode())
                .isEqualTo(1002);

    }
}