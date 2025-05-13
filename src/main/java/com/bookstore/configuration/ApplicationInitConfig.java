package com.bookstore.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bookstore.constant.PredefinedRole;
import com.bookstore.entity.Roles;
import com.bookstore.entity.Users;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    @NonFinal
    static final String ADMIN_USERNAME = "admin";
    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(prefix = "spring", value = "datasource.driverClassName", havingValue = "com.mysql.cj.jdbc.Driver")
    // This code snippet defines a bean named `applicationRunner` of type
    // `ApplicationRunner` in a
    // Spring configuration class. The `ApplicationRunner` interface in Spring Boot
    // allows you to
    // execute code when the application is started.
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("initializing application");
        return args -> {
            if (userRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {
                HashSet<Roles> roles = new HashSet<>();
                roleRepository.save(Roles.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build());

                Roles adminRole = roleRepository.save(Roles.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());
                roles.add(adminRole);
                Users user = Users.builder()
                        .username(ADMIN_USERNAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin. Please change it");
            }
        };
    }

}