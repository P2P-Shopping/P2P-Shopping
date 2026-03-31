package com.p2ps.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.p2ps.auth.model.Users;
import com.p2ps.auth.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users registerUser(String email, String rawPassword) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use!");
        }

        // 2. Hash-uim parola cu BCrypt
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // 3. Creăm entitatea și o salvăm
        Users newUser = new Users(email, hashedPassword);
        return userRepository.save(newUser);
    }
}