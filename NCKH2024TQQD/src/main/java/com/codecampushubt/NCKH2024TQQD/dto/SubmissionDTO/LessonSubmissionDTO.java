package com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO;

import java.time.LocalDateTime;

public class LessonSubmissionDTO {
    private String lessonTitle;
    private String userName;
    private LocalDateTime time;
    private Double score;
    private String status;

    public LessonSubmissionDTO(String lessonTitle, String userName, LocalDateTime time, Double score, String status) {
        this.lessonTitle = lessonTitle;
        this.userName = userName;
        this.time = time;
        this.score = score;
        this.status = status;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
