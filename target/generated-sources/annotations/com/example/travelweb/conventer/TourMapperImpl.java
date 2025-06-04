package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.TourCreation;
import com.example.travelweb.dto.response.TourDetailResponse;
import com.example.travelweb.dto.response.TourResponse;
import com.example.travelweb.entity.Tour;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-02T19:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class TourMapperImpl implements TourMapper {

    @Autowired
    private TimelineMapper timelineMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public TourResponse toTourResponseDTO(Tour tour) {
        if ( tour == null ) {
            return null;
        }

        TourResponse tourResponse = new TourResponse();

        tourResponse.setTourID( tour.getTourID() );
        tourResponse.setTitle( tour.getTitle() );
        tourResponse.setTimelines( timelineMapper.toTimelineResponseDTOList( tour.getTimeLines() ) );
        tourResponse.setReviews( reviewMapper.toResponseDTOList( tour.getReviews() ) );
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
        tourResponse.setImages( imageMapper.toImageResponseDTOList( tour.getImages() ) );

        return tourResponse;
    }

    @Override
    public List<TourResponse> toTourResponseDTOList(List<Tour> tours) {
        if ( tours == null ) {
            return null;
        }

        List<TourResponse> list = new ArrayList<TourResponse>( tours.size() );
        for ( Tour tour : tours ) {
            list.add( toTourResponseDTO( tour ) );
        }

        return list;
    }

    @Override
    public TourDetailResponse toTourDetailResponseDTO(Tour tour) {
        if ( tour == null ) {
            return null;
        }

        TourDetailResponse tourDetailResponse = new TourDetailResponse();

        tourDetailResponse.setTourID( tour.getTourID() );
        tourDetailResponse.setTitle( tour.getTitle() );
        tourDetailResponse.setDescription( tour.getDescription() );
        tourDetailResponse.setDuration( tour.getDuration() );
        tourDetailResponse.setQuantity( tour.getQuantity() );
        tourDetailResponse.setPriceAdult( tour.getPriceAdult() );
        tourDetailResponse.setPriceChild( tour.getPriceChild() );
        tourDetailResponse.setDestination( tour.getDestination() );
        tourDetailResponse.setDomain( tour.getDomain() );
        tourDetailResponse.setAvailability( tour.isAvailability() );
        if ( tour.getStartDate() != null ) {
            tourDetailResponse.setStartDate( Date.from( tour.getStartDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        if ( tour.getEndDate() != null ) {
            tourDetailResponse.setEndDate( Date.from( tour.getEndDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        tourDetailResponse.setImages( imageMapper.toImageResponseDTOList( tour.getImages() ) );
        tourDetailResponse.setReviews( reviewMapper.toResponseDTOList( tour.getReviews() ) );

        return tourDetailResponse;
    }

    @Override
    public Tour toEntity(TourCreation tourRequestDTO) {
        if ( tourRequestDTO == null ) {
            return null;
        }

        Tour tour = new Tour();

        tour.setTimeLines( timelineMapper.toEntityList( tourRequestDTO.getTimelines() ) );
        if ( tourRequestDTO.getTourID() != null ) {
            tour.setTourID( tourRequestDTO.getTourID() );
        }
        tour.setTitle( tourRequestDTO.getTitle() );
        tour.setDescription( tourRequestDTO.getDescription() );
        tour.setDuration( tourRequestDTO.getDuration() );
        tour.setQuantity( tourRequestDTO.getQuantity() );
        if ( tourRequestDTO.getPriceAdult() != null ) {
            tour.setPriceAdult( tourRequestDTO.getPriceAdult() );
        }
        if ( tourRequestDTO.getPriceChild() != null ) {
            tour.setPriceChild( tourRequestDTO.getPriceChild() );
        }
        tour.setDestination( tourRequestDTO.getDestination() );
        tour.setDomain( tourRequestDTO.getDomain() );
        if ( tourRequestDTO.getAvailability() != null ) {
            tour.setAvailability( tourRequestDTO.getAvailability() );
        }
        if ( tourRequestDTO.getStartDate() != null ) {
            tour.setStartDate( LocalDateTime.ofInstant( tourRequestDTO.getStartDate().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        if ( tourRequestDTO.getEndDate() != null ) {
            tour.setEndDate( LocalDateTime.ofInstant( tourRequestDTO.getEndDate().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }

        return tour;
    }
}
