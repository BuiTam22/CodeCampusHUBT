package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "UserProfiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileID;

//    @OneToOne
//    @JoinColumn(name = "userID", unique = true, nullable = false)
//    private User user;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String education;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String workExperience;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String skills;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String interests;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String socialLinks; // JSON

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String achievements; // JSON

    @Column(length = 10, nullable = false)
    private String preferredLanguage = "vi";

    @Column(length = 50, nullable = false)
    private String timeZone = "Asia/Ho_Chi_Minh";

    private String notificationPreferences; // JSON

    private LocalDateTime updatedAt = LocalDateTime.now();
}

