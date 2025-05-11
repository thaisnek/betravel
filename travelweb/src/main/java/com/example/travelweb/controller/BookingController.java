package com.example.travelweb.controller;

import com.example.travelweb.dto.request.BookingRequest;
import com.example.travelweb.dto.request.CheckoutRequest;
import com.example.travelweb.dto.request.PaymentRequest;
import com.example.travelweb.dto.response.*;
import com.example.travelweb.entity.Booking;
import com.example.travelweb.entity.Tour;
import com.example.travelweb.enums.BookingStatus;
import com.example.travelweb.service.BookingService;
import com.example.travelweb.service.CheckoutService;
import com.example.travelweb.service.PayPalService;
import com.example.travelweb.service.TourService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private TourService tourService;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest bookingRequest) {
        // Kiểm tra sức chứa tour trước khi tạo booking
        TourDetailResponse tour = tourService.getTourDetails(bookingRequest.getTourId());
        int totalParticipants = tour.getQuantity() - bookingRequest.getNumAdults() - bookingRequest.getNumChildren();
        if (tour.getQuantity() < totalParticipants) {
            throw new IllegalStateException("Không đủ slot cho tour này");
        }

        // Tạo booking
        BookingResponse booking = bookingService.createBooking(bookingRequest);

        // Cập nhật sức chứa tour
        tourService.updateTourQuantity(bookingRequest.getTourId(), totalParticipants);
        return ResponseEntity.ok(booking);
    }
}
