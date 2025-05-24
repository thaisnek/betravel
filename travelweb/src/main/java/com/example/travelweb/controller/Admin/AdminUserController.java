package com.example.travelweb.controller.Admin;

import com.example.travelweb.dto.response.UserResponse;
import com.example.travelweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public Page<UserResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        return userService.getAllUsers(page, size);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
