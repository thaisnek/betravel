package com.example.travelweb.service;

import com.example.travelweb.dto.request.PromotionRequest;
import com.example.travelweb.dto.response.PromotionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromotionService {
    Page<PromotionResponse> getAllPromotions(Pageable pageable);

    PromotionResponse createPromotion(PromotionRequest request);

    PromotionResponse updatePromotion(Long id, PromotionRequest request);

    void deletePromotion(Long id);
}
