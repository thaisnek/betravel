package com.example.travelweb.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private String fullName;
}
