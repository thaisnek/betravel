package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.TimelineCreation;
import com.example.travelweb.dto.response.TimelineResponse;
import com.example.travelweb.entity.Timeline;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-02T19:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class TimelineMapperImpl implements TimelineMapper {

    @Override
    public TimelineResponse toTimelineResponseDTO(Timeline timeline) {
        if ( timeline == null ) {
            return null;
        }

        TimelineResponse timelineResponse = new TimelineResponse();

        timelineResponse.setDay( timeline.getDay() );
        timelineResponse.setDescription( timeline.getDescription() );
        timelineResponse.setTimeLineID( timeline.getTimeLineID() );

        return timelineResponse;
    }

    @Override
    public List<TimelineResponse> toTimelineResponseDTOList(List<Timeline> timelines) {
        if ( timelines == null ) {
            return null;
        }

        List<TimelineResponse> list = new ArrayList<TimelineResponse>( timelines.size() );
        for ( Timeline timeline : timelines ) {
            list.add( toTimelineResponseDTO( timeline ) );
        }

        return list;
    }

    @Override
    public Timeline toEntity(TimelineCreation timelineCreation) {
        if ( timelineCreation == null ) {
            return null;
        }

        Timeline timeline = new Timeline();

        timeline.setDay( timelineCreation.getDay() );
        timeline.setDescription( timelineCreation.getDescription() );

        return timeline;
    }

    @Override
    public List<Timeline> toEntityList(List<TimelineCreation> timelineRequestDTOs) {
        if ( timelineRequestDTOs == null ) {
            return null;
        }

        List<Timeline> list = new ArrayList<Timeline>( timelineRequestDTOs.size() );
        for ( TimelineCreation timelineCreation : timelineRequestDTOs ) {
            list.add( toEntity( timelineCreation ) );
        }

        return list;
    }
}
