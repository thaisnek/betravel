package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.TourCreation;
import com.example.travelweb.dto.response.TourDetailResponse;
import com.example.travelweb.dto.response.TourResponse;
import com.example.travelweb.entity.Tour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TimelineMapper.class, ImageMapper.class})
public interface TourMapper {
    @Mapping(source = "tourID", target = "tourID")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "images", target = "images")
    TourResponse toTourResponseDTO(Tour tour);

    List<TourResponse> toTourResponseDTOList(List<Tour> tours);


    TourDetailResponse toTourDetailResponseDTO(Tour tour);

    @Mapping(source = "timelines", target = "timeLines")
    @Mapping(source = "images", target = "images")
    Tour toEntity(TourCreation tourRequestDTO);
}
