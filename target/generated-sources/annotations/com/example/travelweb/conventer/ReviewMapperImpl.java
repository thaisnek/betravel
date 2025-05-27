package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.ReviewRequest;
import com.example.travelweb.dto.response.ReviewResponse;
import com.example.travelweb.entity.Review;
import com.example.travelweb.entity.Tour;
import com.example.travelweb.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-26T06:13:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public Review toEntity(ReviewRequest requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Review review = new Review();

        review.setTour( reviewRequestToTour( requestDTO ) );
        review.setUser( reviewRequestToUser( requestDTO ) );
        review.setRating( requestDTO.getRating() );
        review.setComment( requestDTO.getComment() );
        review.setTimestamp( requestDTO.getTimestamp() );

        return review;
    }

    @Override
    public ReviewResponse toResponseDTO(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewResponse.ReviewResponseBuilder reviewResponse = ReviewResponse.builder();

        reviewResponse.tourId( reviewTourTourID( review ) );
        reviewResponse.userId( reviewUserUserID( review ) );
        reviewResponse.reviewId( review.getReviewID() );
        reviewResponse.fullName( reviewUserFullName( review ) );
        reviewResponse.rating( review.getRating() );
        reviewResponse.comment( review.getComment() );
        reviewResponse.timestamp( review.getTimestamp() );

        return reviewResponse.build();
    }

    @Override
    public List<ReviewResponse> toResponseDTOList(List<Review> reviews) {
        if ( reviews == null ) {
            return null;
        }

        List<ReviewResponse> list = new ArrayList<ReviewResponse>( reviews.size() );
        for ( Review review : reviews ) {
            list.add( toResponseDTO( review ) );
        }

        return list;
    }

    protected Tour reviewRequestToTour(ReviewRequest reviewRequest) {
        if ( reviewRequest == null ) {
            return null;
        }

        Tour tour = new Tour();

        if ( reviewRequest.getTourId() != null ) {
            tour.setTourID( reviewRequest.getTourId() );
        }

        return tour;
    }

    protected User reviewRequestToUser(ReviewRequest reviewRequest) {
        if ( reviewRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userID( reviewRequest.getUserId() );

        return user.build();
    }

    private Long reviewTourTourID(Review review) {
        if ( review == null ) {
            return null;
        }
        Tour tour = review.getTour();
        if ( tour == null ) {
            return null;
        }
        long tourID = tour.getTourID();
        return tourID;
    }

    private Long reviewUserUserID(Review review) {
        if ( review == null ) {
            return null;
        }
        User user = review.getUser();
        if ( user == null ) {
            return null;
        }
        Long userID = user.getUserID();
        if ( userID == null ) {
            return null;
        }
        return userID;
    }

    private String reviewUserFullName(Review review) {
        if ( review == null ) {
            return null;
        }
        User user = review.getUser();
        if ( user == null ) {
            return null;
        }
        String fullName = user.getFullName();
        if ( fullName == null ) {
            return null;
        }
        return fullName;
    }
}
