package com.example.travelweb.service.implement;

import com.example.travelweb.conventer.ImageMapper;
import com.example.travelweb.conventer.TimelineMapper;
import com.example.travelweb.conventer.TourMapper;
import com.example.travelweb.dto.request.ImageCreation;
import com.example.travelweb.dto.request.TimelineCreation;
import com.example.travelweb.dto.request.TourCreation;
import com.example.travelweb.dto.request.TourRequest;
import com.example.travelweb.dto.response.TourDetailResponse;
import com.example.travelweb.dto.response.TourResponse;
import com.example.travelweb.dto.response.TourResponseWrapper;
import com.example.travelweb.entity.Image;
import com.example.travelweb.entity.Timeline;
import com.example.travelweb.entity.Tour;
import com.example.travelweb.repository.ImageRepository;
import com.example.travelweb.repository.ReviewRepository;
import com.example.travelweb.repository.TimelineRepository;
import com.example.travelweb.repository.TourRepository;
import com.example.travelweb.service.TourService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;


@Service
public class TourServiceImpl implements TourService {
    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TimelineRepository timelineRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private TourMapper tourMapper;

    @Autowired
    private TimelineMapper timelineMapper;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public TourResponse createTour(TourCreation tourCreation) {
        Tour tour = tourMapper.toEntity(tourCreation);

        if (tour.getTimeLines() != null) {
            for (Timeline timeline : tour.getTimeLines()) {
                timeline.setTour(tour);
            }
        }
        if (tour.getImages() != null) {
            for (Image image : tour.getImages()) {
                image.setTour(tour);
            }
        }

        Tour savedTour = tourRepository.save(tour);
        return tourMapper.toTourResponseDTO(savedTour);
    }

    @Override
    public List<TourResponse> getAllAvailableTours() {
        List<Tour> tours = tourRepository.findByAvailabilityTrue();
        return tours.stream()
                .map(tourMapper::toTourResponseDTO)
                .toList();
    }

    @Override
    public TourDetailResponse getTourDetails(Long tourID) {
        Tour tour = tourRepository.findById(tourID)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        List<Timeline> timelines = timelineRepository.findByTourTourID(tourID);
        List<Image> images = imageRepository.findByTourTourID(tourID);

        TourDetailResponse tourDetailResponseDTO = tourMapper.toTourDetailResponseDTO(tour);
        tourDetailResponseDTO.setTimelines(timelineMapper.toTimelineResponseDTOList(timelines));
        tourDetailResponseDTO.setImages(imageMapper.toImageResponseDTOList(images));
        return tourDetailResponseDTO;
    }

    @Override
    public List<TourResponse> getLimitedTours() {
        List<Tour> tours = tourRepository.findAll(); // Lấy tất cả các phòng
        // Giới hạn chỉ lấy 8 phòng đầu tiên
        return tours.stream().limit(8).map(tourMapper::toTourResponseDTO).toList();
    }

