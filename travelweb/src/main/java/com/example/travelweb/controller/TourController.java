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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/tours")
@CrossOrigin(origins = "http://localhost:3000")
public class TourController {
    @Autowired
    private TourService tourService;

    @Autowired
    private TourSearchService tourSearchService;

    @GetMapping("/all-tours")
    public Page<TourResponse> getAllTours(
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) String domain,
            @RequestParam(required = false) Integer star,
            @RequestParam(required = false) String duration,
            @RequestParam(required = false) String sorting,
            @PageableDefault(size = 9) Pageable pageable
    ) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("minPrice", minPrice);
        conditions.put("maxPrice", maxPrice);
        conditions.put("domain", domain);
        conditions.put("star", star);
        conditions.put("duration", duration);
        conditions.put("sorting", sorting);

        return tourService.filterTours(conditions, pageable);
    }



    @GetMapping("/tour-details/{tourID}")
    public ResponseEntity<TourDetailResponse> getTourDetails(@PathVariable Long tourID) {
        TourDetailResponse tourDetails = tourService.getTourDetails(tourID);
        return ResponseEntity.ok(tourDetails);
    }

    @GetMapping("/{tourId}/recommendations")
    public List<TourResponse> getTourRecommendations(@PathVariable Long tourId) {
        return tourService.getTourRecommendations(tourId);
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
