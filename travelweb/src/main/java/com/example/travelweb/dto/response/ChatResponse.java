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
public class ChatResponse {
    private Long chatID;
    private Long userID;
    private Integer adminID;
    private String messages;
    private LocalDate createdDate;
    private String senderRole;
}
