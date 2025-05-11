package com.example.travelweb.controller;

import com.example.travelweb.dto.request.TourCreation;
import com.example.travelweb.dto.response.TourDetailResponse;
import com.example.travelweb.dto.response.TourResponse;
import com.example.travelweb.dto.response.TourResponseWrapper;
import com.example.travelweb.entity.Tour;
import com.example.travelweb.repository.TourRepository;
import com.example.travelweb.service.TourSearchService;
import com.example.travelweb.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class TourController {
    @Autowired
    private TourService tourService;

    @Autowired
    private TourSearchService tourSearchService;

    @GetMapping("/all-tours")
    public ResponseEntity<List<TourResponse>> getAllAvailableTours() {
        List<TourResponse> tours = tourService.getAllAvailableTours();
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/tour-details/{tourID}")
    public ResponseEntity<TourDetailResponse> getTourDetails(@PathVariable Long tourID) {
        TourDetailResponse tourDetails = tourService.getTourDetails(tourID);
        return ResponseEntity.ok(tourDetails);
    }

    @GetMapping("/tours/{tourId}/recommendations")
    public List<TourResponse> getTourRecommendations(@PathVariable Long tourId) {
        return tourService.getTourRecommendations(tourId);
    }

    @GetMapping("/filter")
    @ResponseBody
    public ResponseEntity<List<TourResponse>> filterTours(
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) String domain,
            @RequestParam(required = false) Integer star,
            @RequestParam(required = false) String time,
            @RequestParam(required = false) String sorting) {

        // Xây dựng điều kiện lọc
        Map<String, Object> conditions = new HashMap<>();
        if (minPrice != null && maxPrice != null) {
            conditions.put("minPrice", minPrice);
            conditions.put("maxPrice", maxPrice);
        }
        if (domain != null && !domain.isEmpty()) {
            conditions.put("domain", domain);
        }
        if (star != null) {
            conditions.put("averageRating", (double) star);
        }
        if (time != null && !time.isEmpty()) {
            Map<String, String> timeMap = new HashMap<>();
            timeMap.put("3N2Đ", "3 ngày 2 đêm");
            timeMap.put("4N3Đ", "4 ngày 3 đêm");
            timeMap.put("5N4Đ", "5 ngày 4 đêm");
            conditions.put("duration", timeMap.getOrDefault(time, time));
        }
        if (sorting != null && !sorting.isEmpty()) {
            conditions.put("sorting", sorting);
        }

        // Gọi phương thức lọc
        List<TourResponse> tours = tourService.filterTours(conditions);

        return ResponseEntity.ok(tours);
    }

    @GetMapping("/search")
    public TourResponseWrapper<List<TourResponse>> searchTours(
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return tourService.searchTours(destination, startDate, endDate);
    }

    @GetMapping("/search-tours")
    public ResponseEntity<?> searchTours(@RequestParam String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Missing search query");
            }
            List<TourResponse> tours = tourSearchService.searchTours(keyword);
            return ResponseEntity.ok(tours);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}
