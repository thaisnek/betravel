package com.example.travelweb.entity;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "tbl_chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "adminID", nullable = false)
    private Admin admin;

    private String messages;
    private boolean readStatus;
    private Date createdDate;
}
