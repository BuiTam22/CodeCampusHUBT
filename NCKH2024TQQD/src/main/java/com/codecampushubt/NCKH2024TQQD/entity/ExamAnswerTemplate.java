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
@Table(name = "ExamAnswerTemplates")
public class ExamAnswerTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateID;

    @Column(name = "ExamID", nullable = false)
    private Long examID;

    @Column(name = "Content", columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Column(name = "Keywords", columnDefinition = "NVARCHAR(MAX)")
    private String keywords;

    @Column(name = "MinimumMatchPercentage", precision = 5, scale = 2)
    private BigDecimal minimumMatchPercentage;

    @Column(name = "CreatedBy", nullable = false)
    private Long createdBy;

    @Column(name = "CreatedAt", updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UpdatedAt", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructor

    public ExamAnswerTemplate() {
    }

    public ExamAnswerTemplate(Long examID, String content, String keywords, BigDecimal minimumMatchPercentage, Long createdBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.examID = examID;
        this.content = content;
        this.keywords = keywords;
        this.minimumMatchPercentage = minimumMatchPercentage;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

//    getter & setter

    public Long getTemplateID() {
        return templateID;
    }

    public Long getExamID() {
        return examID;
    }

    public void setExamID(Long examID) {
        this.examID = examID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public BigDecimal getMinimumMatchPercentage() {
        return minimumMatchPercentage;
    }

    public void setMinimumMatchPercentage(BigDecimal minimumMatchPercentage) {
        this.minimumMatchPercentage = minimumMatchPercentage;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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

    @Override
    public String toString() {
        return "ExamAnswerTemplate{" +
                "templateID=" + templateID +
                ", examID=" + examID +
                ", content='" + content + '\'' +
                ", keywords='" + keywords + '\'' +
                ", minimumMatchPercentage=" + minimumMatchPercentage +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
