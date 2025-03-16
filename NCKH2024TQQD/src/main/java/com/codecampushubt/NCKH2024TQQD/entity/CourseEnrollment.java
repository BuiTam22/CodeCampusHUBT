package com.codecampushubt.NCKH2024TQQD.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CourseEnrollments")
public class CourseEnrollment {

    // ID TỰ ĐỘNG TĂNG CỦA ĐĂNG KÝ
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EnrollmentID", nullable = false, updatable = false)
    public Long enrollmentID;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG COURSES
    @ManyToOne
    @JoinColumn(name = "CourseID", nullable = false)
    public Course courseID;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG USERS
    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    public User userID;

    // TIẾN ĐỘ HỌC TẬP (%)
    @Column(name = "Progress", nullable = false, columnDefinition = "INT DEFAULT 0")
    public Integer progress;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG COURSELESSONS (BÀI HỌC TRUY CẬP GẦN NHẤT)
    @Column(name = "LastAccessedLessonID")
    public Long lastAccessedLessonID;

    // THỜI ĐIỂM ĐĂNG KÝ
    @Column(name = "EnrolledAt", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    public LocalDateTime enrolledAt;

    // THỜI ĐIỂM HOÀN THÀNH
    @Column(name = "CompletedAt")
    public LocalDateTime completedAt;

    // ĐÃ CẤP CHỨNG CHỈ CHƯA (TRUE/FALSE)
    @Column(name = "CertificateIssued", nullable = false, columnDefinition = "BIT DEFAULT 0")
    public Boolean certificateIssued;

    // Constructor
    public CourseEnrollment() {
    }

    public CourseEnrollment(Long enrollmentID, Course courseID, User userID, Integer progress, Long lastAccessedLessonID, LocalDateTime enrolledAt, LocalDateTime completedAt, Boolean certificateIssued) {
        this.enrollmentID = enrollmentID;
        this.courseID = courseID;
        this.userID = userID;
        this.progress = progress;
        this.lastAccessedLessonID = lastAccessedLessonID;
        this.enrolledAt = enrolledAt;
        this.completedAt = completedAt;
        this.certificateIssued = certificateIssued;
    }

    public Long getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(Long enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public Course getCourseID() {
        return courseID;
    }

    public void setCourseID(Course courseID) {
        this.courseID = courseID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Long getLastAccessedLessonID() {
        return lastAccessedLessonID;
    }

    public void setLastAccessedLessonID(Long lastAccessedLessonID) {
        this.lastAccessedLessonID = lastAccessedLessonID;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Boolean getCertificateIssued() {
        return certificateIssued;
    }

    public void setCertificateIssued(Boolean certificateIssued) {
        this.certificateIssued = certificateIssued;
    }
}
