package com.codecampushubt.NCKH2024TQQD.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConversationID")
    private Long conversationId;

    @Column(name = "Type", length = 20)
    private String type = "private";

    @Column(name = "Title", length = 255)
    private String title;

//    @ManyToOne
//    @JoinColumn(name = "CreatedBy")
//    private User createdBy;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "LastMessageAt")
    private LocalDateTime lastMessageAt;

    @Column(name = "IsActive")
    private boolean isActive = true;

//    public Conversation() {
//    }
//
//    public Conversation(User createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    // equals, hashCode, and toString methods
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Conversation that = (Conversation) o;
//        return conversationId.equals(that.conversationId);
//    }
//
//    @Override
//    public int hashCode() {
//        return java.util.Objects.hash(conversationId);
//    }
//
//    @Override
//    public String toString() {
//        return "Conversation{" +
//                "conversationId=" + conversationId +
//                ", type='" + type + '\'' +
//                ", title='" + title + '\'' +
//                ", createdBy=" + createdBy +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
//                ", lastMessageAt=" + lastMessageAt +
//                ", isActive=" + isActive +
//                '}';
//    }
}