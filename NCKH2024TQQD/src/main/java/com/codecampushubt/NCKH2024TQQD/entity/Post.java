package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "Posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PostID")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "Title", length = 255)
    private String title;

    @Column(name = "Slug", length = 255, unique = true)
    private String slug;

    @Column(name = "ThumbnailUrl", length = 500)
    private String thumbnailUrl;

    @Column(name = "Content", columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Column(name = "Type", length = 20)
    private String type = "regular";

    @Column(name = "Visibility", length = 20)
    private String visibility = "public";

    @Column(name = "Location", length = 255)
    private String location;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "DeletedAt")
    private LocalDateTime deletedAt;

    @Column(name = "LikesCount")
    private Integer likesCount = 0;

    @Column(name = "CommentsCount")
    private Integer commentsCount = 0;

    @Column(name = "SharesCount")
    private Integer sharesCount = 0;

    @Column(name = "ReportsCount")
    private Integer reportsCount = 0;

    // Constructors...

    public Post() {
    }

    public Post(User user, String title, String slug, String thumbnailUrl, String content, String type, String visibility, String location, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, Integer likesCount, Integer commentsCount, Integer sharesCount, Integer reportsCount) {
        this.user = user;
        this.title = title;
        this.slug = slug;
        this.thumbnailUrl = thumbnailUrl;
        this.content = content;
        this.type = type;
        this.visibility = visibility;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.sharesCount = sharesCount;
        this.reportsCount = reportsCount;
    }

    // Getters & Setters

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
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

    public Integer getSharesCount() {
        return sharesCount;
    }

    public void setSharesCount(Integer sharesCount) {
        this.sharesCount = sharesCount;
    }

    public Integer getReportsCount() {
        return reportsCount;
    }

    public void setReportsCount(Integer reportsCount) {
        this.reportsCount = reportsCount;
    }
    // You can add equals, hashCode, and toString methods as needed
}
