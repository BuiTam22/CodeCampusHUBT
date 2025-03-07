package com.codecampushubt.NCKH2024TQQD.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.web.servlet.tags.EscapeBodyTag;

import java.util.Date;

@Entity
public class PostTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postID;
    private Date CreateAt;

    public long getPostID() {
        return postID;
    }

    public void setPostID(long postID) {
        this.postID = postID;
    }

    public Date getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(Date createAt) {
        CreateAt = createAt;
    }
}
