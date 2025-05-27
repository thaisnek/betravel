package com.example.travelweb.controller;

import com.example.travelweb.configuration.JwtUtil;
import com.example.travelweb.dto.request.LoginRequest;
import com.example.travelweb.dto.request.RegisterRequest;
import com.example.travelweb.dto.response.JwtResponse;
import com.example.travelweb.entity.Admin;
import com.example.travelweb.entity.User;
import com.example.travelweb.repository.AdminRepository;
import com.example.travelweb.repository.UserRepository;
import com.example.travelweb.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        authenticationService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        JwtResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }
}

