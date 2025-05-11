package com.example.travelweb.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tbl_history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long historyID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tourID", nullable = false)
    private Tour tour;

    private String actionType;
    private Date timestamp;
}
