package com.codecampushubt.NCKH2024TQQD.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StoryView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ViewID")
    private Long viewId;

////    @ManyToOne
////    @JoinColumn(name = "StoryID", nullable = false)
//    private Story story;
//
////    @ManyToOne
////    @JoinColumn(name = "ViewerID", nullable = false)
//    private User viewer;

//    @Column(name = "ViewedAt", nullable = false)
//    private LocalDateTime viewedAt = LocalDateTime.now();


}
