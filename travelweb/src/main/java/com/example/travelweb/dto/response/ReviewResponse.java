package com.example.travelweb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private int reviewId;
    private Long tourId;
    private Long userId;
    private String fullName;
    private int rating;
    private String comment;
    private Date timestamp;
}
