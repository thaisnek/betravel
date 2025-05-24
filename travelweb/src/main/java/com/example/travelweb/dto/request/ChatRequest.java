package com.example.travelweb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
    private Long userID;
    private Integer adminID;
    private String messages;
    private String senderRole;
}
