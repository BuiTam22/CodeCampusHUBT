package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "StoryViews", uniqueConstraints = @UniqueConstraint(columnNames = {"storyID", "viewerID"}))
public class StoryView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long viewID;

//    @ManyToOne
//    @JoinColumn(name = "storyID", nullable = false)
//    private Story story;

//    @ManyToOne
//    @JoinColumn(name = "viewerID", nullable = false)
//    private User viewer;

    private LocalDateTime viewedAt = LocalDateTime.now();


}
