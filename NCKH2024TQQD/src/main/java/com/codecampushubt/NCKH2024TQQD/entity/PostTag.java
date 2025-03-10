package com.codecampushubt.NCKH2024TQQD.entity;


import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "PostTags")
//@IdClass(PostTags.PostTagsId.class)
public class PostTag {

    @Id
    @ManyToOne
    @JoinColumn(name = "PostID")
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "TagID")
    private Tag tag;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();


}