package com.bookstore.service;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bookstore.dto.request.UserCreationRequest;
import com.bookstore.dto.request.UserUpdateRequest;
import com.bookstore.dto.response.UserResponse;
import com.bookstore.entity.Roles;
import com.bookstore.entity.Users;
import com.bookstore.exception.AppException;
import com.bookstore.exception.ErrorCode;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
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
                .id("cf0600f538b3")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();

        user = Users.builder()
                .id("cf0600f538b3")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // give
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // when
        var response = userService.createUser(request);

        // then
        Assertions.assertThat(response.getId()).isEqualTo("cf0600f538b3");
        Assertions.assertThat(response.getUsername()).isEqualTo("john");
    }

    @Test
    void createUser_userExisted_throwsException() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        var exception = org.junit.jupiter.api.Assertions.assertThrows(AppException.class,
                () -> userService.createUser(request));

        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_EXISTED);
    }

    @MockBean
    private RoleRepository roleRepository;

    @Test
    void updateUser_validRequest_success() {
        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
                .firstName("Johnny")
                .lastName("Updated")
                .password("newpass123")
                .roles(List.of("ADMIN"))
                .build();

        when(userRepository.findById(anyString())).thenReturn(java.util.Optional.of(user));
        when(roleRepository.findAllById(any())).thenReturn(List.of(new Roles("ADMIN", null, null)));
        when(userRepository.save(any())).thenReturn(user);

        var response = userService.updateUser("cf0600f538b3", updateRequest);

        Assertions.assertThat(response.getUsername()).isEqualTo("john");
    }

    @Test
    void updateUser_userNotFound_throwsException() {
        when(userRepository.findById(anyString())).thenReturn(java.util.Optional.empty());

        var exception = org.junit.jupiter.api.Assertions.assertThrows(AppException.class,
                () -> userService.updateUser("fake-id", new UserUpdateRequest()));

        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_EXISTED);
    }

    @Test
    void deleteUser_validUser_deletesSuccessfully() {
        when(userRepository.findById(anyString())).thenReturn(java.util.Optional.of(user));

        userService.deleteUser("cf0600f538b3");

        // khong throw exception la dat
        org.mockito.Mockito.verify(userRepository).delete(any());
    }

    @Test
    void deleteUser_userNotFound_doesNothing() {
        when(userRepository.findById(anyString())).thenReturn(java.util.Optional.empty());

        userService.deleteUser("nonexistent");

        // verify khong goi delete
        org.mockito.Mockito.verify(userRepository, org.mockito.Mockito.never()).delete(any());
    }

    @Test
    void getUser_validId_returnsUser() {
        when(userRepository.findById(anyString())).thenReturn(java.util.Optional.of(user));

        var response = userService.getUser("cf0600f538b3");

        Assertions.assertThat(response.getUsername()).isEqualTo("john");
    }

    @Test
    void getMyInfo_authenticatedUser_success() {
        org.springframework.security.core.Authentication authentication = org.mockito.Mockito
                .mock(org.springframework.security.core.Authentication.class);
        org.springframework.security.core.context.SecurityContext context = org.mockito.Mockito
                .mock(org.springframework.security.core.context.SecurityContext.class);

        when(context.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("john");
        org.springframework.security.core.context.SecurityContextHolder.setContext(context);

        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(user));

        var response = userService.getMyInfo();

        Assertions.assertThat(response.getUsername()).isEqualTo("john");
    }

}
