package com.example.travelweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbl_admin")
@Getter
@Setter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminID;

    private String username;
    private String password;
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private LocalDate createdDate;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Contact> contacts;
}
