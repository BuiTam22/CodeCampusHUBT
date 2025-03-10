package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CodingExercises")
public class CodingExercise {

    // ID TỰ ĐỘNG TĂNG CỦA BÀI TẬP
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExerciseID", nullable = false, updatable = false)
    public Long exerciseID;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG COURSELESSONS
    @Column(name = "LessonID", nullable = false)
    public Long lessonID;

    // TIÊU ĐỀ BÀI TẬP
    @Column(name = "Title", nullable = false, length = 255)
    public String title;

    // MÔ TẢ CHI TIẾT BÀI TẬP
    @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
    public String description;

    // NGÔN NGỮ LẬP TRÌNH ĐƯỢC SỬ DỤNG
    @Column(name = "ProgrammingLanguage", length = 50)
    public String programmingLanguage;

    // CODE MẪU BAN ĐẦU
    @Column(name = "InitialCode", columnDefinition = "NVARCHAR(MAX)")
    public String initialCode;

    // CODE LỜI GIẢI
    @Column(name = "SolutionCode", columnDefinition = "NVARCHAR(MAX)")
    public String solutionCode;

    // CÁC TEST CASE KIỂM TRA (ĐỊNH DẠNG JSON)
    @Column(name = "TestCases", columnDefinition = "NVARCHAR(MAX)")
    public String testCases;

    // GIỚI HẠN THỜI GIAN CHẠY (MILI GIÂY)
    @Column(name = "TimeLimit", nullable = false, columnDefinition = "INT DEFAULT 1000")
    public Integer timeLimit;

    // GIỚI HẠN BỘ NHỚ SỬ DỤNG (MB)
    @Column(name = "MemoryLimit", nullable = false, columnDefinition = "INT DEFAULT 256")
    public Integer memoryLimit;

    @Column(name = "Difficulty", nullable = false, length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'medium'")
    public String difficulty;

    // ĐIỂM CHO BÀI TẬP
    @Column(name = "Points", nullable = false, columnDefinition = "INT DEFAULT 0")
    public Integer points;

    // THỜI ĐIỂM TẠO
    @Column(name = "CreatedAt", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    public LocalDateTime createdAt;

    // THỜI ĐIỂM CẬP NHẬT
    @Column(name = "UpdatedAt", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    public LocalDateTime updatedAt;


    // Constructor
    public CodingExercise() {
    }

    public CodingExercise(Long lessonID, String title, String description, String programmingLanguage, String initialCode, String solutionCode, String testCases, Integer timeLimit, Integer memoryLimit, String difficulty, Integer points, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.lessonID = lessonID;
        this.title = title;
        this.description = description;
        this.programmingLanguage = programmingLanguage;
        this.initialCode = initialCode;
        this.solutionCode = solutionCode;
        this.testCases = testCases;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.difficulty = difficulty;
        this.points = points;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    // Getter & Setter

    public Long getExerciseID() {
        return exerciseID;
    }

    public Long getLessonID() {
        return lessonID;
    }

    public void setLessonID(Long lessonID) {
        this.lessonID = lessonID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getInitialCode() {
        return initialCode;
    }

    public void setInitialCode(String initialCode) {
        this.initialCode = initialCode;
    }

    public String getSolutionCode() {
        return solutionCode;
    }

    public void setSolutionCode(String solutionCode) {
        this.solutionCode = solutionCode;
    }

    public String getTestCases() {
        return testCases;
    }

    public void setTestCases(String testCases) {
        this.testCases = testCases;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(Integer memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "CodingExercise{" +
                "exerciseID=" + exerciseID +
                ", lessonID=" + lessonID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", programmingLanguage='" + programmingLanguage + '\'' +
                ", initialCode='" + initialCode + '\'' +
                ", solutionCode='" + solutionCode + '\'' +
                ", testCases='" + testCases + '\'' +
                ", timeLimit=" + timeLimit +
                ", memoryLimit=" + memoryLimit +
                ", difficulty='" + difficulty + '\'' +
                ", points=" + points +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
