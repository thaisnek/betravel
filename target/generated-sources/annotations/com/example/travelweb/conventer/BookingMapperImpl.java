package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.BookingRequest;
import com.example.travelweb.dto.response.BookingResponse;
import com.example.travelweb.entity.Booking;
import com.example.travelweb.entity.Tour;
import com.example.travelweb.entity.User;
import java.text.SimpleDateFormat;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-02T19:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class BookingMapperImpl implements BookingMapper {

    @Override
    public Booking toEntity(BookingRequest request) {
        if ( request == null ) {
            return null;
        }

        Booking booking = new Booking();

        booking.setTour( bookingRequestToTour( request ) );
        booking.setUser( bookingRequestToUser( request ) );
        booking.setNumAdults( request.getNumAdults() );
        booking.setNumChildren( request.getNumChildren() );
        booking.setTotalPrice( request.getTotalPrice() );
        booking.setFullName( request.getFullName() );
        booking.setEmail( request.getEmail() );
        booking.setPhoneNumber( request.getPhoneNumber() );
        booking.setAddress( request.getAddress() );
        booking.setPaymentMethod( request.getPaymentMethod() );

        return booking;
    }

    @Override
    public BookingResponse toResponseDto(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        BookingResponse.BookingResponseBuilder bookingResponse = BookingResponse.builder();

        bookingResponse.userId( bookingUserUserID( booking ) );
        bookingResponse.tourId( bookingTourTourID( booking ) );
        bookingResponse.bookingID( booking.getBookingID() );
        if ( booking.getBookingDate() != null ) {
            bookingResponse.bookingDate( new SimpleDateFormat().format( booking.getBookingDate() ) );
        }
        bookingResponse.numAdults( booking.getNumAdults() );
        bookingResponse.numChildren( booking.getNumChildren() );
        bookingResponse.totalPrice( booking.getTotalPrice() );
        bookingResponse.fullName( booking.getFullName() );
        bookingResponse.email( booking.getEmail() );
        bookingResponse.phoneNumber( booking.getPhoneNumber() );
        bookingResponse.address( booking.getAddress() );
        if ( booking.getBookingStatus() != null ) {
            bookingResponse.bookingStatus( booking.getBookingStatus().name() );
        }
        bookingResponse.paymentMethod( booking.getPaymentMethod() );

        return bookingResponse.build();
    }

    protected Tour bookingRequestToTour(BookingRequest bookingRequest) {
        if ( bookingRequest == null ) {
            return null;
        }

        Tour tour = new Tour();

        if ( bookingRequest.getTourId() != null ) {
            tour.setTourID( bookingRequest.getTourId() );
        }

        return tour;
    }

    protected User bookingRequestToUser(BookingRequest bookingRequest) {
        if ( bookingRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userID( bookingRequest.getUserId() );

        return user.build();
    }

    private Long bookingUserUserID(Booking booking) {
        if ( booking == null ) {
            return null;
        }
        User user = booking.getUser();
        if ( user == null ) {
            return null;
        }
        Long userID = user.getUserID();
        if ( userID == null ) {
            return null;
        }
        return userID;
    }

    private Long bookingTourTourID(Booking booking) {
        if ( booking == null ) {
            return null;
        }
        Tour tour = booking.getTour();
        if ( tour == null ) {
            return null;
        }
        long tourID = tour.getTourID();
        return tourID;
    }
}
