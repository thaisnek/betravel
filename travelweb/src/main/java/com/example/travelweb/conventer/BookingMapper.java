package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.BookingRequest;
import com.example.travelweb.dto.response.BookingResponse;
import com.example.travelweb.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "tourId", target = "tour.tourID")
    @Mapping(source = "userId", target = "user.userID")
    Booking toEntity(BookingRequest request);

    @Mapping(source = "user.userID", target = "userId")
    @Mapping(source = "tour.tourID", target = "tourId")
    BookingResponse toResponseDto(Booking booking);
}