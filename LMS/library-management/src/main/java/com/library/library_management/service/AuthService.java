package com.library.library_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.library_management.dto.AuthRequest;
import com.library.library_management.dto.AuthResponse;
import com.library.library_management.model.User;
import com.library.library_management.repository.UserRepository;
import com.library.library_management.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Register user
    public User register(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Force normal users from frontend to "USER"
        user.setRole("USER");

        return userRepository.save(user);
    }

    // Login user
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return new AuthResponse(token, user.getRole());
    }
}