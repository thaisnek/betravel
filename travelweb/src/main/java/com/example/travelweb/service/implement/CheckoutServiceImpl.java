package com.example.travelweb.service.implement;

import com.example.travelweb.conventer.CheckoutMapper;
import com.example.travelweb.dto.request.CheckoutRequest;
import com.example.travelweb.dto.response.BookingResponse;
import com.example.travelweb.dto.response.CheckoutResponse;
import com.example.travelweb.entity.Booking;
import com.example.travelweb.entity.Checkout;
import com.example.travelweb.repository.BookingRepository;
import com.example.travelweb.repository.CheckoutRepository;
import com.example.travelweb.service.BookingService;
import com.example.travelweb.service.CheckoutService;
import com.example.travelweb.service.PayPalService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private PayPalService payPalService;

    @Override
    @Transactional
    public String initiatePayment(Long bookingId) throws PayPalRESTException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Gọi PayPalService để tạo thanh toán
        Payment payment = payPalService.createPayment(
                bookingId,
                (double) booking.getTotalPrice() / 23000.0,
                "USD",
                "Payment for booking ID: " + bookingId,
                "http://localhost:8080/ltweb/api/payment/cancel",
                "http://localhost:8080/ltweb/api/payment/success"
        );

        // Tìm approval_url trong các link trả về
        for (com.paypal.api.payments.Links link : payment.getLinks()) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                return link.getHref();
            }
        }
        throw new PayPalRESTException("No approval URL found");
    }

    @Override
    @Transactional
    public CheckoutResponse completePayment(String paymentId, String payerId, Long bookingId) throws PayPalRESTException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Hoàn tất thanh toán qua PayPalService
        Payment executedPayment = payPalService.executePayment(paymentId, payerId);

        // Tạo checkout khi thanh toán thành công
        Checkout checkout = new Checkout();
        checkout.setBooking(booking);
        checkout.setPaymentMethod("paypal");
        checkout.setAmount(booking.getTotalPrice());
        checkout.setPaymentStatus("PAID");
        checkout.setTransactionID(executedPayment.getId());

        Checkout savedCheckout = checkoutRepository.save(checkout);

        // Trả về CheckoutResponse
        return new CheckoutResponse(
                savedCheckout.getCheckoutID(),
                bookingId,
                savedCheckout.getPaymentMethod(),
                savedCheckout.getAmount(),
                savedCheckout.getPaymentStatus(),
                savedCheckout.getTransactionID()
        );
    }

    @Override
    @Transactional
    public void cancelPayment(Long bookingId) {
        // Không tạo checkout khi hủy
        // Booking status sẽ được cập nhật trong PaymentController
    }
}
