package com.example.travelweb.service.implement;

import com.example.travelweb.conventer.TourMapper;
import com.example.travelweb.dto.response.TourResponse;
import com.example.travelweb.entity.Tour;
import com.example.travelweb.repository.TourRepository;
import com.example.travelweb.service.TourSearchService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourSearchServiceImpl implements TourSearchService {
    private static final Logger logger = LoggerFactory.getLogger(TourSearchService.class);
    private static final String FLASK_SEARCH_API_URL = "http://127.0.0.1:5555/api/search-tours?keyword=";

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourMapper tourMapper;

    @Autowired
    private RestTemplate restTemplate;

    public List<TourResponse> searchTours(String keyword) {
        String apiUrl = FLASK_SEARCH_API_URL + keyword;
        try {
            logger.info("Calling Flask search API: {}", apiUrl);
            RelatedToursResponse response = restTemplate.getForObject(apiUrl, RelatedToursResponse.class);
            List<Long> relatedTourIds = response != null && response.getRelatedTours() != null
                    ? response.getRelatedTours()
                    : Collections.emptyList();

            if (relatedTourIds.isEmpty()) {
                logger.warn("No tours found for keyword: {}", keyword);
                return Collections.emptyList();
            }

            List<Tour> tours = tourRepository.findByTourIDIn(relatedTourIds);
            return tours.stream()
                    .map(tourMapper::toTourResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error calling Flask search API for keyword {}: {}", keyword, e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Data
    static class RelatedToursResponse {
        private List<Long> related_tours;

        public List<Long> getRelatedTours() {
            return related_tours;
        }
    }
}
