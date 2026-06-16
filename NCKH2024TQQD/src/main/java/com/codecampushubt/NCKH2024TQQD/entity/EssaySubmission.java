package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "EssaySubmissions")
public class EssaySubmission {
    // ID TỰ ĐỘNG TĂNG CỦA BÀI NỘP
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubmissionID", nullable = false, updatable = false)
    private Long submissionID;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG CODINGEXERCISES
    @ManyToOne
    @JoinColumn(name = "ExerciseID", nullable = false)
    private EssayExercise exercise;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG USERS
    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    // ĐÁP ÁN CỦA HỌC SINH
    @Column(name = "AnswerText", nullable = true)
    private String answerText;

    // THỜI ĐIỂM NỘP BÀI
    @Column(name = "SubmittedAt", nullable = true, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime submittedAt;

    // ĐIỂM CỦA BÀI ĐÓ
    @Column(name = "Score", nullable = true)
    private Double score = 0.0;

    // FEEDBACK CỦA AI
    @Column(name = "Feedback", nullable = true)
    private String feedback;

    @Column(name = "FinalScore", nullable = true)
    private Double finalScore;

    @Column(name = "TeacherFeedBack", nullable = true)
    private String teacherFeedBack;

    public EssaySubmission() {
    }

    public EssaySubmission(EssayExercise exercise, User user, String answerText, LocalDateTime submittedAt, Double score, String feedback, Double finalScore, String teacherFeedBack) {
        this.exercise = exercise;
        this.user = user;
        this.answerText = answerText;
        this.submittedAt = submittedAt;
        this.score = score;
        this.feedback = feedback;
        this.finalScore = finalScore;
        this.teacherFeedBack = teacherFeedBack;
    }

    public Long getSubmissionID() {
        return submissionID;
    }

    public EssayExercise getExercise() {
        return exercise;
    }

    public void setExercise(EssayExercise exercise) {
        this.exercise = exercise;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTeacherFeedBack() {
        return teacherFeedBack;
    }

    public void setTeacherFeedBack(String teacherFeedBack) {
        this.teacherFeedBack = teacherFeedBack;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    @Override
    public String toString() {
        return "EssaySubmission{" +
                "submissionID=" + submissionID +
                ", exercise=" + exercise +
                ", user=" + user +
                ", answerText='" + answerText + '\'' +
                ", submittedAt=" + submittedAt +
                ", score=" + score +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
