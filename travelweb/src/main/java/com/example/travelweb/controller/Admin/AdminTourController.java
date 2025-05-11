package com.example.travelweb.controller.Admin;

import com.example.travelweb.dto.request.TourCreation;
import com.example.travelweb.dto.request.TourRequest;
import com.example.travelweb.dto.response.TourResponse;
import com.example.travelweb.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/tours")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminTourController {
    @Autowired
    private TourService tourService;

    @PostMapping("/create")
    public ResponseEntity<TourResponse> createTour(@RequestBody TourCreation tourRequestDTO) {
        TourResponse createdTour = tourService.createTour(tourRequestDTO);
        return ResponseEntity.status(201).body(createdTour);
    }

    @GetMapping
    public Page<TourResponse> getAllTours(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return tourService.getAllTours(pageable);
    }

    @PutMapping("/update/{id}")
    public TourResponse updateTour(
            @PathVariable Long id,
            @RequestBody TourRequest request
    ) {
        return tourService.updateTour(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);
    }
}
