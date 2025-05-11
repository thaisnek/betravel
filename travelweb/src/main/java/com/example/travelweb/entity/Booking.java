package com.example.travelweb.entity;

import jakarta.persistence.*;
import com.example.travelweb.enums.BookingStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_booking")
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tourID", nullable = false)
    private Tour tour;

    private Date bookingDate;
    private int numAdults;
    private int numChildren;
    private long totalPrice;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String paymentMethod;

    private BookingStatus bookingStatus;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Invoice invoice;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Checkout checkout;
}
