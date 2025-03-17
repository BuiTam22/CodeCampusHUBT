package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CodingSubmissions")
public class CodingSubmission {

    // ID TỰ ĐỘNG TĂNG CỦA BÀI NỘP
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubmissionID", nullable = false, updatable = false)
    public Long submissionID;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG CODINGEXERCISES
    @ManyToOne
    @JoinColumn(name = "ExerciseID", nullable = false)
    public CodingExercise exerciseID;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG USERS
    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    public User userID;

    // CODE ĐÃ NỘP
    @Column(name = "Code", columnDefinition = "NVARCHAR(MAX)")
    public String code;

    // NGÔN NGỮ LẬP TRÌNH SỬ DỤNG
    @Column(name = "Language", length = 50)
    public String language;

    // TRẠNG THÁI CHẤM BÀI (CHỈ NHẬN GIÁ TRỊ HỢP LỆ)
    @Column(name = "Status", length = 20)
    public String status;

    // THỜI GIAN CHẠY (MILI GIÂY)
    @Column(name = "ExecutionTime")
    public Integer executionTime;

    // BỘ NHỚ SỬ DỤNG (KB)
    @Column(name = "MemoryUsed")
    public Integer memoryUsed;

    // SỐ TEST CASE ĐÃ PASS
    @Column(name = "TestCasesPassed", nullable = false, columnDefinition = "INT DEFAULT 0")
    public Integer testCasesPassed;

    // TỔNG SỐ TEST CASE
    @Column(name = "TotalTestCases", nullable = false, columnDefinition = "INT DEFAULT 0")
    public Integer totalTestCases;

    // ĐIỂM ĐẠT ĐƯỢC
    @Column(name = "Score", nullable = false, columnDefinition = "INT DEFAULT 0")
    public Integer score;

    // THỜI ĐIỂM NỘP BÀI
    @Column(name = "SubmittedAt", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    public LocalDateTime submittedAt;


    // Constructor
    public CodingSubmission() {
    }

    public CodingSubmission(Long submissionID, CodingExercise exerciseID, User userID, String code, String language, String status, Integer executionTime, Integer memoryUsed, Integer testCasesPassed, Integer totalTestCases, Integer score, LocalDateTime submittedAt) {
        this.submissionID = submissionID;
        this.exerciseID = exerciseID;
        this.userID = userID;
        this.code = code;
        this.language = language;
        this.status = status;
        this.executionTime = executionTime;
        this.memoryUsed = memoryUsed;
        this.testCasesPassed = testCasesPassed;
        this.totalTestCases = totalTestCases;
        this.score = score;
        this.submittedAt = submittedAt;
    }

    public Long getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(Long submissionID) {
        this.submissionID = submissionID;
    }

    public CodingExercise getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(CodingExercise exerciseID) {
        this.exerciseID = exerciseID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Integer executionTime) {
        this.executionTime = executionTime;
    }

    public Integer getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Integer memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public Integer getTestCasesPassed() {
        return testCasesPassed;
    }

    public void setTestCasesPassed(Integer testCasesPassed) {
        this.testCasesPassed = testCasesPassed;
    }

    public Integer getTotalTestCases() {
        return totalTestCases;
    }

    public void setTotalTestCases(Integer totalTestCases) {
        this.totalTestCases = totalTestCases;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
