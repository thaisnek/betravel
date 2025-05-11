package com.example.travelweb.controller;

import com.example.travelweb.dto.request.UserCreation;
import com.example.travelweb.entity.User;
import com.example.travelweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    User createUser(@RequestBody UserCreation request) {
        return userService.userCreation(request);
    }
}
