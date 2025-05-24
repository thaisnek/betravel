package com.example.travelweb.service.implement;

import com.example.travelweb.conventer.ReviewMapper;
import com.example.travelweb.dto.request.ReviewRequest;
import com.example.travelweb.dto.response.ReviewResponse;
import com.example.travelweb.entity.Booking;
import com.example.travelweb.entity.History;
import com.example.travelweb.entity.Review;
import com.example.travelweb.entity.Tour;
import com.example.travelweb.enums.ActionType;
import com.example.travelweb.enums.BookingStatus;
import com.example.travelweb.repository.BookingRepository;
import com.example.travelweb.repository.HistoryRepository;
import com.example.travelweb.repository.ReviewRepository;
import com.example.travelweb.repository.TourRepository;
import com.example.travelweb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private HistoryRepository historyRepository;

    public boolean canReview(Long userId, Long tourId) {

        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        LocalDate currentDate = LocalDate.now();
        if (tour.getEndDate().isAfter(currentDate)) {
            return false;
        }

        List<Booking> bookings = bookingRepository.findByUserUserIDAndTourTourID(userId, tourId);
        return bookings.stream()
                .anyMatch(booking -> booking.getBookingStatus() == BookingStatus.CONFIRMED);
    }

    public ReviewResponse createReview(ReviewRequest requestDTO) {

        if (!canReview(requestDTO.getUserId(), requestDTO.getTourId())) {
            throw new RuntimeException("You must complete the tour to review it");
        }

        Review review = reviewMapper.toEntity(requestDTO);
        review.setTimestamp(new Date());

        review = reviewRepository.save(review);

        History history = new History();
        history.setUser(review.getUser());
        history.setTour(review.getTour());
        history.setActionType(ActionType.REVIEW);
        history.setTimestamp(LocalDate.now());
        historyRepository.save(history);

        return reviewMapper.toResponseDTO(review);
    }

    public List<ReviewResponse> getReviewsByTourId(Long tourId) {
        List<Review> reviews = reviewRepository.findByTourTourID(tourId);
        return reviews.stream()
                .map(reviewMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReviewResponse> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable)
                .map(reviewMapper::toResponseDTO);
    }

    @Override
    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }
}
