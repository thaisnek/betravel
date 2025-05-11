package com.example.travelweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tbl_checkout")
@Getter
@Setter
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long checkoutID;

    @OneToOne
    @JoinColumn(name = "bookingID", nullable = false)
    private Booking booking;

    private String paymentMethod;
    private Date paymentDate;
    private double amount;

    private String paymentStatus;

    private String transactionID;
}
