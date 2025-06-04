package com.example.travelweb.service.implement;

import com.example.travelweb.dto.request.BookingRequest;
import com.example.travelweb.dto.response.BookingResponse;
import com.example.travelweb.entity.*;
import com.example.travelweb.enums.ActionType;
import com.example.travelweb.enums.BookingStatus;
import com.example.travelweb.conventer.BookingMapper;
import com.example.travelweb.repository.*;
import com.example.travelweb.service.BookingService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public Page<BookingResponse> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable)
                .map(bookingMapper::toResponseDto);
    }

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequestDTO) {
        User user = userRepository.findById(bookingRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tour tour = tourRepository.findById(bookingRequestDTO.getTourId())
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        Booking booking = bookingMapper.toEntity(bookingRequestDTO);
        booking.setUser(user);
        booking.setTour(tour);
        booking.setBookingDate(new Date());

        long totalPrice = (tour.getPriceAdult() * bookingRequestDTO.getNumAdults()) +
                (tour.getPriceChild() * bookingRequestDTO.getNumChildren());

        if (bookingRequestDTO.getPromotionCode() != null && !bookingRequestDTO.getPromotionCode().isEmpty()) {
            Promotion promotion = promotionRepository.findByCode(bookingRequestDTO.getPromotionCode())
                    .orElseThrow(() -> new RuntimeException("Invalid promotion code"));

            LocalDate currentDate = LocalDate.now();
            if (promotion.getStartDate().isAfter(currentDate) ||
                    promotion.getEndDate().isBefore(currentDate) ||
                    promotion.getQuantity() <= 0) {
                throw new RuntimeException("Promotion code is not valid or expired");
            }

            double discount = promotion.getDiscount();
            long discountAmount = (long) (totalPrice * (discount / 100.0));
            totalPrice -= discountAmount;

            promotion.setQuantity(promotion.getQuantity() - 1);
            promotionRepository.save(promotion);
        }

        booking.setTotalPrice(totalPrice);
        booking.setBookingStatus(BookingStatus.valueOf("PENDING"));

        Booking savedBooking = bookingRepository.save(booking);

        History history = new History();
        history.setUser(user);
        history.setTour(tour);
        history.setBooking(savedBooking);
        history.setActionType(ActionType.BOOK);
        history.setTimestamp(LocalDate.now());
        historyRepository.save(history);

        return bookingMapper.toResponseDto(savedBooking);
    }

    @Override
    public BookingResponse updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setBookingStatus(BookingStatus.valueOf(status));
        Booking updatedBooking = bookingRepository.save(booking);
        return bookingMapper.toResponseDto(updatedBooking);
    }

    @Override
    public BookingResponse findById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return bookingMapper.toResponseDto(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}