package com.codecampushubt.NCKH2024TQQD.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserRanking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "HistoryID") // ID tự tăng của lịch sử
    private Long historyId;

//    @ManyToOne
//    @JoinColumn(name = "UserID", nullable = false) // Liên kết với người dùng
//    private User user;
//
//    @Column(name = "Type", length = 20, nullable = false) // Loại hoạt động (EVENT hoặc COURSE)
//    private String type;
//
//    @Column(name = "RelatedID") // ID của sự kiện hoặc khóa học liên quan
//    private Long relatedId;
//
//    @Column(name = "PointsEarned", nullable = false) // Số điểm đạt được
//    private int pointsEarned;
//
//    @Column(name = "Reason", length = 255) // Lý do được cộng điểm
//    private String reason;
//
//    @Column(name = "CreatedAt", nullable = false) // Thời điểm tạo bản ghi
//    private LocalDateTime createdAt = LocalDateTime.now();

}
