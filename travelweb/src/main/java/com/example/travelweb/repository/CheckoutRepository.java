package com.example.travelweb.repository;

import com.example.travelweb.entity.Booking;
import com.example.travelweb.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    Optional<Checkout> findByBooking(Booking booking);
    Optional<Checkout> findByTransactionID(String transactionId);

    @Query("SELECT SUM(c.amount) FROM Checkout c WHERE c.paymentStatus = 'PAID'")
    Double sumTotalRevenue();
}
