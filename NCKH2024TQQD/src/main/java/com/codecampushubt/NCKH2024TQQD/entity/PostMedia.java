package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PostMedia")
public class PostMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MediaID")
    private Long mediaId;

//    @ManyToOne
//    @JoinColumn(name = "PostID")
//    private Post post;

    @Column(name = "MediaUrl", length = 255, nullable = false)
    private String mediaUrl;

    @Column(name = "MediaType", length = 20)
    private String mediaType;

    @Column(name = "ThumbnailUrl", length = 255)
    private String thumbnailUrl;

    @Column(name = "Size")
    private Integer size;

    @Column(name = "Width")
    private Integer width;

    @Column(name = "Height")
    private Integer height;

    @Column(name = "Duration")
    private Integer duration;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    public PostMedia() {
    }


}
