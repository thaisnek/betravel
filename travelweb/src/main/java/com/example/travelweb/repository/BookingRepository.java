package com.example.travelweb.repository;

import com.example.travelweb.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findByBookingID(Long bookingID);

    List<Booking> findByUserUserIDAndTourTourID(Long userId, Long tourId);
}
