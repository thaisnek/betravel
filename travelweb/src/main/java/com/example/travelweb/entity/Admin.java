package com.example.travelweb.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminID;

    private String username;
    private String password;
    private String email;
    private Date createdDate;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Chat> chats;
}
