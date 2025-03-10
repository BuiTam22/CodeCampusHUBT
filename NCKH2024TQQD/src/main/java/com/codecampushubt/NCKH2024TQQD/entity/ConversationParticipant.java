package com.codecampushubt.NCKH2024TQQD.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ConversationParticipants")
public class ConversationParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ParticipantID")
    private Long participantId;

//    @ManyToOne
//    @JoinColumn(name = "ConversationID")
//    private Conversation conversation;
//
//    @ManyToOne
//    @JoinColumn(name = "UserID")
//    private User user;

    @Column(name = "JoinedAt")
    private LocalDateTime joinedAt = LocalDateTime.now();

    @Column(name = "LeftAt")
    private LocalDateTime leftAt;

    @Column(name = "Role", length = 20)
    private String role = "member";

    @Column(name = "LastReadMessageID")
    private Long lastReadMessageId;

    @Column(name = "IsAdmin")
    private boolean isAdmin = false;

    @Column(name = "IsMuted")
    private boolean isMuted = false;

//    public ConversationParticipant() {
//    }
//
//    public ConversationParticipant(Conversation conversation, User user) {
//        this.conversation = conversation;
//        this.user = user;
//    }
//
//    // equals, hashCode, and toString methods
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ConversationParticipant that = (ConversationParticipant) o;
//        return participantId.equals(that.participantId);
//    }
//
//    @Override
//    public int hashCode() {
//        return java.util.Objects.hash(participantId);
//    }
//
//    @Override
//    public String toString() {
//        return "ConversationParticipant{" +
//                "participantId=" + participantId +
//                ", conversation=" + conversation +
//                ", user=" + user +
//                ", joinedAt=" + joinedAt +
//                ", leftAt=" + leftAt +
//                ", role='" + role + '\'' +
//                ", lastReadMessageId=" + lastReadMessageId +
//                ", isAdmin=" + isAdmin +
//                ",
}