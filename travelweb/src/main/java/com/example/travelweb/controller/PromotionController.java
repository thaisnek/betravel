package com.example.travelweb.controller;

import com.example.travelweb.dto.request.PromotionValidateRequest;
import com.example.travelweb.dto.response.PromotionValidateResponse;
import com.example.travelweb.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promotions")
@CrossOrigin(origins = "http://localhost:3000")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/validate")
    public ResponseEntity<PromotionValidateResponse> validatePromotion(@RequestBody PromotionValidateRequest request) {
        PromotionValidateResponse response = promotionService.validatePromotion(request);
        return ResponseEntity.ok(response);
    }
}
