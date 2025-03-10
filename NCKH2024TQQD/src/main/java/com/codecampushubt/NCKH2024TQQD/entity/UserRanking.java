package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "UserRankings")
public class UserRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RankingID")
    private Long rankingId;

//    @ManyToOne
//    @JoinColumn(name = "UserID")
//    private User user; // Thay "User" bằng tên entity của bảng Users

    @Column(name = "Tier", length = 20)
    private String tier;

    @Column(name = "TotalPoints")
    private Integer totalPoints = 0;

    @Column(name = "EventPoints")
    private Integer eventPoints = 0;

    @Column(name = "CoursePoints")
    private Integer coursePoints = 0;

    @Column(name = "ProblemsSolved")
    private Integer problemsSolved = 0;

    @Column(name = "Accuracy", precision = 5, scale = 2)
    private BigDecimal accuracy = BigDecimal.ZERO;

    @Column(name = "Wins")
    private Integer wins = 0;

    @Column(name = "MonthlyScore")
    private Integer monthlyScore = 0;

    @Column(name = "WeeklyScore")
    private Integer weeklyScore = 0;

    @Column(name = "LastCalculatedAt")
    private LocalDateTime lastCalculatedAt = LocalDateTime.now();

    // Constructors, getters, setters, equals, hashCode, toString...



    // Constructor with parameters

}
