package com.example.travelweb.controller;

import com.example.travelweb.dto.response.TourResponse;
import com.example.travelweb.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {

    @Autowired
    TourService tourService;

    @GetMapping("/tours")
    public ResponseEntity<List<TourResponse>> getLimitedTours() {
        List<TourResponse> tours = tourService.getLimitedTours();
        return ResponseEntity.ok(tours);
    }
}
