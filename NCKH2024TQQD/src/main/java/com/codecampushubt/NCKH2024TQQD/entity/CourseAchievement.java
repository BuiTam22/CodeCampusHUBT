package com.codecampushubt.NCKH2024TQQD.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CourseAchievements")
public class CourseAchievement {

    // ID TỰ ĐỘNG TĂNG CỦA THÀNH TÍCH
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AchievementID", nullable = false, updatable = false)
    public Long achievementID;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG COURSES
    @Column(name = "CourseID", nullable = false)
    public Long courseID;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG USERS
    @Column(name = "UserID", nullable = false)
    public Long userID;

    // THỜI GIAN HOÀN THÀNH KHÓA HỌC (PHÚT)
    @Column(name = "CompletionTime")
    public Integer completionTime;

    // SỐ CÂU TRẢ LỜI ĐÚNG
    @Column(name = "CorrectAnswers")
    public Integer correctAnswers;

    // TỔNG SỐ CÂU HỎI
    @Column(name = "TotalQuestions")
    public Integer totalQuestions;

    // ĐIỂM SỐ ĐẠT ĐƯỢC
    @Column(name = "Score", precision = 5, scale = 2)
    public BigDecimal score;

    // LOẠI HUY HIỆU ĐẠT ĐƯỢC (KHÔNG DÙNG ENUM)
    @Column(name = "BadgeType", length = 50)
    public String badgeType;

    // THỜI ĐIỂM ĐẠT THÀNH TÍCH
    @Column(name = "AwardedAt", nullable = false)
    public LocalDateTime awardedAt;

    public CourseAchievement() {
    }

    public CourseAchievement(Long courseID, Long userID, Integer completionTime, Integer correctAnswers, Integer totalQuestions, BigDecimal score, String badgeType, LocalDateTime awardedAt) {
        this.courseID = courseID;
        this.userID = userID;
        this.completionTime = completionTime;
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
        this.score = score;
        this.badgeType = badgeType;
        this.awardedAt = awardedAt;
    }

    public Long getAchievementID() {
        return achievementID;
    }

    public Long getCourseID() {
        return courseID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Integer getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Integer completionTime) {
        this.completionTime = completionTime;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getBadgeType() {
        return badgeType;
    }

    public void setBadgeType(String badgeType) {
        this.badgeType = badgeType;
    }

    public LocalDateTime getAwardedAt() {
        return awardedAt;
    }

    public void setAwardedAt(LocalDateTime awardedAt) {
        this.awardedAt = awardedAt;
    }

    @Override
    public String toString() {
        return "CourseAchievement{" +
                "achievementID=" + achievementID +
                ", courseID=" + courseID +
                ", userID=" + userID +
                ", completionTime=" + completionTime +
                ", correctAnswers=" + correctAnswers +
                ", totalQuestions=" + totalQuestions +
                ", score=" + score +
                ", badgeType='" + badgeType + '\'' +
                ", awardedAt=" + awardedAt +
                '}';
    }
}
