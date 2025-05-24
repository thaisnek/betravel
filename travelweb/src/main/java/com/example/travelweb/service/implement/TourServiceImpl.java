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
import com.example.travelweb.entity.Review;
import com.example.travelweb.entity.Timeline;
import com.example.travelweb.entity.Tour;
import com.example.travelweb.repository.ImageRepository;
import com.example.travelweb.repository.ReviewRepository;
import com.example.travelweb.repository.TimelineRepository;
import com.example.travelweb.repository.TourRepository;
import com.example.travelweb.service.TourService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


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

    @Value("${tour.upload.dir}")
    private String imageUploadDir;

    @Override
    public TourResponse createTour(TourCreation tourCreation) {
        Tour tour = tourMapper.toEntity(tourCreation);

        if (tour.getTimeLines() != null) {
            for (Timeline timeline : tour.getTimeLines()) {
                timeline.setTour(tour);
            }
        }

        Tour savedTour = tourRepository.save(tour);
        return tourMapper.toTourResponseDTO(savedTour);
    }

    @Override
    public List<String> uploadTourImages(Long tourId, MultipartFile[] imageFiles, boolean replaceOldImages) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found with ID: " + tourId));

        if (replaceOldImages) {
            List<Image> oldImages = tour.getImages();
            for (Image oldImage : oldImages) {
                Path oldFilePath = Paths.get(imageUploadDir, oldImage.getImageURL());
                try {
                    if (Files.exists(oldFilePath)) {
                        Files.delete(oldFilePath);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Không thể xóa ảnh cũ: " + e.getMessage());
                }
            }
            tour.getImages().clear();
            tourRepository.save(tour);
        }

        List<String> fileNames = new ArrayList<>();

        if (imageFiles != null && imageFiles.length > 0) {
            for (MultipartFile imageFile : imageFiles) {
                if (!imageFile.isEmpty()) {
                    String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                    Path filePath = Paths.get(imageUploadDir, fileName);

                    try {
                        Files.createDirectories(filePath.getParent());
                        Files.write(filePath, imageFile.getBytes());

                        Image image = new Image();
                        image.setImageURL(fileName);
                        image.setTour(tour);
                        imageRepository.save(image);

                        fileNames.add(fileName);
                    } catch (IOException e) {
                        throw new RuntimeException("Không thể lưu ảnh cho tour: " + e.getMessage());
                    }
                }
            }
        }

        return fileNames;
    }

    private Integer calculateAverageRating(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0;
        }

        double average = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        return (int) Math.round(average);
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

        // Tính và set rating trung bình
        Integer avgRating = calculateAverageRating(tour.getReviews());
        tourDetailResponseDTO.setAverageRating(avgRating);

        return tourDetailResponseDTO;
    }

    @Override
    public List<TourResponse> getLimitedTours() {
        List<Tour> tours = tourRepository.findAll();

        return tours.stream()
                .limit(8)
                .map(tour -> {
                    TourResponse tourResponse = tourMapper.toTourResponseDTO(tour);
                    Integer avgRating = calculateAverageRating(tour.getReviews());
                    tourResponse.setAverageRating(avgRating);
                    return tourResponse;
                })
                .toList();
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
    public Page<TourResponse> filterTours(Map<String, Object> conditions, Pageable pageable) {
        Long minPrice = (Long) conditions.get("minPrice");
        Long maxPrice = (Long) conditions.get("maxPrice");
        String domain = (String) conditions.get("domain");
        String duration = (String) conditions.get("duration");

        Integer star = (Integer) conditions.get("star");
        List<Long> tourIds = null;
        if (star != null) {
            tourIds = reviewRepository.findTourIdsByAverageRating(star);
            if (tourIds.isEmpty()) {
                return Page.empty(pageable);
            }
        }

        Page<Tour> tours = tourRepository.filterTours(minPrice, maxPrice, domain, duration, tourIds, pageable);

        return tours.map(tour -> {
            TourResponse tourResponse = tourMapper.toTourResponseDTO(tour);
            Integer avgRating = calculateAverageRating(tour.getReviews());
            tourResponse.setAverageRating(avgRating);
            return tourResponse;
        });
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


        tour.getTimeLines().clear();
        if (request.getTimelines() != null) {
            for (TimelineCreation t : request.getTimelines()) {
                Timeline timeline = new Timeline();
                timeline.setDay(t.getDay());
                timeline.setDescription(t.getDescription());
                timeline.setTour(tour);
                tour.getTimeLines().add(timeline);
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
