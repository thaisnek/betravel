package com.example.travelweb.service;

import com.example.travelweb.dto.request.AdminCreateRequest;
import com.example.travelweb.dto.response.AdminResponse;

public interface AdminService {
    AdminResponse createAdmin(AdminCreateRequest request);
}
