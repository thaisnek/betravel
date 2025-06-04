package com.example.travelweb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {
    private Long userId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String message;
}
