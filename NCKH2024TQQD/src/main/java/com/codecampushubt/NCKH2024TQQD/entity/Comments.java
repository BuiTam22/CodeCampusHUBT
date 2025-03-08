package com.codecampushubt.NCKH2024TQQD.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CommentID;
//    post ID
//    user ID
//    ParentCommentID id bình luận cha
    private String Content;
    private String LikesCount;
    private String RepliesCount;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Date DeletedAt;
//    IsEdited BIT DEFAULT 0, -- Đánh dấu đã chỉnh sửa
//    IsDeleted BIT DEFAULT 0 -- Đánh dấu đã xóa

    public int getCommentID() {
        return CommentID;
    }

    public void setCommentID(int commentID) {
        CommentID = commentID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getLikesCount() {
        return LikesCount;
    }

    public void setLikesCount(String likesCount) {
        LikesCount = likesCount;
    }

    public String getRepliesCount() {
        return RepliesCount;
    }

    public void setRepliesCount(String repliesCount) {
        RepliesCount = repliesCount;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return DeletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        DeletedAt = deletedAt;
    }
}
