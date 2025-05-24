package com.example.travelweb.conventer;

import com.example.travelweb.dto.response.HistoryResponseDTO;
import com.example.travelweb.dto.response.ImageResponse;
import com.example.travelweb.dto.response.TourResponse;
import com.example.travelweb.entity.History;
import com.example.travelweb.entity.Image;
import com.example.travelweb.entity.Tour;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-24T17:15:30+0700",
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

        return tourResponse;
    }
}
