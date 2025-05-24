package com.example.travelweb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private Long userId;
    private Long tourId;
    private int numAdults;
    private int numChildren;
    private long totalPrice;
    private String fullName;
    private String email;
    private String address;
    private String phoneNumber;
    private String paymentMethod;
    private String promotionCode;
}