    @Override
    public void updateTourQuantity(Long tourId, int newQuantity) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new IllegalArgumentException("Tour không tồn tại"));
        if (newQuantity < 0) {
            throw new IllegalStateException("Sức chứa tour không thể âm");
        }
        tour.setQuantity(newQuantity);
        tourRepository.save(tour);
    }

    @Override
    public List<TourResponse> getTourRecommendations(Long tourId) {
        String apiUrl = "http://127.0.0.1:5555/api/tour-recommendations?tour_id=" + tourId;
        try {
            RelatedToursResponse response = restTemplate.getForObject(apiUrl, RelatedToursResponse.class);
            List<Long> relatedTourIds = response != null ? response.getRelatedTours() : Collections.emptyList();
            List<Tour> tours = tourRepository.findByTourIDIn(relatedTourIds);
            // Ánh xạ sang TourResponse
            return tours.stream()
                    .map(tourMapper::toTourResponseDTO)
                    .toList();
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi API Python: " + e.getMessage());
            return Collections.emptyList();
        }
    }


    @Setter
    static class RelatedToursResponse {
        private List<Long> related_tours;

        public List<Long> getRelatedTours() {
            return related_tours;
        }
    }

    @Override
    public List<TourResponse> filterTours(Map<String, Object> conditions) {
        // Xử lý điều kiện lọc
        Long minPrice = (Long) conditions.get("minPrice");
        Long maxPrice = (Long) conditions.get("maxPrice");
        String domain = (String) conditions.get("domain");
        Double averageRating = (Double) conditions.get("averageRating");
        String duration = (String) conditions.get("duration");

        // Xử lý điều kiện trung bình sao
        List<Long> tourIds = null;
        if (averageRating != null) {
            tourIds = reviewRepository.findTourIdsByAverageRating(averageRating);
            if (tourIds.isEmpty()) {
                return Collections.emptyList(); // Không có tour nào thỏa mãn trung bình sao
            }
        }

        // Truy vấn dữ liệu với các điều kiện
        List<Tour> tours = tourRepository.filterTours(minPrice, maxPrice, domain, duration, tourIds);

        // Chuyển đổi sang TourResponse
        List<TourResponse> tourResponses = tours.stream()
                .map(tourMapper::toTourResponseDTO)
                .collect(Collectors.toList());

        // Xử lý sắp xếp
        String sorting = (String) conditions.get("sorting");
        if (sorting != null && !sorting.isEmpty()) {
            switch (sorting) {
                case "new":
                    tourResponses.sort(Comparator.comparing(TourResponse::getTourID, Comparator.reverseOrder()));
                    break;
                case "old":
                    tourResponses.sort(Comparator.comparing(TourResponse::getTourID));
                    break;
                case "hight-to-low":
                    tourResponses.sort(Comparator.comparing(TourResponse::getPriceAdult, Comparator.reverseOrder()));
                    break;
                case "low-to-high":
                    tourResponses.sort(Comparator.comparing(TourResponse::getPriceAdult));
                    break;
            }
        }

        return tourResponses;
    }

    @Override
    public TourResponseWrapper<List<TourResponse>> searchTours(String destination, LocalDate startDate, LocalDate endDate) {
        try {
            List<Tour> tours = tourRepository.findToursByCriteria(destination, startDate, endDate);
            List<TourResponse> tourResponses = tourMapper.toTourResponseDTOList(tours);
            return new TourResponseWrapper<>(true, "Tours retrieved successfully", tourResponses);
        } catch (Exception e) {
            return new TourResponseWrapper<>(false, "Error retrieving tours: " + e.getMessage(), null);
        }
    }

    @Override
    public Page<TourResponse> getAllTours(Pageable pageable) {
        return tourRepository.findAll(pageable)
                .map(tourMapper::toTourResponseDTO);
    }

    @Override
    public TourResponse updateTour(Long id, TourRequest request) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        // Update fields from request
        tour.setTitle(request.getTitle());
        tour.setDescription(request.getDescription());
        tour.setDuration(request.getDuration());
        tour.setQuantity(request.getQuantity());
        tour.setPriceAdult(request.getPriceAdult());
        tour.setPriceChild(request.getPriceChild());
        tour.setDestination(request.getDestination());
        tour.setDomain(request.getDomain());
        tour.setAvailability(request.isAvailability());
        tour.setStartDate(request.getStartDate());
        tour.setEndDate(request.getEndDate());

        // --- Xử lý timelines ---
        // Xóa hết timeline cũ
        tour.getTimeLines().clear();
        if (request.getTimelines() != null) {
            for (TimelineCreation t : request.getTimelines()) {
                Timeline timeline = new Timeline();
                timeline.setDay(t.getDay());
                timeline.setDescription(t.getDescription());
                timeline.setTour(tour); // quan trọng!
                tour.getTimeLines().add(timeline);
            }
        }

        // --- Xử lý images ---
        tour.getImages().clear();
        if (request.getImages() != null) {
            for (ImageCreation img : request.getImages()) {
                Image image = new Image();
                image.setImageURL(img.getImageURL());
                image.setDescription(img.getDescription());
                image.setTour(tour); // quan trọng!
                tour.getImages().add(image);
            }
        }

        tour = tourRepository.save(tour);
        return tourMapper.toTourResponseDTO(tour);
    }

    @Override
    public void deleteTour(Long id) {
        tourRepository.deleteById(id);
    }
}
