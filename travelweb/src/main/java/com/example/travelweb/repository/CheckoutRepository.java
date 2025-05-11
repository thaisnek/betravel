package com.example.travelweb.repository;

import com.example.travelweb.entity.Booking;
import com.example.travelweb.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    Optional<Checkout> findByBooking(Booking booking);
    Optional<Checkout> findByTransactionID(String transactionId);
}
