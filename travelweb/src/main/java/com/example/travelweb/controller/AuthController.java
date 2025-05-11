//package com.example.travelweb.controller;
//
//import com.example.travelweb.dto.request.LoginRequest;
//import com.example.travelweb.dto.request.SignupRequest;
//import com.example.travelweb.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
//public class AuthController {
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {
//        try {
//            User user = userService.registerUser(signupRequest);
//            return ResponseEntity.ok("Đăng ký thành công: " + user.getUsername());
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        try {
//            User user = userService.authenticateUser(loginRequest);
//            return ResponseEntity.ok("Đăng nhập thành công: " + user.getUsername());
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//}
