package com.example.travelweb.controller.Admin;

import com.example.travelweb.dto.request.AdminCreateRequest;
import com.example.travelweb.dto.response.AdminResponse;
import com.example.travelweb.entity.Admin;
import com.example.travelweb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public AdminResponse createAdmin(@RequestBody AdminCreateRequest request) {
        return adminService.createAdmin(request);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<AdminResponse> getAdminById(@PathVariable Long adminId) {
        AdminResponse adminResponse = adminService.getAdminById(adminId);
        return ResponseEntity.ok(adminResponse);
    }
}
