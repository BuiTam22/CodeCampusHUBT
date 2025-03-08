package com.codecampushubt.NCKH2024TQQD.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Stories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "StoryID")
    private Long storyId;

//    @ManyToOne
//    @JoinColumn(name = "UserID", nullable = false)
//    private User user;
//
//    @Column(name = "MediaUrl", length = 255)
    private String mediaUrl;

//    @Column(name = "MediaType", length = 20)
    private String mediaType;

//    @Column(name = "Duration", nullable = false)
    private int duration = 15;

//    @Column(name = "ViewCount", nullable = false)
    private int viewCount = 0;

//    @Column(name = "BackgroundColor", length = 20)
    private String backgroundColor;

//    @Column(name = "TextContent", length = 500)
    private String textContent;

//    @Column(name = "FontStyle", length = 50)
    private String fontStyle;

////    @Column(name = "CreatedAt", nullable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();
//
////    @Column(name = "ExpiresAt")
//    private LocalDateTime expiresAt;

//    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted = false;

    // Constructor mặc định


    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }


}
