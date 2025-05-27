package com.example.travelweb.service.implement;

import com.example.travelweb.conventer.AdminMapper;
import com.example.travelweb.dto.request.AdminCreateRequest;
import com.example.travelweb.dto.request.UserCreation;
import com.example.travelweb.dto.response.AdminResponse;
import com.example.travelweb.entity.Admin;
import com.example.travelweb.entity.User;
import com.example.travelweb.repository.AdminRepository;
import com.example.travelweb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminMapper adminMapper;

    public AdminResponse createAdmin(AdminCreateRequest request){
        Admin admin = adminMapper.toEntity(request);
        admin.setCreatedDate(LocalDate.now());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        Admin saved = adminRepository.save(admin);
        return adminMapper.toResponse(saved);
    }

    public Admin getAdminById(Long adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }
}
