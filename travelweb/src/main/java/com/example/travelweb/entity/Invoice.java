package com.example.travelweb.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tbl_invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceID;

    @OneToOne
    @JoinColumn(name = "bookingID", nullable = false)
    private Booking booking;

    private long amount;
    private Date dateIssued;
    private String details;
}
