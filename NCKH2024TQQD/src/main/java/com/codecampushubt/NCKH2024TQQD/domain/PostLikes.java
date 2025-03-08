package com.codecampushubt.NCKH2024TQQD.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    ID tự tăng của lượt thích
    private long LikeId;
//    posst id  liên kết với bài được thích
//    userid Người dùng thực hiện việc thích
    private Date CreateAt;

    public long getLikeId() {
        return LikeId;
    }

    public void setLikeId(long likeId) {
        LikeId = likeId;
    }

    public Date getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(Date createAt) {
        CreateAt = createAt;
    }
}
