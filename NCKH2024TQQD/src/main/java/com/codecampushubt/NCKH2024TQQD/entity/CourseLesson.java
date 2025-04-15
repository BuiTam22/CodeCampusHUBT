package com.codecampushubt.NCKH2024TQQD.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CourseLessons")
public class CourseLesson {

    // ID TỰ ĐỘNG TĂNG CỦA BÀI HỌC
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LessonID", nullable = false, updatable = false)
    private Long lessonID;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG COURSEMODULES
    @ManyToOne
    @JoinColumn(name = "moduleID", nullable = false)
    private CourseModule module;

    // TIÊU ĐỀ BÀI HỌC
    @Column(name = "Title", nullable = false, length = 255)
    private String title;

    // MÔ TẢ BÀI HỌC
    @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    // LOẠI BÀI HỌC (VIDEO, TEXT, QUIZ, ASSIGNMENT, CODING)
    @Column(name = "Type", nullable = false, length = 50)
    private String type;

    // NỘI DUNG CHO BÀI HỌC DẠNG VĂN BẢN
    @Column(name = "Content", columnDefinition = "NVARCHAR(MAX)")
    private String content;

    // ĐƯỜNG DẪN VIDEO
    @Column(name = "VideoUrl", length = 255)
    private String videoUrl;

    // THỜI LƯỢNG (PHÚT)
    @Column(name = "Duration")
    private Integer duration;

    // THỨ TỰ SẮP XẾP TRONG MODULE
    @Column(name = "OrderIndex", nullable = false)
    private Integer orderIndex;

    // CÓ CHO PHÉP XEM THỬ KHÔNG (TRUE/FALSE)
    @Column(name = "IsPreview", nullable = false, columnDefinition = "BIT DEFAULT 0")
    private Boolean isPreview;

    // BÀI HỌC ĐÃ XUẤT BẢN CHƯA (TRUE/FALSE)
    @Column(name = "IsPublished", nullable = false, columnDefinition = "BIT DEFAULT 0")
    private Boolean isPublished;

    // THỜI ĐIỂM TẠO BÀI HỌC
    @Column(name = "CreatedAt", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime createdAt;

    // THỜI ĐIỂM CẬP NHẬT BÀI HỌC
    @Column(name = "UpdatedAt", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime updatedAt;

    @Column(name = "Slug", nullable = true)
    private String slug;


    // Constructor
    public CourseLesson() {
    }

    public CourseLesson(Long lessonID, CourseModule module, String title, String description, String type, String content, String videoUrl, Integer duration, Integer orderIndex, Boolean isPreview, Boolean isPublished, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.lessonID = lessonID;
        this.module = module;
        this.title = title;
        this.description = description;
        this.type = type;
        this.content = content;
        this.videoUrl = videoUrl;
        this.duration = duration;
        this.orderIndex = orderIndex;
        this.isPreview = isPreview;
        this.isPublished = isPublished;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getLessonID() {
        return lessonID;
    }

    public void setLessonID(Long lessonID) {
        this.lessonID = lessonID;
    }

    public CourseModule getModule() {
        return module;
    }

    public void setModule(CourseModule module) {
        this.module = module;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Boolean getPreview() {
        return isPreview;
    }

    public void setPreview(Boolean preview) {
        isPreview = preview;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
