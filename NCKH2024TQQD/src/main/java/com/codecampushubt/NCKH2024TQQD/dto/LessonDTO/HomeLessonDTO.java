package com.codecampushubt.NCKH2024TQQD.dto.LessonDTO;

public class HomeLessonDTO {
    private Long lessonID;
    private String title;
    private String description;
    private String image;
    private String slug;
    private Integer orderIndex;
    private String creatorUserName;

    public HomeLessonDTO() {}

    public HomeLessonDTO(Long lessonID, String title, String description,
                         String image, String slug, Integer orderIndex,
                         String creatorUserName) {
        this.lessonID = lessonID;
        this.title = title;
        this.description = description;
        this.image = image;
        this.slug = slug;
        this.orderIndex = orderIndex;
        this.creatorUserName = creatorUserName;
    }

    public Long getLessonID() { return lessonID; }
    public void setLessonID(Long lessonID) { this.lessonID = lessonID; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

    public String getCreatorUserName() { return creatorUserName; }
    public void setCreatorUserName(String creatorUserName) { this.creatorUserName = creatorUserName; }
}
