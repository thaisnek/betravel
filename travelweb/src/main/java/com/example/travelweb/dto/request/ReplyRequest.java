package com.example.travelweb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRequest {
    private Long chatID;
    private String replyMessage;
    private Long adminId;
}
