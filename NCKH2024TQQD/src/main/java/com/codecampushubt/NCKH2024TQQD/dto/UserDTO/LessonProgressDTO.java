package com.codecampushubt.NCKH2024TQQD.dto.UserDTO;

public class LessonProgressDTO {
    private String lessonTitle;
    private String lessonSlug;
    private String lessonType;
    private Long exerciseCount;
    private Long completedCount;
    private Double totalScore;

    public LessonProgressDTO(String lessonTitle, String lessonSlug, String lessonType,
                             Long exerciseCount, Long completedCount, Double totalScore) {
        this.lessonTitle = lessonTitle;
        this.lessonSlug = lessonSlug;
        this.lessonType = lessonType;
        this.exerciseCount = exerciseCount;
        this.completedCount = completedCount;
        this.totalScore = totalScore != null ? totalScore : 0.0;
    }

    public LessonProgressDTO(String lessonTitle, String lessonSlug, String lessonType,
                             Object exerciseCount, Object completedCount, Object totalScore) {
        this.lessonTitle = lessonTitle;
        this.lessonSlug = lessonSlug;
        this.lessonType = lessonType;

        // Ép kiểu thủ công bên trong để đảm bảo an toàn
        this.exerciseCount = (exerciseCount instanceof Number n) ? n.longValue() : 0L;
        this.completedCount = (completedCount instanceof Number n) ? n.longValue() : 0L;
        this.totalScore = (totalScore instanceof Number n) ? n.doubleValue() : 0.0;
    }

    public String getLessonTitle() { return lessonTitle; }
    public String getLessonSlug() { return lessonSlug; }
    public String getLessonType() { return lessonType; }
    public Long getExerciseCount() { return exerciseCount; }
    public Long getCompletedCount() { return completedCount; }
    public Double getTotalScore() { return totalScore; }

    public int getProgressPercent() {
        if (exerciseCount == null || exerciseCount == 0) return 0;
        return (int) Math.min(100, (completedCount * 100) / exerciseCount);
    }
}
