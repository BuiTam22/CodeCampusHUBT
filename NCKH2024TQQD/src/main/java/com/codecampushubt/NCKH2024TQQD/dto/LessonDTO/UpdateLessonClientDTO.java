package com.codecampushubt.NCKH2024TQQD.dto.LessonDTO;

import java.time.LocalDateTime;

/**
 * Payload cập nhật bài học/cuộc thi từ trang chỉnh sửa.
 */
public class UpdateLessonClientDTO {
    private Long lessonId;
    private String title;
    private String description;
    private Integer duration;
    private String type;
    private Boolean isContest;
    private LocalDateTime contestStartTime;
    private LocalDateTime contestEndTime;

    public UpdateLessonClientDTO() {
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsContest() {
        return isContest;
    }

    public void setIsContest(Boolean contest) {
        this.isContest = contest;
    }

    public LocalDateTime getContestStartTime() {
        return contestStartTime;
    }

    public void setContestStartTime(LocalDateTime contestStartTime) {
        this.contestStartTime = contestStartTime;
    }

    public LocalDateTime getContestEndTime() {
        return contestEndTime;
    }

    public void setContestEndTime(LocalDateTime contestEndTime) {
        this.contestEndTime = contestEndTime;
    }
}
