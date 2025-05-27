package com.example.travelweb.controller.Admin;

import com.example.travelweb.dto.request.AdminCreateRequest;
import com.example.travelweb.dto.response.AdminResponse;
import com.example.travelweb.entity.Admin;
import com.example.travelweb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public AdminResponse createAdmin(@RequestBody AdminCreateRequest request) {
        return adminService.createAdmin(request);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long adminId) {
        Admin admin = adminService.getAdminById(adminId);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(admin);
    }
}
