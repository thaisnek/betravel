package com.example.travelweb.controller.Admin;

import com.example.travelweb.dto.request.AdminCreateRequest;
import com.example.travelweb.dto.response.AdminResponse;
import com.example.travelweb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public AdminResponse createAdmin(@RequestBody AdminCreateRequest request) {
        return adminService.createAdmin(request);
    }
}
