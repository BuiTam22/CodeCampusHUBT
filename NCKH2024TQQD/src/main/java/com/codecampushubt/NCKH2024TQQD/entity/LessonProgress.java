package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LessonProgress")
public class LessonProgress {

    // ID TỰ ĐỘNG TĂNG CỦA TIẾN ĐỘ
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProgressID", nullable = false, updatable = false)
    public Long progressID;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG COURSEENROLLMENTS
    @Column(name = "EnrollmentID", nullable = false)
    public Long enrollmentID;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG COURSELESSONS
    @Column(name = "LessonID", nullable = false)
    public Long lessonID;

    // TRẠNG THÁI HỌC TẬP (KHÔNG DÙNG ENUM)
    @Column(name = "Status", length = 20)
    public String status;

    // THỜI ĐIỂM HOÀN THÀNH
    @Column(name = "CompletedAt")
    public LocalDateTime completedAt;

    // THỜI GIAN ĐÃ HỌC (GIÂY)
    @Column(name = "TimeSpent", nullable = false, columnDefinition = "INT DEFAULT 0")
    public Integer timeSpent;

    // VỊ TRÍ XEM VIDEO GẦN NHẤT
    @Column(name = "LastPosition", nullable = false, columnDefinition = "INT DEFAULT 0")
    public Integer lastPosition;

    // Constructor
    public LessonProgress() {
    }

    public LessonProgress(Integer lastPosition, Integer timeSpent, LocalDateTime completedAt, String status, Long lessonID, Long enrollmentID) {
        this.lastPosition = lastPosition;
        this.timeSpent = timeSpent;
        this.completedAt = completedAt;
        this.status = status;
        this.lessonID = lessonID;
        this.enrollmentID = enrollmentID;
    }

    // getter & setter

    public Long getProgressID() {
        return progressID;
    }

    public Long getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(Long enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public Long getLessonID() {
        return lessonID;
    }

    public void setLessonID(Long lessonID) {
        this.lessonID = lessonID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Integer getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(Integer lastPosition) {
        this.lastPosition = lastPosition;
    }

    @Override
    public String toString() {
        return "LessonProgress{" +
                "progressID=" + progressID +
                ", enrollmentID=" + enrollmentID +
                ", lessonID=" + lessonID +
                ", status='" + status + '\'' +
                ", completedAt=" + completedAt +
                ", timeSpent=" + timeSpent +
                ", lastPosition=" + lastPosition +
                '}';
    }
}
