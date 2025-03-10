package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "Posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "PostID")
//    private Long postId;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user; // Thay "User" bằng tên entity của bảng Users

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



    // You can add equals, hashCode, and toString methods as needed
}
