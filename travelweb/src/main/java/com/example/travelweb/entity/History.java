package com.example.travelweb.entity;

import com.example.travelweb.enums.ActionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "tbl_history")
@Getter
@Setter
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

    private ActionType actionType;
    private LocalDate timestamp;

    public void setUserId(Long userId) {
    }

    public void setTourId(Long tourId) {

    }
}
