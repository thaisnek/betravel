package com.example.travelweb.controller;

import com.example.travelweb.dto.request.CheckoutRequest;
import com.example.travelweb.dto.response.BookingResponse;
import com.example.travelweb.dto.response.CheckoutResponse;
import com.example.travelweb.dto.response.TourDetailResponse;
import com.example.travelweb.entity.Booking;
import com.example.travelweb.entity.History;
import com.example.travelweb.entity.Tour;
import com.example.travelweb.entity.User;
import com.example.travelweb.enums.ActionType;
import com.example.travelweb.repository.BookingRepository;
import com.example.travelweb.repository.HistoryRepository;
import com.example.travelweb.repository.TourRepository;
import com.example.travelweb.repository.UserRepository;
import com.example.travelweb.service.BookingService;
import com.example.travelweb.service.CheckoutService;
import com.example.travelweb.service.TourService;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {
    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TourService tourService;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestParam Long bookingId) {
        try {
            bookingService.findById(bookingId);
            String approvalUrl = checkoutService.initiatePayment(bookingId);
            return ResponseEntity.ok(approvalUrl);
        } catch (PayPalRESTException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/success")
    public RedirectView successPayment(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            @RequestParam("bookingId") Long bookingId) {
        try {
            CheckoutResponse checkout = checkoutService.completePayment(paymentId, payerId, bookingId);
            bookingService.updateBookingStatus(bookingId, "CONFIRMED");
            String frontendUrl = "http://localhost:3000/payment/success"+ "?paymentId=" + paymentId
                    + "&PayerID=" + payerId
                    + "&bookingId=" + bookingId;
            return new RedirectView(frontendUrl);
        } catch (PayPalRESTException e) {
            return new RedirectView("http://localhost:3000/error?message=" + e.getMessage());
        }
    }

    @GetMapping("/cancel")
    public RedirectView cancelPayment(@RequestParam Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        bookingService.updateBookingStatus(bookingId, "CANCELLED");

        Booking bookingSaved = bookingRepository.save(booking);

        History history = new History();
        history.setUser(bookingSaved.getUser());
        history.setTour(bookingSaved.getTour());
        history.setBooking(bookingSaved);
        history.setActionType(ActionType.CANCEL);
        history.setTimestamp(LocalDate.now());
        historyRepository.save(history);

        Long tourId = booking.getTour().getTourID();
        int totalParticipants = booking.getNumAdults() + booking.getNumChildren();
        TourDetailResponse tour = tourService.getTourDetails(tourId);
        int newQuantity = tour.getQuantity() + totalParticipants;
        tourService.updateTourQuantity(tourId, newQuantity);
        String url = "http://localhost:3000/payment/cancel?bookingId=" + bookingId;
        return new RedirectView(url);
    }
}