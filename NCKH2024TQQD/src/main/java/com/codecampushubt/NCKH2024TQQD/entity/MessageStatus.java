package com.codecampushubt.NCKH2024TQQD.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MessageStatus")
public class MessageStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusID")
    private Long statusId;

//    @ManyToOne
//    @JoinColumn(name = "MessageID")
//    private Message message;
//
//    @ManyToOne
//    @JoinColumn(name = "UserID")
//    private User user;

    @Column(name = "Status", length = 20)
    private String status = "sent";

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

//    public MessageStatus() {
//    }
//
//    public MessageStatus(Message message, User user, String status) {
//        this.message = message;
//        this.user = user;
//        this.status = status;
//    }
//
//    // equals, hashCode, and toString methods
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        MessageStatus that = (MessageStatus) o;
//        return statusId.equals(that.statusId);
//    }
//
//    @Override
//    public int hashCode() {
//        return java.util.Objects.hash(statusId);
//    }
//
//    @Override
//    public String toString() {
//        return "MessageStatus{" +
//                "statusId=" + statusId +
//                ", message=" + message +
//                ", user=" + user +
//                ", status='" + status + '\'' +
//                ", updatedAt=" + updatedAt +
//                '}';
//    }
}