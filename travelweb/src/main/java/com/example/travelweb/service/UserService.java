package com.example.travelweb.service;

import com.example.travelweb.dto.request.ChangePasswordRequest;
import com.example.travelweb.dto.request.UpdateUserRequest;
import com.example.travelweb.dto.request.UserCreation;
import com.example.travelweb.dto.response.UserResponse;
import com.example.travelweb.entity.User;
import com.example.travelweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${avatar.upload.dir}")
    private String uploadDir;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User userCreation(UserCreation request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setCreatedDate(new Date());
        return userRepository.save(user);
    }

    public User updateUser(Long userId, UpdateUserRequest updateUserDto) {
        User user = findById(userId);
        user.setFullName(updateUserDto.getFullName());
        user.setAddress(updateUserDto.getAddress());
        user.setEmail(updateUserDto.getEmail());
        user.setPhoneNumber(updateUserDto.getPhoneNumber());
        user.setUpdatedDate(new Date());
        return userRepository.save(user);
    }

    public boolean changePassword(Long userId, ChangePasswordRequest changePasswordDto) {
        User user = findById(userId);
        if (passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            if (passwordEncoder.matches(changePasswordDto.getNewPassword(), user.getPassword())) {
                return false;
            }
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            user.setUpdatedDate(new Date());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public String updateAvatar(Long userId, MultipartFile avatarFile) {
        User user = findById(userId);
        String fileName = System.currentTimeMillis() + "." + avatarFile.getOriginalFilename().substring(avatarFile.getOriginalFilename().lastIndexOf(".") + 1);
        Path filePath = Paths.get(uploadDir, fileName);

        try {
            // Xóa ảnh cũ nếu có
            if (user.getAvatar() != null) {
                Path oldFilePath = Paths.get(uploadDir, user.getAvatar());
                if (Files.exists(oldFilePath)) {
                    Files.delete(oldFilePath);
                }
            }

            // Lưu ảnh mới
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, avatarFile.getBytes());
            user.setAvatar(fileName);
            user.setUpdatedDate(new Date());
            userRepository.save(user);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Không thể lưu ảnh đại diện: " + e.getMessage());
        }
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + userId));
    }

    public Page<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);

        Page<UserResponse> dtoPage = usersPage.map(user -> {
            UserResponse dto = new UserResponse();
            dto.setUserId(user.getUserID());
            dto.setUsername(user.getUsername());
            dto.setPassword(user.getPassword());
            dto.setFullName(user.getFullName());
            dto.setAddress(user.getAddress());
            dto.setPhone(user.getPhoneNumber());
            dto.setEmail(user.getEmail());
            if (user.getAvatar() != null) {
                dto.setAvatarUrl("/ltweb/images/avatar/" + user.getAvatar());
            } else {
                dto.setAvatarUrl(null);
            }
            return dto;
        });

        return dtoPage;
    }

    public UserResponse getUserProfile(Long id) {
        User user = findById(id);
        return UserResponse.builder()
                .userId(user.getUserID())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .address(user.getAddress())
                .password(user.getPassword())
                .phone(user.getPhoneNumber())
                .email(user.getEmail())
                .avatarUrl(user.getAvatar())
                .build();
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with id " + id + " does not exist.");
        }
        userRepository.deleteById(id);
    }
}
