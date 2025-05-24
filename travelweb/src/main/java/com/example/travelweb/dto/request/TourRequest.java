package com.example.travelweb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourRequest {
    private String title;
    private String description;
    private String duration;
    private int quantity;
    private long priceAdult;
    private long priceChild;
    private String destination;
    private String domain;
    private boolean availability;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TimelineCreation> timelines;
}
