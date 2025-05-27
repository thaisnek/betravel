package com.example.travelweb.service;

import com.example.travelweb.configuration.JwtUtil;
import com.example.travelweb.dto.request.LoginRequest;
import com.example.travelweb.dto.request.RegisterRequest;
import com.example.travelweb.dto.response.JwtResponse;
import com.example.travelweb.entity.Admin;
import com.example.travelweb.entity.User;
import com.example.travelweb.repository.AdminRepository;
import com.example.travelweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public void registerUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .build();
        userRepository.save(user);
    }

    public JwtResponse login(LoginRequest request) {
        var adminOpt = adminRepository.findByUsername(request.getUsername());
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
                throw new RuntimeException("Invalid password");
            }
            String token = jwtUtil.generateToken(admin.getUsername(), "ADMIN",admin.getAdminID());
            return new JwtResponse(token, "ADMIN");
        }

        var userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid password");
            }
            String token = jwtUtil.generateToken(user.getUsername(), "USER", user.getUserID());
            return new JwtResponse(token, "USER");
        }

        throw new RuntimeException("User or Admin not found");
    }
}
