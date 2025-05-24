package com.example.travelweb.entity;

import com.example.travelweb.enums.SenderRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "tbl_chat")
@Getter
@Setter
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
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    private SenderRole senderRole;
}
