package com.bookstore.configuration;

import java.beans.BeanProperty;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bookstore.entity.Users;

import com.bookstore.enums.Role;
import com.bookstore.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    private PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(prefix = "spring", value = "datasource.driverClassName", havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        log.info("init application");
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                HashSet<String> roles = new HashSet<>();
                roles.add(Role.ADMIN.name());

                Users user = Users.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        // .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin. Please change it");
            }
        };
    }

}