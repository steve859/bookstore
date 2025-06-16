package com.bookstore.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.constant.PredefinedRole;
import com.bookstore.dto.request.UserCreationRequest;
import com.bookstore.dto.request.UserUpdateRequest;
import com.bookstore.dto.response.UserResponse;
import com.bookstore.entity.Roles;
import com.bookstore.entity.Users;
import com.bookstore.exception.AppException;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.UserMapper;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        Users user = userMapper.toUser(request);
        user.setDebtAmount(BigDecimal.ZERO);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Roles> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest req) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (req.getFirstName() != null && !req.getFirstName().isBlank()) {
            user.setFirstName(req.getFirstName());
        }
        if (req.getLastName() != null && !req.getLastName().isBlank()) {
            user.setLastName(req.getLastName());
        }
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        if (req.getDob() != null) {
            user.setDob(req.getDob());
        }
        if (req.getPhone() != null && !req.getPhone().isBlank()) {
            user.setPhone(req.getPhone());
        }
        if (req.getRoles() != null && !req.getRoles().isEmpty()) {
            // biến List<String> roles -> Set<Role> tương tự resolveCategories
            user.setRoles(resolveRoles(req.getRoles()));
        }
        // userMapper.updateUser(user, request);
        // user.setPassword(passwordEncoder.encode(request.getPassword()));
        // var roles = roleRepository.findAllById(request.getRoles());
        // user.setRoles(new HashSet<>(roles));
        Users saved = userRepository.save(user);
        return userMapper.toUserResponse(saved);
    }

    public void deleteUser(String userId) {
        Users user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    @Transactional(readOnly = true)
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        log.info("Authenticated username: {}", name);
        Users user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Hibernate.initialize(user.getRoles());
        log.info("Roles: {}", user.getRoles());
        Hibernate.initialize(user.getRoles());
        return userMapper.toUserResponse(user);
    }

    // public Users getUserEntity(){}
    private Set<Roles> resolveRoles(List<String> rolesString) {
        Set<Roles> rolesSet = new HashSet<>();
        for (String roleName : rolesString) {
            // tìm role đã tồn tại
            Roles role = roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        // nếu chưa có thì tạo mới và lưu
                        Roles newRole = Roles.builder()
                                .name(roleName)
                                .build();
                        return roleRepository.save(newRole);
                    });
            rolesSet.add(role);
        }
        return rolesSet;
    }

    public List<UserResponse> getUserExceptAdmin() {
        return userRepository.findAllByRoleNot(PredefinedRole.ADMIN_ROLE).stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }
}
