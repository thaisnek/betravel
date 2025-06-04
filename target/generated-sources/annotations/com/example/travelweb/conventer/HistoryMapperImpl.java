package com.example.travelweb.conventer;

import com.example.travelweb.dto.response.BookingResponse;
import com.example.travelweb.dto.response.HistoryResponseDTO;
import com.example.travelweb.dto.response.ImageResponse;
import com.example.travelweb.dto.response.ReviewResponse;
import com.example.travelweb.dto.response.TourResponse;
import com.example.travelweb.entity.Booking;
import com.example.travelweb.entity.History;
import com.example.travelweb.entity.Image;
import com.example.travelweb.entity.Review;
import com.example.travelweb.entity.Tour;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-02T19:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class HistoryMapperImpl implements HistoryMapper {

    @Override
    public HistoryResponseDTO toDto(History history) {
        if ( history == null ) {
            return null;
        }

        HistoryResponseDTO.HistoryResponseDTOBuilder historyResponseDTO = HistoryResponseDTO.builder();

        historyResponseDTO.historyID( history.getHistoryID() );
        historyResponseDTO.actionType( history.getActionType() );
        historyResponseDTO.timestamp( history.getTimestamp() );
        historyResponseDTO.tourTitle( historyTourTitle( history ) );
        historyResponseDTO.tourResponse( tourToTourResponse( history.getTour() ) );
        historyResponseDTO.bookingResponse( bookingToBookingResponse( history.getBooking() ) );

        return historyResponseDTO.build();
    }

    private String historyTourTitle(History history) {
        if ( history == null ) {
            return null;
        }
        Tour tour = history.getTour();
        if ( tour == null ) {
            return null;
        }
        String title = tour.getTitle();
        if ( title == null ) {
            return null;
        }
        return title;
    }

    protected ImageResponse imageToImageResponse(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageResponse imageResponse = new ImageResponse();

        imageResponse.setImageID( image.getImageID() );
        imageResponse.setImageURL( image.getImageURL() );
        imageResponse.setDescription( image.getDescription() );

        return imageResponse;
    }

    protected List<ImageResponse> imageListToImageResponseList(List<Image> list) {
        if ( list == null ) {
            return null;
        }

        List<ImageResponse> list1 = new ArrayList<ImageResponse>( list.size() );
        for ( Image image : list ) {
            list1.add( imageToImageResponse( image ) );
        }

        return list1;
    }

    protected ReviewResponse reviewToReviewResponse(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewResponse.ReviewResponseBuilder reviewResponse = ReviewResponse.builder();

        reviewResponse.rating( review.getRating() );
        reviewResponse.comment( review.getComment() );
        reviewResponse.timestamp( review.getTimestamp() );

        return reviewResponse.build();
    }

    protected List<ReviewResponse> reviewListToReviewResponseList(List<Review> list) {
        if ( list == null ) {
            return null;
        }

        List<ReviewResponse> list1 = new ArrayList<ReviewResponse>( list.size() );
        for ( Review review : list ) {
            list1.add( reviewToReviewResponse( review ) );
        }

        return list1;
    }

    protected TourResponse tourToTourResponse(Tour tour) {
        if ( tour == null ) {
            return null;
        }

        TourResponse tourResponse = new TourResponse();

        tourResponse.setTourID( tour.getTourID() );
        tourResponse.setTitle( tour.getTitle() );
        tourResponse.setDescription( tour.getDescription() );
        tourResponse.setDuration( tour.getDuration() );
        tourResponse.setQuantity( tour.getQuantity() );
        tourResponse.setPriceAdult( tour.getPriceAdult() );
        tourResponse.setPriceChild( tour.getPriceChild() );
        tourResponse.setDestination( tour.getDestination() );
        tourResponse.setDomain( tour.getDomain() );
        tourResponse.setAvailability( tour.isAvailability() );
        if ( tour.getStartDate() != null ) {
            tourResponse.setStartDate( Date.from( tour.getStartDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( tour.getEndDate() != null ) {
            tourResponse.setEndDate( Date.from( tour.getEndDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        tourResponse.setImages( imageListToImageResponseList( tour.getImages() ) );
        tourResponse.setReviews( reviewListToReviewResponseList( tour.getReviews() ) );

        return tourResponse;
    }

    protected BookingResponse bookingToBookingResponse(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        BookingResponse.BookingResponseBuilder bookingResponse = BookingResponse.builder();

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
}
