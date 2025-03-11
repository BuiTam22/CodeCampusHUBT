package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class RankingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatID")
    private Long statId;

//    @ManyToOne
//    @JoinColumn(name = "UserID")
//    private User user;

    @Column(name = "PeriodType")
    private String periodType;

    @Column(name = "StartDate")
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @Column(name = "TotalPoints")
    private Integer totalPoints = 0;

    @Column(name = "EventsParticipated")
    private Integer eventsParticipated = 0;

    @Column(name = "CoursesCompleted")
    private Integer coursesCompleted = 0;

    @Column(name = "AverageAccuracy")
    private BigDecimal averageAccuracy;
    public RankingHistory() {}
    public RankingHistory(Long statId, String periodType, LocalDate startDate, LocalDate endDate, Integer totalPoints, Integer eventsParticipated, Integer coursesCompleted, BigDecimal averageAccuracy) {
        this.statId = statId;
        this.periodType = periodType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPoints = totalPoints;
        this.eventsParticipated = eventsParticipated;
        this.coursesCompleted = coursesCompleted;
        this.averageAccuracy = averageAccuracy;
    }

    public Long getStatId() {
        return statId;
    }

    public void setStatId(Long statId) {
        this.statId = statId;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getEventsParticipated() {
        return eventsParticipated;
    }

    public void setEventsParticipated(Integer eventsParticipated) {
        this.eventsParticipated = eventsParticipated;
    }

    public Integer getCoursesCompleted() {
        return coursesCompleted;
    }

    public void setCoursesCompleted(Integer coursesCompleted) {
        this.coursesCompleted = coursesCompleted;
    }

    public BigDecimal getAverageAccuracy() {
        return averageAccuracy;
    }

    public void setAverageAccuracy(BigDecimal averageAccuracy) {
        this.averageAccuracy = averageAccuracy;
    }
}
