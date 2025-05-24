package com.example.travelweb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponse {
    private Integer adminID;
    private String username;
    private String email;
    private LocalDate createdDate;
}
