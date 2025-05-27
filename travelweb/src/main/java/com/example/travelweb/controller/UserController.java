package com.example.travelweb.controller;

import com.example.travelweb.dto.api.ApiResponse;
import com.example.travelweb.dto.request.ChangePasswordRequest;
import com.example.travelweb.dto.request.UpdateUserRequest;
import com.example.travelweb.dto.request.UserCreation;
import com.example.travelweb.dto.response.AvatarResponse;
import com.example.travelweb.dto.response.UserResponse;
import com.example.travelweb.entity.User;
import com.example.travelweb.repository.UserRepository;
import com.example.travelweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest updateUserRequest) {
        try {
            User updatedUser = userService.updateUser(userId, updateUserRequest);
            return ResponseEntity.ok(
                    ApiResponse.<User>builder()
                            .code(200)
                            .message("Cập nhật thông tin thành công!")
                            .result(updatedUser)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<User>builder()
                            .code(400)
                            .message("Bạn chưa thay đổi thông tin nào, vui lòng kiểm tra lại!")
                            .build()
            );
        }
    }

    @PutMapping("/change-password/{userId}")
    public ResponseEntity<ApiResponse<Object>> changePassword(@PathVariable Long userId, @RequestBody ChangePasswordRequest changePasswordRequest) {
        boolean result = userService.changePassword(userId, changePasswordRequest);
        if (!result) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .code(400)
                            .message("Mật khẩu cũ không chính xác hoặc mật khẩu mới trùng với mật khẩu cũ!")
                            .build()
            );
        }
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(200)
                        .message("Đổi mật khẩu thành công!")
                        .build()
        );
    }

    @PutMapping("/change-avatar/{userId}")
    public ResponseEntity<ApiResponse<AvatarResponse>> changeAvatar(@PathVariable Long userId, @RequestParam("avatar") MultipartFile avatarFile) {
        try {
            if (avatarFile.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        ApiResponse.<AvatarResponse>builder()
                                .code(400)
                                .message("Vui lòng chọn một file ảnh!")
                                .build()
                );
            }
            if (!avatarFile.getContentType().startsWith("image/")) {
                return ResponseEntity.badRequest().body(
                        ApiResponse.<AvatarResponse>builder()
                                .code(400)
                                .message("File phải là ảnh (jpeg, png, jpg, gif)!")
                                .build()
                );
            }
            if (avatarFile.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.badRequest().body(
                        ApiResponse.<AvatarResponse>builder()
                                .code(400)
                                .message("Kích thước ảnh không được vượt quá 5MB!")
                                .build()
                );
            }

            String fileName = userService.updateAvatar(userId, avatarFile);
            AvatarResponse responseDto = new AvatarResponse();
            responseDto.setAvatarUrl("/ltweb/images/avatar/" + fileName);
            return ResponseEntity.ok(
                    ApiResponse.<AvatarResponse>builder()
                            .code(200)
                            .message("Cập nhật ảnh thành công!")
                            .result(responseDto)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<AvatarResponse>builder()
                            .code(400)
                            .message("Có vấn đề khi cập nhật ảnh: " + e.getMessage())
                            .build()
            );
        }
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserProfile(id));
    }
}
