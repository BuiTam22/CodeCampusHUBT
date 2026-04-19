package com.codecampushubt.NCKH2024TQQD.dto.BlogDTO;

import java.time.LocalDateTime;

public class BlogPostDTO {
    private Long postId;
    private String title;
    private String slug;
    private String thumbnailUrl;
    private String content;
    private String type;
    private String authorName;
    private String authorImage;
    private LocalDateTime createdAt;
    private Integer likesCount;
    private Integer commentsCount;

    public BlogPostDTO() {
    }

    public BlogPostDTO(Long postId, String title, String slug, String thumbnailUrl, String content,
                        String type, String authorName, String authorImage,
                        LocalDateTime createdAt, Integer likesCount, Integer commentsCount) {
        this.postId = postId;
        this.title = title;
        this.slug = slug;
        this.thumbnailUrl = thumbnailUrl;
        this.content = content;
        this.type = type;
        this.authorName = authorName;
        this.authorImage = authorImage;
        this.createdAt = createdAt;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
    }

    // Getters & Setters

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }
}
