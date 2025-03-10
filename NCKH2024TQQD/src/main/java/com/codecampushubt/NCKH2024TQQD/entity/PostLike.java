package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;

//import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PostLikes")
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LikeID")
    private Long likeId;

//    @ManyToOne
//    @JoinColumn(name = "PostID")
//    private Post post;

//    @ManyToOne
//    @JoinColumn(name = "UserID")
//    private User user;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();

//    public PostLike() {
//    }
//
//    public PostLike(Post post, User user) {
//        this.post = post;
//        this.user = user;
//    }
//
//    // equals, hashCode, and toString methods
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        PostLike postLike = (PostLike) o;
//        return likeId.equals(postLike.likeId);
//    }
//
//    @Override
//    public int hashCode() {
//        return java.util.Objects.hash(likeId);
//    }
//
//    @Override
//    public String toString() {
//        return "PostLike{" +
//                "likeId=" + likeId +
//                ", post=" + post +
//                ", user=" + user +
//                ", createdAt=" + createdAt +
//                '}';
//    }
}
