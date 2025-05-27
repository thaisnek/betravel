package com.example.travelweb.repository;

import com.example.travelweb.dto.response.TopCustomerResponse;
import com.example.travelweb.dto.response.TopTourResponse;
import com.example.travelweb.entity.Booking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findByBookingID(Long bookingID);

    List<Booking> findByUserUserIDAndTourTourID(Long userId, Long tourId);

    @Query("SELECT b.tour.tourID as id, b.tour.title as name, SUM(b.numAdults + b.numChildren) as booked, (b.tour.quantity - SUM(b.numAdults + b.numChildren)) as available " +
            "FROM Booking b WHERE b.bookingStatus = com.example.travelweb.enums.BookingStatus.CONFIRMED GROUP BY b.tour.tourID, b.tour.title, b.tour.quantity ORDER BY booked DESC")
    List<TopTourProjection> findTop5ToursByConfirmedBookings(Pageable pageable);

    @Query("SELECT b.user.userID as id, b.user.fullName as name, COUNT(b.bookingID) as totalPurchases, SUM(b.numChildren * b.tour.priceChild + b.numAdults * b.tour.priceAdult) as totalAmount " +
            "FROM Booking b WHERE b.bookingStatus = com.example.travelweb.enums.BookingStatus.CONFIRMED GROUP BY b.user.userID, b.user.fullName ORDER BY totalAmount DESC")
    List<TopCustomerProjection> findTop5CustomersByConfirmedBookings(Pageable pageable);


    public interface TopTourProjection {
        Long getId();
        String getName();
        Long getBooked();
        Long getAvailable();
    }

    public interface TopCustomerProjection {
        Long getId();
        String getName();
        Long getTotalPurchases();
        Long getTotalAmount();
    }
}
