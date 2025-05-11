package com.example.travelweb.dto.request;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourCreation {
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
    private List<TimelineCreation> timelines;
    private List<ImageCreation> images;
}
