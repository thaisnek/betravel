package com.example.travelweb.controller;

import com.example.travelweb.dto.request.ReviewRequest;
import com.example.travelweb.dto.response.ReviewResponse;
import com.example.travelweb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/can-review")
    public ResponseEntity<Boolean> canReview(
            @RequestParam Long userId,
            @RequestParam Long tourId) {
        boolean canReview = reviewService.canReview(userId, tourId);
        return ResponseEntity.ok(canReview);
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest requestDTO) {
        ReviewResponse createdReview = reviewService.createReview(requestDTO);
        return ResponseEntity.ok(createdReview);
    }

    @GetMapping("/tour/{tourId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByTourId(@PathVariable Long tourId) {
        List<ReviewResponse> reviews = reviewService.getReviewsByTourId(tourId);
        return ResponseEntity.ok(reviews);
    }
}
