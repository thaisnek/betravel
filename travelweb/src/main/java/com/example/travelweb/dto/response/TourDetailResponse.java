package com.example.travelweb.dto.response;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TourDetailResponse {
    private Long tourID;
    private String title;
    private String description;
    private String duration;
    private int quantity;
    private Long priceAdult;
    private Long priceChild;
    private String destination;
    private String domain;
    private Boolean availability;
    private Date startDate;
    private Date endDate;
    private List<TimelineResponse> timelines;
    private List<ImageResponse> images;
    private List<ReviewResponse> reviews;
    private Integer averageRating;
}
