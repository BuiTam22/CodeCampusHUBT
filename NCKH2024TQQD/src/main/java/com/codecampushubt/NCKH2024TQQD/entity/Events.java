package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "Events" )
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventID;
    private String title;
    private String description;
    private String category;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private String location;
    private String imageUrl;
    private Integer maxAttendees;
    private Integer currentAttendees = 0;
    private BigDecimal price;
    private String organizer;
    private String difficulty;
    private String status = "upcoming";
//    private User createdBy; phần này đợi bẳng user
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    



}
