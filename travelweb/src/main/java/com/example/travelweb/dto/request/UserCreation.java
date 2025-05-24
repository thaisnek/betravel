package com.example.travelweb.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreation {
    private String username;
    private String fullName;
    private String password;
    private String email;
}
