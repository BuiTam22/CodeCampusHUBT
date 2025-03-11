package com.codecampushubt.NCKH2024TQQD.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CourseID")
    private Long courseID;

    @Column(name = "Title", nullable = false, length = 255)
    private String title;

    @Column(name = "Slug", unique = true, length = 255)
    private String slug;

    @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "ShortDescription", length = 500)
    private String shortDescription;

    @Column(name = "InstructorID")
    private Long instructorId; // Không dùng @ManyToOne để tránh khóa ngoại

    @Column(name = "Level", length = 20)
    private String level;

    @Column(name = "Category", length = 50)
    private String category;

    @Column(name = "SubCategory", length = 50)
    private String subCategory;

    @Column(name = "Language", length = 20, nullable = false)
    private String language = "vi"; // Giá trị mặc định

    @Column(name = "Duration")
    private Integer duration;

    @Column(name = "Capacity")
    private Integer capacity;

    @Column(name = "EnrolledCount", nullable = false)
    private Integer enrolledCount = 0;

    @Column(name = "Rating", precision = 3, scale = 2, nullable = false)
    private BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "RatingCount", nullable = false)
    private Integer ratingCount = 0;

    @Column(name = "Price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "DiscountPrice", precision = 10, scale = 2)
    private BigDecimal discountPrice;

    @Column(name = "ImageUrl", length = 255)
    private String imageUrl;

    @Column(name = "VideoUrl", length = 255)
    private String videoUrl;

    @Column(name = "Requirements", columnDefinition = "NVARCHAR(MAX)")
    private String requirements;

    @Column(name = "Objectives", columnDefinition = "NVARCHAR(MAX)")
    private String objectives;

    @Column(name = "Syllabus", columnDefinition = "NVARCHAR(MAX)")
    private String syllabus;

    @Column(name = "Status", length = 20, nullable = false)
    private String status = "draft";

    @Column(name = "IsPublished", nullable = false)
    private Boolean isPublished = false;

    @Column(name = "PublishedAt")
    private LocalDateTime publishedAt;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UpdatedAt", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "DeletedAt")
    private LocalDateTime deletedAt;


    //Constructor
    public Course() {
    }

    public Course(String title, String slug, String description, String shortDescription, Long instructorId, String level, String category, String subCategory, String language, Integer duration, Integer capacity, Integer enrolledCount, BigDecimal rating, Integer ratingCount, BigDecimal price, BigDecimal discountPrice, String imageUrl, String videoUrl, String requirements, String objectives, String syllabus, String status, Boolean isPublished, LocalDateTime publishedAt, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.shortDescription = shortDescription;
        this.instructorId = instructorId;
        this.level = level;
        this.category = category;
        this.subCategory = subCategory;
        this.language = language;
        this.duration = duration;
        this.capacity = capacity;
        this.enrolledCount = enrolledCount;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.price = price;
        this.discountPrice = discountPrice;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.requirements = requirements;
        this.objectives = objectives;
        this.syllabus = syllabus;
        this.status = status;
        this.isPublished = isPublished;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    //getter and setter


    public Long getId() {
        return courseID;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public String getLevel() {
        return level;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getEnrolledCount() {
        return enrolledCount;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getObjectives() {
        return objectives;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setEnrolledCount(Integer enrolledCount) {
        this.enrolledCount = enrolledCount;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}

