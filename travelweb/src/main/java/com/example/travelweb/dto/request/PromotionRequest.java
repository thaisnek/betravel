package com.example.travelweb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequest {
    private Long promotionID;
    private String code;
    private String description;
    private double discount;
    private LocalDate startDate;
    private LocalDate endDate;
    private int quantity;
}
