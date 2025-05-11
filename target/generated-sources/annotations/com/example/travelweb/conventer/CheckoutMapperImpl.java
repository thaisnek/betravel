package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.CheckoutRequest;
import com.example.travelweb.dto.response.CheckoutResponse;
import com.example.travelweb.entity.Booking;
import com.example.travelweb.entity.Checkout;
import java.text.SimpleDateFormat;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-11T09:23:59+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class CheckoutMapperImpl implements CheckoutMapper {

    @Override
    public Checkout toEntity(CheckoutRequest request) {
        if ( request == null ) {
            return null;
        }

        Checkout checkout = new Checkout();

        checkout.setBooking( checkoutRequestToBooking( request ) );
        checkout.setPaymentMethod( request.getPaymentMethod() );
        checkout.setAmount( request.getAmount() );

        return checkout;
    }

    @Override
    public CheckoutResponse toResponseDto(Checkout checkout) {
        if ( checkout == null ) {
            return null;
        }

        CheckoutResponse.CheckoutResponseBuilder checkoutResponse = CheckoutResponse.builder();

        checkoutResponse.bookingId( checkoutBookingBookingID( checkout ) );
        checkoutResponse.checkoutID( checkout.getCheckoutID() );
        checkoutResponse.paymentMethod( checkout.getPaymentMethod() );
        checkoutResponse.amount( (long) checkout.getAmount() );
        if ( checkout.getPaymentDate() != null ) {
            checkoutResponse.paymentDate( new SimpleDateFormat().format( checkout.getPaymentDate() ) );
        }
        checkoutResponse.paymentStatus( checkout.getPaymentStatus() );
        checkoutResponse.transactionID( checkout.getTransactionID() );

        return checkoutResponse.build();
    }

    protected Booking checkoutRequestToBooking(CheckoutRequest checkoutRequest) {
        if ( checkoutRequest == null ) {
            return null;
        }

        Booking booking = new Booking();

        if ( checkoutRequest.getBookingId() != null ) {
            booking.setBookingID( checkoutRequest.getBookingId() );
        }

        return booking;
    }

    private Long checkoutBookingBookingID(Checkout checkout) {
        if ( checkout == null ) {
            return null;
        }
        Booking booking = checkout.getBooking();
        if ( booking == null ) {
            return null;
        }
        long bookingID = booking.getBookingID();
        return bookingID;
    }
}
