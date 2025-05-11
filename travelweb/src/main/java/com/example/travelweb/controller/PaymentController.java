package com.example.travelweb.controller;

import com.example.travelweb.dto.request.CheckoutRequest;
import com.example.travelweb.dto.response.BookingResponse;
import com.example.travelweb.dto.response.CheckoutResponse;
import com.example.travelweb.dto.response.TourDetailResponse;
import com.example.travelweb.service.BookingService;
import com.example.travelweb.service.CheckoutService;
import com.example.travelweb.service.TourService;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestParam Long bookingId) {
        try {
            // Kiểm tra booking tồn tại
            bookingService.findById(bookingId);
            String approvalUrl = checkoutService.initiatePayment(bookingId);
            return ResponseEntity.ok(approvalUrl);
        } catch (PayPalRESTException e) {
            bookingService.updateBookingStatus(bookingId, "CANCELLED");
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/success")
    public ResponseEntity<String> successPayment(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            @RequestParam("bookingId") Long bookingId) {
        try {
            // Hoàn tất thanh toán và tạo checkout
            CheckoutResponse checkout = checkoutService.completePayment(paymentId, payerId, bookingId);
            // Cập nhật trạng thái booking
            bookingService.updateBookingStatus(bookingId, "CONFIRMED");
            return ResponseEntity.ok("Payment successful, Checkout ID: " + checkout.getCheckoutID());
        } catch (PayPalRESTException e) {
            bookingService.updateBookingStatus(bookingId, "CANCELLED");
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPayment(@RequestParam Long bookingId) {
        BookingResponse booking = bookingService.findById(bookingId);
        bookingService.updateBookingStatus(bookingId, "CANCELLED");
        Long tourId = booking.getTourId();
        int totalParticipants = booking.getNumAdults() + booking.getNumChildren();
        TourDetailResponse tour = tourService.getTourDetails(tourId);
        int newQuantity = tour.getQuantity() + totalParticipants;
        tourService.updateTourQuantity(tourId, newQuantity);
        return ResponseEntity.ok("Payment cancelled");
    }
}