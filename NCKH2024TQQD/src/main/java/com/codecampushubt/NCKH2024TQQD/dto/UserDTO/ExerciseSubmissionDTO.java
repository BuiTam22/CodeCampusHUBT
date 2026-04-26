package com.codecampushubt.NCKH2024TQQD.dto.UserDTO;

import java.time.LocalDateTime;

/**
 * DTO biểu diễn một bài exercise đã hoàn thành của user,
 * dùng để hiển thị trong expand panel tại trang profile.
 */
public class ExerciseSubmissionDTO {

    private String exerciseTitle;
    private String exerciseSlug;
    private String exerciseType;   // "coding" | "essay"
    private Double score;
    private String status;         // "accepted" | "wrong_answer" | … (null với essay)
    private LocalDateTime submittedAt;

    // ── Constructor ──────────────────────────────────────────────────────────
    public ExerciseSubmissionDTO(String exerciseTitle, String exerciseSlug,
                                  String exerciseType, Double score,
                                  String status, LocalDateTime submittedAt) {
        this.exerciseTitle = exerciseTitle;
        this.exerciseSlug  = exerciseSlug;
        this.exerciseType  = exerciseType;
        this.score         = score != null ? score : 0.0;
        this.status        = status;
        this.submittedAt   = submittedAt;
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String getExerciseTitle()  { return exerciseTitle; }
    public String getExerciseSlug()   { return exerciseSlug; }
    public String getExerciseType()   { return exerciseType; }
    public Double getScore()          { return score; }
    public String getStatus()         { return status; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
}
