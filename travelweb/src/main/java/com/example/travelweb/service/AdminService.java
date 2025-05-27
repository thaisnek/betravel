package com.example.travelweb.service;

import com.example.travelweb.dto.request.AdminCreateRequest;
import com.example.travelweb.dto.response.AdminResponse;
import com.example.travelweb.entity.Admin;

public interface AdminService {
    AdminResponse createAdmin(AdminCreateRequest request);

    Admin getAdminById(Long adminId);
}
