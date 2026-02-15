package com.canhhocit.Library_Managerment.config;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.canhhocit.Library_Managerment.entities.User;
import com.canhhocit.Library_Managerment.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123");
            admin.setFullName("System Admin");
            admin.setRole("ADMIN");
            admin.setStatus("ACTIVE");
            admin.setCreatedAt(LocalDateTime.now());
            userRepository.save(admin);
            log.info("CREATED admin account: username: admin, password:123. Let's Change your Password!");
        }else{
            log.info("admin Account is existed!");
        }
    }
}
